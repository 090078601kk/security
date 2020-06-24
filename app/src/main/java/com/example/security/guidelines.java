package com.example.security;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class guidelines extends AppCompatActivity {
    ImageView kid,has,voi,theif,rape,stalker;
    Bundle bundle;
    String sl;
    String link,img,title,des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelines);
        bundle=getIntent().getExtras();
        sl=bundle.getString("lang");
        kid=(ImageView)findViewById(R.id.kid);
        has=(ImageView)findViewById(R.id.haras);
        theif=(ImageView)findViewById(R.id.theif);
        voi=(ImageView)findViewById(R.id.violence);
        rape=(ImageView)findViewById(R.id.rape);
        stalker=(ImageView)findViewById(R.id.stalker);
        //language show only
        Toast.makeText(this, sl, Toast.LENGTH_SHORT).show();

        kid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl.equals("urdu"))
                {
                    con("اغوا");
                }else {
                    con("KIDNAPPING");

                }
            }
        });
        has.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl.equals("urdu"))
                {
                    con("ہراساں کرنا:");
                }else {
                    con("Harassment");

                }
            }
        });
        theif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl.equals("urdu"))
                {
                    con("چوری");
                }else {
                    con("THEIFT");

                }
            }
        });
        voi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl.equals("urdu"))
                {
                    con("تشدد");
                }else {
                    con("VIOLENCE");

                }
            }
        });
        rape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl.equals("urdu"))
                {
                    con("عصمت دری");
                }else {
                    con("RAPE");

                }
            }
        });
        stalker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl.equals("urdu"))
                {
                    con("اسٹاکر");
                }else {
                    con("STALKER");
                }
            }
        });
    }
    public void con(final String info){
        String path;
        if(sl.equals("urdu"))
        {
             path=Url.domain_guide_urdu;

        }else {
             path=Url.domain_guide_eng;

        }
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.DEPRECATED_GET_OR_POST,path,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {



                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);

                        img=jsonObject.getString("img");
                        title=jsonObject.getString("t");
                        des=jsonObject.getString("des");
                        link=jsonObject.getString("link");
                        bundle.putString("img",img);
                        bundle.putString("t",title);
                        bundle.putString("des",des);
                        bundle.putString("link",link);
                    Intent intent=new Intent(guidelines.this,guideline_single.class);
                    intent.putExtras(bundle);
                        startActivity(intent);



                } catch (JSONException e) {
                    Toast.makeText(guidelines.this, "Json Error ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }




            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(guidelines.this, "Internet Not Found!!!!!", Toast.LENGTH_SHORT).show();
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

        MySingleton.getmInstanse(guidelines.this).addToRequest(stringRequest);
    }
}
