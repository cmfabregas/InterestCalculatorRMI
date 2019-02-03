import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteCalculator extends Remote
{

    public int getMonthlyPayment(double annualInterestRate, double loanAmount, int numYears) throws RemoteException;
    public int getTotalPayment(int totalMonthly, int numYears) throws RemoteException; 
    public void writeFile(double interestRate, double loanAmount, int numYears, int totalMonthly, int totalPayment) throws RemoteException;
    public byte[] downloadFile(String fileName) throws RemoteException;

    public void calcAverage() throws RemoteException;
    

}