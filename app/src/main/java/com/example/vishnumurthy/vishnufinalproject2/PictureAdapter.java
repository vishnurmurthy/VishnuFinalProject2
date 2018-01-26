package com.example.vishnumurthy.vishnufinalproject2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;



public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.MyViewHolder>{

    private List<PictureText> picturesList;
    private Context mCallback;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }


    public PictureAdapter(List<PictureText> picturesList) {
        this.picturesList = picturesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_list_row, parent, false);

        mCallback = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PictureText pt = picturesList.get(position);
        holder.title.setText(pt.getTitle());

    }

    @Override
    public int getItemCount() {
        return picturesList.size();
    }


}
