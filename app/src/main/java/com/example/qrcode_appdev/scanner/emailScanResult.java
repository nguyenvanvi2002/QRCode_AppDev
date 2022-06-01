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

public class emailScanResult extends BottomSheetDialogFragment {
    private TextView email_address, email_subject,email_content, btn_copy;
    private ImageView btn_send_email, btn_share;
    private String address, sub, content;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_email, container, false);

        email_address = view.findViewById(R.id.email_address);
        email_subject = view.findViewById(R.id.email_subject);
        email_content = view.findViewById(R.id.email_content);

        email_address.setText(address);
        email_subject.setText(sub);
        email_content.setText(content);

        btn_copy = view.findViewById(R.id.btn_copy);
        btn_send_email = view.findViewById(R.id.btn_send_email);
        btn_share = view.findViewById(R.id.btn_share);


        btn_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("message/rfc822");
                myIntent.putExtra(Intent.EXTRA_EMAIL, address);
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,content);
                startActivity(Intent.createChooser(myIntent, "Choose an Email client"));
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                myIntent.putExtra(Intent.EXTRA_EMAIL, address);
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,content);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });


        return view;
    }
    public void fetchTiltle(String txt) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> address = txt);
    }

    public void fetchSub(String txt) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> sub = txt);
    }
    public void fetchContent(String txt) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> content = txt);
    }
}
