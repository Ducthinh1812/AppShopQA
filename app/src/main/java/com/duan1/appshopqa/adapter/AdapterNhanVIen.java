package com.duan1.appshopqa.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.duan1.appshopqa.R;
import com.duan1.appshopqa.model.NhanVien;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterNhanVIen extends RecyclerView.Adapter<AdapterNhanVIen.nhanvienViewHolder> {
    Context context;
    ArrayList<NhanVien> nhanvienList;
    String Email,Name,mailcu;
    EditText email,name;
    Button btnhuy,btnluu;
    String urlxoanv="https://website1812.000webhostapp.com/ShopQuanAo/deletenhanvien.php";
    String urlsuanv="https://website1812.000webhostapp.com/ShopQuanAo/suanhanvien.php";
    public AdapterNhanVIen(ArrayList<NhanVien> nhanvienList, Context context) {
        this.nhanvienList = nhanvienList;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterNhanVIen.nhanvienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhan_vien,parent,false);
        return new AdapterNhanVIen.nhanvienViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull AdapterNhanVIen.nhanvienViewHolder holder, int position) {

        NhanVien nv =nhanvienList.get(position);
        holder.textviewnv.setText(nv.getTenNV());
        holder.textviewemail.setText(nv.getGmailNV() );
        holder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("B???n c?? mu???n x??a "+nv.getTenNV()+" kh??ng?");
                builder.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("X??a", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlxoanv, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject object= null;
                                try {
                                    object = new JSONObject(response);
                                    String check=object.getString("state");
                                    if(check.equals("delete")){
                                        delete(position);
                                        Toast.makeText(context, "X??a Th??nh C??ng.", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context,"x???y ra l???i!", Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String,String> getParams() throws AuthFailureError{
                                HashMap<String,String> deHashMap=new HashMap<>();
                                deHashMap.put("GmailNV",nv.getGmailNV());
                                return deHashMap;
                            }
                        };
                        RequestQueue requestQue = Volley.newRequestQueue(context);
                        requestQue.add(stringRequest);
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        holder.btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View edit=LayoutInflater.from(context).inflate(R.layout.layout_suanv,null);
                email=edit.findViewById(R.id.edt_mail);
                name=edit.findViewById(R.id.edt_name);
                btnhuy=edit.findViewById(R.id.huythongtinnv);
                btnluu=edit.findViewById(R.id.suathongtinnv);
                email.setText(nv.getGmailNV());
                name.setText(nv.getTenNV());
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("S???a Th??ng tin nh??n vi??n "+nv.getTenNV());
                builder.setView(edit);
                AlertDialog dialo=builder.create();
                btnluu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Please Wait..");
                        Email=email.getText().toString();
                        Name=name.getText().toString();
                        mailcu=nv.getGmailNV();
                        if(!validatename()  | !validateemail()  ){
                            return;
                        }
                        else{
                            progressDialog.show();
                            StringRequest request = new StringRequest(Request.Method.POST, urlsuanv, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                            },new Response.ErrorListener(){

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context,"x???y ra l???i!", Toast.LENGTH_SHORT).show();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<String, String>();
                                    params.put("GmailNV",Email);
                                    params.put("TenNV",Name);
                                    params.put("mailcu",mailcu);
                                    return params;

                                }
                            };
                            RequestQueue requestQue = Volley.newRequestQueue(context);
                            requestQue.add(request);
                        }
                    }
                });
              btnhuy.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      dialo.dismiss();
                  }
              });
                dialo.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return nhanvienList.size();
    }
    public class nhanvienViewHolder extends RecyclerView.ViewHolder{
        TextView textviewnv,textviewemail;
        ImageView btnxoa,btnsua;
        public nhanvienViewHolder(@NonNull  View itemView) {
            super(itemView);
            textviewnv = (TextView) itemView.findViewById(R.id.textviewnv);
            textviewemail= (TextView) itemView.findViewById(R.id.textviewemail);
            btnxoa=itemView.findViewById(R.id.imgdeletenv);
            btnsua=itemView.findViewById(R.id.imgsuanv);
        }
    }
    public void delete(int item){
        nhanvienList.remove(item);
        notifyItemRemoved(item);
    }
    public boolean validatename(){
        if(Name.isEmpty()){
            name.setError("H??y nh???p t??n c???a b???n.");
            return false;
        } else{
            name.setError(null);
            return true;
        }
    }
    public boolean validateemail(){
        String a="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(Email.isEmpty()){
            email.setError("H??y nh???p gmail c???a b???n.");
            return false;
        }else if(!Email.matches(a)) {
            email.setError("Nh???p ????ng ?????nh d???ng gmail.");
            return false;
        }else{
            email.setError(null);
            return true;
        }
    }
}