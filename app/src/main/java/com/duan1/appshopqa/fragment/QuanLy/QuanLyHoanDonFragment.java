package com.duan1.appshopqa.fragment.QuanLy;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.duan1.appshopqa.R;
import com.duan1.appshopqa.adapter.AdapterHoaDon;
import com.duan1.appshopqa.model.HoaDon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class QuanLyHoanDonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quan_ly_hoan_don, container, false);
    }

    RecyclerView rclHD;
    ArrayList<HoaDon> hoaDonArrayList;
    AdapterHoaDon adapterHoaDon;
    String urlGetHD = "https://cyclothymic-floors.000webhostapp.com/DBShopQuanAo/GetHoaDon.php";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rclHD = view.findViewById(R.id.rclHD);
        hoaDonArrayList = new ArrayList<>();

        adapterHoaDon = new AdapterHoaDon(getActivity(), hoaDonArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rclHD.setLayoutManager(linearLayoutManager);
        rclHD.setAdapter(adapterHoaDon);
        getHD(urlGetHD);
    }

    private void getHD(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        hoaDonArrayList.add(new HoaDon(
                                object.getString("MAHD"),
                                object.getInt("MAKH"),
                                object.getString("MANV"),
                                object.getString("NGAYTAO")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapterHoaDon.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
