package com.liyawei.yelpdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yelp.clientlib.entities.Business;

public class DetailActivity extends AppCompatActivity {

    Business mBusiness;

    ImageView picture, ratting;
    TextView name;
    TextView phone;
    TextView addr;
    TextView area;
    TextView city;
    TextView recentReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        name = (TextView) findViewById(R.id.detail_name);
        phone = (TextView) findViewById(R.id.detail_phone);
        addr = (TextView) findViewById(R.id.detail_addr);
        area = (TextView) findViewById(R.id.detail_area);
        city = (TextView) findViewById(R.id.detail_city);
        recentReview = (TextView) findViewById(R.id.reviews);

        picture = (ImageView) findViewById(R.id.detail_imageView);
        ratting = (ImageView) findViewById(R.id.ratting_imageView);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            mBusiness = (Business) intent.getSerializableExtra(Intent.EXTRA_TEXT);
            name.setText(mBusiness.name());
            phone.setText(mBusiness.phone());
            addr.setText(mBusiness.location().displayAddress().get(0));
            area.setText(mBusiness.location().neighborhoods().get(0));
            city.setText(mBusiness.location().city());
            recentReview.setText(mBusiness.snippetText());

            Picasso.with(this)
                    .load(mBusiness.imageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .resize(200, 200)
                    .centerCrop()
                    .into(picture);
            Picasso.with(this)
                    .load(mBusiness.ratingImgUrlLarge())
                    .into(ratting);
        }
    }
}
