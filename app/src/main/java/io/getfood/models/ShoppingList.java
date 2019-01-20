package io.getfood.models;

import java.io.Serializable;
import java.text.MessageFormat;

public class ShoppingList implements Serializable {

    /**
     * Shopping list title
     */
    private String listName;

    /**
     * Shopping list creation date
     */
    private String date;

    /**
     * Color for the shopping list background
     */
    private int color;

    /**
     * Total amount of items in the shopping list
     */
    private int totalItems;

    /**
     * Total amount of completed items in the shopping list
     */
    private int completedItems;

    /**
     * Creates an Shopping list model
     * @param listName title
     * @param date created date
     * @param color background color
     * @param totalItems amount of items
     * @param completedItems amount of completed items
     */
    public ShoppingList(String listName, String date, int color, int totalItems, int completedItems) {
        this.listName = listName;
        this.date = date;
        this.color = color;
        this.totalItems = totalItems;
        this.completedItems = completedItems;
    }

    /**
     * Gets the shopping list title
     * @return shopping list title
     */
    public String getListName() {
        return listName;
    }

    /**
     * Sets the shopping list title
     * @param listName list title
     */
    public void setListName(String listName) {
        this.listName = listName;
    }

    /**
     * Gets the shopping list creation date
     * @return shopping list creation date
     */
    public String getDate() {
        String datePrefix = "Created on";
        return MessageFormat.format("{0} {1}", datePrefix, this.date);
    }

    /**
     * Sets the shopping list creation date
     * @param date creation date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets shopping list color
     * @return shopping list color
     */
    public int getColor() {
        return color;
    }

    /**
     * Sets shopping list color
     * @param color for the background
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Get total amount of items
     * @return total amount of items
     */
    public int getTotalItems() {
        return totalItems;
    }

    /**
     * Sets the total amount of items
     * @param totalItems total item count
     */
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    /**
     * Get the completed item count
     * @return completed item count
     */
    public int getCompletedItems() {
        return completedItems;
    }

    /**
     * Set the completed item count
     * @param completedItems total completed item count
     */
    public void setCompletedItems(int completedItems) {
        this.completedItems = completedItems;
    }

    /**
     * Creates a string of the completed items and total items.
     * For example: 3/9
     * @return completed and total item string
     */
    public String getCount() {
        return "" + completedItems + "/" + totalItems;
    }
}
