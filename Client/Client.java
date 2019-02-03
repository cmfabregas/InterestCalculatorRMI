import java.io.*;
import java.rmi.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextPane;


public class Client {

   //global
    private static RemoteCalculator r;

    //window variables
    private JFrame frame;
    private final Action action = new SwingAction();
	private JTextField textField;
	private JTextField textField_1;
    
    public Client(){
        initialize();
    }
    public static void main(String argv[]) {
      
        //window frame event
        EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
     }
     
     
     private void initialize() {
		
		//draws the JFrame
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//textfield to input the annual interest rate
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			//sets constraints on what keys can be typed
			public void keyTyped(KeyEvent e) {
				char c= e.getKeyChar();
				if(!Character.isDigit(c) && c !=8 && c !=46) {
					e.consume();
				}
			}
		});
		//draws the textfield
		textField.setBounds(159, 70, 130, 30);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		//textfield for the number of years
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c= e.getKeyChar();
				if(!Character.isDigit(c) && c !=8 && c !=46) {
					e.consume();
				}
			}
		});
		//draws the textfield for the number of years
		textField_1.setBounds(159, 150, 130, 30);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		//label for annual interestrate
		JLabel lblAnnualInterestRate = new JLabel("Annual Interest Rate:");
		lblAnnualInterestRate.setBounds(20, 70, 136, 16);
		frame.getContentPane().add(lblAnnualInterestRate);
		
		JLabel lblLoanAmount = new JLabel("Loan Amount:");
		lblLoanAmount.setBounds(20, 150, 88, 16);
		frame.getContentPane().add(lblLoanAmount);
		
		//creates a dropdown menu with 40 items
		JComboBox<Integer> comboBox = new JComboBox<Integer>();
		for(int i=1; i <=40; i++) {
			comboBox.addItem(i);
		}
		comboBox.setBounds(159, 110, 130, 30);
		frame.getContentPane().add(comboBox);
		
		//label for number of years
		JLabel lblNumberOfYears = new JLabel("Number of Years");
		lblNumberOfYears.setBounds(20, 110, 126, 16);
		frame.getContentPane().add(lblNumberOfYears);
		
		//label for monthly payment
		JLabel lblMonthlyPayment = new JLabel("Monthly Payment: ");
		lblMonthlyPayment.setBounds(20, 214, 157, 16);
		frame.getContentPane().add(lblMonthlyPayment);
		
		//label for total payment
		JLabel lblTotalPayment = new JLabel("Total Payment: ");
		lblTotalPayment.setBounds(20, 241, 117, 16);
		frame.getContentPane().add(lblTotalPayment);
		
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(159, 214, 61, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(159, 242, 61, 16);
		frame.getContentPane().add(lblNewLabel_3);
        
        //button that initializes results
		JButton btnNewButton = new JButton("Result");
		btnNewButton.setAction(action);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                
                
				int numYears = Integer.parseInt(comboBox.getSelectedItem().toString());
				double interestRate = Double.parseDouble(textField.getText());
				double loanAmount = Double.parseDouble(textField_1.getText());
                
                //starts connection with the server
                try {
                    String name = "//127.0.0.1/RemoteCalculator";
                    
                    try {
         
                     //double annualInterestRate, double loanAmount, int numYears
                     r = (RemoteCalculator) Naming.lookup(name);
                    
                     //gets monthly payment and total payment from server
                     int totalMonthly = r.getMonthlyPayment(interestRate,loanAmount,numYears);
                     int totalPayment = r.getTotalPayment(totalMonthly, 10);
                     
                     //sets results on user interface
                     lblNewLabel_2.setText(Integer.toString(totalMonthly));
                     lblNewLabel_3.setText(Integer.toString(totalPayment));
                     System.out.println("total monthly : "+ totalMonthly + "total payment: "+totalPayment);
                    
            
                     //save to file paremeters (double interestRate, double loanAmount, int numYears, int totalMonthly, int totalPayment)
                     r.writeFile(interestRate, loanAmount, numYears, totalMonthly, totalPayment);


                    //downloads file back to client
                    byte[] filedata = r.downloadFile("records.txt");
                    File file = new File("records.txt");
                    BufferedOutputStream output = new
                    BufferedOutputStream(new FileOutputStream(file.getName()));
                    output.write(filedata,0,filedata.length);
                    output.flush();
                    output.close();

                   } catch(Exception f) {
                     System.err.println("Remote exception: "+f.getMessage());
                     f.printStackTrace();
                   }
                    
                 } catch(Exception f) {
                    System.err.println("FileServer exception: "+ f.getMessage());
                    f.printStackTrace();
                 }
              
				//sets the text in the label panel
				

			}
			
		});
		btnNewButton.setBounds(309, 228, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Result");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
