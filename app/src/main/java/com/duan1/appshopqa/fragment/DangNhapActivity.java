package com.duan1.appshopqa.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.duan1.appshopqa.activity.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class DangNhapActivity extends AppCompatActivity {
    Button btnDangNhap;
    RequestQueue requestQueue;
    CheckBox checkBox;
    TextInputEditText edTaiKhoan, edMatKhau;
    TextView tvdangki,tvquenpass;
    String names;
    String str_email,str_password;
    String urllogin = "https://website1812.000webhostapp.com/ShopQuanAo/Login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        btnDangNhap=findViewById(R.id.btndangnhap);
        edTaiKhoan=findViewById(R.id.edtemail);
        edMatKhau=findViewById(R.id.edtpass);
        checkBox=findViewById(R.id.checkBox);
        SharedPreferences preferences =getSharedPreferences("user_file", MODE_PRIVATE);
        edTaiKhoan.setText(preferences.getString("gmail",""));
        edMatKhau.setText(preferences.getString("matkhau",""));
        checkBox.setChecked(preferences.getBoolean("remember",false));
        tvquenpass=findViewById(R.id.edtforgotpass);
        tvdangki=findViewById(R.id.tvdangki);
        requestQueue= Volley.newRequestQueue(this);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                names =edTaiKhoan.getText().toString();
                if(!validateemail() | !validatepass() ){
                    return;
                }
                else{
                    final ProgressDialog progressDialog = new ProgressDialog(DangNhapActivity.this);
                    progressDialog.setMessage("Please Wait..");
                    progressDialog.show();
                    str_email = edTaiKhoan.getText().toString().trim();
                    str_password = edMatKhau.getText().toString().trim();
                    StringRequest request = new StringRequest(Request.Method.POST, urllogin, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            if(response.equalsIgnoreCase("Đăng Nhập Thành Công")){
                                remember(str_email,str_password, checkBox.isChecked());
                                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("user",str_email);
                                startActivity(intent);
                                Toast.makeText(DangNhapActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(DangNhapActivity.this, response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    },new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(DangNhapActivity.this, "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                                params.put("GMAILNV",str_email);
                                params.put("MATKHAUNV",str_password);
                                params.put("GMAIL",str_email);
                                params.put("MATKHAU",str_password);
                            return params;

                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(DangNhapActivity.this);
                    requestQueue.add(request);
                }}
        });
        tvdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplication(),DangKyActivity.class);
                startActivity(intent);
            }
        });
        tvquenpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplication(),QuenPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
    public boolean validateemail(){
        String a="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(edTaiKhoan.getText().toString().equals("")){
            edTaiKhoan.setError("Hãy nhập gmail của bạn.");
            return false;
        }else if(!names.matches(a)) {
            edTaiKhoan.setError("Nhập đúng định dạng gmail.");
            return false;
        }else{
            edTaiKhoan.setError(null);
            return true;
        }
    }
    private void remember(String strname, String strpass, boolean checked) {
        SharedPreferences preferences=getSharedPreferences("user_file",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        if(!checked){
            editor.clear();
        }else {
            editor.putString("gmail",strname);
            editor.putString("matkhau",strpass);
            editor.putBoolean("remember",checked);
        }
        editor.commit();
    }
    public boolean validatepass(){
        if(edMatKhau.getText().toString().equals("")){
            edMatKhau.setError("Nhập mật khẩu của bạn");
            return false;
        } else{
            edMatKhau.setError(null);
            return true;
        }
    }
}