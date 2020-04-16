package com.example.ingredieat.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.ingredieat.entity.Step;

import java.util.List;

public class StepAdapter extends ArrayAdapter<Step> {
    
    public StepAdapter(@NonNull Context context, int resource, @NonNull List<Step> objects) {
        super(context, resource, objects);
    }
}
