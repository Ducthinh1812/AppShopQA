package com.duan1.appshopqa.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class DangKyActivity extends AppCompatActivity {
    TextInputEditText edtname, edtsdt,edtgmail,edtpass;
    Button btndangki;
    String cemail;
    String urllogin = "https://website1812.000webhostapp.com/ShopQuanAo/registerKH.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        edtname=findViewById(R.id.edtname);
        edtsdt=findViewById(R.id.edtsdt);
        edtgmail=findViewById(R.id.edtgmail);
        edtpass=findViewById(R.id.edtmatkhau);
        btndangki=findViewById(R.id.btndangki);
        btndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cemail = edtgmail.getText().toString();
                final ProgressDialog progressDialog = new ProgressDialog(DangKyActivity.this);
                progressDialog.setMessage("Please Wait..");
                if(!validatename() | !validatesdt() | !validateemail() | !validatepass() ){
                    return;
                }
                else{
                    progressDialog.show();
                    String str_name = edtname.getText().toString().trim();
                    String str_sdt = edtsdt.getText().toString().trim();
                    String str_email = edtgmail.getText().toString().trim();
                    String str_password = edtpass.getText().toString().trim();


                    StringRequest request = new StringRequest(Request.Method.POST, urllogin, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            if(response.equalsIgnoreCase("Đăng kí Thành Công")){
                                edtname.setText("");
                                edtsdt.setText("");
                                edtgmail.setText("");
                                edtpass.setText("");
                                Toast.makeText(DangKyActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(DangKyActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    },new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(DangKyActivity.this, "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("TENKH",str_name);
                            params.put("SDT",str_sdt);
                            params.put("GMAIL",str_email);
                            params.put("MATKHAU",str_password);
                            return params;

                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(DangKyActivity.this);
                    requestQueue.add(request);
                }
            }
        });
    }
    public  void MDanhNhap(View view){
        Intent integer=new Intent(DangKyActivity.this,DangNhapActivity.class);
        startActivity(integer);
    }
    public boolean validatename(){
        if(edtname.getText().toString().equals("")){
            edtname.setError("Hãy nhập tên của bạn.");
            return false;
        } else{
            edtname.setError(null);
            return true;
        }
    }
    public boolean validateemail(){
        String a="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(edtgmail.getText().toString().equals("")){
            edtgmail.setError("Hãy nhập gmail của bạn.");
            return false;
        }else if(!cemail.matches(a)) {
            edtgmail.setError("Nhập đúng định dạng gmail.");
            return false;
        }else{
            edtgmail.setError(null);
            return true;
        }
    }
    public boolean validatepass(){
        if(edtpass.getText().toString().equals("")){
            edtpass.setError("Nhập mật khẩu của bạn");
            return false;
        }else if(edtpass.length()<6) {
            edtpass.setError("Nhập mật khẩu trên 6 kí tự.");
            return false;
        }
        else{
            edtpass.setError(null);
            return true;
        }
    }
    public boolean validatesdt(){
        String a = "^0[0-9]{9}$";
        if(edtsdt.getText().toString().equals("")){
            edtsdt.setError("Nhập số điện thoại của bạn");
            return false;
        }else if(!edtsdt.getText().toString().matches(a)){
            edtsdt.setError("Hãy nhập đúng định dạng số điện thoại!");
            return false;
        }
        else{
            edtsdt.setError(null);
            return true;
        }
    }
}