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
import io.getfood.modules.home.HomeActivity;

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

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @OnTextChanged(R.id.email)
    public void onEmailChange() {
        validate();
    }

    @OnTextChanged(R.id.password)
    public void onPasswordChange() {
        validate();
    }

    @OnTextChanged(R.id.first_name)
    public void onFirstNameChange() {
        validate();
    }

    @OnTextChanged(R.id.last_name)
    public void onLastNameChange() {
        validate();
    }

    @OnClick(R.id.to_login)
    public void onToLoginButtonPressed() {
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @OnClick(R.id.register)
    public void onRegisterButtonClick() {
        this.signUpPresenter.register(
                emailInput.getText().toString(),
                passwordInput.getText().toString(),
                firstnameInput.getText().toString(),
                lastnameInput.getText().toString()
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        signUpPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull SignUpContract.Presenter presenter) {
        signUpPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onRegister(User userControllerAuthenticate) {
        System.out.println(userControllerAuthenticate);
        startActivity(new Intent(getContext(), HomeActivity.class));
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void setRegisterButtonEnabled(boolean state) {
        this.registerButton.setEnabled(state);
    }

    private void validate() {
        this.signUpPresenter.validate(
                emailInput.getText().toString(),
                passwordInput.getText().toString(),
                firstnameInput.getText().toString(),
                lastnameInput.getText().toString()
        );
    }
}
