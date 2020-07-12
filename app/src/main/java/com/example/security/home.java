package com.example.security;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class home extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;

    LinearLayout em,con,trivia,guide,gen;
    String ls="english";
    Bundle bundle;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */

                Toast.makeText(home.this, "Shaked!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(home.this,alert.class));
            }
        });


        bundle=getIntent().getExtras();
        bundle.putString("lang",ls);
        if(bundle.getString("start").equals("start"))
        {
            language();

        }
        bundle.putString("start","end");
        em=(LinearLayout)findViewById(R.id.def);
        em.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(home.this,emergency.class);
                startActivity(intent);
            }
        });
        guide=(LinearLayout)findViewById(R.id.guide);
        gen=(LinearLayout)findViewById(R.id.gen);
        con=(LinearLayout)findViewById(R.id.con);
        trivia=(LinearLayout)findViewById(R.id.trivia);
        trivia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(home.this,trivia.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(home.this,guidelines.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(home.this,contacts.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(home.this,general_issues.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }
    public void language(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Which language do you want to select?");
        alertDialogBuilder.setPositiveButton("اردو", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ls="urdu";
                bundle.putString("lang",ls);
                Toast.makeText(home.this, ls, Toast.LENGTH_LONG).show();

            }
        }).setNegativeButton("English", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ls="english";
                bundle.putString("lang",ls);
                Toast.makeText(home.this, ls, Toast.LENGTH_LONG).show();

            }
        }).setTitle("Language!!");

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
