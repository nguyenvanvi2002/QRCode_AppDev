package com.example.qrcode_appdev.scanner;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.qrcode_appdev.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class wifiScanResult extends BottomSheetDialogFragment {
    private TextView wifi_name,wifi_security,wifi_password;
    private Button btnBack;
    private TextView btn_connect;
    private ImageView close, btn_copy,btn_share;
    private String fetchName, fetchSecurity, fetchPassword, copy_text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_wifi, container, false);

        wifi_name = view.findViewById(R.id.wifi_name);
        wifi_security = view.findViewById(R.id.wifi_security);
        wifi_password = view.findViewById(R.id.wifi_password);
        btn_connect = view.findViewById(R.id.btn_connect);
        btnBack = view.findViewById(R.id.btnBack);
        btn_copy = view.findViewById(R.id.btn_copy);
        btn_share = view.findViewById(R.id.btn_share);

        wifi_name.setText(fetchName);
        wifi_security.setText(fetchSecurity);
        wifi_password.setText(fetchPassword );

        copy_text = "Tên mạng: " + fetchName +"\n"+"Loại bảo mật: "+ fetchSecurity+"\n"+"Mật khẩu: "+fetchPassword;

        btn_copy.setOnClickListener(view1 -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(copy_text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText(null,copy_text);
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(requireContext(), "Text copied into clipboard",Toast.LENGTH_LONG).show();
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String sub = "Wifi "  + fetchName;
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                String body = copy_text ;
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.S)
            @Override
            public void onClick(View view) {
                WifiManager wifi = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if(wifi.isWifiEnabled()){

                    ConnectToNetworkWEP(fetchName, fetchPassword);

                }else{
                    if (Build.VERSION.SDK_INT<Build.VERSION_CODES.Q)
                        wifi.setWifiEnabled(true);
                    else
                    {
                        Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                        startActivityForResult(panelIntent,1);
                    }
                }


            }
        });

        return view;
    }

    public void fetchWifiName(String text) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> fetchName = text);
    }
    public void fetchWifiType(String text) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> fetchSecurity = text);
    }
    public void fetchWifiPassword(String text) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> fetchPassword = text);
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public boolean ConnectToNetworkWEP(String networkSSID, String password )
    {
        try {

            WifiConfiguration wifiConfig = new WifiConfiguration();
            wifiConfig.SSID = String.format("\"%s\"", networkSSID);
            wifiConfig.preSharedKey = String.format("\"%s\"", password);

            WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            int netId = wifiManager.addNetwork(wifiConfig);
            wifiManager.disconnect();
            wifiManager.enableNetwork(netId, true);
            wifiManager.reconnect();

//            WifiConfiguration conf = new WifiConfiguration();
//            conf.SSID = "\"" + networkSSID + "\"";   // Please note the quotes. String should contain SSID in quotes
//            conf.wepKeys[0] = password;  //WEP password is in hex, we do not need to surround it with quotes.
//            conf.wepTxKeyIndex = 0;
//            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
//
//            conf.preSharedKey = "\""+ password +"\"";
//            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//
//            WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
//            wifiManager.addNetwork(conf);
//
//            List<WifiConfiguration> list = wifiManager.getCallerConfiguredNetworks();
//            for( WifiConfiguration i : list ) {
//                if(i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
//                    wifiManager.disconnect();
//                    wifiManager.enableNetwork(i.networkId, true);
//                    wifiManager.reconnect();
//
//                    break;
//                }
//            }

            //WiFi Connection success, return true
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
