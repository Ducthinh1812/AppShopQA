package com.duan1.appshopqa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.duan1.appshopqa.R;
import com.duan1.appshopqa.model.HoaDonCT;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterHoaDonCT extends RecyclerView.Adapter<AdapterHoaDonCT.HoaDonCTHolder> {

    Context context;
    ArrayList<HoaDonCT> arrayList;

    public AdapterHoaDonCT(Context context, ArrayList<HoaDonCT> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public HoaDonCTHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham, parent, false);
        return new HoaDonCTHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHoaDonCT.HoaDonCTHolder holder, int position) {
        HoaDonCT hoaDonCT = arrayList.get(position);

        holder.tvTenQA.setText(hoaDonCT.getTensp()+"");
        holder.tvMOTAQA.setText("Mô tả: "+hoaDonCT.getMoTaSP() );
        holder.tvMOTAQA.setMaxLines(2);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGiaQA.setText("Giá : "+decimalFormat.format(hoaDonCT.getGiasp())+" VND" );
        holder.tvSLQA.setText("Số lượng : "+hoaDonCT.getSoluongsp());

        Picasso.get().load(hoaDonCT.getHinhsp())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgQA);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class HoaDonCTHolder extends RecyclerView.ViewHolder {
        ImageView imgQA;
        TextView tvTenQA;
        TextView tvGiaQA;
        TextView tvSLQA;
        TextView tvMOTAQA;
        CardView cardView;
        ImageView btnXoaSP, btnSuaSP;
        public HoaDonCTHolder(@NonNull View itemView) {
            super(itemView);
            imgQA = (ImageView) itemView.findViewById(R.id.imgQLSP);
            tvTenQA = (TextView) itemView.findViewById(R.id.tvTenQLSP);
            tvGiaQA = (TextView) itemView.findViewById(R.id.tvGiaQLSP);
            tvSLQA = (TextView) itemView.findViewById(R.id.tvQLSLSP);
            tvMOTAQA = (TextView) itemView.findViewById(R.id.tvMoTaQLSP);
            btnXoaSP = itemView.findViewById(R.id.imgXoaSP);
            btnSuaSP = itemView.findViewById(R.id.imgSuaSP);
        }
    }
}
