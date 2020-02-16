package com.example.w4_p5;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

public class WordInput extends LinearLayout {

    private int length;

    public WordInput(Context context, int length) {
        super(context);
        this.length = length;
        init();
    }

    private void init() {
        super.setGravity(TEXT_ALIGNMENT_CENTER);
        EditText[] editTexts = new EditText[length];
        for (int i = 0; i < length; i++) {
            editTexts[i] = new EditText(getContext());
            editTexts[i].setFocusable(false);
            editTexts[i].setTextSize(40);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.width = 180;
            editTexts[i].setLayoutParams(layoutParams);
            this.addView(editTexts[i]);
        }
    }
}
