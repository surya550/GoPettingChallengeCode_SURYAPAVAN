package com.suryapavan.gopetting.challengecode.database;
/**
 * Created by surya on 4/4/2017.
 */

public class DataCartObject {


    int Id, Quantity;
    String Name, EndDate, Icon;

    public DataCartObject() {
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getEndDate() {
        return EndDate;
    }

    public String getIcon() {
        return Icon;
    }

    public int getQuantity() {
        return Quantity;
    }



    public void setId(int Id) {
        this.Id = Id;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public void setIcon(String Icon) {
        this.Icon = Icon;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }



}
