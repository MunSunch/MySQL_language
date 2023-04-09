package com.digdes.school;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

public class Main
{
    public static void main( String[] args ) throws Exception
    {
        List<Map<String, Object>> table = new ArrayList<>();
        JavaSchoolStarter.execute(table, "INSERT VALUES name='Munir', age=24");
        JavaSchoolStarter.execute(table, "INSERT VALUES name='Andrey', age=26");
        JavaSchoolStarter.execute(table, "INSERT VALUES name='Tolya', age=17");
        JavaSchoolStarter.execute(table, "INSERT VALUES name='Kolya', age=16");
        var res
                = JavaSchoolStarter.execute(table, "SELECT name WHERE age<20 OR age=16");
        res.stream().forEach(System.out::println);
    }
}