package getfood.io.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import getfood.io.R;
import getfood.io.models.ListItem;
import getfood.io.models.ShoppingList;


public class ShoppingListAdapter extends ArrayAdapter<ListItem> {

    private Context context;
    private List<ListItem> shoppingListItems;

    private int mainItemColor, disabledItemColor;

    public ShoppingListAdapter(Context context, ArrayList<ListItem> items) {
        super(context, 0 , items);

        this.context = context;
        this.shoppingListItems = items;

        this.mainItemColor = context.getResources().getColor(R.color.getfood_main_blue);
        this.disabledItemColor = context.getResources().getColor(R.color.colorBlack);

        Collections.sort(this.shoppingListItems, (o1, o2) -> o1.isChecked().compareTo(o2.isChecked()));
    }

    @NonNull
    @Override
    public View getView(int position, View itemView, @NonNull ViewGroup parent) {
        View listItem = itemView;

        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.item_shopping_list, parent,false);

        ListItem shoppingItem = shoppingListItems.get(position);

        TextView titleText = listItem.findViewById(R.id.text_shopping_list_title);
        titleText.setText(shoppingItem.getName());

        TextView valueText = listItem.findViewById(R.id.text_shopping_list_value);
        valueText.setText("?");

        CheckBox checkBox = listItem.findViewById(R.id.checkbox_shopping_list);
        checkBox.setChecked(shoppingItem.isChecked());

        View finalListItem = listItem;

        listItem.setOnClickListener(view -> checkBox.setChecked(!checkBox.isChecked()));
        checkBox.setOnCheckedChangeListener((checkbox, isChecked) -> updateSingleItem(finalListItem, isChecked));

        updateSingleItem(finalListItem, shoppingItem.isChecked());

        return listItem;
    }

    private void updateSingleItem(View listItem, boolean isChecked) {
        TextView titleText = listItem.findViewById(R.id.text_shopping_list_title);
        TextView valueText = listItem.findViewById(R.id.text_shopping_list_value);

        if(isChecked) {
            titleText.setTextColor(disabledItemColor);
            valueText.setTextColor(disabledItemColor);
            titleText.setPaintFlags(titleText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            titleText.setTextColor(mainItemColor);
            valueText.setTextColor(mainItemColor);
            titleText.setPaintFlags(0);
        }

        //TODO: Update item in API logic
    }
}
