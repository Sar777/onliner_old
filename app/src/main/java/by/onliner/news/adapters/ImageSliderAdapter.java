package by.onliner.news.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import by.onliner.news.App;
import by.onliner.news.R;

/**
 * Created by orion on 3.11.16.
 */

public class ImageSliderAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> mResources;

    public ImageSliderAdapter(Context context, ArrayList<String> urls) {
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mResources = urls;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View itemView = mLayoutInflater.inflate(R.layout.layout_image_slider_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_slider);

        Picasso.with(App.getContext()).
                load(getUrl(position)).
                error(R.drawable.ic_broken_image).
                memoryPolicy(MemoryPolicy.NO_CACHE).
                into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        itemView.findViewById(R.id.pb_fullscreen_loading).setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                    }
                });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ViewGroup) object);
    }

    private String getUrl(int pos) {
        return mResources.get(pos);
    }
}
