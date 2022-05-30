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

import java.util.PrimitiveIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class eventScanResult extends BottomSheetDialogFragment {

    private TextView title_event, content_event,day_event, btn_copy;
    private ImageView btn_send_sms, btn_send_email, btn_share;
    private String title, content, startDay, endDay, text_copy;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_event, container, false);

        title_event = view.findViewById(R.id.title_event);
        content_event = view.findViewById(R.id.content_event);
        day_event = view.findViewById(R.id.day_event);

        title_event.setText(title);
        content_event.setText(content);
        day_event.setText("Ngày bắt đầu: "+ startDay + "\nNgày kết thúc: " + endDay);

        btn_copy = view.findViewById(R.id.btn_copy);
        btn_send_sms = view.findViewById(R.id.btn_send_sms);
        btn_send_email = view.findViewById(R.id.btn_send_email);
        btn_share = view.findViewById(R.id.btn_share);

        text_copy = "Tiêu đề: " + title + "\nNội dung: " + content + "\nNgày bắt đầu: "+ startDay + "\nNgày kết thúc: " + endDay;

        btn_send_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "",null));
                myIntent.putExtra("sms_body",text_copy);
                startActivity(Intent.createChooser(myIntent, "Choose an SMS client"));
            }
        });

        btn_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("message/rfc822");
                myIntent.putExtra(Intent.EXTRA_SUBJECT,"Văn bản");
                myIntent.putExtra(Intent.EXTRA_TEXT,text_copy);
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
                String body = text_copy ;
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });

        return view;
    }
    public void fetchTitle(String txt) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> title = txt);
    }

    public void fetchContent(String txt) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> content = txt);
    }

    public void fetchStarDay(String txt) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> startDay = txt);
    }
    public void fetchEndDay(String txt) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> endDay = txt);
    }
}
