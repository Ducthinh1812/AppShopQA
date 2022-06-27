package com.duan1.appshopqa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.duan1.appshopqa.R;
import com.duan1.appshopqa.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterGioHang extends BaseAdapter {

    Context context;
    ArrayList<GioHang> giohangArrayList;

    public AdapterGioHang(Context context, ArrayList<GioHang> giohangArrayList) {
        this.context = context;
        this.giohangArrayList = giohangArrayList;
    }

    @Override
    public int getCount() {
        return giohangArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return giohangArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolderGH viewHolder = null;
        if(view==null)
        {
            viewHolder = new ViewHolderGH();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_gio_hang,null);
            viewHolder.txttengiohang = view.findViewById(R.id.textviewgiohang);
            viewHolder.txtgiagiohang = view.findViewById(R.id.textviewgiagiohang);
            viewHolder.imggiohang = view.findViewById(R.id.imageviewgiohang);
            viewHolder.btnGiaTri = view.findViewById(R.id.buttonvalues);
            viewHolder.btnTru = view.findViewById(R.id.buttonminus);
            viewHolder.btnCong = view.findViewById(R.id.buttonplus);

            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolderGH) view.getTag();
        }
        GioHang giohang = (GioHang) getItem(i);
        viewHolder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText("Giá: "+ decimalFormat.format(giohang.getGiasp())+" VND");
        Picasso.get().load(giohang.getHinhsp())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(viewHolder.imggiohang);
        viewHolder.btnGiaTri.setText(Integer.toString(giohang.getSoluongsp()));

//        viewHolder.XoaSanPham.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                giohangArrayList.remove(i);
//            }
//        });


        return view;
    }

    public class ViewHolderGH {
        TextView txttengiohang,txtgiagiohang;
        ImageView imggiohang,XoaSanPham;
        Button btnTru,btnCong,btnGiaTri;



    }
}
