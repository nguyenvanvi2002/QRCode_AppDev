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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class CreateQREvent extends Fragment {
    Button back;
    Button create;
    EditText editTitle, startday, endday;
    ResultCreate resultCreate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_event, container, false);
        back = (Button) view.findViewById(R.id.btnBack);
        create = (Button) view.findViewById(R.id.btnCreate);
        editTitle = view.findViewById(R.id.editTitle);
        startday = view.findViewById(R.id.startday);
        endday = view.findViewById(R.id.endday);

        back.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStackImmediate());

        create.setOnClickListener(view12 -> {
            String txt = "\n" +
                    "BEGIN:VEVENT\n" +
                    "SUMMARY:" + editTitle.getText().toString().trim()+
                    "\nDESCRIPTION:" +
                    "\nLOCATION:" +
                    "\nDTSTART:" +startday.getText().toString().trim()+
                    "\nDTEND:" + endday.getText().toString().trim()+
                    "\nEND:VEVENT";
            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(txt, BarcodeFormat.QR_CODE, 260,260);
                resultCreate = new ResultCreate(matrix);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view_pager,resultCreate).addToBackStack(null).commit();
            } catch (WriterException e) {
                e.printStackTrace();
            }

        });
        return view;
    }

}
