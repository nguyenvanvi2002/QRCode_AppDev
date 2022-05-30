package com.example.qrcode_appdev.scanner;

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

import com.example.qrcode_appdev.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class phoneNumberScanResult extends BottomSheetDialogFragment {
    private TextView phone_number, btn_copy;
    private ImageView btn_call, btn_send_email , btn_share;
    private String phone;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_text, container, false);
        phone_number = view.findViewById(R.id.phone_number);
        phone_number.setText(phone);
        btn_copy = view.findViewById(R.id.btn_copy);
        btn_send_email = view.findViewById(R.id.btn_send_email);
        btn_share = view.findViewById(R.id.btn_share);


        btn_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("message/rfc822");
                myIntent.putExtra(Intent.EXTRA_SUBJECT,"Văn bản");
                myIntent.putExtra(Intent.EXTRA_TEXT,phone);
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
                String body = phone ;
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });

        return view;
    }
    public void fetchPhone(String txt) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> phone = txt);
    }
}
