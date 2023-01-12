package com.wisdomleafassignment.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisdomleafassignment.R;
import com.wisdomleafassignment.screens.adapter.ListAdapter;
import com.wisdomleafassignment.screens.model.Datum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IListView {
    RecyclerView recyclerView;
    int page = 1, limit = 20;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    private ListAdapter listAdapter;
    private IListPresenter iListPresenter;
    private ProgressBar loadingPB;
    private boolean loading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerviewId);
        loadingPB = findViewById(R.id.idPBLoading);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_id);
        iListPresenter = new ListPresenter(this);
        initRecycler();

        callApi();
        setUpPagination();
        setUpSwipeRefresh();

    }

    private void callApi() {
        if (isConnected()) {
            iListPresenter.getList(page, limit);
            Log.e("page and limit", String.valueOf(page));
        } else {
            loadingPB.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (isConnected()) {
                iListPresenter.getList(0, limit);
                Log.e("page and limit", String.valueOf("0"));
            } else {
                loadingPB.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }

            swipeRefreshLayout.setRefreshing(false);
            initRecycler();
        });
    }

    private void setUpPagination() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState > 0) {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            page++;
                            loadingPB.setVisibility(View.VISIBLE);
                            callApi();

                        }
                    }else{
                        loading = true;
                    }
                }
            }
        });
    }

    private void initRecycler() {
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listAdapter = new ListAdapter(iListPresenter, this);
        recyclerView.setAdapter(listAdapter);

    }

    public boolean isConnected() {
        boolean connected;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return false;
    }


    @Override
    public void setList(Object model) throws IOException, JSONException {
        List<Datum> list = new ArrayList<>();

        list.clear();
        if (model != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String respData = objectMapper.writeValueAsString(model);

            JSONArray jsonArray = new JSONArray(respData);
            JSONObject values;

            for (int j = 0; j < jsonArray.length(); j++) {
                values = jsonArray.getJSONObject(j);
                String id = values.getString("id");
                String author = values.getString("author");
                int width = values.getInt("width");
                int height = values.getInt("height");
                String url = values.getString("url");
                String downloadUrl = values.getString("download_url");

                Datum datum = new Datum(id, author, width, height, url, downloadUrl);
                list.add(datum);
                //Log.i(TAG, "list: " + list.get(j).getDownloadUrl());
                listAdapter.setData(list);
            }
            loadingPB.setVisibility(View.INVISIBLE);

        }
    }
}