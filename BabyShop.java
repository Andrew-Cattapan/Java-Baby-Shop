/*
 * Andrew Cattapan
 * Baby Shop Program
 * May 20th, 2022
 */

package babyshop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.PrintWriter;


abstract class BabyItem
{
    /*
    * Abstract class with name variable that is reused for other classes.
    * Includes the total price of all the purchases.
    * Includes the total tax price of all the purchases.
    */
    protected String name;
    static double totalPrice = 0;
    static double totalTax = 0;
    
    public BabyItem()
    {
        this.name = "";
    }
    public BabyItem(String name)
    {
        this.name = name;
    }
    public final String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    
    public double getTotalPriceWithTax()
    {
        return totalPrice + totalTax;
    }
    
    public abstract double getCost();
}

class BabyFood extends BabyItem
{
    /*Takes baby food with their prices and formats them through a reusable class.*/
    private int numberOfJars;
    private double pricePerDozen;
    static double totalFoodPrice;
    static double totalFoodTax;
    
    public BabyFood (String name, int numberOfJars, double pricePerDozen)
    {
        super(name);
        this.numberOfJars = numberOfJars;
        this.pricePerDozen = pricePerDozen;
        totalFoodPrice += getCost();
        totalPrice += getCost();
    }
    
    public void incrementNumberOfJars(int numberOfJars)
    {
        this.numberOfJars += numberOfJars;
    }
    
    public int getNumberOfJars()
    {
        return numberOfJars;
    }
    
    public double getPricePerDozen()
    {
        return this.pricePerDozen;
    }
    
    @Override
    public double getCost()
    {
        return numberOfJars * pricePerDozen / 12;
    }
    
    public String toString()
    {
        return String.format("%-33s$%7.2f  [Tax: $0.00]\n   %d items @ $%.2f/dozen", name, getCost(), numberOfJars, pricePerDozen);
    }
}

class BabyClothes extends BabyItem
{
    /*Takes baby clothes and their prices and taxes and formats them through a reusable class.*/
    private int quantity;
    private double pricePerItem;
    static double totalClothesPrice;
    static double totalClothesTax;

    public BabyClothes (String name, int quantity, double pricePerItem)
    {
        super(name);
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
        totalPrice += this.getCost();
        totalClothesPrice += this.getCost();
    }
    
    public void incrementQuantity(int quantity)
    {
        this.quantity += quantity;
    }
    
    public int getQuantity()
    {
        return quantity;
    }
    
    public double getPricePerItem()
    {
        return pricePerItem;
    }
    
    public double getTax()
    {
        return quantity * pricePerItem * 0.07;
    }
    
    @Override
    public double getCost()
    {
        return quantity * pricePerItem;
    }
    
    public String toString()
    {
        return String.format("%-33s$%7.2f  [Tax: $%.2f]\n   %d items @ $%.2f/each", name, getCost(), getTax(), quantity, pricePerItem);
    }
}

class BabyToy extends BabyItem
{
    /*Takes baby toys and their prices with tax and formats them through a reusable class.*/
    private int quantity;
    private double pricePerToy;
    private double accessoryPrice;
    static double totalToyPrice;
    static double totalToyTax;
    
    public BabyToy(String name, int quantity, double pricePerToy, double accessoryPrice)
    {
        super(name);
        this.quantity = quantity;
        this.pricePerToy = pricePerToy;
        this.accessoryPrice = accessoryPrice;
    }
    
    public void incrementQuantity(int quantity)
    {
        this.quantity += quantity;
    }
    
    public int getQuantity()
    {
        return quantity;
    }
    
    public double getPricePerToy()
    {
        return this.pricePerToy;
    }
    
    public double getTax()
    {
        return quantity * (pricePerToy + accessoryPrice) * 0.07;
    }
    
    @Override
    public double getCost()
    {
        return quantity * (pricePerToy + accessoryPrice);
    }
    
    public String toString()
    {
        return String.format("%-33s$%7.2f  [Tax: $%.2f]\n   %d toys @ $%.2f/each + $%.2f", name, getCost(), getTax(), quantity, pricePerToy, accessoryPrice);
    }
}

