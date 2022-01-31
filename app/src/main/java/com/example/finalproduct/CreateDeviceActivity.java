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

    @Override
    public String toString() {
        return this.name;
    }

    public Type(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
}

public class CreateDeviceActivity extends AppCompatActivity {
    EditText edtDeviceName, edtDeviceQuantity;
    Spinner spDeviceType;
    ContentValues values = new ContentValues();
    Type selectedType;
    int selectedTypeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_device);
        edtDeviceName = findViewById(R.id.edtDeviceName);
        edtDeviceQuantity = findViewById(R.id.edtDeviceQuantity);
        spDeviceType = findViewById(R.id.spinnerDeviceType);

        //get the list of type
        Cursor cr = getContentResolver().query(TypeProvider.CONTENT_URI,null,null,null,"_id");
        StringBuilder stringBuilder = new StringBuilder();
        List<Type> lstType = new ArrayList<>();

        while(cr.moveToNext()){
            int id = cr.getInt(0);
            String name = cr.getString(1);
            lstType.add(new Type(id, name));
        }
        //for the spinner display
        ArrayAdapter<Type> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lstType);
        //specify the layout of the dropdown list
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //apply the adapter to the spinner
        spDeviceType.setAdapter(adapter);
        //responding to the user selection
        spDeviceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedType = (Type) spDeviceType.getSelectedItem();
                selectedTypeId = selectedType.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void clickToInsertDevice(View view) {
        values.put("name", edtDeviceName.getText().toString());
        values.put("quantity", edtDeviceQuantity.getText().toString());
        values.put("typeId", selectedTypeId);

        Uri uri = getContentResolver().insert(DeviceProvider.CONTENT_URI,values);
        Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
    }
}