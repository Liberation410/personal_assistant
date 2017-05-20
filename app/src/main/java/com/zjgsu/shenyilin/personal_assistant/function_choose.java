package com.zjgsu.shenyilin.personal_assistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class function_choose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_choose);

        Button button_user=(Button)findViewById(R.id.button_user);
        button_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(function_choose.this,personal.class);
                startActivity(intent);
            }
        });

        Button button_notebook=(Button)findViewById(R.id.button_notebook);
        button_notebook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(function_choose.this,function_notebook.class);
                startActivity(intent);
            }
        });

    }
}
