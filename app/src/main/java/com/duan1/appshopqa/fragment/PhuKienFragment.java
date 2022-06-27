package com.duan1.appshopqa.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
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
import com.duan1.appshopqa.adapter.AdapterSanPham;
import com.duan1.appshopqa.model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhuKienFragment extends Fragment {

    Toolbar toolbar;
    RecyclerView recyclerView;
    boolean isLoading = false;
    boolean isLimit = false;
    View footerV;
    AdapterSanPham adapterSanPham;
    ArrayList<SanPham> listQA;
    String urlGetQA = "https://cyclothymic-floors.000webhostapp.com/DBShopQuanAo/getPK.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phukien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Anh xa
//        toolbar = view.findViewById(R.id.toolBarQA);
        recyclerView = view.findViewById(R.id.lvPhuKien);
        listQA = new ArrayList<>();
        adapterSanPham = new AdapterSanPham(listQA, getActivity());
        //-------------------
        //tool bar
        //-------------------
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterSanPham);
        getPK(urlGetQA);

    }

    private void getPK(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                listQA.add(new SanPham(
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
                        adapterSanPham.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Lỗi !" , Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}
