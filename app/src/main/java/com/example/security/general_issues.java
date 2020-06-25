package com.example.security;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.ImageRequest;

import static com.example.security.Url.domain_img;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class general_issues extends AppCompatActivity {
    ImageView heart,preg,hbp,dep,anx,covid,tb,skin,mus;
    Bundle bundle;
    String sl;
    String link,logo,title,des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_issues);
        bundle=getIntent().getExtras();
        sl=bundle.getString("lang");
        heart=(ImageView)findViewById(R.id.heart);
        preg=(ImageView)findViewById(R.id.preg);
        hbp=(ImageView)findViewById(R.id.hbp);
        dep=(ImageView)findViewById(R.id.dep);
        anx=(ImageView)findViewById(R.id.anx);
        covid=(ImageView)findViewById(R.id.covid);
        tb=(ImageView)findViewById(R.id.tb);
        skin=(ImageView)findViewById(R.id.skin);
        mus=(ImageView)findViewById(R.id.mus);
        //language show only
        Toast.makeText(this, sl, Toast.LENGTH_SHORT).show();

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl.equals("urdu"))
                {
                    con("دل کا دورہ");
                }else {
                    con("Heart Attack");

                }
            }
        });
        preg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl.equals("urdu"))
                {
                    con(" حمل:");
                }else {
                    con("Pregnancy");

                }
            }
        });
        hbp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl.equals("urdu"))
                {
                    con("ہائی بلڈ پریشر");
                }else {
                    con("High Blood Pressure");

                }
            }
        });
        dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl.equals("urdu"))
                {
                    con("افسردگی");
                }else {
                    con("Depression");

                }
            }
        });
        anx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl.equals("urdu"))
                {
                    con("پریشان ");
                }else {
                    con("Anxiety");

                }
            }
        });
        covid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl.equals("urdu"))
                {
                    con("کورونا وائرس");
                }else {
                    con("Corona Virus");
                }
            }
        });
        skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl.equals("urdu"))
                {
                    con("جلد میں انفیکشن");
                }else {
                    con("Skin Infection");
                }
            }
        });
        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl.equals("urdu"))
                {
                    con("ٹی بی");
                }else {
                    con("T B");
                }
            }
        });
        mus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl.equals("urdu"))
                {
                    con("پٹھوں میں درد");
                }else {
                    con("Muscular  Issues");
                }
            }
        });
    }
    public void con(final String info){
        String path;
        if(sl.equals("urdu"))
        {
            path=Url.domain_gen_issue_urdu;

        }else {
            path=Url.domain_gen_issue_eng;

        }
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.DEPRECATED_GET_OR_POST,path,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {



                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);

                    logo=jsonObject.getString("logo");
                    title=jsonObject.getString("t");
                    des=jsonObject.getString("des");
                    link=jsonObject.getString("link");
                    bundle.putString("logo",logo);
                    bundle.putString("t",title);
                    bundle.putString("des",des);
                    bundle.putString("link",link);
                    Intent intent=new Intent(general_issues.this,guideline_single.class);
                    intent.putExtras(bundle);
                    startActivity(intent);



                } catch (JSONException e) {
                    Toast.makeText(general_issues.this, "Json Error ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }




            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(general_issues.this, "Internet Not Found!!!!!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<String,String>();
                params.put("g",info);
                return params;
            }
        };

        MySingleton.getmInstanse(general_issues.this).addToRequest(stringRequest);
    }
}


