package com.example.qrcode_appdev;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class textScanResult extends BottomSheetDialogFragment {
    private TextView text_result, btn_copy;
    private ImageView btn_send_sms, btn_send_email, btn_share;
    private String fetchText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_text, container, false);

        text_result = view.findViewById(R.id.qr_text_result);
        btn_copy = view.findViewById(R.id.btn_copy);
        btn_send_sms = view.findViewById(R.id.btn_send_sms);
        btn_send_email = view.findViewById(R.id.btn_send_email);
        btn_share = view.findViewById(R.id.btn_share);

        text_result.setText(fetchText);

        btn_send_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms", "",null));
                myIntent.putExtra("sms_body",fetchText);
                startActivity(Intent.createChooser(myIntent, "Choose an SMS client"));
            }
        });

        btn_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("message/rfc822");
                myIntent.putExtra(Intent.EXTRA_SUBJECT,"Văn bản");
                myIntent.putExtra(Intent.EXTRA_TEXT,fetchText);
                startActivity(Intent.createChooser(myIntent, "Choose an Email client"));
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String sub = "Văn bản";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                String body = fetchText ;
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });

        return view;
    }

    public void fetchText(String text) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> fetchText = text);
    }
}
