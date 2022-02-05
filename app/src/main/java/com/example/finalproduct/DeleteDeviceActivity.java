package com.example.finalproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteDeviceActivity extends AppCompatActivity {
    private String ERROR_DELETE_DEVICE = "Unable to delete device. Please try again!";
    private EditText deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_device);
        deviceId = findViewById(R.id.edtDeviceId);
    }

    public void clickToDeleteDevice(View view) {
        long id = Long.parseLong(deviceId.getText().toString());
        Uri uri = ContentUris.withAppendedId(DeviceProvider.CONTENT_URI, id);
        int count = getContentResolver().delete(uri, null, null);
        if (count > 0 ) {
            Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, ERROR_DELETE_DEVICE, Toast.LENGTH_SHORT).show();
        }
    }
}