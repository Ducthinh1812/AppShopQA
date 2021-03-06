package com.duan1.appshopqa.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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


public class ThemNVFragment extends Fragment {
    TextInputEditText edtname,edtgmail,edtpass;
    Button btndangki;
    String cemail;
    String urllogin = "https://website1812.000webhostapp.com/ShopQuanAo/registerNV.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_them_n_v, container, false);

    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtname=view.findViewById(R.id.Edttnv);
        edtgmail=view.findViewById(R.id.Edtgmailnv);
        edtpass=view.findViewById(R.id.Edtpassnv);
        btndangki=view.findViewById(R.id.btndangkinv);
        btndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cemail = edtgmail.getText().toString();
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Please Wait..");
                if(!validatename() | !validateemail() | !validatepass() ){
                    return;
                }
                else{
                    progressDialog.show();
                    String str_name = edtname.getText().toString().trim();
                    String str_email = edtgmail.getText().toString().trim();
                    String str_password = edtpass.getText().toString().trim();


                    StringRequest request = new StringRequest(Request.Method.POST, urllogin, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            if(response.equalsIgnoreCase("????ng k?? Th??nh C??ng")){
                                edtname.setText("");
                                edtgmail.setText("");
                                edtpass.setText("");
                                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    },new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),"x???y ra l???i!", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("TenNV",str_name);
                            params.put("GmailNV",str_email);
                            params.put("MatKhauNV",str_password);
                            return params;

                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(request);
                }
            }
        });
    }
    public  void MDanhNhap(View view){
        Intent integer=new Intent(getActivity(),DangNhapActivity.class);
        startActivity(integer);
    }
    public boolean validatename(){
        if(edtname.getText().toString().equals("")){
            edtname.setError("H??y nh???p t??n c???a b???n.");
            return false;
        } else{
            edtname.setError(null);
            return true;
        }
    }
    public boolean validateemail(){
        String a="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(edtgmail.getText().toString().equals("")){
            edtgmail.setError("H??y nh???p gmail c???a b???n.");
            return false;
        }else if(!cemail.matches(a)) {
            edtgmail.setError("Nh???p ????ng ?????nh d???ng gmail.");
            return false;
        }else{
            edtgmail.setError(null);
            return true;
        }
    }
    public boolean validatepass(){
        if(edtpass.getText().toString().equals("")){
            edtpass.setError("Nh???p m???t kh???u c???a b???n");
            return false;
        }else if(edtpass.length()<6) {
            edtpass.setError("Nh???p m???t kh???u tr??n 6 k?? t???.");
            return false;
        }
        else{
            edtpass.setError(null);
            return true;
        }
    }
}