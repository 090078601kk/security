package com.example.security;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class contacts_show extends AppCompatActivity {
    TextView  name, number ;
   String na , num;
   Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_show);
        bundle=getIntent().getExtras();
        na=bundle.getString( "name");
        num=bundle.getString( "number");
        name=(TextView)findViewById(R.id.name);
        number=(TextView)findViewById(R.id.number);
        name.setText(na);
        number.setText(num);

    }
}
