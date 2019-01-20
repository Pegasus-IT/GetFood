package io.getfood.modules.auth.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.getfood.R;
import io.getfood.data.swagger.models.User;
import io.getfood.modules.BaseFragment;
import io.getfood.modules.auth.login.LoginActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfileFragment extends BaseFragment implements ProfileContract.View {

    private ProfileContract.Presenter profilePresenter;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @BindView(R.id.user_initials_block)
    TextView userInitials;
    @BindView(R.id.profile_full_name)
    TextView fullName;
    @BindView(R.id.profile_email)
    EditText usernameInput;
    @BindView(R.id.profile_password)
    EditText passwordInput;
    @BindView(R.id.profile_firstname)
    EditText firstNameInput;
    @BindView(R.id.profile_lastname)
    EditText lastNameInput;
    @BindView(R.id.update_account)
    Button updateButton;
    @BindView(R.id.delete_account)
    TextView deleteText;

    @OnTextChanged(R.id.profile_email)
    void onProfileEmailChange() {
        validate();
    }

    @OnTextChanged(R.id.profile_firstname)
    void onProfileFirstNameChange() {
        validate();
    }

    @OnTextChanged(R.id.profile_lastname)
    void onProfileLastNameChange() {
        validate();
    }

    @OnClick(R.id.update_account)
    void onUpdateAccountClick() {
        profilePresenter.update(
                usernameInput.getText().toString(),
                passwordInput.getText().toString(),
                firstNameInput.getText().toString(),
                lastNameInput.getText().toString()
        );
    }

    @OnClick(R.id.delete_account)
    void onDeleteAccountClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Yes", (dialog, which) -> profilePresenter.delete())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .show();
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void setPresenter(@NonNull ProfileContract.Presenter presenter) {
        profilePresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        profilePresenter.start();
    }

    @Override
    public void onProfileLoad(User user) {
        mHandler.post(() -> {
            userInitials.setText(user.getInitials());
            fullName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
            usernameInput.setText(user.getEmail());
            firstNameInput.setText(user.getFirstName());
            lastNameInput.setText(user.getLastName());
            validate();
        });
    }

    @Override
    public void onProfileUpdate(User user) {
        showSnackbar("Profile saved!", R.color.color_success);
    }

    @Override
    public void onProfileDeleted() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra("disableAutoAuthStart", "yes");
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void setSignUpEnabled(boolean state) {
        updateButton.setEnabled(state);
    }

    private void validate() {
        profilePresenter.validate(
          usernameInput.getText().toString(),
          firstNameInput.getText().toString(),
          lastNameInput.getText().toString()
        );
    }
}
