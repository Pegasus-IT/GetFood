package io.getfood.modules.getting_started;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.getfood.R;
import io.getfood.data.swagger.models.Family;
import io.getfood.modules.BaseFragment;
import io.getfood.modules.auth.login.LoginActivity;
import io.getfood.modules.home.HomeActivity;
import io.getfood.modules.qr_scan.QRScanActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class GettingStartedFragment extends BaseFragment implements GettingStartedContract.View {

    @BindView(R.id.getting_started_buttons)
    LinearLayout gettingStartedButtons;

    @BindView(R.id.lets_get_started)
    TextView letsGetStartedText;

    @BindView(R.id.lets_get_started_underline)
    TextView letsGetStartedUnderlineText;

    @BindView(R.id.not_ready)
    TextView notReadyYetText;

    private GettingStartedContract.Presenter gettingStartedPresenter;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * Creates a new instance
     *
     * @return instance
     */
    public static GettingStartedFragment newInstance() {
        return new GettingStartedFragment();
    }

    /**
     * Open the camera to scan the family QR code
     */
    @OnClick(R.id.join_family_button)
    void onJoinFamilyClick() {
        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            openCamera();
        }
    }

    /**
     * Open the create family dialog
     */
    @OnClick(R.id.create_family_button)
    void onCreateFamilyClick() {
        createFamilyInput();
    }

    /**
     * Navigate back to the login activity
     */
    @OnClick(R.id.not_ready)
    void onNotReadyYetClick() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra("disableAutoAuthStart", "yes");
        openActivity(intent, R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * @param presenter given presenter
     * @inheritDoc
     */
    @Override
    public void setPresenter(@NonNull GettingStartedContract.Presenter presenter) {
        gettingStartedPresenter = checkNotNull(presenter);
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
        View view = inflater.inflate(R.layout.getting_started_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onResume() {
        super.onResume();
        gettingStartedPresenter.start();
    }

    /**
     * @param state state
     * @inheritDoc
     */
    @Override
    public void setButtonsVisible(boolean state) {
        mHandler.post(() -> {
            if (state) {
                this.letsGetStartedText.setVisibility(LinearLayout.VISIBLE);
                this.letsGetStartedUnderlineText.setVisibility(LinearLayout.VISIBLE);
                this.gettingStartedButtons.setVisibility(LinearLayout.VISIBLE);
                this.notReadyYetText.setVisibility(LinearLayout.VISIBLE);
            } else {
                this.letsGetStartedText.setVisibility(LinearLayout.GONE);
                this.letsGetStartedUnderlineText.setVisibility(LinearLayout.GONE);
                this.gettingStartedButtons.setVisibility(LinearLayout.GONE);
                this.notReadyYetText.setVisibility(LinearLayout.GONE);
            }
        });
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onAlreadyInFamily() {
        openActivity(new Intent(getContext(), HomeActivity.class), false);
    }

    /**
     * @param familyControllerCreate family
     * @inheritDoc
     */
    @Override
    public void onFamilyJoined(Family familyControllerCreate) {
        System.out.println(familyControllerCreate.getName());
        openActivity(new Intent(getContext(), HomeActivity.class), false);
    }

    private void openCamera() {
        openActivity(new Intent(getActivity(), QRScanActivity.class), false);
    }

    /**
     * Creates the create family dialog
     */
    public void createFamilyInput() {
        final EditText familyName = new EditText(getContext());
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.getting_started_create_family)
                .setMessage(R.string.getting_started_create_family_description)
                .setView(familyName)
                .setPositiveButton("Create", (dialog, whichButton) -> {
                    if (!familyName.getText().toString().isEmpty()) {
                        gettingStartedPresenter.createFamilyWithName(familyName.getText().toString());
                    } else {
                        createFamilyInput();
                    }
                })
                .setNegativeButton("Cancel", (dialog, whichButton) -> {
                })
                .show();
    }
}
