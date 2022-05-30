package com.example.qrcode_appdev;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.qrcode_appdev.scanner.contactScanResult;
import com.example.qrcode_appdev.scanner.eventScanResult;
import com.example.qrcode_appdev.scanner.phoneNumberScanResult;
import com.example.qrcode_appdev.scanner.smsScanResult;
import com.example.qrcode_appdev.scanner.textScanResult;
import com.example.qrcode_appdev.scanner.urlScanResult;
import com.example.qrcode_appdev.scanner.wifiScanResult;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QrScanFragment extends Fragment {

    public static final int GET_FROM_GALLERY = 3;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ExecutorService cameraExecutor;
    public PreviewView previewView;
    private MyImageAnalyzer analyzer;
    private Button btnFlash, btnPhoto_library;
    private boolean lightOn = false;

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        previewView = view.findViewById(R.id.previewView);


        btnFlash = view.findViewById(R.id.flash);

        btnPhoto_library = view.findViewById(R.id.btnPhoto_library);
        btnPhoto_library.setOnClickListener(view1 -> startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY));

        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity());

        //noinspection deprecation
        analyzer = new MyImageAnalyzer(getFragmentManager());

        cameraProviderFuture.addListener(() -> {
            try {
                if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != (PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, 101);
                }
                ProcessCameraProvider processCameraProvider = cameraProviderFuture.get();
                bindPreview(processCameraProvider);


            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireActivity()));

        return view;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101 && grantResults.length > 0) {
            ProcessCameraProvider processCameraProvider = null;
            try {
                processCameraProvider = cameraProviderFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            assert data != null;
            Uri selectedImage = data.getData();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(), selectedImage);
                InputImage image = InputImage.fromBitmap(bitmap, 0);
                BarcodeScannerOptions options =
                        new BarcodeScannerOptions.Builder()
                                .setBarcodeFormats(
                                        Barcode.FORMAT_QR_CODE,
                                        Barcode.FORMAT_AZTEC,
                                        Barcode.FORMAT_ALL_FORMATS)
                                .build();
                BarcodeScanner scanner = BarcodeScanning.getClient(options);
                scanner.process(image)
                        .addOnSuccessListener(this.analyzer::readerBarcodeData)
                        .addOnFailureListener(Throwable::printStackTrace);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindPreview(ProcessCameraProvider processCameraProvider) {

        Preview preview = new Preview.Builder()
                .build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        processCameraProvider.unbindAll();

        ImageCapture imageCapture = new ImageCapture
                .Builder()
                .build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        imageAnalysis.setAnalyzer(cameraExecutor, analyzer);

        Camera camera = processCameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, imageAnalysis);

        btnFlash.setOnClickListener(view -> {
            if ( camera.getCameraInfo().hasFlashUnit() ) {
                camera.getCameraControl().enableTorch(!lightOn);
                lightOn = !lightOn;
            } else {
                Toast.makeText(getActivity(), "Your phone doesn't have a flash light", Toast.LENGTH_SHORT).show();
            }

        });

        previewView.setImplementationMode(PreviewView.ImplementationMode.COMPATIBLE);
    }

    public static class MyImageAnalyzer implements ImageAnalysis.Analyzer {
        private final FragmentManager fragmentManager;
        private final urlScanResult rUrl;
        private final wifiScanResult rWifi;
        private final textScanResult rText;
        private final contactScanResult rContact;
        private final eventScanResult rEvent;
        private final smsScanResult rSMS;
        private final phoneNumberScanResult rPhone;


        public MyImageAnalyzer(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
            rUrl = new urlScanResult();
            rWifi = new wifiScanResult();
            rText = new textScanResult();
            rContact = new contactScanResult();
            rEvent = new eventScanResult();
            rSMS = new smsScanResult();
            rPhone = new phoneNumberScanResult();

        }

        @Override
        public void analyze(@NonNull ImageProxy image) {
            scanBarcodes(image);
        }

        private void scanBarcodes(ImageProxy image) {
            @SuppressLint("UnsafeOptInUsageError") Image image1 = image.getImage();
            assert image1 != null;
            InputImage inputImage = InputImage.fromMediaImage(image1, image.getImageInfo().getRotationDegrees());
            BarcodeScannerOptions options =
                    new BarcodeScannerOptions.Builder()
                            .setBarcodeFormats(
                                    Barcode.FORMAT_QR_CODE,
                                    Barcode.FORMAT_AZTEC,
                                    Barcode.FORMAT_ALL_FORMATS)
                            .build();
            BarcodeScanner scanner = BarcodeScanning.getClient(options);
            // Task failed with an exception
            // ...
            // Task completed successfully
            // [START_EXCLUDE]
            // [START get_barcodes]
            // [END get_barcodes]
            // [END_EXCLUDE]
            Task<List<Barcode>> result = scanner.process(inputImage)
                    .addOnSuccessListener(this::readerBarcodeData)
                    .addOnFailureListener(Throwable::printStackTrace)
                    .addOnCompleteListener(task -> image.close());
        }

        private void readerBarcodeData(List<Barcode> barcodes) {
            for (Barcode barcode : barcodes) {
                Rect bounds = barcode.getBoundingBox();
                Point[] corners = barcode.getCornerPoints();

                String rawValue = barcode.getRawValue();

                int valueType = barcode.getValueType();
                // See API reference for complete list of supported types
                switch (valueType) {
                    case Barcode.TYPE_WIFI:
                        String ssid = Objects.requireNonNull(barcode.getWifi()).getSsid();
                        String password = barcode.getWifi().getPassword();
                        int type = barcode.getWifi().getEncryptionType();
                        String security;
                        if(type == 1) security = "None";
                        else if(type == 2) security ="WPA/WPA2";
                        else security = "WEP";
                        if (!rWifi.isAdded()) {
                            rWifi.show(fragmentManager, "WIFI BARCODE SCANNED");
                        }
                        rWifi.fetchWifiName(ssid);
                        rWifi.fetchWifiType(security);
                        rWifi.fetchWifiPassword(password);
                        break;
                    case Barcode.TYPE_URL:
                        if (!rUrl.isAdded()) {
                            rUrl.show(fragmentManager, "URL BARCODE SCANNED");
                        }
                        rUrl.fetchUrl(Objects.requireNonNull(barcode.getUrl()).getUrl());
                        break;
                    case Barcode.TYPE_CALENDAR_EVENT:
                        String title = barcode.getCalendarEvent().getStatus();
                        String content = barcode.getCalendarEvent().getSummary();
                        Barcode.CalendarDateTime startDay = barcode.getCalendarEvent().getStart();
//                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//                        String date = format1.format(startDay);
                        if (!rEvent.isAdded()) {
                            rEvent.show(fragmentManager, "EVENT BARCODE SCANNED");
                        }
                        rEvent.fetchTitle(title);
                        rEvent.fetchContent(content);
                        rEvent.fetchStarDay("14/5/2020");
                        rEvent.fetchEndDay("20/5/2020");

                        break;
                    case Barcode.TYPE_EMAIL:
                        String address = Objects.requireNonNull(barcode.getEmail()).getAddress();
                        String subject = barcode.getEmail().getSubject();
                        String body = barcode.getEmail().getBody();
                        break;
                    case Barcode.TYPE_TEXT:
                        String text = barcode.getDisplayValue();
                        if (!rText.isAdded()) {
                            rText.show(fragmentManager, "TEXT BARCODE SCANNED");
                        }
                        rText.fetchText(text);
                        break;
                    case Barcode.TYPE_CONTACT_INFO:
                        String name = Objects.requireNonNull(Objects.requireNonNull(barcode.getContactInfo()).getName()).getFormattedName();
                        String address1 = Arrays.toString(barcode.getContactInfo().getAddresses().get(0).getAddressLines()) ;
                        String company = Objects.requireNonNull(barcode.getContactInfo().getOrganization());
                        String phone = barcode.getContactInfo().getPhones().get(0).getNumber();
                        String email = barcode.getContactInfo().getEmails().get(0).getAddress();
                        if (!rContact.isAdded()) {
                            rContact.show(fragmentManager, "CONTACT BARCODE SCANNED");
                        }
                        rContact.fetchName(name);
                        rContact.fetchPhone(phone);
                        rContact.fetchEmail(email);
                        rContact.fetchAddress(address1);
                        rContact.fetchCompany(company);
                        break;
                    case Barcode.TYPE_SMS:
                        String sms = barcode.getSms().getMessage();
                        String phone1 = barcode.getSms().getPhoneNumber();
                        if (!rSMS.isAdded()) {
                            rSMS.show(fragmentManager, "SMS BARCODE SCANNED");
                        }
                        rSMS.fetchPhone(phone1);
                        rSMS.fetchContent(sms);
                        break;
                    case Barcode.TYPE_PHONE:
                        String phone2 = barcode.getPhone().getNumber();
                        if (!rPhone.isAdded()) {
                            rPhone.show(fragmentManager, "PHONE BARCODE SCANNED");
                        }
                        rPhone.fetchPhone(phone2);
                        break;

                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ProcessCameraProvider processCameraProvider = null;
        try {
            processCameraProvider = cameraProviderFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        processCameraProvider.unbindAll();
    }

    @Override
    public void onResume() {
        super.onResume();
        ProcessCameraProvider processCameraProvider = null;
        try {
            processCameraProvider = cameraProviderFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        bindPreview(Objects.requireNonNull(processCameraProvider));
    }
}
