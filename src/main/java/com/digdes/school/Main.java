package com.digdes.school;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

public class Main
{
    public static void main( String[] args ) throws Exception
    {
        List<Map<String, Object>> table = new ArrayList<>();
        JavaSchoolStarter.execute(table, "INSERT VALUES id=3, lastName = 'Федоров', age=40, active=true");
        JavaSchoolStarter.execute(table, "INSERT VALUES id=4, lastName = 'Петров', age=30, active=true");

        System.out.println("\t\tSelect * :");
        var res = JavaSchoolStarter.execute(table, "SELECT *");
        print(res);

        System.out.println("\t\tSELECT * where age<=30");
        res = JavaSchoolStarter.execute(table, "SELECT * where age<=30");
        print(res);

        System.out.println("Update values lastName='Иванов', age=33, active=false where id=4 :");
        res = JavaSchoolStarter.execute(table, "Update values lastName='Иванов', age=33, active=false where id=4");
        print(res);

        System.out.println("delete");
        res = JavaSchoolStarter.execute(table, "delete");
        print(res);
    }

    private static void print(List<Map<String, Object>> table) {
        table.stream().forEach(x -> System.out.println("\t" + x));
        System.out.println();
    }
}