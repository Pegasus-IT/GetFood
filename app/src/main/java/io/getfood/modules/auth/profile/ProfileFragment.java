package io.getfood.modules.auth.profile;

import android.os.Bundle;
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
import io.getfood.R;
import io.getfood.modules.BaseFragment;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfileFragment extends BaseFragment implements ProfileContract.View {

    @BindView(R.id.profile_email)
    EditText usernameInput;
    @BindView(R.id.profile_password)
    EditText passwordInput;
    @BindView(R.id.profile_firstname)
    EditText firstnameInput;
    @BindView(R.id.profile_lastname)
    EditText lastnameInput;
    @BindView(R.id.update_account)
    Button updateButton;
    @BindView(R.id.delete_account)
    TextView deleteText;

    private ProfileContract.Presenter profilePresenter;

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
