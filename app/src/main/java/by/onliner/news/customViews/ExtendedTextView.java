package by.onliner.news.customViews;

/**
 * Created by orion on 22.11.16.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import by.onliner.news.R;

/**
 * Created by orion on 22.11.16.
 */

public class ExtendedTextView extends TextView {
    private static final String TAG = "ExtentedTextView";

    public ExtendedTextView(Context context) {
        super(context);
    }

    public ExtendedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public ExtendedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.ExtendedTextView);
        String customFont = a.getString(R.styleable.ExtendedTextView_fontAsset);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf;
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface: "+e.getMessage());
            return false;
        }

        setTypeface(tf);
        return true;
    }
}