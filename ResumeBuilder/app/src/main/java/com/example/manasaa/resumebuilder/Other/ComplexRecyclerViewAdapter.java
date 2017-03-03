package com.example.manasaa.resumebuilder.Other;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.manasaa.resumebuilder.Model.UserDetails;
import com.example.manasaa.resumebuilder.R;
import com.example.manasaa.resumebuilder.ViewHolder.ViewHolderResumeHeader;

import java.util.List;

/**
 * Created by manasa.a on 02-03-2017.
 */

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private String TAG = ComplexRecyclerViewAdapter.class.getSimpleName();
    private final int RESUME_HEADER_POSITION=0;
    // The items to display in your RecyclerView
    private List<Object> items;
    Context context;
    // Provide a suitable constructor (depends on the kind of dataset)
    public ComplexRecyclerViewAdapter(List<Object> items, Context context) {
        Log.d(TAG, "called constructor ComplexRecyclerViewAdapter(List ");
        this.items = items;
        this.context = context;

    }
    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "called getItemViewType(int position");
        //return super.getItemViewType(position);
        if (items.get(position) instanceof UserDetails) {
            return RESUME_HEADER_POSITION;
        }
        return -1;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "called onCreateViewHolder(");

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        switch (viewType) {
//            case RESUME_HEADER_POSITION :
//                View v1 = inflater.inflate(R.layout.resume_header, parent, false);
//                viewHolder = new ViewHolderResumeHeader(v1);
//                break;
//            default:
//                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
//                viewHolder = new RecyclerViewSimpleTextViewHolder(v);
//                break;
//        }

        //if(viewType ==RESUME_HEADER_POSITION){
            View v1 = inflater.inflate(R.layout.resume_header, parent, false);
                viewHolder = new ViewHolderResumeHeader(v1);
        //}

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "called onBindViewHolder(R) postion"+ position);
        ViewHolderResumeHeader vh1 = (ViewHolderResumeHeader) holder;
        configureViewHolderResumeHeader(vh1, position);

    }
    private void configureViewHolderResumeHeader(ViewHolderResumeHeader vh1, int position) {
        Log.d(TAG, "called  configureViewHolderResumeHeader");
        UserDetails user = (UserDetails) items.get(position);
        if (user != null) {
            vh1.getUserNameTxtView().setText("Name: "+user.getUserName() );
            vh1.getUserEmailTxtView().setText("Hometown: "+user.getUserEmail() );
            Glide.with(context)
                    .load(user.getUserProfileLink())
                    .crossFade()
                    .thumbnail(0.2f)
                    .bitmapTransform(new CircleTransform(context))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(vh1.getUserProfileURLImageView());
           // vh1.getUserProfileURLTxtView().setText();
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "called getItemCount()" + items.size());
        return this.items.size();
    }
}
