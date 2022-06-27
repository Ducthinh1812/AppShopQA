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

public class AdapterTimKiem extends RecyclerView.Adapter<AdapterTimKiem.TimKiemHolder> {

    Context context;
    ArrayList<SanPham> sanPhamArrayList;

    public AdapterTimKiem(Context context, ArrayList<SanPham> sanPhamArrayList) {
        this.context = context;
        this.sanPhamArrayList = sanPhamArrayList;
    }

    @NonNull
    @Override
    public TimKiemHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quan_ao, parent, false);
        return new TimKiemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTimKiem.TimKiemHolder holder, int position) {
        SanPham timKiem = sanPhamArrayList.get(position);

        holder.tvTenQA.setText(timKiem.getTenSP());
        holder.tvMOTAQA.setText("Mô tả: "+timKiem.getMoTaSP() );
        holder.tvMOTAQA.setMaxLines(2);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGiaQA.setText("Giá : "+decimalFormat.format(timKiem.getGiaBan())+" VND" );
        holder.tvSLQA.setText("Số lượng : "+timKiem.getSoLuongNhap());

        Picasso.get().load(timKiem.getHinhAnhSP())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgQA);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",timKiem);
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return sanPhamArrayList.size();
    }

    public class TimKiemHolder extends RecyclerView.ViewHolder {
        ImageView imgQA;
        TextView tvTenQA;
        TextView tvGiaQA;
        TextView tvSLQA;
        TextView tvMOTAQA;
        CardView cardView;
        public TimKiemHolder(@NonNull View itemView) {
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
