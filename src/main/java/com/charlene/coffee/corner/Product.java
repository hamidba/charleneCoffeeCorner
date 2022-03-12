package com.charlene.coffee.corner;

import java.util.List;
import java.util.Objects;

public class Product {

    private String name;
    private double price;
    private ProductType productType;
    private ProductSize productSize;
    private List<Product> availableExtras;

    public Product(String name, double price, ProductType productType, ProductSize productSize, List<Product> availableExtras) {
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.productSize = productSize;
        this.availableExtras = availableExtras;
    }

    public Product(String name, double price, ProductType productType, ProductSize productSize) {
        this(name, price, productType, productSize, null);
    }

    public Product(String name, double price, ProductType productType) {
        this(name, price, productType, null, null);
    }



    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Product setProductType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public ProductSize getProductSize() {
        return productSize;
    }

    public Product setProductSize(ProductSize productSize) {
        this.productSize = productSize;
        return this;
    }

    public List<Product> getAvailableExtras() {
        return availableExtras;
    }

    public boolean hasAvailableExtras(){
        return availableExtras != null && !availableExtras.isEmpty();
    }

    public Product setAvailableExtras(List<Product> availableExtras) {
        this.availableExtras = availableExtras;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && Objects.equals(name, product.name) && productType == product.productType && productSize == product.productSize && Objects.equals(availableExtras, product.availableExtras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, productType, productSize, availableExtras);
    }
}
