package com.zjgsu.shenyilin.personal_assistant;

import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class function_notebook extends AppCompatActivity {

    private ListView listview_note;
    private SqliteDBConnect sd;
    private static int page_size=8;
    private static int page_no=1,page_count=0,count=0;
    private  Button button_addnote,button_first,button_previous,button_next,button_end;
    private SimpleAdapter sa;
    private ProgressBar m_ProgressBar;
    private ActivityManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setProgressBarVisibility(true);
        setContentView(R.layout.activity_function_notebook);
        //am=ActivityManager.getInstance();
        //am.addActivity(this);
        button_addnote=(Button)findViewById(R.id.button_addnote);
        button_first=(Button)findViewById(R.id.button_first);
        button_previous=(Button)findViewById(R.id.button_previous);
        button_next=(Button)findViewById(R.id.button_next);
        button_end=(Button)findViewById(R.id.button_end);
        m_ProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        listview_note=(ListView)findViewById(R.id.listview_note);

        sd=new SqliteDBConnect(function_notebook.this);

        listview_note.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Map<String,Objects>map= (Map<String, Objects>) arg0.getItemAtPosition(arg2);
                Intent intent =new Intent();
                intent.putExtra("noteId",map.get("noteId").toString());
                intent.setClass(function_notebook.this,lookover.class);
                startActivity(intent);
            }
        });

        listview_note.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                @SuppressWarnings("unchecked")
                final  Map<String,Object>map=(Map<String,Object>)parent.getItemAtPosition(position);
                AlertDialog.Builder adb=new AlertDialog.Builder(function_notebook.this);
                adb.setTitle(map.get("noteName").toString());

                adb.setItems(new String[]{"删除", "修改"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                SQLiteDatabase sdb = sd.getReadableDatabase();
                                sdb.delete("note", "noteId=?", new String[]{map.get("noteId").toString()});
                                Toast.makeText(function_notebook.this, "删除成功", Toast.LENGTH_SHORT).show();
                                sdb.close();
                                fenye();
                                break;
                            case 1:
                                Intent intent = new Intent();
                                intent.putExtra("noteId", map.get("noteId").toString());
                                intent.setClass(function_notebook.this, add_note.class);
                                startActivity(intent);
                                break;
                        }
                    }
                });
                adb.show();
                return true;
            }
        });

        button_addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_ProgressBar.setVisibility(View.VISIBLE);
                m_ProgressBar.setProgress(0);
                Intent intent =new Intent();
                intent.setClass(function_notebook.this,add_note.class);
                startActivity(intent);
            }
        });
        button_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page_no==1)Toast.makeText(function_notebook.this,"已到首页",Toast.LENGTH_SHORT).show();
                else page_no=1;
            fenye();
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page_no==page_count)Toast.makeText(function_notebook.this,"已到末页",Toast.LENGTH_SHORT).show();
                else page_no+=1;
                fenye();
            }
        });
        button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page_no==1)Toast.makeText(function_notebook.this,"已到首页",Toast.LENGTH_SHORT).show();
                else page_no-=1;
                fenye();
            }
        });
        button_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page_no==page_count)Toast.makeText(function_notebook.this,"已到末页",Toast.LENGTH_SHORT).show();
                else page_no=page_count;
                fenye();
            }
        });


    }

    public void fenye(){
        SQLiteDatabase sdb=sd.getReadableDatabase();
        count=0;
        Cursor c1=sdb.query("note",new String[]{"noteId","noteName","noteTime"},null,null,null,null,"noteId asc");
        while (c1.moveToNext()){
            int noteid=c1.getInt(c1.getColumnIndex("noteId"));
            if(noteid>count)count=noteid;
        }
        c1.close();
        page_count=count%page_size==0?count/page_size:count/page_size+1;
        if (page_no<1)page_no=1;
        if(page_no>page_count)page_no=page_count;
        Cursor c=sdb.rawQuery("select noteId,noteName,noteTime from note limit ?,?",new String[]{(page_no-1)*page_size+"",page_size+""});
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        while (c.moveToNext()){
            Map<String,Object>map=new HashMap<String,Object>();
            String strName=c.getString(c.getColumnIndex("noteName"));
            if(strName.length()>20){
                map.put("noteName",strName.substring(0,20)+"……");
            }else{
                map.put("noteName",strName);
            }
            map.put("noteTime",c.getString(c.getColumnIndex("noteTime")));
            map.put("noteId",c.getInt(c.getColumnIndex("noteId")));
            list.add(map);
        }
        c.close();
        sdb.close();
        if(count>0){
            sa=new SimpleAdapter(function_notebook.this,list,R.layout.list_note,new String[]{"noteName","noteTime"},new int[]{R.id.text_notename,R.id.text_notetime});
        }
    }


}
