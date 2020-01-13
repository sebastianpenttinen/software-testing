package assignment1;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import assignment1.DrinkChamber;

public class DrinkChamberTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	
	DrinkChamber chamber;
	
	@Before
	public void setUp() throws Exception{
		chamber = new DrinkChamber();
		chamber.loadInventory();
	}
	
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}
	
	@Test
	public void testLoadInventory() {
		assertEquals(Integer.valueOf(10), chamber.getColaCount());
		assertEquals(Integer.valueOf(10), chamber.getCoffeeCount());
		assertEquals(Integer.valueOf(10), chamber.getOJCount());
		
	}
	
	@Test
	public void testTakeACola() {
		chamber.takeACola();
		assertEquals(Integer.valueOf(9), chamber.getColaCount());
		
	}
	
	@Test
	public void testTakeColaWhenEmpty() {
		
		for(int i = 0; i < 11; i++) {
			chamber.takeACola();
		}
		assertEquals("cola\n", errContent.toString());
	}
	
	@Test
	public void testTakeACoffee() {
		chamber.takeACoffee();
		assertEquals(Integer.valueOf(9), chamber.getCoffeeCount());
	}
	
	@Test
	public void testTakeCoffeeWhenEmpty() {
		
		for(int i = 0; i < 11; i++) {
			chamber.takeACoffee();
		}
		assertEquals("cofee\n", errContent.toString());
	}
	
	@Test
	public void testTakeAOJ() {
		chamber.takeAOJ();
		assertEquals(Integer.valueOf(9), chamber.getOJCount());
	}
	
	@Test
	public void testTakeOJWhenEmpty() {
		
		for(int i = 0; i < 11; i++) {
			chamber.takeAOJ();
		}
		assertEquals("orange juice\n", errContent.toString());
	}
	
	@After
	public void restoreStreams() {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	}

}
