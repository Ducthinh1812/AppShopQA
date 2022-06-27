package com.duan1.appshopqa.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

public class TopDoanhThuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_doanh_thu, container, false);
    }

    TextView tvDoanhThu;
    Button btnDoanhThu;
    EditText editDoanhThu;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvDoanhThu = view.findViewById(R.id.tvDTSP);
        btnDoanhThu = view.findViewById(R.id.btnDTSP);
        editDoanhThu = view.findViewById(R.id.editDTSP);

        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDTSP("https://cyclothymic-floors.000webhostapp.com/DBShopQuanAo/GetDTSP.php");
            }
        });
    }

    private  void getDTSP(String url){
        String tenSp = editDoanhThu.getText().toString().trim();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tvDoanhThu.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("TENSP", tenSp);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
