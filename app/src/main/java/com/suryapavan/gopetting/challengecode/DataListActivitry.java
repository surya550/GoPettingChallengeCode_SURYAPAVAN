package com.suryapavan.gopetting.challengecode;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;
import com.suryapavan.gopetting.challengecode.adapter.GuideDataAdapter;
import com.suryapavan.gopetting.challengecode.database.DataCartObject;
import com.suryapavan.gopetting.challengecode.database.DatabaseAccess;
import com.suryapavan.gopetting.challengecode.model.GuideData;
import com.suryapavan.gopetting.challengecode.model.GuideDataResponse;
import com.suryapavan.gopetting.challengecode.rest.ApiClient;
import com.suryapavan.gopetting.challengecode.rest.ApiInterface;
import com.suryapavan.gopetting.challengecode.utils.AppStatus;
import com.suryapavan.gopetting.challengecode.utils.LoadDialog;
import com.suryapavan.gopetting.challengecode.utils.LoadDialogNoInternet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by surya on 4/4/2017.
 */

public class DataListActivitry extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    RecyclerView recyclerView;
    List<GuideData> guideDataList;
    GuideDataAdapter guideDataAdapter;
    FloatingActionButton cart;
    static TextView cartCount;
    DatabaseAccess access;
    ArrayList<DataCartObject> cartdata;
    ImageView user_pic;
    TextView user_name, user_email;

    SharedPreferences preferences;

    LoadDialogNoInternet loadDialogNoInternet = new LoadDialogNoInternet();
    LoadDialog loadDialog = new LoadDialog();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        GoogleSign();

        Init();

        if (AppStatus.getInstance(this).isOnline()) {
            loadDialog.LoadDialog(this);
            GetGuideData();
        } else {
            loadDialogNoInternet.LoadDialog(this, getString(R.string.networkissue), getString(R.string.networkissuemessage));

        }

        user_pic = (ImageView) findViewById(R.id.user_pic);
        user_name = (TextView) findViewById(R.id.user_name);
        user_email = (TextView) findViewById(R.id.user_email);
        Picasso.with(this)
                .load(preferences.getString("google_user_image_url", ""))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(user_pic);

        user_name.setText(preferences.getString("google_user_name", ""));

        user_email.setText(preferences.getString("google_user_emailid", ""));


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

        cart = (FloatingActionButton) findViewById(R.id.fab);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CartDetailsActivity.class));
            }
        });


        cartCount = (TextView) findViewById(R.id.count);

        access = DatabaseAccess.getInstance(this);
        access.open();

        cartdata = access.getCartAllItems(DatabaseAccess.tableNames[0]);

        access.close();
        updateSum(cartdata.size());

    }

    public static void updateSum(int sum) {
        cartCount.setText(Integer.toString(sum));
    }

    private void GoogleSign() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {


                        final Dialog dialog = new Dialog(DataListActivitry.this, R.style.MyDialogTheme);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


                        dialog.setContentView(R.layout.loading_dialog_1);
                        dialog.show();

                        TextView textView = (TextView) dialog.findViewById(R.id.textview);
                        TextView textViewHeading = (TextView) dialog.findViewById(R.id.cdd);
                        TextView textClose = (TextView) dialog.findViewById(R.id.DismissDialogButton);


                        textViewHeading.setText("Signout");
                        textView.setText("Do you Want To Delete All the Data ?");
                        textClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                access = DatabaseAccess.getInstance(DataListActivitry.this);
                                access.open();

                                access.deleteAllData(DatabaseAccess.tableNames[0]);

                                access.close();

                                dialog.dismiss();
                                startActivity(new Intent(DataListActivitry.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                finish();

                            }
                        });


                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    @Override
    public void onBackPressed() {
        signOut();

    }

    private void GetGuideData() {

        ApiInterface apiService =
                ApiClient.createService(ApiInterface.class);
        Call<GuideDataResponse> call = apiService.GetGuideDetails();
        call.enqueue(new Callback<GuideDataResponse>() {
            @Override
            public void onResponse(Call<GuideDataResponse> call, Response<GuideDataResponse> response) {
                int statusCode = response.code();

                guideDataList = response.body().getDataX();


                if (guideDataList.size() > 0) {

                    loadDialog.DismissDialog();
                    guideDataAdapter = new GuideDataAdapter(getApplicationContext(), guideDataList);
                    recyclerView.setAdapter(guideDataAdapter);
                } else {

                    loadDialog.DismissDialog();
                }

            }

            @Override
            public void onFailure(Call<GuideDataResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAG", t.toString());
            }
        });
    }
}
