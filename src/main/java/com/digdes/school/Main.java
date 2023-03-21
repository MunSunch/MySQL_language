package com.digdes.school;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{
    public static void main( String[] args )
    {
        var p = Pattern.compile("\\s");
        Matcher m = p.matcher("SELECT *");
        if(m.find(6)) {
            System.out.println(m.start());
        }
    }
}
