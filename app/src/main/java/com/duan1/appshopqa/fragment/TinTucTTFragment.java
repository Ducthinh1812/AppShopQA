package com.duan1.appshopqa.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.duan1.appshopqa.R;
import com.duan1.appshopqa.fragment.TinTuc.WebTinActivity;
import com.duan1.appshopqa.fragment.TinTuc.XMLPullParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TinTucTTFragment extends Fragment {
    ListView listView;
    List<String> link=new ArrayList<>();
    List<String> list=new ArrayList<>();
    ArrayAdapter<String> adapter;
    Intent intent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_tin_tuc, container, false);


        listView =view.findViewById(R.id.thongtin);
        String  Linktimkiem="https://ngoisao.net/rss/thoi-trang.rss";
        new RSSBenTrong().execute(Linktimkiem);
        adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        intent = new Intent(getContext(), WebTinActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String thongtin = link.get(i);
                //intent
                intent.putExtra("TinTucTT",thongtin);
                startActivity(intent);
            }
        });
        return view;
    }
    public  class RSSBenTrong extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader reader
                        = new InputStreamReader(url.openConnection().getInputStream());
                String line="";
                BufferedReader bufferedReader = new BufferedReader(reader);
                while ((line=bufferedReader.readLine())!=null)
                {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            XMLPullParser xmlParser = new XMLPullParser();
            try {
                Document document = xmlParser.getDocument(s);
                NodeList nodeList = document.getElementsByTagName("item");
                String title="";
                for(int i=0;i<nodeList.getLength();i++)
                {
                    Element element = (Element)nodeList.item(i);
                    title = xmlParser.getValue(element,"title")+"\n";
                    list.add(title);
                    link.add(xmlParser.getValue(element,"link"));
                }
                adapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
    }
}
