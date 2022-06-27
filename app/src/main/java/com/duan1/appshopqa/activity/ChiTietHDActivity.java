package com.duan1.appshopqa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.duan1.appshopqa.R;
import com.duan1.appshopqa.adapter.AdapterHoaDonCT;
import com.duan1.appshopqa.model.HoaDonCT;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietHDActivity extends AppCompatActivity {

    String maHD;
    ArrayList<HoaDonCT> hoaDonCTS;
    AdapterHoaDonCT adapterHoaDonCT;
    RecyclerView rclCTHD;
    Intent intent;
    String urlGetCTHD = "https://cyclothymic-floors.000webhostapp.com/DBShopQuanAo/getCTHD.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hdactivity);

        hoaDonCTS = new ArrayList<>();
        rclCTHD = findViewById(R.id.rclCTHD);
        hoaDonCTS = new ArrayList<>();
        intent = getIntent();

        adapterHoaDonCT = new AdapterHoaDonCT(getApplicationContext(), hoaDonCTS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rclCTHD.setLayoutManager(linearLayoutManager);
        rclCTHD.setAdapter(adapterHoaDonCT);
        getCTHD(urlGetCTHD);
    }

    private void getCTHD(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    String result=jsonObject.getString("status");
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    if(result.equals("thanh cong")){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            hoaDonCTS.add(new HoaDonCT(
                                    object.getInt("MASP"),
                                    object.getString("TENSP"),
                                    object.getString("MOTASP"),
                                    object.getLong("GIABAN"),
                                    object.getString("HINHANHSP"),
                                    object.getInt("SOLUONGXUAT")
                            ));
                            adapterHoaDonCT.notifyDataSetChanged();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                maHD = getIntent().getStringExtra("mahoadon");
                params.put("MAHD", maHD);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


}