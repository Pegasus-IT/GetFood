package io.getfood.modules.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.getfood.R;
import io.getfood.models.ShoppingList;

public class HomeListAdapter extends ArrayAdapter<ShoppingList> {

    private Context context;
    private List<ShoppingList> shoppingLists;

    /**
     * @param context context
     * @param list all ShoppingLists
     */
    public HomeListAdapter(Context context, ArrayList<ShoppingList> list) {
        super(context, 0 , list);

        this.context = context;
        this.shoppingLists = list;
    }

    /**
     * @inheritDoc
     * @param position
     * @param itemView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, View itemView, @NonNull ViewGroup parent) {
        View listItem = itemView;

        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.item_home_list, parent, false);

        ShoppingList shoppingList = shoppingLists.get(position);
        Drawable background = listItem.getBackground();

        TextView titleText = listItem.findViewById(R.id.text_home_list_title);
        titleText.setText(shoppingList.getListName());

        TextView dateText = listItem.findViewById(R.id.text_home_list_date);
        dateText.setText(shoppingList.getDate());

        TextView itemCountText = listItem.findViewById(R.id.text_home_list_count);
        itemCountText.setText(shoppingList.getCount());

        ((GradientDrawable) background.mutate()).setColor(shoppingList.getColor());

        return listItem;
    }
}
