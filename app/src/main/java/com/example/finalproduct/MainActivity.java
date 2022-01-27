package com.example.finalproduct;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {
    private List<Fragment> fragmentList = new ArrayList<>();
    private TypeFragment tabType;
    private DeviceFragment deviceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab1 = bar.newTab();
        tab1.setText("Device");
        tab1.setTabListener(this);
        bar.addTab(tab1);

        ActionBar.Tab tab2 = bar.newTab();
        tab2.setText("Type");
        tab2.setTabListener(this);
        bar.addTab(tab2);
    }

    public void clickToInsertType(View view) {
        Intent intent = new Intent(this, CreateTypeActivity.class);
        startActivity(intent);
    }

    public void clickToViewType(View view) {
        Intent intent = new Intent(this, ViewTypeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if(tab.getPosition() == 0) {
            if(deviceFragment == null){
                deviceFragment = new DeviceFragment();
                fragmentList.add(deviceFragment);
            } else{
                deviceFragment = (DeviceFragment) fragmentList.get(tab.getPosition());
            }
            ft.replace(android.R.id.content,deviceFragment);
        } else {
            if(tabType == null){
                tabType = new TypeFragment();
            }
            ft.replace(android.R.id.content,tabType);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public void clickToInsertDevice(View view) {
        Intent intent = new Intent(this, CreateDeviceActivity.class);
        startActivity(intent);
    }

    public void clickToViewDevice(View view) {
        Intent intent = new Intent(this, ViewDeviceActivity.class);
        startActivity(intent);
    }

    public void clickToUpdateDevice(View view) {
    }

    public void clickToDeleteDevice(View view) {
    }
}