package io.getfood.modules.auth.login;

import android.app.Activity;
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
import io.getfood.util.ShowKeyboard;

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

    /**
     * Creates a new instance
     *
     * @return login fragment
     */
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    /**
     * Check if username is valid on input change
     */
    @OnTextChanged(R.id.username)
    public void onUsernameChange() {
        loginPresenter.checkValid(usernameInput.getText().toString(), passwordInput.getText().toString());
    }

    /**
     * Check if password is valid on input change
     */
    @OnTextChanged(R.id.password)
    public void onPasswordChange() {
        loginPresenter.checkValid(usernameInput.getText().toString(), passwordInput.getText().toString());
    }

    /**
     * @param presenter given presenter
     * @inheritDoc
     */
    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        loginPresenter = checkNotNull(presenter);
    }

    /**
     * Send credentials to login on button click
     */
    @OnClick(R.id.button_login)
    public void onLoginButtonClick() {
        loginPresenter.login(usernameInput.getText().toString(), passwordInput.getText().toString());
    }

    /**
     * Display the fingerprint authentication
     */
    @OnClick(R.id.button_fingerprint)
    public void onFingerprintButtonClick() {
        showFingerAuth();
    }

    /**
     * Navigate to sign up
     */
    @OnClick(R.id.no_account)
    public void onNoAccountTextClick() {
        startActivity(new Intent(getActivity(), SignUpActivity.class));
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, view);

        boolean disableAutoAuthStart = false;
        Activity activity = getActivity();
        if (activity != null) {
            Bundle bundle = activity.getIntent().getExtras();

            if (bundle != null) {
                String gettingStarted = bundle.getString("disableAutoAuthStart");
                disableAutoAuthStart = gettingStarted != null;
            }
        }

        loginPresenter.setDisableAutoAuthStart(disableAutoAuthStart);

        return view;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void showFingerAuth() {
        createFingerprintManagerInstance().authenticate(new KFingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationSuccess() {
                loginPresenter.validateStoredToken();
            }

            @Override
            public void onSuccessWithManualPassword(@NotNull String password) {
                passwordInput.setText(password);
                loginPresenter.login(usernameInput.getText().toString(), password);
            }

            @Override
            public void onFingerprintNotRecognized() {
                onError("Fingerprint not recognized.");
            }

            @Override
            public void onAuthenticationFailedWithHelp(@Nullable String help) {
                if (help != null) {
                    onError(help);
                } else {
                    onError("Login with fingerprint failed.");
                }
            }

            @Override
            public void onFingerprintNotAvailable() {
                onError("Fingerprint not available!");
            }

            @Override
            public void onCancelled() {
                passwordInput.setFocusableInTouchMode(true);
                passwordInput.requestFocus();
                passwordInput.postDelayed(new ShowKeyboard(passwordInput, getActivity()), 300);
            }
        }, getFragmentManager());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.start();
    }

    /**
     * Creates the fingerprint instance
     * @return instance
     */
    private KFingerprintManager createFingerprintManagerInstance() {
        return new KFingerprintManager(getContext(), Globals.BIOMETRIC_KEY);
    }

    /**
     * @param user model
     * @inheritDoc
     */
    @Override
    public void onLogin(User user) {
        System.out.println(user);
        openActivity(new Intent(getContext(), GettingStartedActivity.class), false);
    }

    /**
     * @param user model
     * @inheritDoc
     */
    @Override
    public void onTokenValidated(User user) {
        System.out.println(user);
        openActivity(new Intent(getContext(), GettingStartedActivity.class), false);
    }

    /**
     * @param text
     * @inheritDoc
     */
    @Override
    public void setUsernameText(String text) {
        usernameInput.setText(text);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void showFingerPrintButton() {
        fingerprintButton.setAlpha(1f);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideFingerPrintButton() {
        fingerprintButton.setAlpha(0f);
    }

    /**
     * @param state
     * @inheritDoc
     */
    @Override
    public void setLoginButtonEnabled(boolean state) {
        loginButton.setEnabled(state);
    }

    /**
     * @param state
     * @inheritDoc
     */
    @Override
    public void setFingerprintButtonEnabled(boolean state) {
        fingerprintButton.setEnabled(state);
    }
}
