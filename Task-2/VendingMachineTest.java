package VendingMachine;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import java.io.FileWriter; 

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class VendingMachineTest {
	VendingMachine vm;
	
	private final InputStream systemIn = System.in;
	private final PrintStream systemOut = System.out;
	 
	 private ByteArrayInputStream testIn;
	 private ByteArrayOutputStream testOut;
	 
	String newlineChar = System.getProperty("line.separator");
	 
	@Before
	public void setUp() throws Exception {

			vm = new VendingMachine();
			vm.powerUpVendingMechine();
			vm.setAmountPaid(0.0);
	}
	
	@Before
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }
	
	private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

	private String getOutput() {
		return testOut.toString();
	}
	
	@Test
	public void calculateChangeCancel() {
		// path [1,2,3,5]
		vm.calculateChange(3.00, "CANCEL");
		String reply = getOutput();
		assertTrue(reply.contains("You have pressed CANCEL. Current ammount is 0.0 Euro"));
	}
	@Test
	public void calculateChangeWrongCoin() {
		// path [1,2,3,6,8,10,11,12,14]
		vm.calculateChange(3.00, "PE");
		String reply = getOutput();
		assertTrue(reply.contains("Wrong coin type!You have paid 0.0 Euro"));
	}
	
	@ Test
	public void calculateChangeNoInput() {
		// path [1,2,4]
		vm.calculateChange(3.00, "");
		String reply = getOutput();
		assertTrue(reply.contains("You have paid 0.0 Euro"));
	}
	
	@Test
	public void calculateChangeTCCoin() { 
		// path [1,2,3,6,7,2,4]
		vm.calculateChange(5.00, "TC");
		String reply = getOutput();
		System.out.println(reply);
		assertTrue(reply.contains("You have paid 0.2 Euro"));
	}
	
	@Test
	public void calculateChangeFCCoin() {
		// path [1,2,3,6,8,9,2,4]
		vm.calculateChange(5.00, "FC");
		String reply = getOutput();
		System.out.println(reply);
		assertTrue(reply.contains("You have paid 0.5 Euro"));
	}
	
	@Test
	public void calculateChangeOECoin() {
		// path [1,2,3,6,8,10,11,2,4]
		vm.calculateChange(5.00, "OE");
		String reply = getOutput();
		assertTrue(reply.contains("You have paid 1.0 Euro"));
	}
	
	@Test
	public void calculateChangeTECoin() {
		// path [1,2,3,6,8,10,12,13,2,4]
		vm.calculateChange(5.00, "TE");
		String reply = getOutput();
		System.out.println(reply);
		assertTrue(reply.contains("You have paid 2.0 Euro"));
	}
	
	@Test
	public void calculateChangeAllCoins() {
		// path [1,2,3,6,7,2,3,6,8,9,2,3,6,8,10,11,2,3,6,8,10,12,13,2,3,6,8,10,12,14,2,4]
		vm.calculateChange(5.00, "TC FC OE TE PE");
		String reply = getOutput();
		assertTrue(reply.contains("Wrong coin type!You have paid 3.7 Euro"));
	}
		      

	
// ################# CaptureMoney Starts ################################
	
	@Test
	public void captureMoneyCancel() {
		// path [1,2,3,4,6,8,11,2,3,4,6,7]
		provideInput("OE"+ newlineChar + "CANCEL" + newlineChar);
		Boolean result = vm.captureMoney("COLA", 3.00);
		String reply = getOutput();
		System.out.println(reply);      
		assertTrue(reply.contains("You have paid 1.0 Euro\n" + 
				"Type CANCEL to return your coins\n" + 
				"Enter more coin(s) to get your drink.\n" + 
				"You have pressed CANCEL. Current ammount is 1.0 Euro\n" + 
				"Returning your sum 1.0 EURO\n" + 
				"1 1 Euro \n" + 
				"Your Change is \n" + 
				"  0 x 2Euro\n" + 
				"  1 x 1Euro\n" + 
				"  0 x 50Cent\n" + 
				"  0 x 20Cent\n" + 
				"Thank you for your business, you see again! "));
		assertTrue(result==false);
	}
	
	@Test
	public void captureMoneyNoChange() {
		// path [1,2,3,4,6,8,9,10]
		provideInput("OE"+ newlineChar + "TE" + newlineChar);
		Boolean result = vm.captureMoney("COLA", 3.00);
		String reply = getOutput();
		assertTrue(reply.contains("You have paid 1.0 Euro\n" + 
				"Type CANCEL to return your coins\n" + 
				"Enter more coin(s) to get your drink.\n" + 
				"You have paid 3.0 Euro\n" + 
				"DRINK DELIVERED! \n" + 
				"\n" + 
				"\n" + 
				"Thank you for your business, you see again!"));
		assertTrue(result == true);
		}
	
	@Test
	public void captureMoneyWithChange() {
		// path [1,2,3,4,6,8,9,12]
		provideInput("TE" + newlineChar + "TE" + newlineChar);
		Boolean result =  vm.captureMoney("COLA", 3.00);
		String reply = getOutput();
		assertTrue(reply.contains("You have paid 2.0 Euro\n" + 
				"Type CANCEL to return your coins\n" + 
				"Enter more coin(s) to get your drink.\n" + 
				"You have paid 4.0 Euro\n" + 
				"Your change is: 1.0 EURO\n" + 
				"1 1 Euro \n" + 
				"Your Change is \n" + 
				"  0 x 2Euro\n" + 
				"  1 x 1Euro\n" + 
				"  0 x 50Cent\n" + 
				"  0 x 20Cent\n" + 
				"DRINK DELIVERED! \n" + 
				"\n" + 
				"\n" + 
				"Thank you for your business, you see again!"));
		assertTrue(result == true);
	}
		
	@After
    public void restoreSystemInputOutput() {
		System.setIn(systemIn);
        System.setOut(systemOut);
    }
	

}