class NoItemsEnteredException extends Exception
{      
    /*Custom Exception to be thrown if the number of items purchased is zero.*/
    public  NoItemsEnteredException()
    {
        super();
    }
    public NoItemsEnteredException(String message)
    {
        super(message);
    }
}

public class BabyShop {
    
    static BabyFood[] food = new BabyFood[5];
    static BabyClothes[] clothes = new BabyClothes[5];
    static BabyToy[] toy = new BabyToy[5];
    
    public static void main(String[] args)
    {
        /*Runs main program logic*/
        food[0] = new BabyFood("Peas", 0, 3.99);
        food[1] = new BabyFood("Carrots", 0, 5.99);
        food[2] = new BabyFood("Yogurt", 0, 7.23);
        food[3] = new BabyFood("Milk", 0, 5.22);
        food[4] = new BabyFood("Blueberries", 0, 4.99);
        
        clothes[0] = new BabyClothes("Shirt", 0, 7.99);
        clothes[1] = new BabyClothes("Onesie", 0, 9.63);
        clothes[2] = new BabyClothes("Burp Cloth", 0, 3.43);
        clothes[3] = new BabyClothes("Socks", 0, 2.34);
        clothes[4] = new BabyClothes("Pants", 0, 4.98);
        
        toy[0] = new BabyToy("Rattle", 0, 4.54, 2.11);
        toy[1] = new BabyToy("Stuffed Bear", 0, 13.43, 2.37);
        toy[2] = new BabyToy("Train", 0, 5.99, 1.23);
        toy[3] = new BabyToy("Truck", 0, 4.89, 2.38);
        toy[4] = new BabyToy("Pacifier", 0, 5.34, 1.33);


        String userType;
        int quantity;
        int loop = 0;
        int firstCounter = 0;
        Scanner keyboard = new Scanner(System.in);
        
        while (loop == 0)
        {
            userType = getBabyType();
            if (userType.length() == 0)
            {
                break;
            }
            switch(userType)
            {
                case "food":
                    getFoodType(keyboard, food, firstCounter);
                    firstCounter = 1;
                    break;
                case "clothes":
                    getClothesType(keyboard, clothes, firstCounter);
                    firstCounter = 1;
                    break;
                case "toys":
                    getToyType(keyboard, toy, firstCounter);
                    firstCounter = 1;
                    break;
                default:
                    System.out.println("Program error...");
                    System.exit(0);
            }
        }
        try
        {
            quantity = itemsEntered(food, clothes, toy);
            displayAndPrintReceipt(food, clothes, toy, keyboard, firstCounter);
        }
        catch (NoItemsEnteredException e)
        {
            System.err.println("NoItemsEnteredException: No items selected to print out on receipt.");
        }
        
        System.out.println("Exiting...");
    }
    
    public static int itemsEntered(BabyFood[] food, BabyClothes[] clothes, BabyToy[] toy) throws NoItemsEnteredException
    {
        /*
        * Returns the total number of items ordered using the static variables declared by the objects.
        * Throws a NoItemsEnteredException if there are no items.
        */
        int totalQuantity = 0;
        
        for(int i = 0; i < 5; i++)
        {
            totalQuantity += food[i].getNumberOfJars();            
        }
        
        for(int i = 0; i < 5; i++)
        {
            totalQuantity += clothes[i].getQuantity();
        }
        
        for(int i = 0; i < 5; i++)
        {
            totalQuantity += toy[i].getQuantity();            
        }
        
        if (totalQuantity == 0)
        {
            throw new NoItemsEnteredException();
        }
        
        return totalQuantity;
    }
    
