package com.company;
import java.util.*;
import java.io.*;

class NewThread extends Thread
{
    public void run()
    {
        System.out.println("<<<Child Thread Created>>>");
        for(int i=1;i<=100;i++)
        {
            try
            {
                Thread.sleep(1000);
            } catch(Exception e){}

            System.out.println("Number: " + i);

            if(i%10 == 0)
            {
                System.out.println(i + " numbers counted!!");
            }
        }
        System.out.println("<<<Child Thread Terminated>>>");
    }
}

public class Main {

    public static void main(String[] args){

        System.out.println("<<<Inside Main Thread>>>");

        NewThread count = new NewThread();
        
        count.start();

        System.out.println("<<<Main Thread Terminated>>>");
    }

}