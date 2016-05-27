package com.liyawei.yelpdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.yelp.clientlib.entities.Business;

public class DetailActivity extends AppCompatActivity {

    Business mBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            mBusiness = (Business) intent.getSerializableExtra(Intent.EXTRA_TEXT);
            ((TextView) findViewById(R.id.detail_text)).setText(mBusiness.name());
        }
    }
}
