package com.duan1.appshopqa.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.duan1.appshopqa.R;
import com.duan1.appshopqa.activity.ChiTietHDActivity;
import com.duan1.appshopqa.model.HoaDon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterHoaDon extends RecyclerView.Adapter<AdapterHoaDon.HoaDonHolder> {

    Context context;
    ArrayList<HoaDon> hoaDonArrayList;

    public AdapterHoaDon(Context context, ArrayList<HoaDon> hoaDonArrayList) {
        this.context = context;
        this.hoaDonArrayList = hoaDonArrayList;
    }

    @NonNull
    @Override
    public HoaDonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoa_don, parent, false);
        return new HoaDonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHoaDon.HoaDonHolder holder, int position) {
        HoaDon hoaDon = hoaDonArrayList.get(position);
        holder.tvMaHD.setText(hoaDon.getMaHD());
        holder.tvMAKHHD.setText(String.valueOf(hoaDon.getMaKH()));
        holder.tvMaNVHD.setText(String.valueOf(hoaDon.getMaNV()));
        holder.tvNgayTaoHD.setText(hoaDon.getNgayTao());

        if (hoaDon.getMaNV().equals("null")) {
            holder.btnDuyetHD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = v.getContext().getSharedPreferences("user_file", Context.MODE_PRIVATE);
                    String user = preferences.getString("gmail", "");

                    RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://cyclothymic-floors.000webhostapp.com/DBShopQuanAo/DuyetHoaDon.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {
                                Toast.makeText(context, "Thành Công!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Không thành công!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("GMAILNV", user);
                            params.put("MAHD", hoaDon.getMaHD());
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            });
        } else {
            holder.btnDuyetHD.setText("Đã duyệt");
            holder.btnDuyetHD.setClickable(false);
            holder.btnDuyetHD.setBackgroundColor(Color.RED);
        }

        holder.cvHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChiTietHDActivity.class);
                intent.putExtra("mahoadon", hoaDon.getMaHD());
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return hoaDonArrayList.size();
    }

    public class HoaDonHolder extends RecyclerView.ViewHolder {
        CardView cVsnpham;
        TextView tvMaHD;
        TextView tvMAKHHD;
        TextView tvMaNVHD;
        TextView tvNgayTaoHD;
        CardView cvHoaDon;
        Button btnDuyetHD;

        public HoaDonHolder(@NonNull View itemView) {
            super(itemView);
            cVsnpham = (CardView) itemView.findViewById(R.id.cVsnpham);
            tvMaHD = (TextView) itemView.findViewById(R.id.tvMaHD);
            tvMAKHHD = (TextView) itemView.findViewById(R.id.tvMAKHHD);
            tvMaNVHD = (TextView) itemView.findViewById(R.id.tvMaNVHD);
            tvNgayTaoHD = (TextView) itemView.findViewById(R.id.tvNgayTaoHD);
            cvHoaDon = itemView.findViewById(R.id.cvHoaDon);
            btnDuyetHD = itemView.findViewById(R.id.btnDuyetHoaDon);
        }
    }
}