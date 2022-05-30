package com.example.qrcode_appdev.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qrcode_appdev.R;
import com.example.qrcode_appdev.create.ResultCreate;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class CreateQRContact extends Fragment {
    Button back;
    Button crea;
    EditText editName, editPhone, editEmail ;
    ResultCreate resultCreate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_url, container, false);
        back = (Button) view.findViewById(R.id.btnBack);
        crea = (Button) view.findViewById(R.id.btnCreate);
        editName = view.findViewById(R.id.editTextName);
        editPhone = view.findViewById(R.id.editPhone);
        editEmail= view.findViewById(R.id.editEmail);
        back.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStackImmediate());

        crea.setOnClickListener(view12 -> {
            String contact = "BEGIN:VCARD\nVERSION:3.0\nN:" + editName.getText().toString().trim()
                    + "\nTEL;WORK;VOICE:" + editPhone.getText().toString().trim()
                    + "\nEMAIL;WORK;INTERNET:" + editEmail.getText().toString().trim()
                    + "\nEND:VCARD";
            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(contact, BarcodeFormat.QR_CODE, 260,260);
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
