package by.onliner.news.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import by.onliner.news.App;
import by.onliner.news.listeners.OnFullScreenImageListener;
import by.onliner.news.R;

/**
 * Created by orion on 5.11.16.
 */

public class HorizontalRecyclerImageSliderAdapter extends RecyclerView.Adapter<HorizontalRecyclerImageSliderAdapter.MyViewHolder> {
    private ArrayList<String> mResource;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar mProgressBarLoading;
        private ImageView mImageView;

        public MyViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.img_recycler_slider);
            mImageView.setOnClickListener(new OnFullScreenImageListener(mActivity, mResource));

            mProgressBarLoading = (ProgressBar) view.findViewById(R.id.pb_loading_image);
        }
    }

    public HorizontalRecyclerImageSliderAdapter(Activity activity, ArrayList<String> resource) {
        this.mActivity = activity;
        this.mResource = resource;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_recycler_image, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mProgressBarLoading.setVisibility(View.VISIBLE);

        Picasso.with(App.getContext()).
                load(mResource.get(position)).
                error(R.drawable.ic_broken_image).
                resize(0, 300).
                into(holder.mImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.mProgressBarLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.mProgressBarLoading.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mResource.size();
    }
}