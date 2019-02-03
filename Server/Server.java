import java.io.*; 
import java.rmi.*;

public class Server 
{
	
	public static void main(String argv[]) 
	{
		if(System.getSecurityManager() == null)
		{
			System.setSecurityManager(new RMISecurityManager());
		}
		try
		{
			//creates new object and starts server
			RemoteCalculator cal = new InterestCalc("RemoteCalculator");
			Naming.rebind("//127.0.0.1/RemoteCalculator", cal);
			
			
		}catch(Exception e) 
		{
			System.out.println("FileServer: "+e.getMessage());
         	e.printStackTrace();
		}
	}

}
