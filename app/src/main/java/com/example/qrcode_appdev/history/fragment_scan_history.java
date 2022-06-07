package com.example.qrcode_appdev.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrcode_appdev.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class fragment_scan_history extends Fragment {
    List<InforQRcode> inforQRcodeList = new ArrayList<InforQRcode>();
    private RecyclerView recyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_scan, container, false);
        Toast.makeText(getActivity(), "Count "+ inforQRcodeList.size(), Toast.LENGTH_SHORT).show();

        DatabaseHelper1 db = new DatabaseHelper1(this.getContext());
        List<QrModel> allQr = db.getAll();
        Collections.reverse(allQr);
        recyclerview = view.findViewById(R.id.item_list);
        recyclerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);
        mAdapter = new ListAdapter(allQr, this.getContext());
        recyclerview.setAdapter(mAdapter);
        return view;
    }

}