    public static void displayAndPrintReceipt(BabyFood[] food, BabyClothes[] clothes, BabyToy[] toy, Scanner keyboard, int firstCounter)
    {
        /*Prompts the user for a filename to write to and outputs a receipt to
            that file.
        
        Args:
            food: an array of BabyFood items
            clothes: an array of BabyClothes items
            toy: an array of BabyToy items
            keyboard: a Scanner instance for accepting input
            firstCounter: a int used to keep track of whether or not the previous
                input was an integer.
        
        Raises:
            Throws an Exception if the filename already exists and loops to let the user try again.
            Throws a FileNotFoundException if the file is corrupted in the middle of the program.
        */
        String outputFileName = "";
        int loop = 0;
        PrintWriter outputFile = null;
        
        do
        {
            System.out.println("Please enter desired output filename:");
            outputFileName = keyboard.nextLine();
            if (firstCounter == 1)
            {
                outputFileName = keyboard.nextLine();
            }
            if (outputFileName.endsWith(".txt") == false)
            {
                outputFileName += ".txt";
            }
            try
            {
                File testFile = new File(outputFileName);
                if (testFile.exists())
                {
                    throw new Exception();
                }
                outputFile = new PrintWriter(outputFileName);
                loop = 1;
            }
            catch (Exception e)
            {
                System.err.println("Name already exists.");
                firstCounter = 0;
            }
        } while(loop == 0);
        
        String line = "--------------------------------------------------------";
        outputFile.println(line);
        
        for (int i = 0; i < 5; i++)
        {
            if (food[i].getCost() != 0)
            {
                outputFile.println(food[i]);
            }
        }
        
        for (int i = 0; i < 5; i++)
        {
            if (clothes[i].getCost() != 0)
            {
               outputFile.println(clothes[i]);
            }
        }
        
        for (int i = 0; i < 5; i++)
        {
            if (toy[i].getCost() != 0)
            {
                outputFile.println(toy[i]);
            }
        }
        
        outputFile.println(line);
        outputFile.printf("Food Price\t:\t$%6.02f\n", food[0].totalFoodPrice);
        outputFile.printf("Clothes Price\t:\t$%6.02f\tTax:\t\t$%6.02f\n", clothes[0].totalClothesPrice, clothes[0].totalClothesTax);
        outputFile.printf("Toy Price\t:\t$%6.02f\tTax:\t\t$%6.02f\n", toy[0].totalToyPrice, toy[0].totalToyTax);
        outputFile.printf("Total Price\t:\t$%6.02f\tTotal Tax:\t$%6.02f\n", food[0].totalPrice, food[0].totalTax);
        outputFile.printf("Total With Tax\t:\t$%6.02f\n", food[0].getTotalPriceWithTax());
        
        outputFile.close();
        
        try
        {
            displayReceipt(outputFileName);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("FileNotFoundException: The file that was just created was renamed or destroyed.");
            System.exit(0);
        }
    }
    
    public static void displayReceipt(String outputFileName) throws FileNotFoundException
    {
        /*Prints the contents of the receipt text file to the user.
        
        Args:
           outputFileName: the String name of the file the receipt printed to.
        
        Raises:
            FileNotFoundException if file does not exist.
        */
        File readFileName = new File(outputFileName);           
        Scanner readFile = new Scanner(readFileName);
        for(int i=0;;i++)
        {
            if(readFile.hasNext())
            {
                System.out.println(readFile.nextLine());
            }
            else
            {
                break;
            }
        }
    }
    
