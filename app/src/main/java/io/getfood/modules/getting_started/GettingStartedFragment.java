package io.getfood.modules.getting_started;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.getfood.R;
import io.getfood.modules.BaseFragment;
import io.getfood.modules.auth.login.LoginActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class GettingStartedFragment extends BaseFragment implements GettingStartedContract.View {

    private GettingStartedContract.Presenter gettingStartedPresenter;

    public static GettingStartedFragment newInstance() {
        return new GettingStartedFragment();
    }

    @OnClick(R.id.not_ready)
    void onNotReadyYetClick() {
        openActivity(new Intent(getContext(), LoginActivity.class), R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void setPresenter(@NonNull GettingStartedContract.Presenter presenter) {
        gettingStartedPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.getting_started_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        gettingStartedPresenter.start();
    }
}
