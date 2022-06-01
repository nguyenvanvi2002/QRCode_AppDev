package com.example.qrcode_appdev.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qrcode_appdev.R;
import com.example.qrcode_appdev.create.CreateQRCard;
import com.example.qrcode_appdev.create.CreateQRContact;
import com.example.qrcode_appdev.create.CreateQREmail;
import com.example.qrcode_appdev.create.CreateQREvent;
import com.example.qrcode_appdev.create.CreateQRPhoneNumber;
import com.example.qrcode_appdev.create.CreateQRSms;
import com.example.qrcode_appdev.create.CreateQRText;
import com.example.qrcode_appdev.create.CreateQRUrl;
import com.example.qrcode_appdev.create.CreateQRWifi;

public class CreateFragment extends Fragment {
    ImageView imgCard,imgContact, imgEmail, imgSms, imgUrl, imgPhoneNumber, imgText, imgWifi,imgEvent;


    CreateQRCard createQrCard = new CreateQRCard();
    CreateQRContact createQrContact = new CreateQRContact();
    CreateQREmail createQrEmail = new CreateQREmail();
    CreateQREvent createQrEvent = new CreateQREvent();
    CreateQRPhoneNumber createQrPhoneNumber = new CreateQRPhoneNumber();
    CreateQRSms createQrSms = new CreateQRSms();
    CreateQRText createQrText = new CreateQRText();
    CreateQRUrl createQrUrl = new CreateQRUrl();
    CreateQRWifi createQrWifi = new CreateQRWifi();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        imgCard = view.findViewById(R.id.card_create);
        imgContact  =  view.findViewById(R.id.contact_create);
        imgEmail    =  view.findViewById(R.id.email_create);
        imgEvent   = view.findViewById(R.id.event_create);
        imgPhoneNumber =  view.findViewById(R.id.phone_number_create);
        imgSms =   view.findViewById(R.id.sms_create);
        imgText = view.findViewById(R.id.text_create);
        imgUrl = view.findViewById(R.id.url_create);
        imgWifi = view.findViewById(R.id.wifi_create);

        imgUrl.setOnClickListener(view17 -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.view_pager,createQrUrl).addToBackStack(null).commit();
        });

        imgText.setOnClickListener(view16 -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.view_pager,createQrText).addToBackStack(null).commit();

        });
        imgWifi.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.view_pager,createQrWifi).addToBackStack(null).commit();
        });
        imgPhoneNumber.setOnClickListener(view14 -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.view_pager,createQrPhoneNumber).addToBackStack(null).commit();
        });
        imgSms.setOnClickListener(view15 -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.view_pager,createQrSms).addToBackStack(null).commit();
        });

        imgContact.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.view_pager,createQrContact).addToBackStack(null).commit();
        });
        imgEvent.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.view_pager,createQrEvent).addToBackStack(null).commit();
        });
        imgEmail.setOnClickListener(view12 -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.view_pager,createQrEmail).addToBackStack(null).commit();
        });
        imgCard.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.view_pager,createQrContact).addToBackStack(null).commit();
        });


//        imgEmail.setOnClickListener(view13 -> {
//            FragmentManager fragmentManager = getParentFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.view_pager,createQrEvent).addToBackStack(null).commit();
//        });


//

//        imgUrl.setOnClickListener( view18-> {
//            FragmentManager fragmentManager = getParentFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.view_pager,createQrWifi).addToBackStack(null).commit();
//        });
        return view;
    }
}
