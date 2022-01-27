package com.example.finalproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTypeActivity extends AppCompatActivity {
    TypeProvider typeProvider;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_type);
        txtResult = findViewById(R.id.txtResult);
        typeProvider = new TypeProvider();
        Cursor cr = getContentResolver().query(typeProvider.CONTENT_URI,null,null,null,"_id");
        StringBuilder stringBuilder = new StringBuilder();

        while(cr.moveToNext()){
            int id = cr.getInt(0);
            String name = cr.getString(1);
            stringBuilder.append(id+"       "+ name +"\n");
        }
        txtResult.setText(stringBuilder.toString());
    }
}