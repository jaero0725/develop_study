package com.java.zeroco;

public class LamdaTest {
    public int max(int a, int b){
        return a < b ? a : b;
    }

    public void printVar(String name, int i){
        System.out.println(name+"="+i);
    }

    public static void main(String[] args) {
        //(int a, int b) -> {return a > b ? a : b};
        //(int a, int b) -> {a > b ? a : b};

        //(String name, int i) -> {System.out.println(name+"="+i) };

        /*
            람다식은 익명 클래스의 객체와 동등함
         */


    }
}
