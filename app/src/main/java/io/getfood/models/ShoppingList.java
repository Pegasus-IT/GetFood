package io.getfood.models;

import java.io.Serializable;
import java.text.MessageFormat;

public class ShoppingList implements Serializable {

    private String listName;
    private String date;
    private int color;
    private int totalItems;
    private int completedItems;

    public ShoppingList(String listName, String date, int color, int totalItems, int completedItems) {
        this.listName = listName;
        this.date = date;
        this.color = color;
        this.totalItems = totalItems;
        this.completedItems = completedItems;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getDate() {
        String datePrefix = "Created on";
        return MessageFormat.format("{0} {1}", datePrefix, this.date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getCompletedItems() {
        return completedItems;
    }

    public void setCompletedItems(int completedItems) {
        this.completedItems = completedItems;
    }

    public String getCount() {
        return "" + completedItems + "/" + totalItems;
    }
}
