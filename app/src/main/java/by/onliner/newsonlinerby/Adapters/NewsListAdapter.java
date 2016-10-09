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
import by.onliner.newsonlinerby.Structures.HeaderNews;

public class NewsListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<HeaderNews> objects;

    ImageLoader imageLoader = ImageLoader.getInstance();

    public NewsListAdapter(Context context, ArrayList<HeaderNews> products) {
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

        HeaderNews p = getNew(position);

        imageLoader.displayImage(p.getImage(), ((ImageView)view.findViewById(R.id.i_preview_image)), new DisplayImageOptions.Builder().cacheOnDisk(true).build());

        ((TextView)view.findViewById(R.id.tv_preview_title)).setText(p.getTitle());
        ((TextView)view.findViewById(R.id.tv_preview_comments)).setText(p.getComments().toString());
        ((TextView)view.findViewById(R.id.tv_preview_views)).setText(p.getViews().toString());

        // Attributes
        if (p.getAttributes().getUpd())
            view.findViewById(R.id.tv_preview_upd_status).setVisibility(View.VISIBLE);

        if (p.getAttributes().getPhotos() > 0) {
            view.findViewById(R.id.l_preview_photos).setVisibility(View.VISIBLE);
            ((TextView)view.findViewById(R.id.tv_preview_photos)).setText(p.getAttributes().getPhotos().toString());
        }

        if (p.getAttributes().getCamera())
            view.findViewById(R.id.i_preview_video).setVisibility(View.VISIBLE);

        return view;
    }

    HeaderNews getNew(int position) {
        return ((HeaderNews) getItem(position));
    }
}
