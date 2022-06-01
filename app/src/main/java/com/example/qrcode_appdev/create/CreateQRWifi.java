package com.example.qrcode_appdev.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qrcode_appdev.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class CreateQRWifi extends Fragment implements AdapterView.OnItemSelectedListener{
    Button back;
    Button create;
    EditText wifi_name, wifi_password;
    ResultCreate resultCreate;
    String wifi_security;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_wifi, container, false);
        back = (Button) view.findViewById(R.id.btnBack);
        create = (Button) view.findViewById(R.id.btnCreate);
        wifi_name = view.findViewById(R.id.wifi_name);

        Spinner wifi_type = view.findViewById(R.id.wifi_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.string_wifi_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wifi_type.setAdapter(adapter);
        wifi_type.setOnItemSelectedListener(this);

        wifi_password = view.findViewById(R.id.wifi_password);
        back.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStackImmediate());

        create.setOnClickListener(view12 -> {
            String txt = "WIFI:T:"+wifi_security+";S:"+wifi_name.getText().toString().trim()+";P:"+wifi_password.getText().toString().trim()+";H:;";
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        wifi_security = parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
