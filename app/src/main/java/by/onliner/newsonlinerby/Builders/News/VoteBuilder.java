package by.onliner.newsonlinerby.Builders.News;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.jsoup.nodes.Element;

import by.onliner.newsonlinerby.App;
import by.onliner.newsonlinerby.Builders.IBuilder;
import by.onliner.newsonlinerby.R;

/**
 * Формирование опроса
 */
public class VoteBuilder implements IBuilder<Element, View> {
    @Override
    public View build(Element element) {
        LayoutInflater inflater = (LayoutInflater) App.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_vote, null);

        ((TextView)view.findViewById(R.id.tv_vote_title)).setText(element.getElementsByClass("news-vote__title").first().text());
        ((TextView)view.findViewById(R.id.tv_vote_stat)).setText(element.getElementsByClass("news-vote__speech").first().text());

        ViewGroup checkboxLayout = (ViewGroup)view.findViewById(R.id.l_vote_options);
        RadioGroup radioGroup = new RadioGroup(view.getContext());
        for (Element checkboxElement : element.getElementsByClass("news-form__checkbox-item")) {
            RadioButton radioButton = new RadioButton(view.getContext());
            radioButton.setTextColor(Color.BLACK);
            radioButton.setTextSize(13);
            radioButton.setText(checkboxElement.getElementsByClass("news-form__checkbox-sign").first().text());
            radioGroup.addView(radioButton);
        }

        checkboxLayout.addView(radioGroup);
        return view;
    }
}
