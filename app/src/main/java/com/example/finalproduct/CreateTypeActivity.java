package com.example.finalproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateTypeActivity extends AppCompatActivity {
    EditText edtName;
    ContentValues values = new ContentValues();
    TypeProvider typeProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_type);
        edtName = findViewById(R.id.edtTypeName);
        typeProvider = new TypeProvider();
    }

    public boolean isAvailable(String typeName) {
        boolean result = true;
        Cursor cr = getContentResolver().query(TypeProvider.CONTENT_URI, null, null, null, "_id");

        while (cr.moveToNext()) {
            if (cr.getString(1).equals(typeName)) {
                result = false;
                break;
            }
        }
        return result;
    }

    public void clickToSubmit(View view) {
        // Check if selected name is available
        String edtNameString = edtName.getText().toString();
        if (!isAvailable(edtNameString)) {
            Toast.makeText(this,"Already registered name. Please select another one!",Toast.LENGTH_SHORT).show();
            return;
        }
        values.put("name",edtName.getText().toString());

        Uri uri = getContentResolver().insert(typeProvider.CONTENT_URI,values);
        Toast.makeText(this,uri.toString(),Toast.LENGTH_SHORT).show();
    }

}