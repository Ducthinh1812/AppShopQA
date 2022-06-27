package com.duan1.appshopqa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.duan1.appshopqa.R;
import com.duan1.appshopqa.fragment.DangNhapActivity;

public class LoadingActivity1 extends AppCompatActivity {
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading1);
        linearLayout=findViewById(R.id.manhLoading1);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLayout.setVisibility(View.GONE);
                Intent intent = new Intent(LoadingActivity1.this, DangNhapActivity.class);
                startActivity(intent);

                finish();
            }
        }, 3000);
    }
}