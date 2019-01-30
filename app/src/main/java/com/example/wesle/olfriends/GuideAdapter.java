package com.example.wesle.olfriends;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.GuideViewHolder> {

    private Context context;
    private List<Guide> guideList;

    public GuideAdapter(Context context, List<Guide> guideList) {
        this.context = context;
        this.guideList = guideList;
    }

    @NonNull
    @Override
    public GuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.guide_layout, null);
        GuideViewHolder holder = new GuideViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GuideViewHolder holder, int position) {
        Guide guide = guideList.get(position);

        holder.txtViewTitle.setText(guide.getTitle());
        holder.txtViewDesc.setText(guide.getDesc());
        holder.imageView.setImageDrawable(context.getResources().getDrawable(guide.getImage()));
    }

    @Override
    public int getItemCount() {
        return guideList.size();
    }

    class GuideViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView txtViewTitle, txtViewDesc;

        public GuideViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            txtViewTitle = itemView.findViewById(R.id.txtViewTitle);
            txtViewDesc = itemView.findViewById(R.id.txtViewDesc);
        }
    }

}