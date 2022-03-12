package com.charlene.coffee.corner;

import com.charlene.coffee.corner.Order;
import com.charlene.coffee.corner.Product;
import com.charlene.coffee.corner.ProductSize;
import com.charlene.coffee.corner.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CharleneCoffeeCornerOrderTest {

    private Product extraMilk;
    private Product foamedMilk;
    private Product specialRoastCoffee;

    private Product smallCoffee;
    private Product mediumCoffee;
    private Product largeCoffee;

    private Product orangeJuice;
    private Product baconRoll;

    @BeforeEach
    public void init(){

        extraMilk = new Product("Extra Milk", 0.30, ProductType.EXTRA);
        foamedMilk = new Product("Foamed Milk", 0.50, ProductType.EXTRA);
        specialRoastCoffee = new Product("Special roast coffee", 0.90, ProductType.EXTRA);

        smallCoffee = new Product("small coffee", 2.50, ProductType.BEVERAGE, ProductSize.SMALL, List.of(extraMilk, foamedMilk, specialRoastCoffee));
        mediumCoffee = new Product("medium coffee", 3.00, ProductType.BEVERAGE, ProductSize.MEDIUM, List.of(extraMilk, foamedMilk, specialRoastCoffee));
        largeCoffee = new Product("large coffee", 3.50, ProductType.BEVERAGE, ProductSize.LARGE, List.of(extraMilk, foamedMilk, specialRoastCoffee));
        orangeJuice = new Product("orange juice", 3.95, ProductType.BEVERAGE, ProductSize.SMALL, List.of(extraMilk, foamedMilk, specialRoastCoffee));

        baconRoll = new Product("Bacon Roll", 4.50, ProductType.SNACK);
    }



    @Test
    public void testOrderBeverageWithExtra() {
        Order order = new Order(List.of(smallCoffee, extraMilk));

        assertEquals(order.getDate(), LocalDate.now());
        assertEquals(order.getTotal(), BigDecimal.valueOf(smallCoffee.getPrice() + extraMilk.getPrice()).setScale(2, RoundingMode.CEILING).doubleValue());
        assertEquals(order.getBonus(), 0.0);
        assertEquals(order.printReceipt(), receiptBuilder(order));
    }

    @Test
    public void testOrderBeverageNoExtraWithSnack() {

        Order order = new Order(List.of(mediumCoffee, baconRoll));

        assertEquals(order.getDate(), LocalDate.now());
        assertEquals(order.getTotal(), BigDecimal.valueOf(mediumCoffee.getPrice() + baconRoll.getPrice()).setScale(2, RoundingMode.CEILING).doubleValue());
        assertEquals(order.getBonus(), 0.0);
        assertEquals(order.printReceipt(), receiptBuilder(order));
    }

    @Test
    public void testOrderBeverageWithExtraAndSnack() {

        Order order = new Order(List.of(smallCoffee, baconRoll, extraMilk));

        assertEquals(order.getDate(), LocalDate.now());
        assertEquals(order.getTotal(), BigDecimal.valueOf(smallCoffee.getPrice() + baconRoll.getPrice()).setScale(2, RoundingMode.CEILING).doubleValue());
        assertEquals(order.printReceipt(), receiptBuilder(order));

    }

    @Test
    public void testOrder5Beverages() {

        Order order = new Order(List.of(smallCoffee, smallCoffee, mediumCoffee, largeCoffee, orangeJuice));

        assertEquals(order.getDate(), LocalDate.now());
        assertEquals(order.getTotal(), BigDecimal.valueOf(smallCoffee.getPrice() + mediumCoffee.getPrice() + largeCoffee.getPrice() + orangeJuice.getPrice()).setScale(2, RoundingMode.CEILING).doubleValue());
        assertEquals(order.getBonus(), smallCoffee.getPrice());
        assertEquals(order.printReceipt(), receiptBuilder(order));
    }

    @Test
    public void testOrder3BeveragesWithSnackAndExtra() {
        Order order = new Order(List.of(smallCoffee, baconRoll, extraMilk, mediumCoffee, specialRoastCoffee, orangeJuice));

        assertEquals(order.getDate(), LocalDate.now());
        assertEquals(order.getTotal(), BigDecimal.valueOf(smallCoffee.getPrice() + baconRoll.getPrice() + mediumCoffee.getPrice() + specialRoastCoffee.getPrice() + orangeJuice.getPrice()).setScale(2, RoundingMode.CEILING).doubleValue());
        assertEquals(order.getBonus(), extraMilk.getPrice());

        assertEquals(order.printReceipt(), receiptBuilder(order));

    }

    @Test
    public void testEmptyOrder() {
        Order order = new Order();

        assertEquals(order.getDate(), LocalDate.now());
        assertEquals(order.getTotal(), 0.0);
        assertEquals(order.getBonus(), 0.0);
    }

    private String receiptBuilder(Order order) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---------------------------\n");
        stringBuilder.append(" Charlene's Coffee Corner  \n");
        stringBuilder.append("---------------------------\n");
        stringBuilder.append("ID : ").append(order.getId()).append("\n");
        stringBuilder.append("Date : ").append(order.getDate()).append("\n");
        stringBuilder.append("---------------------------\n");
        stringBuilder.append("---------------------------\n");
        stringBuilder.append("Item (Price)\n");
        stringBuilder.append("---------------------------\n");
        order.getProducts().forEach(p -> stringBuilder.append(p.getName()).append(" (").append(p.getPrice()).append(" CHF)\n"));
        stringBuilder.append("---------------------------\n");
        stringBuilder.append("Sub Total Amount = ").append(order.getSubTotal()).append(" CHF \n");
        stringBuilder.append("Bonus = ").append(order.getBonus()).append(" CHF \n");
        stringBuilder.append("\n");
        stringBuilder.append("Total = ").append(order.getTotal()).append(" CHF \n");
        stringBuilder.append("---------------------------\n");
        return stringBuilder.toString();
    }

}
