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

public class guideline_single extends AppCompatActivity {
    ImageView img;
    TextView link,des,title;
    Bundle bundle;

    String t,i,l,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideline_single);
        bundle=getIntent().getExtras();
        i=bundle.getString("img");
        t=bundle.getString("t");
        l=bundle.getString("link");
        d=bundle.getString("des");

        img=(ImageView)findViewById(R.id.img);

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

        ImageRequest imageee=new ImageRequest(domain_img+i, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                img.setImageBitmap(response);

            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(guideline_single.this, "Internet not found!!!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        MySingleton.getmInstanse(this).addToRequest(imageee);



    }
}
