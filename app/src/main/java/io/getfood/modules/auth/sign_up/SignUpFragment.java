package io.getfood.modules.auth.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
import io.getfood.modules.getting_started.GettingStartedActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class SignUpFragment extends BaseFragment implements SignUpContract.View {

    @BindView(R.id.email)
    EditText emailInput;
    @BindView(R.id.password)
    EditText passwordInput;
    @BindView(R.id.first_name)
    EditText firstnameInput;
    @BindView(R.id.last_name)
    EditText lastnameInput;
    @BindView(R.id.register)
    Button registerButton;
    private SignUpContract.Presenter signUpPresenter;

    /**
     * Creates a new instance
     *
     * @return instance
     */
    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    /**
     * Validate email
     */
    @OnTextChanged(R.id.email)
    public void onEmailChange() {
        validate();
    }

    /**
     * Validate password
     */
    @OnTextChanged(R.id.password)
    public void onPasswordChange() {
        validate();
    }

    /**
     * Validate firstName
     */
    @OnTextChanged(R.id.first_name)
    public void onFirstNameChange() {
        validate();
    }

    /**
     * Validate lastName
     */
    @OnTextChanged(R.id.last_name)
    public void onLastNameChange() {
        validate();
    }

    /**
     * Navigate to the login activity
     */
    @OnClick(R.id.to_login)
    public void onToLoginButtonPressed() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra("disableAutoAuthStart", "yes");
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * Validates the given credentials
     */
    @OnClick(R.id.register)
    public void onRegisterButtonClick() {
        this.signUpPresenter.register(
                emailInput.getText().toString(),
                passwordInput.getText().toString(),
                firstnameInput.getText().toString(),
                lastnameInput.getText().toString()
        );
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onResume() {
        super.onResume();
        signUpPresenter.start();
    }

    /**
     * @param presenter given presenter
     * @inheritDoc
     */
    @Override
    public void setPresenter(@NonNull SignUpContract.Presenter presenter) {
        signUpPresenter = checkNotNull(presenter);
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
        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * @param userControllerAuthenticate user
     * @inheritDoc
     */
    @Override
    public void onRegister(User userControllerAuthenticate) {
        System.out.println(userControllerAuthenticate);
        openActivity(new Intent(getContext(), GettingStartedActivity.class), false);
    }

    /**
     * @param state check
     * @inheritDoc
     */
    @Override
    public void setRegisterButtonEnabled(boolean state) {
        this.registerButton.setEnabled(state);
    }

    /**
     * Validates the username, password, firstName and lastName
     */
    private void validate() {
        this.signUpPresenter.validate(
                emailInput.getText().toString(),
                passwordInput.getText().toString(),
                firstnameInput.getText().toString(),
                lastnameInput.getText().toString()
        );
    }
}
