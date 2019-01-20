package io.getfood.modules.shopping_list;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.getfood.R;
import io.getfood.data.swagger.models.ListItem;
import io.getfood.models.SeriazableListItem;
import io.getfood.models.ShoppingList;


public class ShoppingListAdapter extends ArrayAdapter<SeriazableListItem> {

    private Context context;
    private List<SeriazableListItem> shoppingListItems;
    private ShoppingListContract.Presenter presenter;
    private ShoppingList shoppingList;

    private int mainItemColor, disabledItemColor;

    public ShoppingListAdapter(Context context, ArrayList<SeriazableListItem> listItems, ShoppingList shoppingList, ShoppingListContract.Presenter presenter) {
        super(context, 0, listItems);

        this.context = context;
        this.shoppingListItems = listItems;
        this.presenter = presenter;
        this.shoppingList = shoppingList;

        this.mainItemColor = context.getResources().getColor(R.color.getfood_main_blue);
        this.disabledItemColor = context.getResources().getColor(R.color.colorBlack);
    }

    @NonNull
    @Override
    public View getView(int position, View itemView, @NonNull ViewGroup parent) {
        View listItem = itemView;

        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.item_shopping_list, parent, false);

        ListItem shoppingItem = shoppingListItems.get(position);

        TextView titleText = listItem.findViewById(R.id.text_shopping_list_title);
        titleText.setText(shoppingItem.getName());


        CheckBox checkBox = listItem.findViewById(R.id.checkbox_shopping_list);
        boolean isChecked = shoppingItem.isChecked();
        checkBox.setChecked(isChecked);

        View finalListItem = listItem;
        listItem.setOnClickListener((listView) -> {
            checkBox.setChecked(!shoppingItem.isChecked());
            updateSingleItem(shoppingItem, finalListItem, checkBox.isChecked(), true);
        });
        checkBox.setOnClickListener((checkbox) -> updateSingleItem(shoppingItem, finalListItem, checkBox.isChecked(), true));
        updateSingleItem(shoppingItem, finalListItem, shoppingItem.isChecked(), false);

        return listItem;
    }

    private void updateSingleItem(ListItem shoppingItem, View listItem, boolean isChecked, boolean update) {
        TextView titleText = listItem.findViewById(R.id.text_shopping_list_title);

        if (isChecked) {
            titleText.setTextColor(disabledItemColor);
            titleText.setPaintFlags(titleText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            titleText.setTextColor(mainItemColor);
            titleText.setPaintFlags(0);
        }

        if(update && isChecked != shoppingItem.isChecked()) {
            System.out.println("Should update!");
            System.out.println(isChecked);
            System.out.println(shoppingItem.isChecked());

            shoppingItem.setChecked(isChecked);
            presenter.setMarked(shoppingItem, shoppingList);
        }
        shoppingItem.setChecked(isChecked);
    }
}
