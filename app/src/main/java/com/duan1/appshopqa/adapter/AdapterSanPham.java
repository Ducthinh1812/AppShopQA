package com.duan1.appshopqa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.duan1.appshopqa.R;
import com.duan1.appshopqa.activity.ChiTietSanPham;
import com.duan1.appshopqa.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterSanPham extends RecyclerView.Adapter<AdapterSanPham.SanPhamViewHolder> {
    ArrayList<SanPham> arrayList;
    Context context;

    public AdapterSanPham(ArrayList<SanPham> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quan_ao,parent,false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {

        SanPham sanPham =arrayList.get(position);

        holder.tvTenQA.setText(sanPham.getTenSP());
        holder.tvMOTAQA.setText("Mô tả: "+sanPham.getMoTaSP() );
        holder.tvMOTAQA.setMaxLines(2);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGiaQA.setText("Giá : "+decimalFormat.format(sanPham.getGiaBan())+" VND" );
        holder.tvSLQA.setText("Số lượng : "+sanPham.getSoLuongNhap());

        Picasso.get().load(sanPham.getHinhAnhSP())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgQA);

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
        return arrayList.size();
    }
    public class SanPhamViewHolder extends RecyclerView.ViewHolder{
         ImageView imgQA;
         TextView tvTenQA;
         TextView tvGiaQA;
         TextView tvSLQA;
         TextView tvMOTAQA;
         CardView cardView;
        public SanPhamViewHolder(@NonNull  View itemView) {
            super(itemView);
            cardView= itemView.findViewById(R.id.cVsnpham);
            imgQA = (ImageView) itemView.findViewById(R.id.imgQA);
            tvTenQA = (TextView) itemView.findViewById(R.id.tvTenQA);
            tvGiaQA = (TextView) itemView.findViewById(R.id.tvGiaQA);
            tvSLQA = (TextView) itemView.findViewById(R.id.tvSLQA);
            tvMOTAQA = (TextView) itemView.findViewById(R.id.tvMOTAQA);
        }
    }
}
