package com.duan1.appshopqa.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.duan1.appshopqa.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class QuenPasswordActivity extends AppCompatActivity {
    TextInputEditText edtseach;
    Button btnseach;
    TextView tvMK;
    String urlnv = "https://website1812.000webhostapp.com/ShopQuanAo/quenmatkhau.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_password);
        tvMK = findViewById(R.id.txtMatKhau);
        edtseach = findViewById(R.id.edtseach);
        btnseach = findViewById(R.id.btnseach);
        btnseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quenMK();
            }
        });
    }

    public void quenMK() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        String str_seach = edtseach.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlnv, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tvMK.setText(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext()," Xảy ra lỗi !", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("GmailNV", str_seach);
                params.put("Gmail", str_seach);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}