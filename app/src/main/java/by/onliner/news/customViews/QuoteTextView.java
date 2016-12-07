package by.onliner.news.customViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import by.onliner.news.App;
import by.onliner.news.R;

/**
 * Created by Mi Air on 17.10.2016.
 */

public class QuoteTextView extends TextView {
    protected Paint paint = new Paint();
    public static final int BORDER_TOP = 0x00000001;
    public static final int BORDER_BOTTOM = 0x00000002;

    protected Border[] borders;

    public QuoteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public QuoteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuoteTextView(Context context) {
        super(context);
        init();
    }
    private void init(){
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);

        borders = new Border[2];
        borders[0] = new Border(BORDER_BOTTOM);
        borders[0].setColor(ResourcesCompat.getColor(App.getContext().getResources(), R.color.colorOnlinerBlockquoteText, null));
        borders[0].setWidth(13);
        borders[1] = new Border(BORDER_TOP);
        borders[1].setColor(ResourcesCompat.getColor(App.getContext().getResources(), R.color.colorOnlinerBlockquoteText, null));
        borders[1].setWidth(11);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (borders == null)
            return;

        for (Border border : borders) {
            paint.setColor(border.getColor());
            paint.setStrokeWidth(border.getWidth());

            switch (border.getStyle()) {
                case BORDER_TOP:
                    canvas.drawLine(getWidth() / 2  - getWidth() / 9, 0, getWidth() / 2  + getWidth() / 9, 0, paint);
                    break;
                case BORDER_BOTTOM:
                    canvas.drawLine(getWidth() / 2  - getWidth() / 9, getHeight(), getWidth() / 2  + getWidth() / 9, getHeight(), paint);
                    break;
                default:
                    break;
            }
        }
    }
}
