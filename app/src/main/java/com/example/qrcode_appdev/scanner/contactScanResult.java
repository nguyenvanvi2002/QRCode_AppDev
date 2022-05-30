package com.example.qrcode_appdev.scanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qrcode_appdev.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class contactScanResult extends BottomSheetDialogFragment {
    private TextView contact_name,contact_phone, contact_email, contact_address, contact_company, btn_copy;
    private String name,phone,email, address, company, content;
    private ImageView btn_call,btn_send_sms, btn_send_email,btn_location,btn_add_contact, btn_share;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_contact, container, false);

        contact_name = view.findViewById(R.id.contact_name);
        contact_phone = view.findViewById(R.id.contact_phone);
        contact_email = view.findViewById(R.id.contact_email);
        contact_address = view.findViewById(R.id.contact_address);
        contact_company = view.findViewById(R.id.contact_company);

        contact_name.setText(name);
        contact_phone.setText(phone);
        contact_email.setText(email);
        contact_address.setText(address);
        contact_company.setText(company);

        content = "Tên: "+name+"\nĐiện thoại: "+phone+"\nEmail: "+email+"\nĐịa chỉ: "+address+"\nTổ chức: "+company;

        btn_copy = view.findViewById(R.id.btn_copy);
        btn_copy.setOnClickListener(view1 -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(content);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText(null,content);
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(requireContext(), "Text copied into clipboard",Toast.LENGTH_LONG).show();
        });
        btn_share = view.findViewById(R.id.btn_share);

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String sub = "Contact "  + name;
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                String body = content ;
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });






        return view;
    }

    public void fetchName(String txt) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> name = txt);
    }

    public void fetchPhone(String txt) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> phone = txt);
    }

    public void fetchEmail(String txt) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> email = txt);
    }

    public void fetchAddress(String txt) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> address = txt);
    }
    public void fetchCompany(String txt) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> company = txt);
    }
}
