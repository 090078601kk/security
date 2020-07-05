package com.example.security;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class contacts extends AppCompatActivity {
    Context context;
    Button save;
    TextView phone,con;
    String name="security";
    TextView tv;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        context = this.context;
        tv = findViewById(R.id.TV);
        save = (Button) findViewById(R.id.save);
        phone = (TextView) findViewById(R.id.phone);
        con = (TextView) findViewById(R.id.con);
        getContactList();
        save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final String p = phone.getText().toString();
                                        // 03058403240
                                        int counter = p.length();
                                        //Save Now
                                        if (counter < 11) {
                                            phone.setError("Enter Right Phone No");
                                        } else {
                                            try {
                                                Toast.makeText(contacts.this, "Contactadded", Toast.LENGTH_LONG).show();
                                                Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
                                                contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                                                contactIntent
                                                        .putExtra(ContactsContract.Intents.Insert.NAME, "EMERGENCY")
                                                        .putExtra(ContactsContract.Intents.Insert.PHONE, p);

                                                startActivityForResult(contactIntent, 1);
                                            } catch (Exception e) {

                                                tv.setText(e.toString());
                                                setContentView(tv);
                                            }
                                        }
                                    }
                                }
        );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 1)
        {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Added Contact", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled Added Contact", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                        Log.i("nammm", "Name: " + name);
                        Log.i("namemee", "Phone Number: " + phoneNo);

                        if(name.equals("EMERGENCY"))
                        {
                            String as=con.getText().toString();
                            con.setText(as+phoneNo);

                        }

                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }
}
