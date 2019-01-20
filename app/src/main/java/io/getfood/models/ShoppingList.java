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

    private String _id;
    private String listName;
    private String date;
    private int color;
    private int totalItems;
    private int completedItems;
    private ArrayList<SeriazableListItem> items;

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

    public ArrayList<SeriazableListItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<SeriazableListItem> items) {
        this.items = items;
    }

    public static ArrayList<ShoppingList> parse(ArrayList<ListModel> listModels) {
        ArrayList<ShoppingList> shoppingLists = new ArrayList<>();

        for (ListModel list : listModels) {
            shoppingLists.add(ShoppingList.parse(list));
        }

        return shoppingLists;
    }

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
