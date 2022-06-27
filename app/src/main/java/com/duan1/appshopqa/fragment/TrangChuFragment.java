package com.duan1.appshopqa.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.duan1.appshopqa.R;
import com.duan1.appshopqa.adapter.AdapterSanPhamMoi;
import com.duan1.appshopqa.model.GioHang;
import com.duan1.appshopqa.model.SanPham;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrangChuFragment extends Fragment {

    ViewFlipper viewFlipper;
    public static ArrayList<GioHang> giohanglist;
    ArrayList<SanPham> sanPhamArrayList;
    AdapterSanPhamMoi adapterSanPhamMoi;
    RecyclerView rclSPMoi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trang_chu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sanPhamArrayList = new ArrayList<>();
        viewFlipper = (ViewFlipper)view.findViewById(R.id.viewlipper);
        adapterSanPhamMoi = new AdapterSanPhamMoi(getActivity(), sanPhamArrayList);
        rclSPMoi = view.findViewById(R.id.rclHangKM);


        if (giohanglist != null){

        }else {
            giohanglist = new ArrayList<>();
        }
        ViewFlip();
        getSPMoiNhat("https://website1812.000webhostapp.com/ShopQuanAo/getNhanVien.php");
        rclSPMoi.setHasFixedSize(true);
        rclSPMoi.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rclSPMoi.setAdapter(adapterSanPhamMoi);
    }

    void ViewFlip(){
        ArrayList<String> mang = new ArrayList<>();
        mang.add("https://giaycaosmartmen.com/wp-content/uploads/2020/11/phoi-do-nam.jpg");
        mang.add("https://media.coolmate.me/uploads/September2020/cach-phoi-do-dep-cho-nam-1.jpg");
        mang.add("https://danangaz.com/wp-content/uploads/2021/02/cach-phoi-do-thap-nien-90-3-min.jpg");
        mang.add("https://lh6.googleusercontent.com/SN8jeMibgTQEtwZ3ctgiZcp4ciZBs27JErplD0lHYRrBVfr41avoUVfoSDvp8L43-ew-uQwJVzyFqegbi2VghV0bbV9j7RbOHqxJ8n-7kHZKp_kKoXFIePL4QxpuFx75FJfvko1Z");

        for(int i=0;i<mang.size();i++)
        {
            ImageView imageView = new ImageView(getActivity());
            Picasso.get().load(mang.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);

        Animation animation_in = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_in);
        Animation animation_out = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_out);

        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);
    }

    void getSPMoiNhat(String url){
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
                        adapterSanPhamMoi.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
