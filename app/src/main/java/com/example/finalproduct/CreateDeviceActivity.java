package com.example.finalproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class Type {
    private int id;
    private String name;

    public Type(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

public class CreateDeviceActivity extends AppCompatActivity {
    EditText edtDeviceName, edtDeviceQuantity;
    Spinner spDeviceType;
    ContentValues values = new ContentValues();
    DeviceProvider deviceProvider;
    TypeProvider typeProvider;
    String selectedSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_device);
        edtDeviceName = findViewById(R.id.edtDeviceName);
        edtDeviceQuantity = findViewById(R.id.edtDeviceQuantity);
        spDeviceType = findViewById(R.id.spinnerDeviceType);

        Cursor cr = getContentResolver().query(typeProvider.CONTENT_URI,null,null,null,"_id");
        StringBuilder stringBuilder = new StringBuilder();
        List<Type> lstType = new ArrayList<>();
        List<String> lstTypeName = new ArrayList<>();

        while(cr.moveToNext()){
            int id = cr.getInt(0);
            String name = cr.getString(1);
            lstType.add(new Type(id, name));
            lstTypeName.add(name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lstTypeName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDeviceType.setAdapter(adapter);
        spDeviceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSpinner = spDeviceType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        deviceProvider = new DeviceProvider();
    }

    public void clickToInsertDevice(View view) {
        values.put("name", edtDeviceName.getText().toString());
        values.put("quantity", edtDeviceQuantity.getText().toString());
        values.put("type", selectedSpinner.toString());

        Uri uri = getContentResolver().insert(deviceProvider.CONTENT_URI,values);
        Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
    }
}