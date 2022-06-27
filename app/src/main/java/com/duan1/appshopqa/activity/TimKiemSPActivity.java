package com.duan1.appshopqa.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.duan1.appshopqa.adapter.AdapterTimKiem;
import com.duan1.appshopqa.model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimKiemSPActivity extends AppCompatActivity {

    EditText editTimKiem;
    Button btnTimKiem;
    RecyclerView rclTimKiem;
    ArrayList<SanPham> sanPhamArrayList;
    AdapterTimKiem adapterTimKiem;
    String urlTimKiem = "https://cyclothymic-floors.000webhostapp.com/DBShopQuanAo/timKiemSP.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_spactivity);

        sanPhamArrayList = new ArrayList<>();

        editTimKiem = findViewById(R.id.editTimKiem);
        btnTimKiem = findViewById(R.id.btnTimKiemSP);
        rclTimKiem = findViewById(R.id.rclTimKiem);
        adapterTimKiem = new AdapterTimKiem(getApplicationContext(), sanPhamArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rclTimKiem.setLayoutManager(linearLayoutManager);
        rclTimKiem.setAdapter(adapterTimKiem);

        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sanPhamArrayList.clear();
                timKiemSP(urlTimKiem);
            }
        });
    }

    private void timKiemSP(String url){
        String timKiem = editTimKiem.getText().toString().trim();

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
                            sanPhamArrayList.add(new SanPham(
                                    object.getInt("MASP"),
                                    object.getString("TENSP"),
                                    object.getString("HINHANHSP"),
                                    object.getString("MOTASP"),
                                    object.getInt("SOLUONGNHAP"),
                                    object.getInt("GIABAN"),
                                    object.getInt("MATL")
                            ));
                            adapterTimKiem.notifyDataSetChanged();
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
                params.put("tensp", timKiem);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}