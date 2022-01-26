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

    public void clickToSubmit(View view) {
        values.put("name",edtName.getText().toString());

        Uri uri = getContentResolver().insert(typeProvider.CONTENT_URI,values);
        Toast.makeText(this,uri.toString(),Toast.LENGTH_SHORT).show();
    }

}