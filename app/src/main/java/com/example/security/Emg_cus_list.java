package com.example.security;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import static com.example.security.Url.domain_img;


class Emg_cus_list extends BaseAdapter {
    private String[] images;
    private String[] name;
    private String[] ph;

LinearLayout click_able;
    View view;
    LayoutInflater layoutInflater;
    Context context;


    public Emg_cus_list(Context context,String[] image,String[] name ,String[] ph ) {
        this.images = image;
        this.name = name;
        this.ph = ph;

        this.context = context;

    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
        {
            view=new View(context);
            view=layoutInflater.inflate(R.layout.row_item_em,null);

            TextView name_view = (TextView) view.findViewById(R.id.name);
            final ImageView img = (ImageView) view.findViewById(R.id.logo);
            TextView phone_view = (TextView) view.findViewById(R.id.phone);

            name_view.setText(name[position]);
            phone_view.setText(ph[position]);

            ImageRequest imageee=new ImageRequest(domain_img+images[position], new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    img.setImageBitmap(response);

                }
            }, 0, 0, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(view.getContext(), "Internet not found!!!", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            });
            MySingleton.getmInstanse(view.getContext()).addToRequest(imageee);

        }
        return view;
    }


}