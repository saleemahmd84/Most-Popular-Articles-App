package com.example.saleem.mostpopular.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saleem.mostpopular.R;
import com.example.saleem.mostpopular.models.MediaMetadatum;
import com.example.saleem.mostpopular.models.Result;
import com.example.saleem.mostpopular.utils.OnItemClickListener;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Result> dataList;
    private Context context;
    private final OnItemClickListener listener;

    public CustomAdapter(Context context,List<Result> dataList, OnItemClickListener listener){
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle, txtTemperature, txtCoordinates, txtDescription;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title);
            txtTemperature = mView.findViewById(R.id.temperature);
            txtCoordinates = mView.findViewById(R.id.coordinates);
            txtDescription = mView.findViewById(R.id.description);
            coverImage = mView.findViewById(R.id.coverImage);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        bind(holder.mView, dataList.get(position), listener);

        holder.txtTitle.setText(dataList.get(position).getTitle());
        holder.txtTemperature.setText(dataList.get(position).getPublishedDate());
        holder.txtCoordinates.setText(dataList.get(position).getSection());
        holder.txtDescription.setText(dataList.get(position).getAbstract());


        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));

        if (dataList.get(position).getMedia().size() > 0) {

            String ImageURL = "";

            for (MediaMetadatum m : dataList.get(position).getMedia().get(0).getMediaMetadata())
            {
                if(m.getFormat().equals("Standard Thumbnail"))
                {
                    ImageURL = m.getUrl();
                }
            }
            //dataList.get(position).getMedia().get(0).getMediaMetadata().get(0).getUrl();




            builder.build().load(ImageURL)
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.coverImage);
        }
        else
        {
            builder.build().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEV2E_XMLFXWwTjgdEINJQJv6Lct72FTMyWLInTe6kvN4ekKn0")
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.coverImage);
        }

    }

    public void bind(final View mView, final Result item, final OnItemClickListener listener) {

        mView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}