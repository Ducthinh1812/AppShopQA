package com.duan1.appshopqa.fragment.QuanLy;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.duan1.appshopqa.R;
import com.duan1.appshopqa.adapter.AdapterQuanLiSP;
import com.duan1.appshopqa.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuanLySanPhamFragment extends Fragment {

    EditText editTENSPT;
    EditText editSOLUONGNHAPT;
    EditText editHASPT;
    EditText editGIABANT;
    EditText editMOTASPT;
    EditText editMaLoaiT;
    Button btnHuyThem;
    Button btnLuuThem;

    RecyclerView rclQLSP;
    ArrayList<SanPham> sanPhamArrayList;
    AdapterQuanLiSP adapterQuanLiSP;
    FloatingActionButton fab;
    String urlSP = "https://cyclothymic-floors.000webhostapp.com/DBShopQuanAo/GetSP.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container,@Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quan_ly_san_pham, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rclQLSP = view.findViewById(R.id.rclQLSP);
        fab = view.findViewById(R.id.floatingActionButton);
        sanPhamArrayList = new ArrayList<>();
        adapterQuanLiSP = new AdapterQuanLiSP(getActivity(), sanPhamArrayList);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rclQLSP.setLayoutManager(linearLayoutManager);
        rclQLSP.setAdapter(adapterQuanLiSP);

        getQLSP(urlSP);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themSP();
            }
        });
    }

    private void getQLSP(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                sanPhamArrayList.add(new SanPham(
                                        object.getInt("MASP"),
                                        object.getString("TENSP"),
                                        object.getString("HINHANHSP"),
                                        object.getString("MOTASP"),
                                        object.getInt("SOLUONGNHAP"),
                                        object.getInt("GIABAN"),
                                        object.getInt("MATL")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapterQuanLiSP.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Lỗi !" , Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void themSP(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View viewT = LayoutInflater.from(builder.getContext()).inflate(R.layout.dialog_them_sp, null);
        builder.setView(viewT);
        Dialog dialog = builder.create();
        dialog.show();


        editTENSPT = (EditText) viewT.findViewById(R.id.editTENSPT);
        editSOLUONGNHAPT = (EditText) viewT.findViewById(R.id.editSOLUONGNHAPT);
        editHASPT = (EditText) viewT.findViewById(R.id.editHASPT);
        editGIABANT = (EditText) viewT.findViewById(R.id.editGIABANT);
        editMOTASPT = (EditText) viewT.findViewById(R.id.editMOTASPT);
        editMaLoaiT = (EditText) viewT.findViewById(R.id.editMaLoaiT);
        btnHuyThem = (Button) viewT.findViewById(R.id.btnHuyThem);
        btnLuuThem = (Button) viewT.findViewById(R.id.btnLuuThem);



        btnLuuThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_tensp = editTENSPT.getText().toString().trim();
                String str_soluongnhap = editSOLUONGNHAPT.getText().toString().trim();
                String str_hinhanhsp = editHASPT.getText().toString().trim();
                String str_motasp = editMOTASPT.getText().toString().trim();
                String str_giaban = editGIABANT.getText().toString().trim();
                String str_matl = editMaLoaiT.getText().toString().trim();
                String urlUpdateSp = "https://cyclothymic-floors.000webhostapp.com/DBShopQuanAo/ThemSP.php";

                if (str_tensp.equalsIgnoreCase("") || str_soluongnhap.equalsIgnoreCase("")
                        || str_hinhanhsp.equalsIgnoreCase("") || str_motasp.equalsIgnoreCase("")
                        || str_giaban.equalsIgnoreCase("")){
                    Toast.makeText(builder.getContext(), "Hãy nhập hết tất cả các trường trên form!", Toast.LENGTH_SHORT).show();
                }

                RequestQueue requestQueue = Volley.newRequestQueue(builder.getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateSp, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("error")){
                            Toast.makeText(builder.getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(builder.getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(builder.getContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("TENSP", str_tensp);
                        params.put("HINHANHSP", str_hinhanhsp);
                        params.put("MOTASP", str_motasp);
                        params.put("SOLUONGNHAP", str_soluongnhap);
                        params.put("GIABAN", str_giaban);
                        params.put("MALOAI", str_matl);
                        return params;
                    }
                };

                requestQueue.add(stringRequest);
                dialog.dismiss();
            }
        });

        btnHuyThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}