package com.example.qrcode_appdev;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class CreateQRSms extends Fragment {

    Button back;
    Button crea;
    EditText editPhone;
    EditText editSms;
    ResultCreate resultCreate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_url, container, false);
        back = (Button) view.findViewById(R.id.btnBack);
        crea = (Button) view.findViewById(R.id.btnCreate);
        editPhone = view.findViewById(R.id.editTextPhone);
        editSms = view.findViewById(R.id.editTextSms);

        back.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStackImmediate());

        crea.setOnClickListener(view12 -> {
            String sms = "SMSTO:" + editPhone.getText().toString().trim() + ":" + editSms.getText().toString().trim();
            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(sms, BarcodeFormat.QR_CODE, 260,260);
                resultCreate = new ResultCreate(matrix);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,resultCreate).addToBackStack(null).commit();
            } catch (WriterException e) {
                e.printStackTrace();
            }

        });
        return view;
    }
}
