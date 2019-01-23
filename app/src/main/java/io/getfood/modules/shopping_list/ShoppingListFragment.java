package io.getfood.modules.shopping_list;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.getfood.R;
import io.getfood.data.local.Globals;
import io.getfood.models.SerializableListItem;
import io.getfood.models.ShoppingList;
import io.getfood.modules.BaseFragment;
import io.getfood.modules.home.HomeActivity;
import io.getfood.util.UserUtil;

import static android.content.Context.MODE_PRIVATE;
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
    private ArrayList<SerializableListItem> listItems = new ArrayList<>();

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ShoppingListContract.Presenter shoppingListPresenter;
    private SharedPreferences sharedPreferences;

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

        sharedPreferences = getActivity().getSharedPreferences(Globals.DEFAULT_PREFERENCE_SET, MODE_PRIVATE);

        viewContainer = getActivity().findViewById(R.id.shopping_list_container);
        toolbar = getActivity().findViewById(R.id.toolbar);

        Intent intent = getActivity().getIntent();
        this.selectedShoppingList = (ShoppingList) intent.getSerializableExtra("selectedShoppingListItem");
        setHasOptionsMenu(true);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.shopping_list_menu_action_edit:
                showUpdateListAlert();
                return false;
            case R.id.shopping_list_menu_action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to delete this list?")
                        .setPositiveButton("Yes", (dialog, which) -> shoppingListPresenter.delete(selectedShoppingList))
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                        .show();
                return true;
            case R.id.shopping_list_menu_action_clear:
                shoppingListPresenter.deleteCheckedItems(selectedShoppingList, listItems);
                return true;

            default:
                break;
        }

        return false;
    }

    @Override
    protected int getOptionsMenu() {
        return R.menu.toolbar_shopping_list_menu;
    }

    private void showUpdateListAlert() {
        final EditText listName = new EditText(getContext());
        listName.setText(selectedShoppingList.getListName());
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.home_update_list)
                .setMessage(R.string.home_list_description)
                .setView(listName)
                .setPositiveButton("Update", (dialog, whichButton) -> {
                    if (!listName.getText().toString().isEmpty()) {
                        selectedShoppingList.setListName(listName.getText().toString());
                        shoppingListPresenter.updateList(selectedShoppingList);
                    } else {
                        showUpdateListAlert();
                    }
                })
                .setNegativeButton("Cancel", (dialog, whichButton) -> dialog.cancel())
                .show();
    }

    @Override
    public void onLoad(ShoppingList shoppingList) {
        mHandler.post(() -> {
            int index = listView.getFirstVisiblePosition();
            View v = listView.getChildAt(0);
            int top = (v == null) ? 0 : (v.getTop() - listView.getPaddingTop());

            if (menu != null) {
                MenuItem editItem = menu.findItem(R.id.shopping_list_menu_action_edit);
                MenuItem delete = menu.findItem(R.id.shopping_list_menu_action_delete);

                if (UserUtil.isLoggedIn(sharedPreferences) && UserUtil.getUser(sharedPreferences).getId().equals(shoppingList.getCreatedBy())) {
                    editItem.setVisible(true);
                    delete.setVisible(true);
                } else {
                    editItem.setVisible(false);
                    delete.setVisible(false);
                }
            }

            viewContainer.setBackgroundColor(selectedShoppingList.getColor());
            if (getActivity() != null) {
                getActivity().getWindow().setStatusBarColor(selectedShoppingList.getColor());
            }
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

            listView.setSelectionFromTop(index, top);
        });
    }

    @Override
    public void onDelete() {
        try {
            mHandler.post(() -> showSnackbar("List deleted!", R.color.color_success));
            TimeUnit.MILLISECONDS.sleep(1000);
            openActivity(new Intent(getContext(), HomeActivity.class), false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
