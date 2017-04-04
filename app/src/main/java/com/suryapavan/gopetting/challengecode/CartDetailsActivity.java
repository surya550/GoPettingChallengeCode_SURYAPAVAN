package com.suryapavan.gopetting.challengecode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.suryapavan.gopetting.challengecode.adapter.CartItemAdapter;
import com.suryapavan.gopetting.challengecode.database.DataCartObject;
import com.suryapavan.gopetting.challengecode.database.DatabaseAccess;

import java.util.ArrayList;

/**
 * Created by surya on 4/4/2017.
 */

public class CartDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CartItemAdapter cartItemAdapter;
    DatabaseAccess access;
    ArrayList<DataCartObject> cartdata;
    TextView empty_cart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Init();
        CartData(this);

    }

    private void Init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setClickable(true);
        recyclerView.setFocusable(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLongClickable(true);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.VERTICAL,
                        false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        empty_cart = (TextView) findViewById(R.id.empty_cart);
    }

    public void CartData(Context context) {

        access = DatabaseAccess.getInstance(context);
        access.open();

        cartdata = access.getCartAllItems(DatabaseAccess.tableNames[0]);

        access.close();


        if (cartdata.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            empty_cart.setVisibility(View.GONE);
            cartItemAdapter = new CartItemAdapter(context, cartdata);
            recyclerView.setAdapter(cartItemAdapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            empty_cart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CartDetailsActivity.this, DataListActivitry.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();

    }
}
