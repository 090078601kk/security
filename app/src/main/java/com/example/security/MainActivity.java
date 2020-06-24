package com.example.security;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.security.Url.domain;

public class MainActivity extends AppCompatActivity {


int t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t=Integer.valueOf(android.os.Build.VERSION.SDK);
        if(t<22) {

          Url.domain = "http://www.tahaffuz.tk/users/";
            Url.domain_img = "http://www.tahaffuz.tk/admin/images/";
            Url.domain_guide_eng = domain+"guide.php";
            Url.domain_guide_urdu = domain+"guide_urdu.php";
            Url. domain_gen_issue_urdu = domain+"gen_issues.php";
            Url. domain_gen_issue_eng = domain+"gen_issues_urdu.php";
            Url. domain_trivia_eng = domain+"trivia.php";
            Url. domain_trivia_urdu = domain+"trivia_urdu.php";


        }else {
            Url.domain = "https://www.tahaffuz.tk/users/";
            Url.domain_img = "https://www.tahaffuz.tk/admin/images/";
            Url.domain_guide_eng = domain+"guide.php";
            Url.domain_guide_urdu = domain+"guide_urdu.php";
            Url. domain_gen_issue_urdu = domain+"gen_issues.php";
            Url. domain_gen_issue_eng = domain+"gen_issues_urdu.php";
            Url. domain_trivia_eng = domain+"trivia.php";
            Url. domain_trivia_urdu = domain+"trivia_urdu.php";

        }



        /****** Create Thread that will sleep for 5 seconds****/
        final Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(5*1000);

                    // After 5 seconds redirect to another intmainent
                    Intent i=new Intent(getBaseContext(),home.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("start","start");
                    i.putExtras(bundle);
                    startActivity(i);

                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();

    }
}
