package com.example.qrcode_appdev;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class bottom_dialog extends BottomSheetDialogFragment {

    private TextView title;
    private ImageView close;
    private String fetchUrl;
    private Button btnBack,btn_visit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog, container, false);
        title = view.findViewById(R.id.txt_url_result);
        btn_visit = view.findViewById(R.id.btn_open_browser);
        btnBack = view.findViewById(R.id.btnBack);

        title.setText(fetchUrl);

        btn_visit.setOnClickListener(view12 -> {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(fetchUrl));
            startActivity(intent);
        });
        btnBack.setOnClickListener(view1 -> dismiss());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//    }

    public void fetchUrl(String url) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> fetchUrl = url);
    }
}
