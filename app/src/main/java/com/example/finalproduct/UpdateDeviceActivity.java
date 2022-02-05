package com.example.finalproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

class Device {
    private int id;
    private String name;
    private int quantity;
    private int typeId;

    public Device(int id, String name, int quantity, int typeId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTypeId() {
        return typeId;
    }
}

public class UpdateDeviceActivity extends AppCompatActivity {
    EditText newDeviceName, newDeviceQuantity;
    Spinner spDevice, spType;
    Type selectedType;
    Device selectedDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_device);
        newDeviceName = findViewById(R.id.edtDeviceName);
        newDeviceQuantity = findViewById(R.id.edtDeviceQuantity);
        spDevice = findViewById(R.id.spinnerListOfDevices);
        spType = findViewById(R.id.spinnerDeviceType);

        // Retrieve list of devices using Cursor
        Cursor deviceCr = getContentResolver().query(DeviceProvider.CONTENT_URI, null, null, null, "_id");
        List<Device> lstDevice = new ArrayList<>();

        while (deviceCr.moveToNext()) {
            int id = deviceCr.getInt(0);
            String name = deviceCr.getString(1);
            int quantity = deviceCr.getInt(2);
            int typeId = deviceCr.getInt(3);
            lstDevice.add(new Device(id, name, quantity, typeId));
        }

        // Retrieve list of types
        Cursor typeCr = getContentResolver().query(TypeProvider.CONTENT_URI,null,null,null,"_id");
        List<Type> lstType = new ArrayList<>();

        while(typeCr.moveToNext()){
            int id = typeCr.getInt(0);
            String name = typeCr.getString(1);
            lstType.add(new Type(id, name));
        }

        // When user pick a device, fields will appear with the current information of the device
        // Load the device spinner
        ArrayAdapter<Device> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lstDevice);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDevice.setAdapter(adapter);
        spDevice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDevice = (Device) spDevice.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



    }

    public void clickToUpdateDevice(View view) {
    }
}