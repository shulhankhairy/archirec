package com.hexa.mobile.archirecon.tools.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by alenovan on 8/16/17.
 */

public class EditText_fontRegularRoboto extends android.support.v7.widget.AppCompatEditText {

    public EditText_fontRegularRoboto(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditText_fontRegularRoboto(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditText_fontRegularRoboto(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Comfortaa-Regular.ttf");
            setTypeface(tf);
        }
    }

}

