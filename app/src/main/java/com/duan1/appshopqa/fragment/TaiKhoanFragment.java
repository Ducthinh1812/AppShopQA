package com.duan1.appshopqa.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.duan1.appshopqa.model.KhachHang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TaiKhoanFragment extends Fragment {
    private TextView edtMangdung;
    private TextView edtEmailngdung;
    private TextView edtTenngdung;
    private TextView edtSdtngdung;
    private TextView edtDiachingd;
    private TextView edtnamsinhnd;
    ArrayList<KhachHang> listKH;
    Button bntsua,btnxoa,btnluu;
    String Tenkh,Mail,Sdt,Diachi,NamSinh,mailcu;
    EditText tenkh,mail,sdt,diachi,namsinh;
    String urllogin = "https://cyclothymic-floors.000webhostapp.com/DBShopQuanAo/laythongtinKH.php";
    String urlsuakh = "https://cyclothymic-floors.000webhostapp.com/DBShopQuanAo/suaTTKH.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        edtMangdung = root.findViewById(R.id.edtMangdung);
        edtEmailngdung = root.findViewById(R.id.edtEmailngdung);
        edtTenngdung = root.findViewById(R.id.edtTenngdung);
        edtSdtngdung = root.findViewById(R.id.edtSdtngdung);
        edtDiachingd = root.findViewById(R.id.edtDiachingd);
        edtnamsinhnd = root.findViewById(R.id.edtnamsinhnd);
        bntsua = root.findViewById(R.id.btnsuatt);
        SharedPreferences preferences = getActivity().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        String user = preferences.getString("gmail", "");
        String matkhau = preferences.getString("matkhau", "");
        StringRequest request = new StringRequest(Request.Method.POST, urllogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listKH = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("status");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (result.equals("thanh cong")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            listKH.add(new KhachHang(
                                    object.getString("MAKH"),
                                    object.getString("TENKH"),
                                    object.getString("DIACHIKH"),
                                    object.getString("NAMSINH"),
                                    object.getString("SDT"),
                                    object.getString("GMAIL"),
                                    object.getString("MATKHAU")

                            ));
                            KhachHang nv = listKH.get(0);
                            edtMangdung.setText(nv.getMaKH());
                            edtEmailngdung.setText(nv.getGmail());
                            edtTenngdung.setText(nv.getTenKH());
                            edtSdtngdung.setText(nv.getSDT());
                            edtDiachingd.setText(nv.getDiaChiKH());
                            edtnamsinhnd.setText(nv.getNamSinh());
                        }
                    } else {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);



        bntsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KhachHang nv = listKH.get(0);
                View edit = LayoutInflater.from(getActivity()).inflate(R.layout.layout_suakh, null);
                mail = edit.findViewById(R.id.edtGmailKH);
                namsinh = edit.findViewById(R.id.NamsinhKH);
                sdt = edit.findViewById(R.id.edtSDTKH);
                tenkh = edit.findViewById(R.id.edtTenKH);
                diachi = edit.findViewById(R.id.edtDiachiKH);
                btnxoa=edit.findViewById(R.id.huythongtinkh);
                btnluu=edit.findViewById(R.id.suathongtinkh);
                mail.setText(nv.getGmail());
                namsinh.setText(nv.getNamSinh());
                tenkh.setText(nv.getTenKH());
                sdt.setText(nv.getSDT());
                diachi.setText(nv.getDiaChiKH());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Sửa Thông tin khách hàng " + nv.getTenKH());
                builder.setView(edit);
                AlertDialog dialo = builder.create();
                btnluu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Please Wait..");
                        Mail = mail.getText().toString();
                        NamSinh = namsinh.getText().toString();
                        Tenkh = tenkh.getText().toString();
                        Sdt = sdt.getText().toString();
                        Diachi = diachi.getText().toString();
                        mailcu = nv.getGmail();
                        if (!validatename() | !validateemail() | !validatesdt() | !validadiachi()) {
                            return;
                        } else {
                            progressDialog.show();
                            StringRequest request = new StringRequest(Request.Method.POST, urlsuakh, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("Gmail", Mail);
                                    params.put("TenKH", Tenkh);
                                    params.put("DiaChiKH", Diachi);
                                    params.put("SDT", Sdt);
                                    params.put("NamSinh", NamSinh);
                                    params.put("mailcu", mailcu);
                                    return params;

                                }
                            };
                            RequestQueue requestQue = Volley.newRequestQueue(getActivity());
                            requestQue.add(request);
                        }
                    }
                });
                btnxoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialo.dismiss();
                    }
                });
                dialo.show();
            }
        });

        return root;
    }

    public boolean validatename() {
        if (tenkh.getText().toString().equals("")) {
            tenkh.setError("Hãy nhập tên của bạn.");
            return false;
        } else {
            tenkh.setError(null);
            return true;
        }
    }

    public boolean validateemail() {
        String a = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (mail.getText().toString().equals("")) {
            mail.setError("Hãy nhập gmail của bạn.");
            return false;
        } else if (!Mail.matches(a)) {
            mail.setError("Nhập đúng định dạng gmail.");
            return false;
        } else {
            mail.setError(null);
            return true;
        }
    }

    public boolean validatesdt(){
        String a = "^0[0-9]{9}$";
        if(sdt.getText().toString().equals("")){
            sdt.setError("Nhập số điện thoại của bạn");
            return false;
        }else if(!sdt.getText().toString().matches(a)){
            sdt.setError("Hãy nhập đúng định dạng số điện thoại!");
            return false;
        }
        else{
            sdt.setError(null);
            return true;
        }
    }
    public boolean validadiachi() {
        if (diachi.getText().toString().equals("")) {
            diachi.setError("Hãy nhập địa chỉ của bạn.");
            return false;
        } else {
            diachi.setError(null);
            return true;
        }
    }

}