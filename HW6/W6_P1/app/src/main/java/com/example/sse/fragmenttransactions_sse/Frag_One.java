package com.example.sse.fragmenttransactions_sse;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
//import android.support.v4.app.Fragment;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_One extends Fragment {

    final String test = "Frag_One";

    public Frag_One() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e(test, "onCreateView");
        return inflater.inflate(R.layout.frag_one, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        Log.e(test, "onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(test, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        Log.e(test, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e(test, "onDetach");
        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.e(test, "onPause");
        super.onPause();
    }

    @Override
    public void onStart() {
        Log.e(test, "onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.e(test, "onStop");
        super.onStop();
    }

    @Override
    public void onResume() {
        Log.e(test, "onResume");
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        Log.e(test, "onAttach");
        super.onAttach(context);
    }
}
