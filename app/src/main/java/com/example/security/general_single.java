package com.example.security;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import static com.example.security.Url.domain_img;

public class general_single extends AppCompatActivity {
    ImageView logo;
    TextView link,des,title;
    Bundle bundle;

    String t,lo,l,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_single);
        bundle=getIntent().getExtras();
        lo=bundle.getString("logo");
        t=bundle.getString("t");
        l=bundle.getString("link");
        d=bundle.getString("des");

        logo=(ImageView)findViewById(R.id.logo);

        title=(TextView)findViewById(R.id.title);
        link=(TextView)findViewById(R.id.link);
        des=(TextView)findViewById(R.id.des);

        title.setText(t);
        des.setText(d);
        link.setText(l);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(l));
                startActivity(browserIntent);
            }
        });

        ImageRequest imageee=new ImageRequest(domain_img+lo, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                logo.setImageBitmap(response);

            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(general_single.this, "Internet not found!!!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        MySingleton.getmInstanse(this).addToRequest(imageee);


    }
}
