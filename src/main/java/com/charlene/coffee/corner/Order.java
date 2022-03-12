package com.charlene.coffee.corner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Order Class responsible for computing an order
 * that have products
 *
 */
public class Order {

    private long id;
    private LocalDate date;
    private List<Product> products;

    public Order() {
        this.id = new Random().nextLong();
        this.date = LocalDate.now();
        this.products = new ArrayList<>();
    }

    public Order(List<Product> products) {
        this.id = new Random().nextLong();
        this.date = LocalDate.now();
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public Order setId(long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Order setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Order setProducts(List<Product> products) {
        this.products = products;
        return this;
    }

    public Order addProduct(Product product){
        this.products.add(product);
        return this;
    }

    public Order removeProduct(Product product){
        this.products.remove(product);
        return this;
    }

    public double getSubTotal() {
        double subTotal =  this.products.stream().mapToDouble(Product::getPrice).sum();
        return new BigDecimal(subTotal).setScale(2, RoundingMode.CEILING).doubleValue();
    }

    public double getBonus() {

        double total = 0;

        long nbBeverage = products.stream().filter(p -> p.getProductType() == ProductType.BEVERAGE).count();
        long nbExtra = products.stream().filter(p -> p.getProductType() == ProductType.EXTRA).count();
        long nbSnack = products.stream().filter(p -> p.getProductType() == ProductType.SNACK).count();

        int nbDiscountBeverage = Math.toIntExact(nbBeverage / 5);
        if(nbDiscountBeverage > 0) {
            // We assume the discount will be applied to the lowest price product
            List<Product> beverages = products.stream().filter(p -> p.getProductType() == ProductType.BEVERAGE).sorted(Comparator.comparingDouble(Product::getPrice)).collect(Collectors.toList());
            total += IntStream.range(0, nbDiscountBeverage).mapToObj(beverages::get).mapToDouble(Product::getPrice).sum();
        }
        if(nbBeverage > 0 && nbSnack > 0 && nbExtra > 0){
            total += products.stream().filter(p -> p.getProductType() == ProductType.EXTRA).min(Comparator.comparingDouble(Product::getPrice)).map(Product::getPrice).orElse(0.0);
        }
        return new BigDecimal(total).setScale(2, RoundingMode.CEILING).doubleValue();
    }

    public double getTotal() {
        return new BigDecimal(getSubTotal() - getBonus()).setScale(2, RoundingMode.CEILING).doubleValue();
    }



    public String printReceipt() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---------------------------\n");
        stringBuilder.append(" Charlene's Coffee Corner  \n");
        stringBuilder.append("---------------------------\n");
        stringBuilder.append("ID : ").append(id).append("\n");
        stringBuilder.append("Date : ").append(date).append("\n");
        stringBuilder.append("---------------------------\n");
        stringBuilder.append("---------------------------\n");
        stringBuilder.append("Item (Price)\n");
        stringBuilder.append("---------------------------\n");
        products.forEach(p -> stringBuilder.append(p.getName()).append(" (").append(p.getPrice()).append(" CHF)\n"));
        stringBuilder.append("---------------------------\n");
        stringBuilder.append("Sub Total Amount = ").append(getSubTotal()).append(" CHF \n");
        stringBuilder.append("Bonus = ").append(getBonus()).append(" CHF \n");
        stringBuilder.append("\n");
        stringBuilder.append("Total = ").append(getTotal()).append(" CHF \n");
        stringBuilder.append("---------------------------\n");

        return stringBuilder.toString();
    }
}
