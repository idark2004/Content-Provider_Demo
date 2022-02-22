package com.example.finalproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class ViewDeviceActivity extends AppCompatActivity {
    TextView txtAllDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_device);
        txtAllDevices = findViewById(R.id.txtAllDevices);
        Cursor cr = getContentResolver().query(DeviceProvider.CONTENT_URI,null,null,null,"_id");
        StringBuilder stringBuilder = new StringBuilder().append("Empty list :(");

        if (cr != null && cr.getCount() > 0) {
            stringBuilder = new StringBuilder();
            while (cr.moveToNext()) {
                int id = cr.getInt(0);
                String name = cr.getString(1);
                String quantity = cr.getString(2);
                int typeId = cr.getInt(3);
                stringBuilder.append(id + "  " + name + " " + quantity + " " + typeId + "\n");
            }
        }
        txtAllDevices.setText(stringBuilder.toString());
    }
}