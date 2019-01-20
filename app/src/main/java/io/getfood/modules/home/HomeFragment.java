package io.getfood.modules.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private HomeContract.Presenter homePresenter;
    private ArrayList<ShoppingList> shoppingLists;

    /**
     * Creates a new instance
     *
     * @return instance
     */
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

        boolean openCreateList = false;
        Activity activity = getActivity();
        if (activity != null) {
            Bundle bundle = activity.getIntent().getExtras();

            if (bundle != null) {
                String openCreateListString = bundle.getString("openCreateList");
                openCreateList = openCreateListString != null;
            }
        }

        if ( openCreateList ) {
            createListInput();
        }

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
        mHandler.post(homeListAdapter::notifyDataSetChanged);

        createListButton.setOnClickListener(homeView -> createListInput());

        return view;
    }

    public void createListInput() {
        final EditText listName = new EditText(getContext());
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.home_create_list)
                .setMessage(R.string.home_create_list_description)
                .setView(listName)
                .setPositiveButton("Create", (dialog, whichButton) -> {
                    if (!listName.getText().toString().isEmpty()) {
                        homePresenter.createNewList(listName.getText().toString());
                    } else {
                        createListInput();
                    }
                })
                .setNegativeButton("Cancel", (dialog, whichButton) -> {
                })
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        homePresenter.start();
    }

    @Override
    public void setLists(ArrayList<ListModel> lists) {
        mHandler.post(() -> {
            System.out.println(lists);
            shoppingLists.add(new ShoppingList("Feest", "15 Jan", Color.parseColor("#427CFB"), 8, 2));
            shoppingLists.add(new ShoppingList("Week lijst", "15 Jan", Color.parseColor("#00D157"), 18, 6));
            shoppingLists.add(new ShoppingList("Weekend", "15 Jan", Color.parseColor("#FFBB00"), 3, 1));
            shoppingLists.add(new ShoppingList("Feest", "15 Jan", Color.parseColor("#427CFB"), 12, 7));
            shoppingLists.add(new ShoppingList("Week lijst", "15 Jan", Color.parseColor("#00D157"), 9, 4));
            shoppingLists.add(new ShoppingList("Weekend", "15 Jan", Color.parseColor("#FFBB00"), 22, 16));
            homeListAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onListCreate(ListModel listModel) {
        System.out.println("Created list");
        System.out.println(listModel.getTitle());
        showSnackbar("List created!", R.color.color_success);
    }

    private void openShoppingListItem(ShoppingList item) {
        Intent intent = new Intent(getContext(), ShoppingListActivity.class);
        intent.putExtra("selectedShoppingListItem", item);
        openActivity(intent, true);
    }
}
