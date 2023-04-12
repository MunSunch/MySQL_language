package com.digdes.school;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

public class Main
{
    public static void main( String[] args ) throws Exception
    {
        JavaSchoolStarter starter = new JavaSchoolStarter();
        List<Map<String, Object>> table = new ArrayList<>();
        starter.execute(table, "INSERT VALUES id=3, lastname = 'Федоров', age=40, active=true, average=11.2");
        starter.execute(table, "INSERT VALUES id=4, lastname = 'Петров', age=30, active=true,  average=11.25");

        System.out.println("\t\tSelect * :");
        var res = starter.execute(table, "SELECT * ");
        print(res);

        System.out.println("\t\tSELECT * where age<=30");
        res = starter.execute(table, "SELECT * where age<=30");
        print(res);

        System.out.println("\t\tSELECT * where lastname ilike '%ОВ' and lastname like 'П%'");
        res = starter.execute(table, "SELECT * where lastname ilike '%ОВ' and lastname like 'П%'");
        print(res);

        System.out.println("\t\tSELECT lastname ,age where average>11.18 AND average<11.22");
        res = starter.execute(table, "SELECT lastname ,age where average>11.18 AND average<11.22");
        print(res);

        System.out.println("\t\tUpdate values lastname='Иванов', age=33, active=false where id=4 :");
        res = starter.execute(table, "Update values lastname='Иванов', age=33, active=false where id=4");
        print(res);

        System.out.println("\t\tDelete");
        res = starter.execute(table, "delete");
        print(res);
    }

    private static void print(List<Map<String, Object>> table) {
        table.stream().forEach(System.out::println);
        System.out.println();
    }
}