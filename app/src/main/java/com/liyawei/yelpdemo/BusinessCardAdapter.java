package com.liyawei.yelpdemo;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yelp.clientlib.entities.Business;

import java.util.List;

/**
 * Created by liyawei.
 */
public class BusinessCardAdapter extends RecyclerView.Adapter<BusinessCardAdapter.BusinessViewHolder> {
    public static final String LOG_TAG = BusinessCardAdapter.class.getSimpleName();
    private List<Business> businessList;

    public BusinessCardAdapter(List<Business> businessList) {
        this.businessList = businessList;
    }


    @Override
    public int getItemCount() {
        return businessList.size();
    }

    @Override
    public void onBindViewHolder(BusinessViewHolder businessViewHolder, int i) {
        Business business = businessList.get(i);
        businessViewHolder.vName.setText(business.name());
        businessViewHolder.vPhone.setText(business.phone());
        businessViewHolder.vAddress.setText(business.location().address().get(0));
        businessViewHolder.vImage.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public BusinessViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.business_card, viewGroup, false);

        return new BusinessViewHolder(itemView);
    }

    public class BusinessViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected ImageView vImage;
        protected TextView vName;
        protected TextView vPhone;
        protected TextView vAddress;

        public BusinessViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.business_name);
            vPhone = (TextView)  v.findViewById(R.id.business_phone);
            vAddress = (TextView)  v.findViewById(R.id.business_addr);
            vImage = (ImageView) v.findViewById(R.id.business_thumb);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(LOG_TAG, "onClick " + getAdapterPosition());
            Intent intent = new Intent(view.getContext(), DetailActivity.class)
                    .putExtra(Intent.EXTRA_TEXT, businessList.get(getAdapterPosition()));
            view.getContext().startActivity(intent);
        }
    }
}
