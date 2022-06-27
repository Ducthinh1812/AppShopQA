package com.duan1.appshopqa.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

public class DoiMKFragment extends Fragment {
    private TextInputEditText edtpasscu;
    private TextInputEditText edtpassmoi;
    private TextInputEditText edtnhaplaipass;
    private Button btnluuMK;
    private Button btnhuydoimk;
    String urldoimk = "https://website1812.000webhostapp.com/ShopQuanAo/doipass.php";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtpasscu = view.findViewById(R.id.edtpasscu);
        edtpassmoi = view.findViewById(R.id.edtpassmoi);
        edtnhaplaipass = view.findViewById(R.id.edtnhaplaipass);
        btnluuMK = view.findViewById(R.id.btnluuMK);
        btnhuydoimk = view.findViewById(R.id.btnhuydoimk);
        btnhuydoimk.setOnClickListener((v) -> {
            edtpasscu.setText("");
            edtpassmoi.setText("");
            edtnhaplaipass.setText("");
        });
        btnluuMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences=getActivity().getSharedPreferences("user_file", Context.MODE_PRIVATE);
                String user=preferences.getString("gmail","");
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Please Wait..");
                if(!validatepass() ){
                    return;
                }
                else{
                    progressDialog.show();
                    String str_passcu = edtpasscu.getText().toString().trim();
                    String str_passmoi = edtpassmoi.getText().toString().trim();
                    String str_passnhaplai = edtnhaplaipass.getText().toString().trim();
                    StringRequest request = new StringRequest(Request.Method.POST, urldoimk, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            if(response.equalsIgnoreCase("Thay đổi mật khẩu thành công")){
                                edtpasscu.setText("");
                                edtpassmoi.setText("");
                                edtnhaplaipass.setText("");
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
                            Toast.makeText(getActivity(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("MATKHAUNV",str_passcu);
                            params.put("MATKHAU",str_passcu);
                            params.put("GMAILNV",user);
                            params.put("GMAIL",user);
                            params.put("passmoi",str_passmoi);
                            params.put("nhaplaipassmoi",str_passnhaplai);
                            return params;

                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(request);
                }
            }
        });

    }
    public boolean validatepass(){
        if(edtpasscu.getText().toString().equals("")){
            edtpasscu.setError("Nhập mật khẩu của bạn");
            return false;
        }
        else if(edtpassmoi.getText().toString().equals("")) {
            edtpassmoi.setError("Nhập mật khẩu của bạn");
            return false;
        }
        else if(edtnhaplaipass.getText().toString().equals("")) {
            edtnhaplaipass.setError("Nhập mật khẩu của bạn");
            return false;
        }else if(edtpassmoi.length()<6) {
            edtpassmoi.setError("Nhập mật khẩu trên 6 kí tự.");
            return false;
        }
        else{
            edtpasscu.setError(null);
            edtpassmoi.setError(null);
            edtnhaplaipass.setError(null);
            return true;
        }
    }
}
