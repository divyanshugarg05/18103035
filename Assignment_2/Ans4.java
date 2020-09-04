package com.company;
import java.util.*;
import java.lang.Math;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        // write your code here

        long ans = 0L;

        for(long i=1L ; i<=Integer.MAX_VALUE ; ++i){
            long lhs = ((i)*(i+1))/2;
            long rhs = i*i;

            if(lhs == rhs){
                ans = i;
            }
        }
        System.out.println("The smallest n such that Σi = i2 where 1 ≤ i ≤ n is too large to be represented as an int is: " + ans);
    }
}