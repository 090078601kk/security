package com.example.security;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.example.security.Url.domain;

public class emergency extends AppCompatActivity {
    String  path= domain+"em.php";
    String [] ph,name,img;
    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        con();

        ;

    }
    public void con(){
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST,path,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    test=response;

                    JSONArray jsonArray=new JSONArray(response);
                    int a=jsonArray.length();
                    ph=new String[a];
                    name=new String[a];
                    img=new String[a];
                    for(int i=0;i<a;i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        ph[i]=jsonObject.getString("phone");
                        img[i]=jsonObject.getString("logo");
                        name[i]=jsonObject.getString("name");
                    }



                } catch (JSONException e) {
                    Toast.makeText(emergency.this, "Json Error ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                ListView listView=(ListView)findViewById(R.id.listView);


                // For populating list data
                Emg_cus_list cusList = new Emg_cus_list(emergency.this,img,name,ph);
                listView.setAdapter(cusList);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long ihd) {
                        if (Build.VERSION.SDK_INT < 23) {
                            phoneCall(position);
                        }else {

                            if (ActivityCompat.checkSelfPermission(emergency.this,
                                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                                phoneCall(position);
                            }else {
                                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                                //Asking request Permissions
                                ActivityCompat.requestPermissions(emergency.this, PERMISSIONS_STORAGE, 9);
                            }
                        }



                    }
                });



            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(emergency.this, "Internet Not Found!!!!!", Toast.LENGTH_SHORT).show();


                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<String,String>();



                return params;
            }
        };

        MySingleton.getmInstanse(emergency.this).addToRequest(stringRequest);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch(requestCode){
            case 9:
                permissionGranted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(permissionGranted){

            startActivity(new Intent(emergency.this,emergency.class));

        }else {
            Toast.makeText(emergency.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }
    private void phoneCall(int p){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+ph[p]));
            this.startActivity(callIntent);
        }else{
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }
}
