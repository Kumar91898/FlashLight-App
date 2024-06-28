package com.kumar.torchproject;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kumar.torchproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private boolean isSwitch = false;
    private CameraManager cameraManager;
    String cameraID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.switcher.setRotation(270);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraID = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        binding.switcher.setOnClickListener(v -> {
            if (isSwitch){
                binding.switcher.setSpeed(3.0f);
                binding.switcher.setMinAndMaxProgress(0.5f,1.0f);
                binding.switcher.playAnimation();
                turnOffFlashlight();
            } else {
                binding.switcher.setSpeed(3.0f);
                binding.switcher.setMinAndMaxProgress(0.0f,0.5f);
                binding.switcher.playAnimation();
                turnOnFlashlight();
            }
        });
    }

    private void turnOnFlashlight() {
        try {
            cameraManager.setTorchMode(cameraID, true);
            binding.torchStatus.setText("ON");
            binding.torchStatus.setTextColor(getColor(R.color.green));
            isSwitch = true;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void turnOffFlashlight() {
        try {
            cameraManager.setTorchMode(cameraID, false);
            binding.torchStatus.setText("OFF");
            binding.torchStatus.setTextColor(getColor(R.color.red));
            isSwitch = false;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}