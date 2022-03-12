package com.charlene.coffee.corner;

import java.util.List;
import java.util.Scanner;

/**
 * This class initialize the catalog
 * and start accepting orders
 *
 *
 * @author Hamidou
 */
public class CharleneCoffeeCorner {

    private List<Product> catalog;

    public CharleneCoffeeCorner() {
        initCatalog();
    }

    private void initCatalog() {

        Product extraMilk = new Product("Extra Milk", 0.30, ProductType.EXTRA);
        Product foamedMilk = new Product("Foamed Milk", 0.50, ProductType.EXTRA);
        Product specialRoastCoffee = new Product("Special roast coffee", 0.90, ProductType.EXTRA);

        Product smallCoffee = new Product("small coffee", 2.50, ProductType.BEVERAGE, ProductSize.SMALL, List.of(extraMilk, foamedMilk, specialRoastCoffee));
        Product mediumCoffee = new Product("medium coffee", 3.00, ProductType.BEVERAGE, ProductSize.MEDIUM, List.of(extraMilk, foamedMilk, specialRoastCoffee));
        Product largeCoffee = new Product("large coffee", 3.50, ProductType.BEVERAGE, ProductSize.LARGE, List.of(extraMilk, foamedMilk, specialRoastCoffee));
        Product orangeJuice = new Product("orange juice", 3.95, ProductType.BEVERAGE, ProductSize.SMALL, List.of(extraMilk, foamedMilk, specialRoastCoffee));
        Product baconRoll = new Product("Bacon Roll", 4.50, ProductType.SNACK);

        catalog = List.of(smallCoffee, mediumCoffee, largeCoffee, orangeJuice, baconRoll);
    }

    public void startOrder() {
        Order order = new Order();
        do {
            int productChoice = getUserProductChoice();
            if(productChoice == 0) {
                System.out.println("Your Receipt : ");
                System.out.println(" ");
                System.out.println(order.printReceipt());
                break;
            }
            Product product = catalog.get(productChoice-1);
            order.addProduct(product);

            if(product.hasAvailableExtras()){
                List<Product> availableExtras = product.getAvailableExtras();
                int extraChoice = getUserExtraChoice(availableExtras);
                if(extraChoice == 0) continue;
                Product extra = availableExtras.get(extraChoice-1);
                order.addProduct(extra);
            }
        } while (true);
    }

    private int getUserProductChoice(){
        Scanner in = new Scanner(System.in);
        int input;
        System.out.println("\n New Order : \n");
        for(int i = 1; i <= catalog.size(); i++) {
            System.out.printf("%d - %s%n", i, catalog.get(i-1).getName());
        }
        System.out.printf("%d - End Order", 0);
        System.out.println(" ");
        System.out.print("Please enter your choice : ");


        if(in.hasNextInt()){
            input = in.nextInt();

            if(input > catalog.size()){
                System.out.println(" ");
                System.out.print("Please enter a valid choice : ");
                return this.getUserProductChoice();
            }
        }
        else {
            System.out.println(" ");
            System.out.print("Please enter a valid choice : ");
            return this.getUserProductChoice();
        }

        return input;
    }

    private int getUserExtraChoice(List<Product> availableExtras){
        Scanner in = new Scanner(System.in);
        int input;
        System.out.println(" ");
        System.out.println("--- Extras available : ");
        for(int i = 1; i <= availableExtras.size(); i++) {
            System.out.printf("---- %d - %s%n", i, availableExtras.get(i-1).getName());
        }
        System.out.printf("---- %d - No Extra ", 0);
        System.out.println(" ");
        System.out.print("Please select your extra choice : ");

        if(in.hasNextInt()){
            input = in.nextInt();
            if(input > availableExtras.size()){
                System.out.println(" ");
                System.out.print("Please enter a valid extra choice : ");
                return this.getUserExtraChoice(availableExtras);
            }
        }
        else {
            System.out.println(" ");
            System.out.print("Please enter a valid extra choice : ");
            return this.getUserExtraChoice(availableExtras);
        }
        return input;
    }


}
