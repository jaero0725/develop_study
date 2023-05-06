package com.java.zeroco;

import java.util.ArrayList;
import java.util.Collections;

public class Ex11_1 {

    public static void main(String[] args) {
        ArrayList <Integer> list1 = new ArrayList<>(10);
        list1.add(5);
        list1.add(4);
        list1.add(3);
        list1.add(2);
        list1.add(3);
        list1.add(2);

        ArrayList <Integer> list2 = new ArrayList<>(list1.subList(0,4));
        System.out.println(list1 + " ," + list2);

        Collections.sort(list1);
        Collections.sort(list2);
    }
}