    public static String getFoodType(Scanner keyboard, BabyFood[] food, int firstCounter)
    {
        /*Prompts the user for what food they might want from a list and how many.
            Updates the appropriate food[] object to keep track of totals and items ordered.
        
        Args:
            keyboard: a Scanner instance for accepting input
            food: an array of BabyFood
            firstCounter: a int used to keep track of whether or not the previous
                input was an integer.
        
        Returns:
            "Peas," "Carrots," "Yogurt," "Milk," or "Blueberries" string.
        */
        String input = "";
        int loop = 5;
        int quantity;
        while(loop == 5)
        {
            System.out.println("\nWhat food would you like?\n"
                    + food[0].getName() + " for $" + food[0].getPricePerDozen() + "/dozen\n"
                    + food[1].getName() + " for $" + food[1].getPricePerDozen() + "/dozen\n"
                    + food[2].getName() + " for $" + food[2].getPricePerDozen() + "/dozen\n"
                    + food[3].getName() + " for $" + food[3].getPricePerDozen() + "/dozen\n"
                    + food[4].getName() + " for $" + food[4].getPricePerDozen() + "/dozen");
            input = keyboard.nextLine();
            if(firstCounter == 1)
            {
                input = keyboard.nextLine();
            }
            input = formatString(input);
            switch(input)
            {
                case "Peas":
                    System.out.println("You have chosen peas.");
                    loop = 0;
                    break;
                case "Carrots":
                    System.out.println("You have chosen carrots.");
                    loop = 1;
                    break;
                case "Yogurt":
                    System.out.println("You have chosen yogurt.");
                    loop = 2;
                    break;
                case "Milk":
                    System.out.println("You have chosen milk.");
                    loop = 3;
                    break;
                case "Blueberries":
                    System.out.println("You have chosen blueberries.");
                    loop = 4;
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    firstCounter = 0;
            }
        }
        quantity = getQuantity(keyboard);
        food[loop].incrementNumberOfJars(quantity);
        food[loop].totalFoodPrice += food[loop].getCost();
        food[loop].totalPrice += food[loop].getCost();
        return input;
    }
    
    public static String getClothesType(Scanner keyboard, BabyClothes[] clothes, int firstCounter)
    {
        /*Prompts the user for what clothes they might want from a list and how
            many. Also updates the appropriate clothes[] object to keep track of
            totals.
        
        Args:
            keyboard: a Scanner instance for accepting input
            clothes: an array of BabyClothes
            firstCounter: a int used to keep track of whether or not the previous
                input was an integer.
        
        Returns:
            "Shirt," "Onesie," "Burp cloth," "Socks," or "Pants" string.
        */
        String input = "";
        int loop = 5;
        int quantity;
        while(loop == 5)
        {
            System.out.println("\nWhat clothes would you like?\n"
                    + clothes[0].getName() + " for $" + clothes[0].getPricePerItem() + "/item\n"
                    + clothes[1].getName() + " for $" + clothes[1].getPricePerItem() + "/item\n"
                    + clothes[2].getName() + " for $" + clothes[2].getPricePerItem() + "/item\n"
                    + clothes[3].getName() + " for $" + clothes[3].getPricePerItem() + "/item\n"
                    + clothes[4].getName() + " for $" + clothes[4].getPricePerItem() + "/item");
            input = keyboard.nextLine();
            if(firstCounter == 1)
            {
                input = keyboard.nextLine();
            }
            input = formatString(input);
            switch(input)
            {
                case "Shirt":
                    System.out.println("You have chosen shirt.");
                    loop = 0;
                    break;
                case "Onesie":
                    System.out.println("You have chosen onesie.");
                    loop = 1;
                    break;
                case "Burp cloth":
                    System.out.println("You have chosen burp cloth.");
                    loop = 2;
                    break;
                case "Socks":
                    System.out.println("You have chosen socks.");
                    loop = 3;
                    break;
                case "Pants":
                    System.out.println("You have chosen pants.");
                    loop = 4;
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    firstCounter = 0;
            }
        }
        quantity = getQuantity(keyboard);
        clothes[loop].incrementQuantity(quantity);
        clothes[loop].totalClothesPrice += clothes[loop].getCost();
        clothes[loop].totalClothesTax += clothes[loop].getTax();
        clothes[loop].totalPrice += clothes[loop].getCost();
        clothes[loop].totalTax += clothes[loop].getTax();
        return input;
    }
    
