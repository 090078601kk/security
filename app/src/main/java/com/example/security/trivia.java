package com.example.security;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

public class trivia extends AppCompatActivity {
    TextView q,des;
    Button sub,next;
    RadioGroup radioGroup;
    RadioButton oa,ob,oc;
    Bundle bundle;
    String sl;

    String [] q_,o1,o2,o3,des_,ans_;

    //next question
    int limt; //current question
    int position; //current question
    // result
    int r;


String a="Of course, and I carry a charger.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        bundle=getIntent().getExtras();
        sl=bundle.getString("lang");
        q=(TextView)findViewById(R.id.q);
        des=(TextView)findViewById(R.id.des);
        sub=(Button) findViewById(R.id.submit);
        next=(Button) findViewById(R.id.next);
        r=0;

        oa=(RadioButton) findViewById(R.id.oa);
        ob=(RadioButton) findViewById(R.id.ob);
        oc=(RadioButton) findViewById(R.id.oc);
        radioGroup=(RadioGroup) findViewById(R.id.ans);
        position=0;
        limt=0;
        con();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oa.setTextColor(getResources().getColor(R.color.black));
                oc.setTextColor(getResources().getColor(R.color.black));
                ob.setTextColor(getResources().getColor(R.color.black));
                des.setText("");

                // After 5 seconds redirect to another intmainent
                position=  position+1;

                Toast.makeText(trivia.this,String.valueOf(position), Toast.LENGTH_SHORT).show();

                q.setText(q_[position]);
                oa.setText(o1[position]);
                ob.setText(o2[position]);
                oc.setText(o3[position]);
                sub.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);

            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if(limt==position)
              {
                  int as=(r*100)/limt;

                  result_now(String.valueOf(r),String.valueOf(limt),String.valueOf(as));



              }else {

                  int selectedId = radioGroup.getCheckedRadioButtonId();
                  RadioButton radioButton=findViewById(selectedId);
                  if(radioButton.getText().toString().equals(ans_[position]))
                  {
                      r++;

                      Toast.makeText(trivia.this, String.valueOf(r), Toast.LENGTH_SHORT).show();
                      //right
                      position=  position+1;

                      q.setText(q_[position]);
                      oa.setText(o1[position]);
                      ob.setText(o2[position]);
                      oc.setText(o3[position]);

                      // Mistake
                      oa.setTextColor(getResources().getColor(R.color.black));
                      oc.setTextColor(getResources().getColor(R.color.black));
                      ob.setTextColor(getResources().getColor(R.color.black));

                      des.setText("");

                  }else {
                      //worng
                      des.setText(des_[position]);
                      sub.setVisibility(View.GONE);
                      next.setVisibility(View.VISIBLE);


                      // find which radioButton is checked by id
                      if(ans_[position].equals(oa.getText().toString())) {
                          ob.setTextColor(getResources().getColor(R.color.red));
                          oc.setTextColor(getResources().getColor(R.color.red));


                          oa.setTextColor(getResources().getColor(R.color.green));

                      } else if(ans_[position].equals(ob.getText().toString())) {
                          oa.setTextColor(getResources().getColor(R.color.red));
                          oc.setTextColor(getResources().getColor(R.color.red));
                          ob.setTextColor(getResources().getColor(R.color.green));


                      } else if(ans_[position].equals(oc.getText().toString())) {
                          ob.setTextColor(getResources().getColor(R.color.red));
                          oa.setTextColor(getResources().getColor(R.color.red));
                          oc.setTextColor(getResources().getColor(R.color.green));

                      }


                  }


              }
              }
        });

    }
    public void result_now(String r,String limt ,String av){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Your Result !! ").
                setMessage("Total Questions :  "+limt+"\nRight Answers : "+r+"\nYour Average :"+av).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              Intent i=new Intent(trivia.this,home.class);
              i.putExtras(bundle);
                startActivity(i);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void con(){
        String path;
        if(sl.equals("urdu"))
        {
            path=Url.domain_trivia_urdu;

        }else {
            path=Url.domain_trivia_eng;

        }
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.DEPRECATED_GET_OR_POST,path,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {



                    JSONArray jsonArray=new JSONArray(response);
                    int a=jsonArray.length();
                    limt=(a-1);
                    q_=new String[a];
                    o1=new String[a];
                    o2=new String[a];
                    o3=new String[a];
                    des_=new String[a];
                    ans_=new String[a];

                    for(int i=0;i<a;i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        q_[i]=jsonObject.getString("q");
                        des_[i]=jsonObject.getString("des");
                        ans_[i]=jsonObject.getString("ans");
                        o1[i]=jsonObject.getString("oa");
                        o2[i]=jsonObject.getString("ob");
                        o3[i]=jsonObject.getString("oc");
                    }
                    q.setText(q_[position]);
                    oa.setText(o1[position]);
                    ob.setText(o2[position]);
                    oc.setText(o3[position]);

                } catch (JSONException e) {
                    Toast.makeText(trivia.this, "Json Error ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(trivia.this, "Internet Not Found!!!!!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<String,String>();

                return params;
            }
        };

        MySingleton.getmInstanse(trivia.this).addToRequest(stringRequest);

    }





}
