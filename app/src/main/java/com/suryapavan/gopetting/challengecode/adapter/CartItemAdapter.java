package com.suryapavan.gopetting.challengecode.adapter;


/**
 * Created by surya on 4/4/2017.
 */
import android.content.Context;
import android.content.Intent;
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
import com.suryapavan.gopetting.challengecode.CartDetailsActivity;
import com.suryapavan.gopetting.challengecode.R;
import com.suryapavan.gopetting.challengecode.database.DataCartObject;
import com.suryapavan.gopetting.challengecode.database.DatabaseAccess;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    ArrayList<DataCartObject> cartdata;
    private LayoutInflater inflater;
    private Context mContext;
    DatabaseAccess access;
    CartDetailsActivity cartDetailsActivity = new CartDetailsActivity();
    SharedPreferences preferences;


    public CartItemAdapter(Context context, ArrayList<DataCartObject> cartdata) {
        mContext = context;

        this.cartdata = cartdata;
        this.inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.activity_cart_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final DataCartObject currentPost = cartdata.get(position);


        holder.guide_title.setText(currentPost.getName());
        holder.guide_enddate.setText(currentPost.getEndDate());

        Picasso.with(mContext)
                .load(currentPost.getIcon())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.guide_icon);

        holder.guide_quantity.setText("Quantity :  " + String.valueOf(currentPost.getQuantity()));
        holder.remove_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                access = DatabaseAccess.getInstance(mContext);
                access.open();

                access.deleteItem(DatabaseAccess.tableNames[0], cartdata.get(position).getId());

                access.close();


                Toast.makeText(mContext, "Item removed Successfully", Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, CartDetailsActivity.class));


            }
        });


    }


    @Override
    public int getItemCount() {
        return cartdata == null ? 0 : cartdata.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView guide_icon;
        TextView guide_title, guide_enddate, guide_quantity;
        AppCompatButton remove_item;


        ViewHolder(View itemView) {
            super(itemView);

            guide_icon = (ImageView) itemView.findViewById(R.id.guide_Icon);
            guide_title = (TextView) itemView.findViewById(R.id.guide_title);
            guide_enddate = (TextView) itemView.findViewById(R.id.guide_enddate);
            guide_quantity = (TextView) itemView.findViewById(R.id.guide_quantity);
            remove_item = (AppCompatButton) itemView.findViewById(R.id.remove_item);


        }
    }


}
