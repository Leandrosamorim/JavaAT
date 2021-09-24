/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.infnet.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author 55229
 */
public class Quotation {

        private String Id;
        private Product Product;
        private int Amount;
        private double Total;
    
        public Quotation() {
        }

        public Quotation(Product product, int amount){
            this.Id = UUID.randomUUID().toString();
            this.Product = product;
            this.Amount = amount;
            this.Total = Calculate();
        }

        public Quotation(String id, String product, int amount, double total){
            this.Id = id;
            this.Product = new Product(product, amount, total/amount);
            this.Amount = amount;
            this.Total = total;
        }

        // public Quotation(String id, String products, double total) {
        //     this.Id = UUID.randomUUID().toString();
        //     this.Total = total;
        //     var productList = products.split("],[");
        //     for(int i = 0; i<productList.length ; i++)
        //     {
        //         var productString = productList[i].split(",");
        //         this.Products.add(new Product(Integer.parseInt(productString[0]), productString[1], Integer.parseInt(productString[2]),Double.parseDouble(productString[3])));
        //     }
        // }

        public Quotation(Product product) {
            this.Id = UUID.randomUUID().toString();
            this.Product = product;
            this.SetTotal();
        }
    
        public String GetId() {
            return this.Id.toString();
        }
    
        public Product GetProduct() {
            return this.Product;
        }
        public int GetAmount() {
            return this.Amount;
        }
    
        public double GetTotal() {
            return this.Total;
        }
    
        public void SetTotal() {
            this.Total = this.Calculate();
        }
        public void SetProduct(Product product) {
            this.Product = product;
        }
        public void SetAmount(int amount) {
            this.Amount = amount;
        }

        public double Calculate(){
            return this.GetAmount() * this.GetProduct().GetPrice();
        }

        public String[] GetContent(){
            String[] strArr = {(this.GetId()), this.GetProduct().GetName(), Integer.toString(this.Amount), Double.toString(this.GetProduct().GetPrice()), Double.toString(this.Total)};
            return strArr;
        }


    }