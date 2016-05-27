package com.liyawei.yelpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    YelpAPIFactory apiFactory = new YelpAPIFactory(Utility.CONSUMER_KEY, Utility.CONSUMER_SECRET, Utility.TOKEN, Utility.TOKEN_SECRET);
    YelpAPI yelpAPI = apiFactory.createAPI();

    SearchView searchView;

    RecyclerView recList;
    BusinessCardAdapter bca;
    ArrayList<Business> businesses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        if(savedInstanceState == null){
            businesses = new ArrayList<Business>();
        }
        else{
            businesses = (ArrayList<Business>) savedInstanceState.getSerializable("businesses");
        }
        bca = new BusinessCardAdapter(businesses);
        recList.setAdapter(bca);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("businesses", businesses);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        businesses = (ArrayList<Business>) savedInstanceState.getSerializable("businesses");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        Map<String, String> params = new HashMap<>();


        params.put("term", query);
        params.put("sort", "2");
        params.put("limit", "10");
        params.put("lang", "en");

        Call<SearchResponse> call = yelpAPI.search("Toronto", params);
        Callback<SearchResponse> callback = new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                // Update UI text with the searchResponse.

                handleResponce(searchResponse);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                // HTTP error happened, do something to handle it.
            }
        };

        call.enqueue(callback);
        return true;
    }

    private void handleResponce(SearchResponse searchResponse) {
        businesses = searchResponse.businesses();
        int totalNumberOfResult = searchResponse.total();

//        bca = new BusinessCardAdapter(businesses);
//        recList.setAdapter(bca);
//        bca.notifyDataSetChanged();
        recList.setAdapter(new BusinessCardAdapter(businesses));
        recList.invalidate();
    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }
}
