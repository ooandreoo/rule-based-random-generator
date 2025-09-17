package com.rbrg;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ArrayList<String> values = new ArrayList<>();
        values.add("1");
        values.add("2");
        values.add("3");
        values.add("4");
        values.add("5");
        ApproachOne randomGenerator = new ApproachOne();
        randomGenerator.initialize(values,2,4);

        randomGenerator.getRandomValue();
        randomGenerator.getRandomValue();
        randomGenerator.getRandomValue();
        randomGenerator.getRandomValue();
        randomGenerator.getRandomValue();
        randomGenerator.getRandomValue();
        randomGenerator.getRandomValue();
        randomGenerator.getRandomValue();
    }
}
