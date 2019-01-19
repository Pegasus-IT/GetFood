package io.getfood.modules.family;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.getfood.R;
import io.getfood.modules.BaseFragment;

import static com.google.common.base.Preconditions.checkNotNull;

public class FamilyFragment extends BaseFragment implements FamilyContract.View {

    @BindView(R.id.family_leave_button)
    TextView leaveButton;

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
}
