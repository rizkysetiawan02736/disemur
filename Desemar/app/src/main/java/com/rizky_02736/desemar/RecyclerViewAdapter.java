package com.rizky_02736.desemar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    List<DataAdapter> dataAdapters;
    ImageLoader imageLoader;

    public RecyclerViewAdapter(List<DataAdapter> getDataAdapter, Context context){
        super();
        this.dataAdapters=getDataAdapter;
        this.context=context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rows_lowongan, parent, false);
    ViewHolder viewHolder = new ViewHolder(view);
    return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        DataAdapter dataAdapterOBJ = dataAdapters.get(position);
        imageLoader = ImageAdapter.getInstance(context).getImageLoader();
        imageLoader.get(dataAdapterOBJ.geturl(),
                ImageLoader.getImageListener(
                        Viewholder.imgvolley,
                        R.mipmap.ic_launcher,
                        android.R.drawable.ic_dialog_alert
                )
                );
        Viewholder.imgvolley.setImageUrl(dataAdapterOBJ.geturl(),imageLoader);
        Viewholder.txtjudul.setText(dataAdapterOBJ.getjudul());
        Viewholder.txtdeskripsi.setText(dataAdapterOBJ.getdeskripsi());
    }

    @Override
    public int getItemCount() {
        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtjudul;
        public TextView txtdeskripsi;
        public NetworkImageView imgvolley;
        public ViewHolder(View itemView){
            super(itemView);
            txtjudul=(TextView) itemView.findViewById(R.id.txtjudul);
            txtdeskripsi=(TextView) itemView.findViewById(R.id.txtdeskripsi);
            imgvolley=(NetworkImageView)itemView.findViewById(R.id.imgvolley);

        }
    }
}
