package com.example.finalproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class ViewDeviceActivity extends AppCompatActivity {
    DeviceProvider deviceProvider;
    TextView txtAllDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_device);
        txtAllDevices = findViewById(R.id.txtAllDevices);
        deviceProvider = new DeviceProvider();
        Cursor cr = getContentResolver().query(deviceProvider.CONTENT_URI,null,null,null,"_id");
        StringBuilder stringBuilder = new StringBuilder();

        while(cr.moveToNext()){
            int id = cr.getInt(0);
            String name = cr.getString(1);
            String quantity = cr.getString(2);
            String typeName = cr.getString(3);
            System.out.println(name);
            stringBuilder.append(id + "  "+ name + " " + quantity + " " + typeName +"\n");
        }
        txtAllDevices.setText(stringBuilder.toString());
    }
}