/* DISCLAIMER: THIS CODE IS A MODIFIED VERSION OF THE ONE PROVIDED AT 
 * https://code.google.com/a/eclipselabs.org/p/vending-machine/ under
 * Eclipse Public Licence 1.0
 */


package assignment1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class VendingMachine {
        private enum Coin {
            //TC = 20 cent, FC = 50 cent, OE = 1 euro, TE = 2 Euro
        	TC(0.2), FC(0.5), OE(1.0), TE(2.0);
        	
        	Coin(double value) { this.value = value; }
            private final double value;
       //     public double value() { return value; }
        }
        
        private enum SelectionMenu {
                COLA,
                ORANGE_JUICE,
                COFFEE,
                QUIT
        }
        
        private static final String EXIT = "Exit"; 
        public DrinkChamber drinkChamber;
        public double amountPaid; 
        
        public void setAmountPaid(double amountPaid) {
                this.amountPaid = amountPaid;
        }
        
        public void powerUpVendingMechine() {
                drinkChamber = new DrinkChamber();
                drinkChamber.loadInventory();
        }
        
        public void displayMenu() {
                System.out.println("Please select your drink from the menu:\n");
                System.out.println("\t" + SelectionMenu.COLA + "\t\tprice: [" + drinkChamber.cola.price + "] euro" + "\tstill have: [" + drinkChamber.getColaCount().toString() + "]can\n" +
                                                        "\t" + SelectionMenu.COFFEE + "\t\tprice: [" + drinkChamber.coffee.price + "] euro" + "\tstill have: [" + drinkChamber.getCoffeeCount().toString() + "]can\n" +
                                                        "\t" + SelectionMenu.ORANGE_JUICE + "\tprice: [" + drinkChamber.oj.price + "] euro" + "\tstill have: [" +  drinkChamber.getOJCount().toString()+ "]can\n" +
                                                        "\t" + "QUIT\n\n" +
                                                        "Enter:");
        }
             
        
        public int[] calculateReturningCoins(double change) {
        	   //calculates the change in coins, assumes unlimited coins available
        	
        		int[] coinList = new int[4];//number of coins corresponding to TE OE FC TC 
        	
                //System.out.print("Returning coin for change: "+ change );
                
                if ( change / Coin.TE.value >= 1 ) { 
                        int twoEuro = (int)(change / Coin.TE.value);
                        change = change - (twoEuro * Coin.TE.value);
                        coinList[0] = (int)twoEuro;
                        
                        //System.out.println(twoEuro + " 2 Euro "); 
                }
                if ( change / Coin.OE.value >= 1 ) { 
                        int oneEuro = (int)(change / Coin.OE.value);
                        change = change - (oneEuro * Coin.OE.value);
                        System.out.println(oneEuro + " 1 Euro ");
                        coinList[1] = (int)oneEuro;
                        
                        //System.out.println(oneEuro + " 1 Euro "); 
                } 
                if ( change / Coin.FC.value >= 1 ) {        
                		int fiftyCent = (int)(change / Coin.FC.value);
                        change = change - (fiftyCent * Coin.FC.value);
                        coinList[2] = (int)fiftyCent;
                        //System.out.println(fiftyCent + " 50 cents "); 
                } 
                if ( change / Coin.TC.value >= 1 ) { 
                	    int twentyCent = (int)(change / Coin.TC.value);
                        change = change - (twentyCent * Coin.TC.value);
                        coinList[3] = (int)twentyCent;
                        //System.out.println(twentyCent + " 20 cents "); 
                }      
            return coinList;
        }
        
        public void displayReturningCoins(double change){
        	// displays the change
        	
        	int[] coins = calculateReturningCoins(change);
        	
        	System.out.println("Your Change is " );
        	System.out.println("  " + coins[0] +" x 2Euro" );
        	System.out.println("  " + coins[1] +" x 1Euro" );
        	System.out.println("  " + coins[2] +" x 50Cent" );
        	System.out.println("  " + coins[3] +" x 20Cent" );
                    
                    	
        }
        
        public double calculateChange(double price, String insertedCoins) {
        	//calculates the change to be reuturned based on user input
        	//insertedCoins is tokenized using spaces, eg OE OE OE for 3 Euros
        	    
                StringTokenizer st = new StringTokenizer(insertedCoins);
                
                while(st.hasMoreElements()) {
                        String coin = st.nextToken();
                        
                        if (coin.equals("TC")) { amountPaid += Coin.TC.value; }
                        else if (coin.equals("FC")) { amountPaid += Coin.FC.value;}
                        else if (coin.equals("OE")) { amountPaid += Coin.OE.value; }
                        else if (coin.equals("TE")) { amountPaid += Coin.TE.value; }
                        else {System.out.printf("Wrong coin type!"); }
                      
                }
                System.out.println("You have paid " + amountPaid + " Euro");
                return amountPaid - price;
        }
        
        
        public Boolean captureMoney(String selection, double price){ //throws Exception {
        	    //receives coins from the user. 
        	    
        	    BufferedReader coins = new BufferedReader(new InputStreamReader(System.in));
        	        	               
                while(true){
                String amount = null;
                
                try {
                        amount = coins.readLine();
                        if (amount != null) {
                                double change = calculateChange(price, amount);
                                                             
                                if ( change >= 0.0) {
                                        //processSelection(selection, true);
                                        if (change > 0.0) {
                                                System.out.println("Your change is: " + change + " EURO");
                                                displayReturningCoins(change);
                                                System.out.println("Thank you for your business, you see again! \n\n\n\n");
                                                change = 0;
                                         }
                                        break; 
                                   	}
                                
                                        
                                } 
                        else {
                              System.out.println("You did not put enough money, please put in more coins.");
                           	}		
                } catch (IOException e) {
                        System.out.println("Error in reading input.");
                        System.exit(1);
                }       
              }
                
                return true;
                
        }
        
        public void processSelection(String selection) {
            double price = 0.0;
            switch (SelectionMenu.valueOf(selection)) {
                    case COLA: 
                            price = drinkChamber.cola.price;
                            System.out.printf("The price is %.2f Euro, please insert a coin.\n", drinkChamber.cola.price);
                            if (captureMoney(selection, price)) { drinkChamber.takeACola(); }
                            else {System.out.printf("ERROR");}
                            break;
                            
                    case COFFEE: 
                            price = drinkChamber.coffee.price;
                            System.out.printf("The price is %.2f EURO, please the coin.\n", drinkChamber.coffee.price);
                            if (captureMoney(selection, price)) { drinkChamber.takeACoffee(); } 
                            else {System.out.printf("ERROR");}
                            break;
                            
                    case ORANGE_JUICE: 
                            price = drinkChamber.oj.price;
                            System.out.printf("The price is %.2f EURO, please put in the coin.\n", drinkChamber.oj.price);
                            if (captureMoney(selection, price)) { drinkChamber.takeAOJ(); } 
                            else {System.out.printf("ERROR");} 
                            break;
                            
                    case QUIT:
            }
    }
        
              
        public String captureInputAndRespond(){
        	//user is allowed to insert max 10 coins to pay for a drink
        	    	
        	BufferedReader choosen = new BufferedReader(new InputStreamReader(System.in));
        	for (int i = 0; i < 10; i++){
        		this.displayMenu();
        		String selection = null;
        		
        		try {
                    selection = choosen.readLine();                    
        		} catch (IOException e) {
                    System.out.println("Error in reading input.");
                    System.exit(1);
        		}
        		
        		 //check if valid options
        		 if (selection.equals("QUIT") || selection.equals("COLA") || selection.equals("COFFEE") || selection.equals("ORANGE_JUICE")){
        			 if (!selection.equals("QUIT")) {
	         			 System.out.printf("You have selected " + selection + "\n");        		
	        			 processSelection(selection);
	        			 System.out.println("DRINK DELIVERED! \n\n"); 
	        			 //reseting the amount paid
	        			 amountPaid=0;
	        		 }
	        		 else {
	        			 System.out.println("Exiting...BYE!");   
	        			 System.exit(1);			 
	        		 }
        		 }
        		 else { System.out.println("Wrong input try again!!!\n\n");}   
        	}
        	return EXIT;
         }
        	
        
        public static void main(String[] args) {
                VendingMachine vm = new VendingMachine();
                vm.powerUpVendingMechine();
                while(true) {
                        vm.setAmountPaid(0.0);
                        if (vm.captureInputAndRespond().equals("Exit"))
                                break;
                }
        }
}
