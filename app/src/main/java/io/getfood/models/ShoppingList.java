package io.getfood.models;

import android.graphics.Color;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import io.getfood.data.swagger.models.ListItem;
import io.getfood.data.swagger.models.ListModel;

public class ShoppingList implements Serializable {

    /**
     * Shopping List id
     */
    private String _id;

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
     * Shopping List Items (which are serializable because ShoppingList is also Serializable)
     */
    private ArrayList<SeriazableListItem> items;

    /**
     * Creates an Shopping list model
     * @param listName title
     * @param date created date
     * @param color background color
     * @param totalItems amount of items
     * @param completedItems amount of completed items
     */
    public ShoppingList(@Nullable String _id, String listName, String date, int color, int totalItems, int completedItems, ArrayList<SeriazableListItem> items) {
        this._id = _id;
        this.listName = listName;
        this.date = date;
        this.color = color;
        this.totalItems = totalItems;
        this.completedItems = completedItems;
        this.items = items;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
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

    /**
     * All items in the Shopping List
     * @return all shopping list items
     */
    public ArrayList<SeriazableListItem> getItems() {
        return items;
    }

    /**
     * Set the items currently in the Shopping List
     * @param items shopping list items
     */
    public void setItems(ArrayList<SeriazableListItem> items) {
        this.items = items;
    }

    /**
     * Parse listModels and convert them to a Shopping LIst
     *
     * @param listModels list models array
     * @return parsed shopping list array
     */
    public static ArrayList<ShoppingList> parse(ArrayList<ListModel> listModels) {
        ArrayList<ShoppingList> shoppingLists = new ArrayList<>();

        for (ListModel list : listModels) {
            shoppingLists.add(ShoppingList.parse(list));
        }

        return shoppingLists;
    }

    /**
     * Parse the ListModel and convert it to ShoppingList
     * @param listModel list to be parsed
     * @return parsed shopping list
     */
    public static ShoppingList parse(ListModel listModel) {
        DateTime dateTime = new DateTime( listModel.getCreatedDate() ) ;
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendDayOfMonth(1)
                .appendLiteral(' ')
                .appendMonthOfYearShortText()
                .appendLiteral(' ')
                .appendYear(4, 4)
                .toFormatter();

        ArrayList<SeriazableListItem> seriazableListItems = new ArrayList<>();

        int completedCount = 0;
        for (ListItem listItem : listModel.getItems()) {
            SeriazableListItem seriazableListItem = new SeriazableListItem();
            seriazableListItem.setId(listItem.getId());
            seriazableListItem.setName(listItem.getName());
            seriazableListItem.setParentId(listItem.getParentId());
            seriazableListItem.setChecked(listItem.isChecked());
            seriazableListItem.setCheckedAt(listItem.getCheckedAt());

            if(listItem.isChecked()) {
                completedCount++;
            }

            seriazableListItems.add(seriazableListItem);
        }

        return new ShoppingList(
                listModel.getId(),
                listModel.getTitle(),
                dateTime.toString(formatter),
                Color.parseColor(listModel.getColor()),
                seriazableListItems.size(),
                completedCount,
                seriazableListItems
        );
    }
}
