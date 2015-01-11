package com.patronat;

import com.patronat.stats.Stat;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;



public class App extends Application<Configurator>
{
    static List<Stat> stats = new ArrayList<Stat>();
    static List<Stat> stats2 = new ArrayList<Stat>();

    public static void main(String[] args) throws Exception {
        getData();
        if (stats.isEmpty()){
            throw new NullPointerException("File is empty!");
        }
        new App().run(args);
    }

    @Override
    public String getName() {
        return "app";
    }

    @Override
    public void initialize(final Bootstrap<Configurator> bootstrap) {

    }

    public void run(final Configurator configurator, final Environment environment) {
        final Resource resource = new Resource(configurator.getTemplate(), stats, stats2);
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configurator.getTemplate());
        environment.healthChecks().register(configurator.getTemplate(), healthCheck);
        environment.jersey().register(resource);
    }

    private static void getData() {
        File file = new File("dane.txt");
        Scanner scanner;
        BigDecimal next;
        try {
            scanner = new Scanner(file);
            scanner.useLocale(Locale.US);
            while(scanner.hasNextBigDecimal())
            {
                next = scanner.nextBigDecimal();
                stats.add(new Stat(next));
                stats2.add(new Stat(next));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
