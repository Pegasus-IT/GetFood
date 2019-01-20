package io.getfood.modules.qr_scan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.zxing.BarcodeFormat;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.getfood.R;
import io.getfood.data.swagger.models.Family;
import io.getfood.modules.BaseFragment;
import io.getfood.modules.home.HomeActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class QRScanFragment extends BaseFragment implements QRScanContract.View {

    @BindView(R.id.scanner_view)
    CodeScannerView scannerView;
    CodeScanner codeScanner;
    private QRScanContract.Presenter qrScanPresenter;

    /**
     * Creates a new instance
     *
     * @return instance
     */
    public static QRScanFragment newInstance() {
        return new QRScanFragment();
    }

    /**
     * @param presenter given presenter
     * @inheritDoc
     */
    @Override
    public void setPresenter(@NonNull QRScanContract.Presenter presenter) {
        qrScanPresenter = checkNotNull(presenter);
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
        View view = inflater.inflate(R.layout.scan_qr_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * @param view
     * @param savedInstanceState
     * @inheritDoc
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        codeScanner = new CodeScanner(getActivity(), scannerView);
        ArrayList<BarcodeFormat> barcodeFormats = new ArrayList<>();
        barcodeFormats.add(BarcodeFormat.QR_CODE);
        codeScanner.setFormats(barcodeFormats);
        codeScanner.setDecodeCallback(result -> {
            if (result.getText().startsWith("getfood")) {
                qrScanPresenter.validateCode(result.getText().split(":")[2]);
            }
        });

        scannerView.setOnClickListener((scannerView) -> codeScanner.startPreview());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onResume() {
        super.onResume();
        qrScanPresenter.start();
    }

    /**
     * @param family
     * @inheritDoc
     */
    @Override
    public void onFamilyJoined(Family family) {
        this.showSnackbar(String.valueOf(R.string.getting_started_join_successful), R.color.getfood_main_blue);
        startActivity(new Intent(getContext(), HomeActivity.class));
    }
}
