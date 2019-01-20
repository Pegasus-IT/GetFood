package io.getfood.modules.qr_scan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.getfood.R;
import io.getfood.data.local.Globals;
import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.FamilyControllerApi;
import io.getfood.data.swagger.models.Family;
import io.getfood.models.ApiManager;
import io.getfood.models.ShoppingList;
import io.getfood.models.SwaggerApiError;
import io.getfood.modules.BaseFragment;
import io.getfood.modules.home.HomeActivity;
import io.getfood.modules.home.HomeListAdapter;

import static android.content.Context.MODE_PRIVATE;
import static com.google.common.base.Preconditions.checkNotNull;

public class QRScanFragment extends BaseFragment implements QRScanContract.View {

    private QRScanContract.Presenter qrScanPresenter;
    @BindView(R.id.scanner_view)
    CodeScannerView scannerView;
    CodeScanner codeScanner;

    /**
     * Creates a new instance
     *
     * @return instance
     */
    public static QRScanFragment newInstance() {
        return new QRScanFragment();
    }

    @Override
    public void setPresenter(@NonNull QRScanContract.Presenter presenter) {
        qrScanPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_qr_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        codeScanner = new CodeScanner(getActivity(), scannerView);
        ArrayList<BarcodeFormat> barcodeFormats = new ArrayList<>();
        barcodeFormats.add(BarcodeFormat.QR_CODE);
        codeScanner.setFormats(barcodeFormats);
        codeScanner.setDecodeCallback(result -> {
            if(result.getText().startsWith("getfood")) {
                qrScanPresenter.validateCode(result.getText().split(":")[2]);
            }
        });

        scannerView.setOnClickListener((scannerView) -> codeScanner.startPreview());
    }

    @Override
    public void onResume() {
        super.onResume();
        qrScanPresenter.start();
    }

    @Override
    public void onFamilyJoined(Family family) {
        this.showSnackbar(String.valueOf(R.string.getting_started_join_successful), R.color.getfood_main_blue);
        startActivity(new Intent(getContext(), HomeActivity.class));
    }
}
