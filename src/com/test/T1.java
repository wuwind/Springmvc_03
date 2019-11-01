package com.test;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import java.util.ArrayList;
import java.util.List;

public class T1 {
    public static void main(String args[]){
        List<String> a = new ArrayList<>();
        a.add("a3");
        a.add("a4");
        a.add("a5");
        a.add("a6");

        a.add(0,"a1");
        a.add(1,"a2");

        for (String s : a) {
            System.out.println(s);
        }
    }
}