    public static String getToyType(Scanner keyboard, BabyToy[] toy, int firstCounter)
    {
        /*Prompts the user for what toys they might want from a list and how many.
            Also updates the appropriate toy[] object to keep track of totals.

        Args:
            keyboard: a Scanner instance for accepting input
            toy: an array of BabyToys
            firstCounter: a int used to keep track of whether or not the previous
                input was an integer.
        
        Returns:
            "Rattle," "Stuffed bear," "Train," "Truck," or "Pacifier" string.
        */
        String input = "";
        int loop = 5;
        int quantity;
        while(loop == 5)
        {
            System.out.println("\nWhat toy would you like?\n"
                    + toy[0].getName() + " for $" + toy[0].getPricePerToy() + "/toy\n"
                    + toy[1].getName() + " for $" + toy[1].getPricePerToy() + "/toy\n"
                    + toy[2].getName() + " for $" + toy[2].getPricePerToy() + "/toy\n"
                    + toy[3].getName() + " for $" + toy[3].getPricePerToy() + "/toy\n"
                    + toy[4].getName() + " for $" + toy[4].getPricePerToy() + "/toy");
            input = keyboard.nextLine();
            if(firstCounter == 1)
            {
                input = keyboard.nextLine();
            }
            input = formatString(input);
            switch(input)
            {
                case "Rattle":
                    System.out.println("You have chosen rattle.");
                    loop = 0;
                    break;
                case "Stuffed bear":
                    System.out.println("You have chosen stuffed bear.");
                    loop = 1;
                    break;
                case "Train":
                    System.out.println("You have chosen train.");
                    loop = 2;
                    break;
                case "Truck":
                    System.out.println("You have chosen truck.");
                    loop = 3;
                    break;
                case "Pacifier":
                    System.out.println("You have chosen pacifier.");
                    loop = 4;
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    firstCounter = 0;
            }
        }
        quantity = getQuantity(keyboard);
        toy[loop].incrementQuantity(quantity);
        toy[loop].totalToyPrice += toy[loop].getCost();
        toy[loop].totalToyTax += toy[loop].getTax();
        toy[loop].totalPrice += toy[loop].getCost();
        toy[loop].totalTax += toy[loop].getTax();
        return input;
    }
            
    public static int getQuantity(Scanner keyboard) throws InputMismatchException
    {
        /*Prompts user for an integer greater than zero and returns it.
        
        Args:
        keyboard: a Scanner instance for accepting input
        
        Returns:
            A positive integer or zero.
        
        Raises:
            InputMismatchException if a string is entered.
        */
        int loop = 0;
        int quantity = 0;
        while(loop == 0)
        {
            System.out.print("Please enter how many you want: ");
            try
            {
                quantity = keyboard.nextInt();
            }
            catch(InputMismatchException e)
            {
                System.err.println(e.toString() + " entry must be an integer.");
                keyboard.nextLine();
                continue;
            }
            if (quantity < 0)
            {
               System.err.println("Quantity must be positive or zero.");
            }
            else
            {
                loop = 1;
            }
        }
        return quantity;
    }
    
    public static String getBabyType()
    {
        /*Prompts the user for a string asking what type of item they would like to order.
        
        Args:
            None
        
        Returns:
            One of the following Strings: "food," "clothes," or "toys"
        */
        Scanner keyboard = new Scanner(System.in);
        int loop = 0;
        String input = "";
        while (loop == 0)
        {
            System.out.println("Please enter what you would like to buy (or just"
                    + " press enter to finish):\n'Food'\n'Clothes'\n'Toys'");
            input = keyboard.nextLine();
            if(input.length() == 0)
            {
                input = "";
                break;
            }
            switch(input.toLowerCase())
            {
                case "food":
                    System.out.println("You have chosen food.");
                    loop = 1;
                    break;
                case "clothes":
                    System.out.println("You have chosen clothes.");
                    loop = 1;
                    break;
                case "toys":
                    System.out.println("You have chosen toys.");
                    loop = 1;
                    break;
                default:
                    System.out.println("Invalid input. Please try again:");
            }
        }
        return input;   
    }
    
    public static String formatString(String input)
    {
        /*Returns string passed with first letter capitalization
        
        Args:
            input: String to be formatted
        
        Returns:
            Original string with proper capitalization
        */
        if(input.length() != 0)
        {
            input = input.toLowerCase();
            input = input.substring(0, 1).toUpperCase() + input.substring(1);    
        }
        else
        {
            System.out.println("Input is too small. Try again.");
        }
        return input;
    }
}

