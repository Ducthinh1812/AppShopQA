package com.duan1.appshopqa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.duan1.appshopqa.R;
import com.duan1.appshopqa.activity.ChiTietSanPham;
import com.duan1.appshopqa.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterSanPhamMoi extends RecyclerView.Adapter<AdapterSanPhamMoi.SanPhamMoiHolder> {
    Context context;
    ArrayList<SanPham> sanphamArrayList;

    public AdapterSanPhamMoi(Context context, ArrayList<SanPham> sanphamArrayList) {
        this.context = context;
        this.sanphamArrayList = sanphamArrayList;
    }

    @NonNull
    @Override
    public SanPhamMoiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham_new, parent, false);
        return new SanPhamMoiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSanPhamMoi.SanPhamMoiHolder holder, int position) {
        SanPham sanPham = sanphamArrayList.get(position);
        holder.txtTenSP.setText(sanPham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSP.setText("Giá: "+decimalFormat.format(sanPham.getGiaBan())+"VND");
        Picasso.get().load(sanPham.getHinhAnhSP())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",sanPham);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sanphamArrayList.size();
    }

    public static class SanPhamMoiHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtTenSP, txtGiaSP;
        CardView cardView;
        public SanPhamMoiHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageviewsanpham);
            txtTenSP = itemView.findViewById(R.id.textviewtensanpham);
            txtGiaSP = itemView.findViewById(R.id.textviewgiasanpham);
            cardView = itemView.findViewById(R.id.cardview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
