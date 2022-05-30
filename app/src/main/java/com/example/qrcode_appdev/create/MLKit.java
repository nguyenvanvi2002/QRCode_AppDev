package com.example.qrcode_appdev.create;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import android.util.SparseIntArray;
import android.view.Surface;

import com.google.mlkit.vision.common.InputImage;

import java.io.IOException;
import java.nio.ByteBuffer;

import static android.content.Context.CAMERA_SERVICE;


public class MLKit {
    private static final String NAME = "MLKIT";
    private static final String MY_CAMERA_ID = "my_camera_id";

    private void imageFromBitmap(Bitmap bitmap) {
        int rotationDegree = 0;
        // [START image_from_bitmap]
        InputImage image = InputImage.fromBitmap(bitmap, rotationDegree);
        // [END image_from_bitmap]
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void imageFromMediaImage(Image mediaImage, int rotation) {
        InputImage image = InputImage.fromMediaImage(mediaImage, rotation);
    }

    private void imageFromBuffer(ByteBuffer byteBuffer, int rotationDegrees) {
        InputImage image = InputImage.fromByteBuffer(byteBuffer,
                480,
                360,
                rotationDegrees,
                InputImage.IMAGE_FORMAT_NV21 // or IMAGE_FORMAT_YV12
        );
        // [END image_from_buffer]
    }

    private void imageFromArray(byte[] bytearray, int rotation) {
        // [START image_from_array]
        InputImage image = InputImage.fromByteArray(
                bytearray,
                480,
                360,
                rotation,
                InputImage.IMAGE_FORMAT_NV21
        );
    }

    private void imageFromPath(Context context, Uri uri) {
        InputImage image;
        try {
            image = InputImage.fromFilePath(context, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotationCompensation(String cameraId, Activity activity, boolean isFrontFacing)
            throws CameraAccessException {
        // Get the device's current rotation relative to its "native" orientation.
        // Then, from the ORIENTATIONS table, look up the angle the image must be
        // rotated to compensate for the device's rotation.
        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = ORIENTATIONS.get(deviceRotation);

        // Get the device's sensor orientation.
        CameraManager cameraManager = (CameraManager) activity.getSystemService(CAMERA_SERVICE);
        int sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SENSOR_ORIENTATION);

        if (isFrontFacing) {
            rotationCompensation = (sensorOrientation + rotationCompensation) % 360;
        } else { // back-facing
            rotationCompensation = (sensorOrientation - rotationCompensation + 360) % 360;
        }
        return rotationCompensation;
    }
    // [END get_rotation]

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getCompensation(Activity activity, boolean isFrontFacing) throws CameraAccessException {
        // Get the ID of the camera using CameraManager. Then:
        int rotation = getRotationCompensation(MY_CAMERA_ID, activity, isFrontFacing);
    }
}


