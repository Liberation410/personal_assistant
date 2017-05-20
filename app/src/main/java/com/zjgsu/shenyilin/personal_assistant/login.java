package com.zjgsu.shenyilin.personal_assistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.security.AccessController.getContext;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText text_username=(EditText)findViewById(R.id.text_username);
        final EditText text_key=(EditText)findViewById(R.id.text_key);
        Button button_login=(Button)findViewById(R.id.button_login);
        Button button_cancel=(Button)findViewById(R.id.button_cancel);

        button_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent successlogin=new Intent(login.this,function_choose.class);
               // successlogin.putExtra("username_in",text_username.getText().toString());
                //successlogin.putExtra("key_in",text_key.getText().toString());
                if (text_username.getText().toString().equals("1512180623")&& text_key.getText().toString().equals("303821")){
                    startActivity(successlogin);
                }else{
                    Toast.makeText(login.this, "登录密码与用户名不匹配，请重新输入", Toast.LENGTH_SHORT).show();
                    text_key.setText("");
                }

            }

        });

        button_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });




    }
}
