package com.example.amam.dairy;

public class Market {
    private String datePublish, milkQuantity, contactPerson, farmerName, farmerId, Price;

    public Market() {
    }

    public Market(String datePublish, String milkQuantity, String contactPerson, String farmerName, String farmerId, String price) {
        this.datePublish = datePublish;
        this.milkQuantity = milkQuantity;
        this.contactPerson = contactPerson;
        this.farmerName = farmerName;
        this.farmerId = farmerId;
        Price = price;
    }

    public String getDatePublish() {
        return datePublish;
    }

    public void setDatePublish(String datePublish) {
        this.datePublish = datePublish;
    }

    public String getMilkQuantity() {
        return milkQuantity;
    }

    public void setMilkQuantity(String milkQuantity) {
        this.milkQuantity = milkQuantity;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}



