package com.example.wesle.olfriends;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;


public class PagesAdapter extends RecyclerView.Adapter<PagesAdapter.PagesViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Pages> pagesList;

    //getting the context and product list with constructor
    public PagesAdapter(Context mCtx, List<Pages> pagesList) {
        this.mCtx = mCtx;
        this.pagesList = pagesList;
    }

    @Override
    public PagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_pages, null);
        return new PagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PagesViewHolder holder, int position) {
        //getting the product of the specified position
        Pages pages = pagesList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(pages.getTitle());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(pages.getImage()));

    }


    @Override
    public int getItemCount() {
        return pagesList.size();
    }


    class PagesViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        ImageView imageView;

        public PagesViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (getAdapterPosition()) {
                        case 0:
                            final Intent intent = new Intent(mCtx, ContentActivity.class);
                            mCtx.startActivity(intent);
                            break;
                        case 1:
                            final Intent intent2 = new Intent(mCtx, FriendListActivity.class);
                            mCtx.startActivity(intent2);
                            break;
                        case 2:
                            final Intent intent3 = new Intent(mCtx, FriendsActivity.class);
                            mCtx.startActivity(intent3);
                            break;
                        case 3:
                            final Intent intent4 = new Intent(mCtx, NewsActivity.class);
                            mCtx.startActivity(intent4);
                            break;
                        case 4:
                            final Intent intent5 = new Intent(mCtx, HelpActivity.class);
                            mCtx.startActivity(intent5);
                            break;
                    }
                }
            });
        }
    }
}