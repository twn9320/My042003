package com.example.yvtc.my042003;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ArrayList<Student>mylist;
    ArrayList<Map<String,String>> showdata;
    File myfile;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myfile = new File(getFilesDir()+File.separator+"mydata.json");
        lv= (ListView)findViewById(R.id.listView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Add");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("Add")){
            Intent it = new Intent(MainActivity.this,AddActivity.class);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showdata = new ArrayList<>();
        if(myfile.exists()){
            try {
                FileReader fr = new FileReader(myfile);
                BufferedReader br = new BufferedReader(fr);
                String data = br.readLine();
                Gson gson = new Gson();
                mylist = gson.fromJson(data,new TypeToken<ArrayList<Student>>(){}.getType());
                for(Student s:mylist){
                    Map m1 = new HashMap();
                    m1.put("Name",s.Name);
                    m1.put("Addr",s.Addr);
                    m1.put("Tel",s.Tel);
                    showdata.add(m1);
                }
                SimpleAdapter adapter = new SimpleAdapter(MainActivity.this,showdata,
                        R.layout.myitem,new String[]{"Name","Addr","Tel"},new int[]{R.id.textView,
                        R.id.textView2,R.id.textView3});
                lv.setAdapter(adapter);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
