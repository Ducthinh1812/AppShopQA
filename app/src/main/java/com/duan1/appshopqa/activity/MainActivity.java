package com.duan1.appshopqa.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.duan1.appshopqa.R;
import com.duan1.appshopqa.fragment.DanhSachNVFragment;
import com.duan1.appshopqa.fragment.DoiMKFragment;
import com.duan1.appshopqa.fragment.PhuKienFragment;
import com.duan1.appshopqa.fragment.QuanAoFragment;
import com.duan1.appshopqa.fragment.QuanLy.QuanLyHoanDonFragment;
import com.duan1.appshopqa.fragment.QuanLy.QuanLySanPhamFragment;
import com.duan1.appshopqa.fragment.TaiKhoanFragment;
import com.duan1.appshopqa.fragment.ThemNVFragment;
import com.duan1.appshopqa.fragment.ThongTinShopFragment;
import com.duan1.appshopqa.fragment.TinTucTTFragment;
import com.duan1.appshopqa.fragment.TopDoanhThuFragment;
import com.duan1.appshopqa.fragment.TrangChuFragment;
import com.duan1.appshopqa.model.KhachHang;
import com.duan1.appshopqa.model.NhanVien;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final int FRAGMENT_TRANGCHU = 0;
    private static final int FRAGMENT_QUANAO = 1;
    private static final int FRAGMENT_PHUKIEN = 2;
    private static final int FRAGMENT_SPHOT = 3;
    private static final int FRAGMENT_THONGTINSHOP = 4;
    private static final int FRAGMENT_QLSANPHAM = 5;
    private static final int FRAGMENT_QLHOADON = 6;
    private static final int FRAGMENT_TOPDOANHTHU = 7;
    private static final int FRAGMENT_THEMTV = 8;
    private static final int FRAGMENT_DANHSACHNV = 9;
    private static final int FRAGMENT_TAIKHOAN = 10;
    private static final int FRAGMENT_DOIMK = 11;
    private static final int FRAGMENT_DANGXUAT = 12;
    private int mCurrentFragment = FRAGMENT_TRANGCHU;

    ArrayList<KhachHang> khachHangArrayList;
    ArrayList<NhanVien> nhanVienArrayList;
    private DrawerLayout mdrawerLayout;
    String urllayma = "https://website1812.000webhostapp.com/ShopQuanAo/getNhanVien.php";
    View view;
    TextView tvTenKH, tvTenDN;
    ImageView imgGio, imgTim;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgGio = findViewById(R.id.imgGioHang);
        imgTim = findViewById(R.id.imgTimKiem);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        mdrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mdrawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mdrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new TrangChuFragment());
        navigationView.getMenu().findItem(R.id.nav_trangchu).setChecked(true);


        imgGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GioHangActivity.class);
                startActivity(intent);
            }
        });

        imgTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TimKiemSPActivity.class);
                startActivity(intent);
            }
        });


        SharedPreferences preferences=MainActivity.this.getSharedPreferences("user_file", Context.MODE_PRIVATE);
        String user=preferences.getString("gmail","");
        view = navigationView.getHeaderView(0);
        tvTenDN = view.findViewById(R.id.tvTenDN);
        tvTenKH = view.findViewById(R.id.tvTenTK);
        setTenKH();
        tvTenKH.setText(user);


        StringRequest request = new StringRequest(Request.Method.POST, urllayma, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("Bạn đang đăng nhập với tư cách là nhân viên")){
                    navigationView.getMenu().findItem(R.id.nav_quanlySanpham).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_quanlyHoaDon).setVisible(true);

                }
                else if(response.equalsIgnoreCase("Xin chào quý khách đến với Shop")){
                    navigationView.getMenu().findItem(R.id.nav_taiKhoan).setVisible(true);

                }else if(response.equalsIgnoreCase("Xin chào Admin")){
                    navigationView.getMenu().findItem(R.id.nav_themNV).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_doanhthu).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_quanlySanpham).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_quanlyHoaDon).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_danhSachNV).setVisible(true);

                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("GMAILNV",user);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);



    }



    @Override
    public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_trangchu){
            if (mCurrentFragment != FRAGMENT_TRANGCHU){
                replaceFragment(new TrangChuFragment());
                mCurrentFragment = FRAGMENT_TRANGCHU;
            }
        }else if (id == R.id.nav_quanao){
            if (mCurrentFragment != FRAGMENT_QUANAO){
                replaceFragment(new QuanAoFragment());

                mCurrentFragment = FRAGMENT_QUANAO;
            }
        }else if (id == R.id.nav_phukien){
            if (mCurrentFragment != FRAGMENT_PHUKIEN){
                replaceFragment(new PhuKienFragment());

                mCurrentFragment = FRAGMENT_PHUKIEN;
            }
        }else if (id == R.id.nav_sanphamhot){
            if (mCurrentFragment != FRAGMENT_SPHOT){
                replaceFragment(new TinTucTTFragment());

                mCurrentFragment = FRAGMENT_SPHOT;
            }
        }else if (id == R.id.nav_thongtincuahang){
            if (mCurrentFragment != FRAGMENT_THONGTINSHOP){
                replaceFragment(new ThongTinShopFragment());

                mCurrentFragment = FRAGMENT_THONGTINSHOP;
            }
        }else if (id == R.id.nav_quanlySanpham){
            if (mCurrentFragment != FRAGMENT_QLSANPHAM){
                replaceFragment(new QuanLySanPhamFragment());

                mCurrentFragment = FRAGMENT_QLSANPHAM;
            }
        }
        else if (id == R.id.nav_quanlyHoaDon){
            if (mCurrentFragment != FRAGMENT_QLHOADON){
                replaceFragment(new QuanLyHoanDonFragment());

                mCurrentFragment = FRAGMENT_QLHOADON;
            }
        }
        else if (id == R.id.nav_doanhthu){
            if (mCurrentFragment != FRAGMENT_TOPDOANHTHU){
                replaceFragment(new TopDoanhThuFragment());

                mCurrentFragment = FRAGMENT_TOPDOANHTHU;
            }
        } else if (id == R.id.nav_themNV){
            if (mCurrentFragment != FRAGMENT_THEMTV){
                replaceFragment(new ThemNVFragment());

                mCurrentFragment = FRAGMENT_THEMTV;
            }
        } else if (id == R.id.nav_danhSachNV){
            if (mCurrentFragment != FRAGMENT_DANHSACHNV){
                replaceFragment(new DanhSachNVFragment());

                mCurrentFragment = FRAGMENT_DANHSACHNV;
            }
        } else if (id == R.id.nav_taiKhoan){
            if (mCurrentFragment != FRAGMENT_TAIKHOAN){
                replaceFragment(new TaiKhoanFragment());
                mCurrentFragment = FRAGMENT_TAIKHOAN;
            }
        }else if (id == R.id.nav_change_password){
            if (mCurrentFragment != FRAGMENT_DOIMK){
                replaceFragment(new DoiMKFragment());
                mCurrentFragment = FRAGMENT_DOIMK;
            }
        }else if (id == R.id.nav_logout){
            if (mCurrentFragment != FRAGMENT_DANGXUAT){
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("Đăng Xuất.");
                    builder.setMessage("Bạn Có Muốn Thoát Ứng Dụng Không ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                        }
                     });
                 builder.create().show();
                mCurrentFragment = FRAGMENT_DANGXUAT;
            }
        }
        mdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onBackPressed() {
        if (mdrawerLayout.isDrawerOpen(GravityCompat.START)){
            mdrawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (mCurrentFragment!=FRAGMENT_TRANGCHU){
            replaceFragment(new TrangChuFragment());
            mCurrentFragment = FRAGMENT_TRANGCHU;
        }else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }



    private void setTenKH(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        String user = preferences.getString("gmail", "");
        String matkhau = preferences.getString("matkhau", "");
        StringRequest request = new StringRequest(Request.Method.POST, "https://cyclothymic-floors.000webhostapp.com/DBShopQuanAo/getTenKHandNV.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    nhanVienArrayList = new ArrayList<>();
                    khachHangArrayList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("status");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(result.equals("thanh cong nv")){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            nhanVienArrayList.add(new NhanVien(
                                    object.getInt("MaNV"),
                                    object.getString("TenNV"),
                                    object.getString("GmailNV"),
                                    object.getString("MatKhauNV")
                            ));
                            NhanVien nv= nhanVienArrayList.get(0);
//                            tvTenKH.setText(nv.getGmailNV());
                            tvTenDN.setText(nv.getTenNV());
                        }
                    }else if(result.equals("thanh cong")){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            khachHangArrayList.add(new KhachHang(
                                    object.getString("MaKH"),
                                    object.getString("TenKH"),
                                    object.getString("DiaChiKH"),
                                    object.getString("NamSinh"),
                                    object.getString("SDT"),
                                    object.getString("Gmail"),
                                    object.getString("MatKhau")
                            ));
                            KhachHang kh=khachHangArrayList.get(0);
                            tvTenDN.setText(kh.getTenKH());
//                            tvTenKH.setText(kh.getGmail());
                        }
                    } else {
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
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Gmail", user);
                params.put("MatKhau", matkhau);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }


}