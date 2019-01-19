package io.getfood.modules.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jesusm.kfingerprintmanager.KFingerprintManager;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.getfood.R;
import io.getfood.data.local.Globals;
import io.getfood.data.swagger.models.User;
import io.getfood.modules.BaseFragment;
import io.getfood.modules.auth.sign_up.SignUpActivity;
import io.getfood.modules.getting_started.GettingStartedActivity;
import io.getfood.modules.home.HomeActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginFragment extends BaseFragment implements LoginContract.View {

    @BindView(R.id.username)
    EditText usernameInput;
    @BindView(R.id.password)
    EditText passwordInput;
    @BindView(R.id.button_login)
    Button loginButton;
    @BindView(R.id.button_fingerprint)
    ImageButton fingerprintButton;
    private LoginContract.Presenter loginPresenter;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @OnTextChanged(R.id.username)
    public void onUsernameChange() {
        loginPresenter.checkValid(usernameInput.getText().toString(), passwordInput.getText().toString());
    }

    @OnTextChanged(R.id.password)
    public void onPasswordChange() {
        loginPresenter.checkValid(usernameInput.getText().toString(), passwordInput.getText().toString());
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        loginPresenter = checkNotNull(presenter);
    }

    @OnClick(R.id.button_login)
    public void onLoginButtonClick() {
        loginPresenter.login(usernameInput.getText().toString(), passwordInput.getText().toString());
    }

    @OnClick(R.id.button_fingerprint)
    public void onFingerprintButtonClick() {
        showFingerAuth();
    }

    @OnClick(R.id.no_account)
    public void onNoAccountTextClick() {
        startActivity(new Intent(getActivity(), SignUpActivity.class));
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void showFingerAuth() {
        createFingerprintManagerInstance().authenticate(new KFingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationSuccess() {
                startActivity(new Intent(getActivity(), GettingStartedActivity.class));
            }

            @Override
            public void onSuccessWithManualPassword(@NotNull String password) {
                passwordInput.setText(password);
                loginPresenter.login(usernameInput.getText().toString(), password);
            }

            @Override
            public void onFingerprintNotRecognized() {
            }

            @Override
            public void onAuthenticationFailedWithHelp(@Nullable String help) {
            }

            @Override
            public void onFingerprintNotAvailable() {
            }

            @Override
            public void onCancelled() {
            }
        }, getFragmentManager());
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.start();
    }

    private KFingerprintManager createFingerprintManagerInstance() {
        return new KFingerprintManager(getContext(), Globals.BIOMETRIC_KEY);
    }

    @Override
    public void onLogin(User userControllerAuthenticate) {
        System.out.println(userControllerAuthenticate);
        startActivity(new Intent(getContext(), HomeActivity.class));
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void setUsernameText(String text) {
        usernameInput.setText(text);
    }

    @Override
    public void showFingerPrintButton() {
        fingerprintButton.setAlpha(1f);
    }

    @Override
    public void hideFingerPrintButton() {
        fingerprintButton.setAlpha(0f);
    }

    @Override
    public void setLoginButtonEnabled(boolean state) {
        loginButton.setEnabled(state);
    }

    @Override
    public void setFingerprintButtonEnabled(boolean state) {
        fingerprintButton.setEnabled(state);
    }
}
