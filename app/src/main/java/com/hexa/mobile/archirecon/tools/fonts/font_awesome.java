package com.hexa.mobile.archirecon.tools.fonts;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class font_awesome extends android.support.v7.widget.AppCompatTextView {

    public font_awesome(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public font_awesome(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public font_awesome(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/fontawesome-webfont.ttf");
            setTypeface(tf);
        }
    }

}
