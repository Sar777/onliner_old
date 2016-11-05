package by.onliner.newsonlinerby.Builders.News;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import org.jsoup.nodes.Element;

import java.util.ArrayList;

import by.onliner.newsonlinerby.Adapters.HorizontalRecyclerImageSliderAdapter;
import by.onliner.newsonlinerby.App;
import by.onliner.newsonlinerby.Builders.IBuilder;
import by.onliner.newsonlinerby.Common.DividerItemDecoration;
import by.onliner.newsonlinerby.R;

/**
 * Created by Mi Air on 18.10.2016.
 */

public class ImageSliderBuilder implements IBuilder<LinearLayout, LinearLayout> {
    private Element element;
    private Activity mActivity;

    public ImageSliderBuilder(Element element, Activity activity) {
        this.element = element;
        this.mActivity = activity;
    }

    @Override
    public LinearLayout build(LinearLayout layout) {
        final ArrayList<String> imagesUrl = new ArrayList<>();
        for (Element imageElement : element.getElementsByTag("img")) {
            if (imageElement.attr("data-src").isEmpty())
                continue;

            imagesUrl.add(imageElement.attr("data-src"));
        }

        if (imagesUrl.isEmpty())
            throw new ExceptionInInitializerError("ImageSliderBuilder empty images url container");

        LayoutInflater inflater = (LayoutInflater) App.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_image_slider_minizime, null);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_horizontal_image_slider);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        recyclerView.setAdapter(new HorizontalRecyclerImageSliderAdapter(mActivity, imagesUrl));

        recyclerView.addItemDecoration(new DividerItemDecoration(App.getContext(), DividerItemDecoration.HORIZONTAL_LIST, R.drawable.recyler_decoration_yellow));

        layout.addView(view);
        return layout;
    }
}
