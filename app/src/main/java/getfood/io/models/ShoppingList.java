package getfood.io.models;

import java.io.Serializable;
import java.text.MessageFormat;

public class ShoppingList implements Serializable {

    private String listName;
    private String date;
    private int color;

    public ShoppingList(String listName, String date, int color) {
        this.listName = listName;
        this.date = date;
        this.color = color;
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
}
