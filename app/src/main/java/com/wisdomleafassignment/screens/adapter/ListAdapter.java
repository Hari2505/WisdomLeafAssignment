package com.wisdomleafassignment.screens.adapter;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wisdomleafassignment.R;
import com.wisdomleafassignment.screens.IListPresenter;
import com.wisdomleafassignment.screens.MainActivity;
import com.wisdomleafassignment.screens.model.Datum;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private final List<Datum> list;
    private final Context context;
    IListPresenter iListPresenter;

    public ListAdapter(IListPresenter iListPresenter, MainActivity listActivity) {
        this.list = new ArrayList<>();
        this.context = listActivity;
        this.iListPresenter = iListPresenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (list.size() != 0) {
            final Datum model = list.get(position);
            holder.title.setText(model.getAuthor());
            String url = model.getDownloadUrl();
            RequestOptions myOptions = new RequestOptions()
                    .override(100, 100);

            Glide.with(context)
                    .asBitmap()
                    .apply(myOptions)
                    .load(url)
                    .placeholder(R.drawable.img)
                    .into(holder.imageView);
            holder.rootLayout.setOnClickListener(view -> new AlertDialog.Builder(context)
                    .setTitle("Description")
                    .setMessage(model.getAuthor() + "/n" + model.getUrl())
                    .show());

        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Datum> model) {
        list.addAll(model);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView title;
        AppCompatImageView imageView;
        RelativeLayout rootLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image);
            rootLayout = itemView.findViewById(R.id.root_layout);
        }
    }

}
