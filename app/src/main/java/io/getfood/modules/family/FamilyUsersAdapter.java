package io.getfood.modules.family;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.getfood.R;
import io.getfood.data.swagger.models.User;


public class FamilyUsersAdapter extends ArrayAdapter<User> {

    private Context context;
    private ArrayList<User> users;

    /**
     * @param context context
     * @param users   all family members
     */
    public FamilyUsersAdapter(Context context, List<User> users) {
        super(context, 0, users);

        this.context = context;
        this.users = new ArrayList<>(users);
    }

    /**
     * @param position
     * @param itemView
     * @param parent
     * @return
     * @inheritDoc
     */
    @NonNull
    @Override
    public View getView(int position, View itemView, @NonNull ViewGroup parent) {
        View listItem = itemView;

        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.family_user_item, parent, false);

        User user = this.users.get(position);

        TextView initals = listItem.findViewById(R.id.family_user_initials_block);
        initals.setText(user.getInitials());

        TextView firstName = listItem.findViewById(R.id.family_first_name);
        firstName.setText(user.getFirstName());

        return listItem;
    }
}
