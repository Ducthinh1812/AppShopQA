package com.duan1.appshopqa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.duan1.appshopqa.R;
import com.duan1.appshopqa.fragment.TrangChuFragment;
import com.duan1.appshopqa.model.GioHang;
import com.duan1.appshopqa.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {

    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtten, txtgia, txtmota;
    Spinner spinner;
    Button btndatmua;



    int id;
    String Tensanpham;
    Integer Giasanpham;
    String Hinhanhsanpham;
    String Motasanpham;
    int IDSanpham;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        GetInformation();
        CatchEventSpinner();
        ThemGioHang();
    }





    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayadapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayadapter);
    }





    private void GetInformation() {
        SanPham sanpham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        Tensanpham = sanpham.getTenSP();
        Giasanpham = sanpham.getGiaBan();
        Hinhanhsanpham = sanpham.getHinhAnhSP();
        Motasanpham = sanpham.getMoTaSP();
        IDSanpham = sanpham.getMaSP();
        id = sanpham.getMaSP();
        txtten.setText(Tensanpham);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá: " + decimalFormat.format(Giasanpham) + " VNĐ");
        txtmota.setText(Motasanpham);
        Picasso.get().load(Hinhanhsanpham)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(imgChitiet);
    }






    private void Anhxa() {
        imgChitiet = findViewById(R.id.imageviewchitietsanpham);
        txtten = findViewById(R.id.textviewtenchitietsanpham);
        txtgia = findViewById(R.id.textviewgiachitietsanpham);
        txtmota = findViewById(R.id.textviewmotachitietsanpham);
        spinner = findViewById(R.id.spinner);
        btndatmua = findViewById(R.id.buttondatmua);

    }





    void ThemGioHang(){
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TrangChuFragment.giohanglist.size()>0)//gio hang khong rong
                {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());//lay so luong trong spinner
                    boolean tontaimahang = false;
                    for(int i=0;i<TrangChuFragment.giohanglist.size();i++)//dem xem trong gio hang co gi
                    {
                        if(TrangChuFragment.giohanglist.get(i).getIdsp()==id)//neu co hang ta muon mua them
                        {
                            //so luong = so luong cu + moi
                            TrangChuFragment.giohanglist.get(i).setSoluongsp(TrangChuFragment.giohanglist.get(i).getSoluongsp()+sl);
                            if(TrangChuFragment.giohanglist.get(i).getSoluongsp()>=10)//neu so luong sp >10, giu nguyen 10
                            {
                                TrangChuFragment.giohanglist.get(i).setSoluongsp(10);
                            }
                            //tinh tien = Don gia * so luong
                            TrangChuFragment.giohanglist.get(i).setGiasp(Giasanpham*TrangChuFragment.giohanglist.get(i).getSoluongsp());
                            tontaimahang  =true;
                        }
                    }
                    if(tontaimahang==false)//truong hop co hang, nhung ma hang muon mua them khong ton tai
                    {
                        int sl1 = Integer.parseInt(spinner.getSelectedItem().toString());//lay so luong trong spinner
                        //tinh tien
                        long Tien2 = sl1*Giasanpham;
                        //them vao mang gio hang
                        TrangChuFragment.giohanglist.add(new GioHang(id,Tensanpham,Tien2,Hinhanhsanpham,sl1));
                    }
                }
                else //gio hang rong
                {
                    int sl2 = Integer.parseInt(spinner.getSelectedItem().toString());//lay so luong trong spinner
                    //tinh tien
                    long Tien2 = sl2*Giasanpham;
                    //them vao mang gio hang
                    TrangChuFragment.giohanglist.add(new GioHang(id,Tensanpham,Tien2,Hinhanhsanpham,sl2));
                }
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
            }
        });
    }






}