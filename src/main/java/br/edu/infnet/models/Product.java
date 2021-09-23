/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.infnet.models;

/**
 *
 * @author 55229
 */
public class Product {

    private int Id;
    private String Name;
    private int Amount;
    private double Price;
    private double TotalPrice;

    public Product(String name, int amount, double price) {
        this.Name = name;
        this.Amount = amount;
        this.Price = price;
        this.TotalPrice = amount * price;
    }
    public Product(int id, String name, int amount, double price) {
        this.Id = id;
        this.Name = name;
        this.Amount = amount;
        this.Price = price;
        this.TotalPrice = amount * price;
    }

    public Product(){};

    public int GetId() {
        return this.Id;
    }

    public String GetName() {
        return this.Name;
    }

    public int GetAmount() {
        return this.Amount;
    }

    public double GetPrice() {
        return this.Price;
    }

    public double GetTotalPrice() {
        return this.TotalPrice;
    }

    public void SetAmount(int newAmount) {
        this.Amount = newAmount;
    }

    public void SetPrice(double newPrice) {
        this.Price = newPrice;
    }
    
    public String[] GetContent(){
        String[] strArr = {Integer.toString(this.GetId()), this.GetName(), Integer.toString(this.Amount), Double.toString(this.Price), Double.toString(this.TotalPrice)};
        return strArr;
    }
}
