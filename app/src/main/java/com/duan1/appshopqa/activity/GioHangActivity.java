package com.duan1.appshopqa.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.duan1.appshopqa.R;
import com.duan1.appshopqa.adapter.AdapterGioHang;
import com.duan1.appshopqa.fragment.TrangChuFragment;
import com.duan1.appshopqa.model.GioHang;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GioHangActivity extends AppCompatActivity {

    String user;
    Toolbar toolbar;
    ListView listView;
    TextView txtThongbao;
    static TextView txtTongTien;//tong tien luon duoc cong don,khong bi thay doi khi chuyen activity
    Button btnthanhtoan,btntieptucmuahang;
    AdapterGioHang adapterGioHang;
    Calendar calendar = Calendar.getInstance();
    String ngayHT = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        CheckData();
        ClickItemListview();
        ClickButton();
        UpdateTongTien();
    }

    void ActionToolBar()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {//xu ly su kien khi click toolbar
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void Anhxa()
    {
        listView = findViewById(R.id.listviewgiohang);
        txtThongbao = findViewById(R.id.textviewthongbao);
        txtTongTien = findViewById(R.id.textviewtongtien);
        btnthanhtoan = findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmuahang = findViewById(R.id.buttontieptucmuahang);
        adapterGioHang = new AdapterGioHang(GioHangActivity.this,TrangChuFragment.giohanglist);
        listView.setAdapter(adapterGioHang);

    }

    void CheckData()
    {
        if(TrangChuFragment.giohanglist.size()<=0)
        {
            adapterGioHang.notifyDataSetChanged();
            txtThongbao.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }
        else
        {
            adapterGioHang.notifyDataSetChanged();
            txtThongbao.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    void ClickItemListview()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(TrangChuFragment.giohanglist.size()<1)
                {
                    txtThongbao.setVisibility(View.VISIBLE);
                }
                else
                {
                    TrangChuFragment.giohanglist.remove(i);
                    adapterGioHang.notifyDataSetChanged();
                    UpdateTongTien();
                    if(TrangChuFragment.giohanglist.size() <1)
                    {
                        txtThongbao.setVisibility(View.VISIBLE);
                    }else {
                        txtThongbao.setVisibility(View.INVISIBLE);
                        adapterGioHang.notifyDataSetChanged();
                        UpdateTongTien();
                    }
                }
            }
        });
    }

    public static void UpdateTongTien()
    {
        long tongtien=0;
        for(int i = 0;i<TrangChuFragment.giohanglist.size();i++)
        {
            tongtien += TrangChuFragment.giohanglist.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongtien)+" VND");
    }

    void ClickButton()
    {
        btntieptucmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TrangChuFragment.giohanglist.size() >0)
                {
                    ngayHT = String.valueOf(calendar.get(Calendar.HOUR))+String.valueOf(calendar.get(Calendar.MINUTE))+String.valueOf(calendar.get(Calendar.DATE))
                            +String.valueOf(calendar.get(Calendar.MONTH)+1)+String.valueOf(calendar.get(Calendar.YEAR));

                    addHD();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0;i<TrangChuFragment.giohanglist.size(); i++) {
                                addCTHD(i);
                            }
                        }
                    }, 5000);

                    Toast.makeText(GioHangActivity.this, "Vui lòng chờ trong giây lát ....", Toast.LENGTH_LONG).show();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtThongbao.setVisibility(View.VISIBLE);
                            TrangChuFragment.giohanglist.clear();
                            UpdateTongTien();
                            adapterGioHang.notifyDataSetChanged();
                            Toast.makeText(GioHangActivity.this, "Hóa đơn của bạn đã được xử lý!", Toast.LENGTH_SHORT).show();
                        }
                    }, 7000);

                }
                else
                {
                    Toast.makeText(GioHangActivity.this, "Giỏ hàng không có sản phầm nào!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addHD(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://cyclothymic-floors.000webhostapp.com/DBShopQuanAo/insertHD.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("error")) {
                    Toast.makeText(GioHangActivity.this, "Xảy ra lỗi thanh toán không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GioHangActivity.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences preferences=getApplication().getSharedPreferences("user_file", Context.MODE_PRIVATE);
                user = preferences.getString("gmail","");
                params.put("GMAIL", user);
                params.put("MAHD", ngayHT);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void addCTHD(int i){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://cyclothymic-floors.000webhostapp.com/DBShopQuanAo/insertCTHD.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            Toast.makeText(GioHangActivity.this, "Xảy ra lỗi khi thanh toán!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GioHangActivity.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                GioHang gioHang = TrangChuFragment.giohanglist.get(i);
                Map<String, String> params = new HashMap<>();
                SharedPreferences preferences=getApplication().getSharedPreferences("user_file", Context.MODE_PRIVATE);
                user = preferences.getString("gmail","");
                params.put("GMAIL", user);
                params.put("MAHD", ngayHT);
                params.put("MASP", String.valueOf(gioHang.getIdsp()));
                params.put("SOLUONGXUAT", String.valueOf(gioHang.getSoluongsp()));

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}