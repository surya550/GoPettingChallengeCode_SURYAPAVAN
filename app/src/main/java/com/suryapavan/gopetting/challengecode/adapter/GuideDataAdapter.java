package com.suryapavan.gopetting.challengecode.adapter;


/**
 * Created by surya on 4/4/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.suryapavan.gopetting.challengecode.DataListActivitry;
import com.suryapavan.gopetting.challengecode.R;
import com.suryapavan.gopetting.challengecode.database.DataCartObject;
import com.suryapavan.gopetting.challengecode.database.DatabaseAccess;
import com.suryapavan.gopetting.challengecode.model.GuideData;

import java.util.ArrayList;
import java.util.List;

public class GuideDataAdapter extends RecyclerView.Adapter<GuideDataAdapter.ViewHolder> {

    private List<GuideData> guideDataList;
    private LayoutInflater inflater;
    private Context mContext;
    DatabaseAccess access;
    SharedPreferences preferences;
    ArrayList<DataCartObject> cartdata;

    public GuideDataAdapter(Context context, List<GuideData> guideDataList) {
        mContext = context;

        this.guideDataList = guideDataList;
        this.inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.activity_data_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GuideData currentPost = guideDataList.get(position);


        holder.guide_title.setText(currentPost.getName());
        holder.guide_enddate.setText(currentPost.getEndDate());

        Picasso.with(mContext)
                .load(currentPost.getIcon())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.guide_icon);

        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                access = DatabaseAccess.getInstance(mContext);
                access.open();
                ArrayList<DataCartObject> cartObjects;
                // checking
                cartObjects = access.getCartItem(DatabaseAccess.tableNames[0], guideDataList.get(position).getName());
                access.close();

                if (cartObjects.size() > 0) {
                    access = DatabaseAccess.getInstance(mContext);
                    access.open();
                    int count = cartObjects.get(0).getQuantity() + 1;
                    // update the cart
                    access.updateItem(DatabaseAccess.tableNames[0], cartObjects.get(0).getName(), count);

                    access.close();
                } else {
                    access = DatabaseAccess.getInstance(mContext);
                    access.open();
                    // inserting new Item
                    access.insertItem(DatabaseAccess.tableNames[0], guideDataList.get(position).getName(), guideDataList.get(position).getEndDate(),
                            guideDataList.get(position).getIcon(), 1);
                    access.close();
                }


                access = DatabaseAccess.getInstance(mContext);
                access.open();

                cartdata = access.getCartAllItems(DatabaseAccess.tableNames[0]);

                access.close();

                DataListActivitry.updateSum(cartdata.size());

                Toast.makeText(mContext, "Item Added Successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return guideDataList == null ? 0 : guideDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView guide_icon;
        TextView guide_title, guide_enddate;
        AppCompatButton addtocart;


        ViewHolder(View itemView) {
            super(itemView);

            guide_icon = (ImageView) itemView.findViewById(R.id.guide_Icon);
            guide_title = (TextView) itemView.findViewById(R.id.guide_title);
            guide_enddate = (TextView) itemView.findViewById(R.id.guide_enddate);
            addtocart = (AppCompatButton) itemView.findViewById(R.id.addtocart);


        }
    }


}
