package com.example.t;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btnRetrieveInfo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    retrievePhoneState();
                } else {
                    requestPermission();
                }
            }
        });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.READ_PHONE_STATE},
                PERMISSION_REQUEST_CODE
        );
    }

    private void retrievePhoneState() {
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String IMEI = tm.getDeviceId();
            String simserialno = tm.getSimSerialNumber();
            String netcountry = tm.getNetworkCountryIso();
            String voicemailno = tm.getVoiceMailNumber();
            String strphonetype = "";
            int phonetype = tm.getPhoneType();
            switch (phonetype) {
                case (TelephonyManager.PHONE_TYPE_CDMA):
                    strphonetype = "CDMA";
                    break;
                case (TelephonyManager.PHONE_TYPE_GSM):
                    strphonetype = "GSM";
                    break;
                case (TelephonyManager.PHONE_TYPE_NONE):
                    strphonetype = "NONE";
                    break;
            }
            String strsimstate = "";
            int simstate = tm.getSimState();
            switch (simstate) {
                case (TelephonyManager.SIM_STATE_ABSENT):
                    strsimstate = "sim state is missing";
                    break;
                case (TelephonyManager.SIM_STATE_NETWORK_LOCKED):
                    strsimstate = "sim state network is locked";
                    break;
                case (TelephonyManager.SIM_STATE_READY):
                    strsimstate = "sim state is ready";
                    break;
                case (TelephonyManager.SIM_STATE_PIN_REQUIRED):
                    strsimstate = "Pin is required";
                    break;
                case (TelephonyManager.SIM_STATE_UNKNOWN):
                    strsimstate = "State is unknown";
                    break;
            }
            boolean isroaming = tm.isNetworkRoaming();
            String detail = "Phone State Detail";
            detail = detail + "\nIMEI Number: " + IMEI;
            detail = detail + "\nSIM Serial Number: " + simserialno;
            detail = detail + "\nNetwork Country ISO: " + netcountry;
            detail = detail + "\nSIM Country ISO: " + tm.getSimCountryIso();
            detail = detail + "\nVoice Mail Number: " + voicemailno;
            detail = detail + "\nRoaming: " + isroaming;
            detail = detail + "\nNetwork Type: " + strphonetype;
            detail = detail + "\nSim State: " + strsimstate;

            Intent intent = new Intent(MainActivity.this, Details.class);
            intent.putExtra("phoneStateDetails", detail);
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
            // Handle the SecurityException here, such as showing an error message
        }
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                retrievePhoneState();
            } else {
                // Permission denied by the user
                // Handle accordingly, e.g., show a message or disable functionality
            }
        }
    }
}
