package com.example.amam.dairy;

public class Appointment {
    public String  datetime, vetName, vetTitle, vetPhone, vetPrice;

    public Appointment() {
    }

    public Appointment( String datetime, String vetName, String vetTitle, String vetPhone, String vetPrice) {

        this.datetime = datetime;
        this.vetName = vetName;
        this.vetTitle = vetTitle;
        this.vetPhone = vetPhone;
        this.vetPrice = vetPrice;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }

    public String getVetTitle() {
        return vetTitle;
    }

    public void setVetTitle(String vetTitle) {
        this.vetTitle = vetTitle;
    }

    public String getVetPhone() {
        return vetPhone;
    }

    public void setVetPhone(String vetPhone) {
        this.vetPhone = vetPhone;
    }

    public String getVetPrice() {
        return vetPrice;
    }

    public void setVetPrice(String vetPrice) {
        this.vetPrice = vetPrice;
    }
}
