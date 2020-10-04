package com.devsteve.calculadora_practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn_Suma, btn_Resta, btn_Mult, btn_Div;
    private TextView tv_res;
    private EditText et_num, et_num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_res = findViewById(R.id.Tv_respuesta);

        et_num = findViewById(R.id.Et_Num);
        et_num2 = findViewById(R.id.Et_Num2);

        btn_Suma = findViewById(R.id.Btn_suma);
        btn_Suma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                tv_res.setText(suma(Integer.parseInt(et_num.getText().toString()), Integer.parseInt(et_num2.getText().toString()))+"");

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Rellene el campo", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Resta = findViewById(R.id.Btn_resta);
        btn_Resta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_res.setText(resta(Integer.parseInt(et_num.getText().toString()), Integer.parseInt(et_num2.getText().toString()))+"");
            }
        });
        btn_Mult = findViewById(R.id.Btn_mult);
        btn_Mult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_res.setText(mult(Integer.parseInt(et_num.getText().toString()), Integer.parseInt(et_num2.getText().toString()))+"");
            }
        });
        btn_Div = findViewById(R.id.Btn_div);
        btn_Div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_res.setText(div(Integer.parseInt(et_num.getText().toString()), Integer.parseInt(et_num2.getText().toString()))+"");
            }
        });
    }

    public int suma(int a, int b){
    return a+b;
    }
    public int resta(int a, int b){
        return a-b;
    }
    public int mult(int a, int b){
        return a*b;
    }
    public int div(int a, int b){
        int resp=0;
        if (b!=0){
            resp = a/b;
        }
        return resp;
    }


}