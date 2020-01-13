package VendingMachine;

public class DrinkChamber {
    Container<Cola, Integer> colaContainer = new Container<Cola, Integer>();
    Container<Coffee, Integer> coffeeContainer = new Container<Coffee, Integer>();
    Container<OrangeJuice, Integer> ojContainer = new Container<OrangeJuice, Integer>();
    
    Cola cola = new Cola();
    Coffee coffee = new Coffee();
    OrangeJuice oj = new OrangeJuice();
    
    public void loadInventory() {
            colaContainer.addItem(cola, new Integer(10));
            coffeeContainer.addItem(coffee, new Integer(10));
            ojContainer.addItem(oj, new Integer(10));
    } 
    
    public void takeACola(){
    		if (getColaCount().intValue() - 1 < 0)
                    System.err.println("cola");
            colaContainer.addItem(cola, getColaCount().intValue() - 1);
    }
    
    public void takeACoffee() {
            if (getCoffeeCount().intValue() - 1 < 0)
            	    System.err.println("cofee");
            coffeeContainer.addItem(coffee, getCoffeeCount().intValue() - 1);
    }
    
   
    public void takeAOJ(){
        if (getOJCount().intValue() - 1 < 0)
        	System.err.println("orange juice");
        ojContainer.addItem(oj, getOJCount().intValue() - 1);
    }
    
    public Integer getColaCount() {
            return colaContainer.getItemCount(cola);
    }
    
    public Integer getCoffeeCount() {
            return coffeeContainer.getItemCount(coffee);
    }
    
    public Integer getOJCount() {
            return ojContainer.getItemCount(oj);
    }
}