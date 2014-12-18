package com.patronat;

public class App 
{
    public static void main( String[] args )
    {
        if (args == null || args.length == 0) {
            throw new UnsupportedOperationException("No args!");
        }

        if (args.length > 1) {
            throw new UnsupportedOperationException("Too many args!");
        }

        StringBuffer output = new StringBuffer(args[0]);
        output.reverse();
        System.out.println(output);

    }
}
