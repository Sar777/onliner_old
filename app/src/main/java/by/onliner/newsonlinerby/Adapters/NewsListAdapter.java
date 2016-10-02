package by.onliner.newsonlinerby.Adapters;

/**
 * Created by orion on 16.09.2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import by.onliner.newsonlinerby.MainActivity;
import by.onliner.newsonlinerby.R;
import by.onliner.newsonlinerby.Structures.Preview.PreviewData;

public class NewsListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<PreviewData> objects;

    ImageLoader imageLoader = ImageLoader.getInstance();

    public NewsListAdapter(Context context, ArrayList<PreviewData> products) {
        if (context == null)
            return;

        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader.init(MainActivity.imageLoaderConfig);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.l_item_news, parent, false);
        }

        PreviewData p = getNew(position);

        imageLoader.displayImage(p.getImageUrl(), ((ImageView)view.findViewById(R.id.preview_image)), new DisplayImageOptions.Builder().cacheOnDisk(true).build());

        if (p.isUpd())
            ((TextView)view.findViewById(R.id.preview_upd_status)).setVisibility(View.VISIBLE);

        ((TextView)view.findViewById(R.id.preview_title)).setText(p.getTitle());
        ((TextView)view.findViewById(R.id.preview_comments)).setText(p.getComments().toString());
        ((TextView)view.findViewById(R.id.preview_views)).setText(p.getViews().toString());
        //((TextView)view.findViewById(R.id.pre)).setText(p.getDate());

        return view;
    }

    PreviewData getNew(int position) {
        return ((PreviewData) getItem(position));
    }
}
