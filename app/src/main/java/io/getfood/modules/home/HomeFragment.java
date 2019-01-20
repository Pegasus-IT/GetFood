package io.getfood.modules.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.getfood.R;
import io.getfood.data.swagger.models.ListModel;
import io.getfood.models.ShoppingList;
import io.getfood.modules.BaseFragment;
import io.getfood.modules.shopping_list.ShoppingListActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomeFragment extends BaseFragment implements HomeContract.View {

    @BindView(R.id.home_create_list_fab)
    FloatingActionButton createListButton;
    @BindView(R.id.home_listview)
    ListView listView;

    private HomeListAdapter homeListAdapter;

    private HomeContract.Presenter homePresenter;
    private ArrayList<ShoppingList> shoppingLists;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void setPresenter(@NonNull HomeContract.Presenter presenter) {
        homePresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);

        View topPadding = new View(getContext());
        topPadding.setMinimumHeight(20);

        shoppingLists = new ArrayList<>();
        homeListAdapter = new HomeListAdapter(getContext(), shoppingLists);
        listView.setAdapter(homeListAdapter);
        listView.addHeaderView(topPadding);
        listView.setOnItemClickListener((adapterView, listItemView, i, l) -> {
            ShoppingList selectedShoppingList = (ShoppingList) adapterView.getItemAtPosition(i);
            openShoppingListItem(selectedShoppingList);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        homePresenter.start();
    }

    @Override
    public void setLists(ArrayList<ListModel> lists) {
        System.out.println(lists);
        shoppingLists.add(new ShoppingList("Feest", "15 Jan", Color.parseColor("#427CFB"), 8, 2));
        shoppingLists.add(new ShoppingList("Week lijst", "15 Jan", Color.parseColor("#00D157"), 18, 6));
        shoppingLists.add(new ShoppingList("Weekend", "15 Jan", Color.parseColor("#FFBB00"), 3, 1));
        shoppingLists.add(new ShoppingList("Feest", "15 Jan", Color.parseColor("#427CFB"), 12, 7));
        shoppingLists.add(new ShoppingList("Week lijst", "15 Jan", Color.parseColor("#00D157"), 9, 4));
        shoppingLists.add(new ShoppingList("Weekend", "15 Jan", Color.parseColor("#FFBB00"), 22, 16));
        homeListAdapter.notifyDataSetChanged();
    }

    private void openShoppingListItem(ShoppingList item) {
        Intent intent = new Intent(getContext(), ShoppingListActivity.class);
        intent.putExtra("selectedShoppingListItem", item);
        openActivity(intent, true);
    }

//    @Override
//    public void setUsernameText(String text) {
//        usernameInput.setText(text);
//    }
//
//    @Override
//    public void setPasswordText(String text) {
//        passwordInput.setText(text);
//    }
//
//    @Override
//    public void setFirstnameText(String text) {
//        firstnameInput.setText(text);
//    }
//
//    @Override
//    public void setLastnameText(String text) {
//        lastnameInput.setText(text);
//    }
//
//    @Override
//    public void setUpdateButtonEnabled(boolean state) {
//        updateButton.setEnabled(state);
//    }
}
