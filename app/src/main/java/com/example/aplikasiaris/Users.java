package com.example.aplikasiaris;

public class Users extends UsersId {

    String Name;
    String Amount;
    String Price;
    String Description;
    String Category;

    public Users() {
    }

    public Users(String name, String amount, String price, String description, String category) {
        Name = name;
        Amount = amount;
        Price = price;
        Description = description;
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public String getAmount() {
        return Amount;
    }

    public String getPrice() {
        return Price;
    }

    public String getDescription() {
        return Description;
    }

    public String getCategory() {
        return Category;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
