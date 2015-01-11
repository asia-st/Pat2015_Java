package com.patronat;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.patronat.stats.Stat;
import com.patronat.stats.StatAverage;
import com.patronat.stats.StatSum;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.*;


@Path("/numbers")
@Produces(MediaType.APPLICATION_JSON)
public class Resource {
    private final String template;
    private final List<Stat> stats;
    private final List<Stat> sortedStats;
    StatSum sum;
    StatAverage average;

    public Resource(String template, List<Stat> stats, List<Stat> stats2) {
        this.template = template;
        this.stats = stats;
        this.sortedStats = stats2;
    }

    @GET
    @Timed
    public List<Stat> getStats() {
        return stats;
    }

    @GET
    @Timed
    @Path("/sorted")
    public List<Stat> sortedStat() {
        Collections.sort(sortedStats, new Comparator<Stat>() {
            @Override
            public int compare(Stat st1, Stat st2) {
                return st1.getNumber().compareTo(st2.getNumber());
            }
        });
        return sortedStats;
    }

    @GET
    @Timed
    @Path("/sum")
    public StatSum sum() {
        BigDecimal sumBD = BigDecimal.ZERO;
        for (int i = 0; i < stats.size(); i++) {
            sumBD = sumBD.add(stats.get(i).getNumber());
        }
        sum = new StatSum(sumBD);
        return sum;
    }

    @GET
    @Timed
    @Path("/average")
    public StatAverage average() {
        BigDecimal sumDB = sum().getSum();
        BigDecimal size = new BigDecimal(stats.size());
        BigDecimal av = sumDB.divide(size,3);
        average = new StatAverage(av);
        return average;
    }

    @GET
    @Timed
    @Path("/add")
    public List<Stat> add(@QueryParam("number") Optional<String> str) {
        if (str.isPresent()) {
            Locale loc = new Locale("en", "US");
            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(loc);
            df.setParseBigDecimal(true);
            BigDecimal number = (BigDecimal) df.parse(str.get(), new ParsePosition(0));
            if (number != null) {
                stats.add(new Stat(number));
                sortedStats.add(new Stat(number));

                BufferedWriter writer;
                try {
                    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("dane.txt", true)));
                    writer.newLine();
                    writer.write(str.get());
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stats;
    }
}
