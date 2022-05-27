package com.example.qrcode_appdev;


import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class urlScanResult extends BottomSheetDialogFragment {

    private TextView title;
    private ImageView close, btn_visit,btn_share;
    private String fetchUrl;
    private Button btnBack;
    private TextView btn_copy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_url, container, false);
        title = view.findViewById(R.id.txt_url_result);
        btn_visit = view.findViewById(R.id.btn_open_browser);
        btnBack = view.findViewById(R.id.btnBack);

        btn_copy = view.findViewById(R.id.btn_copy);
        btn_copy.setOnClickListener(view1 -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(title.getText());
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText(null,title.getText());
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(requireContext(), "Text copied into clipboard",Toast.LENGTH_LONG).show();
        });

        title.setText(fetchUrl);

        btn_share = view.findViewById(R.id.btn_share);

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String sub = "Trang web";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                String body = fetchUrl ;
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });




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

    @Override
    public void onDetach() {
        super.onDetach();
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public void fetchUrl(String url) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> fetchUrl = url);
    }
}
