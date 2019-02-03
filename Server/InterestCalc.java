/*
*/

import java.rmi.server.*;
import java.io.*;
import java.rmi.*;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Arrays;
import java.time.format.DateTimeFormatter;


public class InterestCalc extends UnicastRemoteObject implements RemoteCalculator
{

    //calculation variables
    /*private static double annualInterestRate, loanAmount, monthlyInterestRate ;
    private static int numYears , totalMonthly,totalPayment;*/
    private String name;

   
    public InterestCalc(String name) throws RemoteException 
    {
    	super();
    	this.name = name;
    }
    
    //sets monthly payment in static variable
    public int getMonthlyPayment(double annualInterestRate, double loanAmount, int numYears)
    {
        double monthlyInterestRate = annualInterestRate / 1200;
        double totalMonthly = loanAmount * monthlyInterestRate / (1 - (1 / Math.pow(1 + monthlyInterestRate, numYears * 12)));
        return (int) totalMonthly;
    }

    //gets total payment 
    public int getTotalPayment(int totalMonthly, int numYears){
            int totalPayment;
        return totalPayment = totalMonthly * numYears * 12;
    }
    
    //writes answers to file
    public void writeFile(double interestRate, double loanAmount, int numYears, int totalMonthly, int totalPayment){

        try{
            FileWriter file = new FileWriter("records.txt",true);
            BufferedWriter buffer = new BufferedWriter(file);
            PrintWriter printwriter = new PrintWriter(buffer);
            DateTimeFormatter datetime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

            LocalDateTime time = LocalDateTime.now();
            
            //prints to file
            printwriter.println("@" + datetime.format(time)+ 
            " Annual Interest Rate: "+interestRate+
            "% Number of Years: $"+numYears+
            " Loan Amount: $"+loanAmount+
            " Monthly Payment: $"+totalMonthly+ 
            " Total Payment: $"+totalPayment);
        
            printwriter.close();


        }catch(Exception e){
            System.out.println("Could not Write to File");
        }
        

    }

    public void calcAverage(){
        //FileReader fr = new FileReader("./records.txt");
        
       Scanner sc = new Scanner(new BufferedReader(new FileReader("records1.txt")));

       int n = 0;
        while (sc.readLine() != null) 
        {
            n++;
        }
        System.out.println("there are "+n);
       
    }


    //allows for downloading file from server to client
    public byte[] downloadFile(String fileName){
        try {
           File file = new File(fileName);
           byte buffer[] = new byte[(int)file.length()];
           BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName));
           input.read(buffer,0,buffer.length);
           input.close();
           return(buffer);
        } catch(Exception e){
           System.out.println("FileImpl: "+e.getMessage());
           e.printStackTrace();
           return(null);
        }
     }



}