package com.patronat;


import com.google.common.primitives.Doubles;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;


public class App
{
    public ArrayList<Double> readFromFile() {
        File file = new File("dane.txt");
        Scanner scanner;
        ArrayList<Double> dataList = new ArrayList<Double>();
        try {
            scanner = new Scanner(file);
            scanner.useLocale(Locale.US);
            while(scanner.hasNextDouble())
            {
                dataList.add(scanner.nextDouble());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public void sort(double [] array) {
        //Arrays.sort(array);
        Arrays.parallelSort(array);
    }

    public double sum(double[] array) {
        //return Arrays.stream(array).sum();
        return Arrays.stream(array).parallel().sum();
    }

    public double average(double sum, double n) {
        return sum / n;
    }

    public void write(double [] Array, double sum, double av) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat)nf;

        for (double db : Array) {
            System.out.println(db);
        }
        System.out.println("Suma = " + df.format(sum));
        System.out.println("Srednia arytmetyczna = " + df.format(av));
    }

    public static void main( String[] args )
    {
        final App app = new App();

        ArrayList<Double> dataList;
        double sum;
        double average;
        double size;

        dataList = app.readFromFile();
        if (dataList.isEmpty()) {
            throw new NullPointerException("File is empty!");
        }
        size = dataList.size();
        double[] dataArray = Doubles.toArray(dataList);

        app.sort(dataArray);
        //sum = app.sum(dataArray);
        sum = Arrays.stream(dataArray).parallel().sum();
        average = app.average(sum, size);

        app.write(dataArray, sum, average);
    }
}
