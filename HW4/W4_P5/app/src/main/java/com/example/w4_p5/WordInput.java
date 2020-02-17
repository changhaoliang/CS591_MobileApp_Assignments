package com.example.w4_p5;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WordInput extends LinearLayout {

    private int length;
    private EditText[] editTexts;

    public WordInput(Context context, int length) {
        super(context);
        this.length = length;
        init();
    }

    public EditText[] getEditTexts() {
        return editTexts;
    }

    public void setText(char c, int pos) {
        editTexts[pos].setText(String.valueOf(c), TextView.BufferType.EDITABLE);
        editTexts[pos].setFocusable(false);
    }

    public void clear() {
        for (int i = 0; i < length; i++) {
            editTexts[i].setVisibility(View.INVISIBLE);
            System.out.println("====");
        }
    }

    private void init() {
        editTexts = new EditText[length];
        for (int i = 0; i < length; i++) {
            editTexts[i] = new EditText(getContext());
            editTexts[i].setFocusable(false);
            editTexts[i].setTextSize(40);
            editTexts[i].setTextAlignment(TEXT_ALIGNMENT_CENTER);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.width = 180;
            editTexts[i].setLayoutParams(layoutParams);
            this.addView(editTexts[i]);
        }
    }
}
