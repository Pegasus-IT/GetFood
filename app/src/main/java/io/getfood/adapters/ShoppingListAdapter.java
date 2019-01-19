package io.getfood.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import io.getfood.R;
import io.getfood.models.ShoppingList;


public class ShoppingListAdapter extends ArrayAdapter<ShoppingList> {

    private Context context;
    private List<ShoppingList> shoppingLists;

    public ShoppingListAdapter(Context context, ArrayList<ShoppingList> list) {
        super(context, 0 , list);

        this.context = context;
        this.shoppingLists = list;
    }

    @NonNull
    @Override
    public View getView(int position, View itemView, @NonNull ViewGroup parent) {
        View listItem = itemView;

        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.item_shopping_list, parent,false);


        return listItem;
    }
}
