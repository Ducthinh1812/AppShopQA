package com.duan1.appshopqa.adapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.duan1.appshopqa.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class AdapterQuanLiSP extends RecyclerView.Adapter<AdapterQuanLiSP.QuanLiSPHolder> {

    Context context;
    ArrayList<SanPham> arrayList;

    private EditText editTENSP;
    private EditText editSOLUONGNHAP;
    private EditText editHASP;
    private EditText editGIABAN;
    private EditText editMOTASP;
    private Button btnHuyUpdate;
    private Button btnLuuUpdate;

    public AdapterQuanLiSP(Context context, ArrayList<SanPham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public QuanLiSPHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham, parent, false);
        return new QuanLiSPHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterQuanLiSP.QuanLiSPHolder holder, @SuppressLint("RecyclerView") int position) {
        SanPham sanPham = arrayList.get(position);

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


        holder.btnSuaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View viewT = LayoutInflater.from(builder.getContext()).inflate(R.layout.dialog_sua_sp, null);
                builder.setView(viewT);
                Dialog dialog = builder.create();
                dialog.show();

                editTENSP = (EditText) viewT.findViewById(R.id.editTENSP);
                editSOLUONGNHAP = (EditText) viewT.findViewById(R.id.editSOLUONGNHAP);
                editHASP = (EditText) viewT.findViewById(R.id.editHASP);
                editGIABAN = (EditText) viewT.findViewById(R.id.editGIABAN);
                editMOTASP = (EditText) viewT.findViewById(R.id.editMOTASP);
                btnHuyUpdate = (Button) viewT.findViewById(R.id.btnHuyUpdate);
                btnLuuUpdate = (Button) viewT.findViewById(R.id.btnLuuUpdate);

                editTENSP.setText(sanPham.getTenSP());
                editSOLUONGNHAP.setText(String.valueOf(sanPham.getSoLuongNhap()));
                editHASP.setText(sanPham.getHinhAnhSP());
                editGIABAN.setText(String.valueOf(sanPham.getGiaBan()));
                editMOTASP.setText(sanPham.getMoTaSP());

                btnLuuUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String str_masp = String.valueOf(sanPham.getMaSP());
                        String str_tensp = editTENSP.getText().toString().trim();
                        String str_soluongnhap = editSOLUONGNHAP.getText().toString().trim();
                        String str_hinhanhsp = editHASP.getText().toString().trim();
                        String str_motasp = editMOTASP.getText().toString().trim();
                        String str_giaban = editGIABAN.getText().toString().trim();
                        String urlUpdateSp = "https://cyclothymic-floors.000webhostapp.com/DBShopQuanAo/UpdateSP.php";

                        if (str_tensp.equalsIgnoreCase("") || str_soluongnhap.equalsIgnoreCase("")
                                || str_hinhanhsp.equalsIgnoreCase("") || str_motasp.equalsIgnoreCase("")
                                || str_giaban.equalsIgnoreCase("")){
                            Toast.makeText(builder.getContext(), "Hãy nhập hết tất cả các trường trên form!", Toast.LENGTH_SHORT).show();
                        }

                        RequestQueue requestQueue = Volley.newRequestQueue(builder.getContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateSp, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equalsIgnoreCase("error")){
                                    Toast.makeText(builder.getContext(), "Sửa không thành công", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(builder.getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(builder.getContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("MASP", str_masp);
                                params.put("TENSP", str_tensp);
                                params.put("HINHANHSP", str_hinhanhsp);
                                params.put("MOTASP", str_motasp);
                                params.put("SOLUONGNHAP", str_soluongnhap);
                                params.put("GIABAN", str_giaban);
                                return params;
                            }
                        };

                        requestQueue.add(stringRequest);
                        notifyItemChanged(position);
                        dialog.dismiss();
                    }
                });


                btnHuyUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });


        holder.btnXoaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlDeleteSp = "https://cyclothymic-floors.000webhostapp.com/DBShopQuanAo/DeleteSP.php";

                RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDeleteSp, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("error")){
                            Toast.makeText(v.getContext(), "Xóa không thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(v.getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(v.getContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("MaSP", String.valueOf(sanPham.getMaSP()));
                        return params;
                    }
                };

                requestQueue.add(stringRequest);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class QuanLiSPHolder extends RecyclerView.ViewHolder {
        ImageView imgQA;
        TextView tvTenQA;
        TextView tvGiaQA;
        TextView tvSLQA;
        TextView tvMOTAQA;
        CardView cardView;
        ImageView btnXoaSP, btnSuaSP;
        public QuanLiSPHolder(@NonNull View itemView) {
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