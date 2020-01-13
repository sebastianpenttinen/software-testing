package assignment1;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import assignment1.VendingMachine;

public class VendingMachineTest {
	VendingMachine vm;
	
	private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;
    
    String newlineChar = System.getProperty("line.separator");
    
    @Before
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }
    
	@Before
	public void setUp() {
		vm = new VendingMachine();
		vm.powerUpVendingMechine();
		vm.setAmountPaid(0.0);
	}
	
	private void provideInput(String data) {
		
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }
	
	private String getOutput() {
		return testOut.toString();
	}
	
	@Test
	public void testMenuDisplay() {
		vm.displayMenu();
		String reply = getOutput();
		assertTrue(reply.contains("Please select your drink from the menu:\n" + 
				"\n" + 
				"	COLA		price: [3.0] euro	still have: [10]can\n" + 
				"	COFFEE		price: [2.5] euro	still have: [10]can\n" + 
				"	ORANGE_JUICE	price: [5.0] euro	still have: [10]can\n" + 
				"	QUIT"));
	}
	@Test
	public void testSelectionOfCola() {
		provideInput("TE"+newlineChar+"OE"+newlineChar);
		vm.processSelection("COLA");
		String reply = getOutput();
		assertTrue(reply.contains("The price is 3.00 Euro, please insert a coin."));
		assertEquals(3.0, vm.amountPaid, 1);
	}
	
	@Test
	public void testSelectionOfCoffee() {
		provideInput("TE"+newlineChar+"FC"+newlineChar);
		vm.processSelection("COFFEE");
		String reply = getOutput();
		assertTrue(reply.contains("The price is 2.50 EURO, please the coin."));	
		assertEquals(2.5, vm.amountPaid, 1);
	}
	
	@Test
	public void testSelectionOfOJ() {
		provideInput("TE"+newlineChar+"TE"+newlineChar+"OE"+newlineChar);
		vm.processSelection("ORANGE_JUICE");
		String reply = getOutput();
		assertTrue(reply.contains("The price is 5.00 EURO, please put in the coin."));
	}

	@Test
	public void testCaptureMoney() {
		provideInput("TE"+newlineChar+"OE"+newlineChar);
		vm.captureMoney("COLA", 3.00);
		String reply = getOutput();
		assertTrue(reply.contains("You have paid 2.0 Euro\nYou have paid 3.0 Euro"));
	}
	
	@Test
	public void testCaptureMoneyWithChange() {
		provideInput("TE"+newlineChar+"TE"+newlineChar);
		vm.captureMoney("COLA", 3.00);
		String reply = getOutput();
		assertTrue(reply.contains("You have paid 4.0 Euro\nYour change is: 1.0 EURO"));
	}
	
	@Test
	public void testCalculateChangeForTE() {
		vm.calculateChange(3.00, "TE TE TE");
		String reply = getOutput();
		assertTrue(reply.contains("You have paid 6.0 Euro"));
	}
	
	@Test
	public void testCalculateChangeForOE() {
		vm.calculateChange(3.00, "OE OE OE");
		String reply = getOutput();
		assertTrue(reply.contains("You have paid 3.0 Euro"));
	}
	
	@Test
	public void testCalculateChangeForTC() {
		vm.calculateChange(0.40, "TC TC TC");
		String reply = getOutput();
		assertTrue(reply.contains("You have paid 0.6000000000000001 Euro"));
	}
	
	@Test
	public void testCalculateChangeForFC() {
		vm.calculateChange(0.40, "FC FC FC");
		String reply = getOutput();
		assertTrue(reply.contains("You have paid 1.5 Euro"));
	}
	@Test
	public void testWrongChange() {
		vm.calculateChange(0.4, "124");
		String reply = getOutput();
		assertTrue(reply.contains("Wrong coin type!"));
	}
	
	@Test
	public void testReturningOECoins() {
		int[] coins = vm.calculateReturningCoins(1.00);
		assertEquals(1, coins[1]);
	}
	
	@Test
	public void testReturningTECoins() {
		int[] coins = vm.calculateReturningCoins(2.00);
		assertEquals(1, coins[0]);
	}
	
	@Test
	public void testReturningFCCoins() {
		int[] coins = vm.calculateReturningCoins(0.50);
		assertEquals(1, coins[2]);
	}
	
	@Test
	public void testReturningTCCoins() {
		int[] coins = vm.calculateReturningCoins(0.20);
		assertEquals(1, coins[3]);
	}
	
	@After
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }
}
