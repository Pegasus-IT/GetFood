package io.getfood.modules.shopping_list;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.getfood.R;
import io.getfood.data.swagger.models.ListItem;
import io.getfood.models.SeriazableListItem;
import io.getfood.models.ShoppingList;
import io.getfood.modules.BaseFragment;

import static com.google.common.base.Preconditions.checkNotNull;

public class ShoppingListFragment extends BaseFragment implements ShoppingListContract.View {

    @BindView(R.id.shopping_listview)
    ListView listView;
    @BindView(R.id.shopping_list_create_fab)
    FloatingActionButton createItem;

    private ShoppingList selectedShoppingList;
    private ShoppingListAdapter shoppingListAdapter;
    private Toolbar toolbar;
    private RelativeLayout viewContainer;
    private ArrayList<SeriazableListItem> listItems = new ArrayList<>();

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ShoppingListContract.Presenter shoppingListPresenter;

    /**
     * Creates a new instance
     *
     * @return instance
     */
    public static ShoppingListFragment newInstance() {
        return new ShoppingListFragment();
    }

    /**
     * @param presenter given presenter
     * @inheritDoc
     */
    @Override
    public void setPresenter(@NonNull ShoppingListContract.Presenter presenter) {
        shoppingListPresenter = checkNotNull(presenter);
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     * @inheritDoc
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopping_list_fragment, container, false);
        ButterKnife.bind(this, view);

        viewContainer = getActivity().findViewById(R.id.shopping_list_container);
        toolbar = getActivity().findViewById(R.id.toolbar);

        Intent intent = getActivity().getIntent();
        this.selectedShoppingList = (ShoppingList) intent.getSerializableExtra("selectedShoppingListItem");

        return view;
    }

    /**
     * @param view
     * @param savedInstanceState
     * @inheritDoc
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        shoppingListPresenter.load(selectedShoppingList.getId());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onResume() {
        super.onResume();
        shoppingListPresenter.start();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void createItemInput() {
        final EditText itemName = new EditText(getContext());
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.shopping_list_create_item_title)
                .setMessage(R.string.shopping_list_create_item_description)
                .setView(itemName)
                .setPositiveButton("Add", (dialog, whichButton) -> {
                    if (!itemName.getText().toString().isEmpty()) {
                        shoppingListPresenter.createNewListItem(itemName.getText().toString(), selectedShoppingList);
                    } else {
                        createItemInput();
                    }
                })
                .setNegativeButton("Cancel", (dialog, whichButton) -> {
                })
                .show();
    }

    @Override
    public void onLoad(ShoppingList shoppingList) {
        mHandler.post(() -> {
            viewContainer.setBackgroundColor(selectedShoppingList.getColor());
            getActivity().getWindow().setStatusBarColor(selectedShoppingList.getColor());
            toolbar.setTitle(selectedShoppingList.getListName());
            toolbar.setSubtitle(selectedShoppingList.getDate());
            toolbar.setTitleTextAppearance(getContext(), R.style.ToolbarTextAppearance_Title_White);
            toolbar.setSubtitleTextAppearance(getContext(), R.style.ToolbarTextAppearance_Subtitle_White);
            shoppingListAdapter = new ShoppingListAdapter(getContext(), listItems, selectedShoppingList, shoppingListPresenter);
            listView.setAdapter(shoppingListAdapter);
            listItems.clear();
            listItems.addAll(shoppingList.getItems());
            mHandler.post(shoppingListAdapter::notifyDataSetChanged);

            createItem.setOnClickListener(view12 -> createItemInput());
        });
    }
}
