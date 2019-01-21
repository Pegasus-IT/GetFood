package io.getfood.models;

import android.graphics.Color;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;

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
    private ArrayList<SerializableListItem> items;

    /**
     * The id of the user this shopping list is created by, this will be used to check whether or not the user can edit/delete this list
     */
    private String createdBy;

    /**
     * Creates an Shopping list model
     * @param _id the id of the list
     * @param listName title
     * @param date created date
     * @param color background color
     * @param totalItems amount of items
     * @param completedItems amount of completed items
     * @param createdBy id of user this list is created by
     * @param items all items in the Shopping List
     */
    public ShoppingList(@Nullable String _id, String listName, String date, int color, int totalItems, int completedItems, String createdBy, ArrayList<SerializableListItem> items) {
        this._id = _id;
        this.listName = listName;
        this.date = date;
        this.color = color;
        this.totalItems = totalItems;
        this.completedItems = completedItems;
        this.createdBy = createdBy;
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
     * Finds the id of the user this Shopping List is created by
     * @return user id
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set the creator of this list (user id)
     * @param createdBy user id
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * All items in the Shopping List
     * @return all shopping list items
     */
    public ArrayList<SerializableListItem> getItems() {
        return items;
    }

    /**
     * Set the items currently in the Shopping List
     * @param items shopping list items
     */
    public void setItems(ArrayList<SerializableListItem> items) {
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

        ArrayList<SerializableListItem> serializableListItems = new ArrayList<>();

        int completedCount = 0;
        for (ListItem listItem : listModel.getItems()) {
            SerializableListItem serializableListItem = new SerializableListItem();
            serializableListItem.setId(listItem.getId());
            serializableListItem.setName(listItem.getName());
            serializableListItem.setParentId(listItem.getParentId());
            serializableListItem.setChecked(listItem.isChecked());
            serializableListItem.setCheckedAt(listItem.getCheckedAt());

            if(listItem.isChecked()) {
                completedCount++;
            }

            serializableListItems.add(serializableListItem);
        }

        return new ShoppingList(
                listModel.getId(),
                listModel.getTitle(),
                dateTime.toString(formatter),
                Color.parseColor(listModel.getColor()),
                serializableListItems.size(),
                completedCount,
                listModel.getCreatedBy().getId(),
                serializableListItems
        );
    }
}
