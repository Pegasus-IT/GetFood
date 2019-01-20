package io.getfood.modules.family;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.getfood.R;
import io.getfood.data.swagger.models.Family;
import io.getfood.data.swagger.models.User;
import io.getfood.modules.BaseFragment;
import io.getfood.modules.auth.sign_up.SignUpActivity;
import io.getfood.modules.getting_started.GettingStartedActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class FamilyFragment extends BaseFragment implements FamilyContract.View {

    @BindView(R.id.family_qr_code)
    ImageView qrCode;

    @BindView(R.id.family_name)
    TextView familyName;

    @BindView(R.id.family_leave_button)
    TextView leaveButton;

    @BindView(R.id.user_grid_view)
    GridView gridView;

    FamilyUsersAdapter familyUsersAdapter;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private FamilyContract.Presenter familyPresenter;

    public static FamilyFragment newInstance() {
        return new FamilyFragment();
    }

    @Override
    public void setPresenter(@NonNull FamilyContract.Presenter presenter) {
        familyPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.family_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        familyPresenter.start();
    }

    @Override
    public void onFamilyLoad(Family family) {
        String base64String = family.getQrCode();
        String base64Image = base64String.split(",")[1];

        byte[] decodedString = android.util.Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        qrCode.setImageBitmap(decodedByte);
        familyName.setText(family.getName());

        mHandler.post(() -> {
            familyUsersAdapter = new FamilyUsersAdapter(getContext(), family.getUsers());
            familyUsersAdapter.notifyDataSetChanged();
            gridView.setAdapter(familyUsersAdapter);
        });
    }

    @Override
    public void onFamilyLeave(Family family) {
        openActivity(new Intent(getActivity(), GettingStartedActivity.class), true);
    }

    @OnClick(R.id.family_leave_button)
    public void onFamilyLeaveClick() {
        familyPresenter.leaveFamily();
    }
}
