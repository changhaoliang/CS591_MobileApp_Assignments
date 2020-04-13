<<<<<<< HEAD
package com.example.sse.interfragmentcommunication;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//this will get inflated down below.

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends Fragment {

    private View view;


    public BottomFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottom, container, false);  //separate me from return statement.
        return view;

 //       return super.onCreateView(inflater, container, savedInstanceState);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        View view = inflater.inflate(R.layout.fragment_bottom, container, false);  //separate me from return statement.
//        txtFunnyMessage = (TextView)view.findViewById(R.id.txtFunnyMessage);      //need a chance to do this other stuff,
//        txtFunnyMessage2 = (TextView)view.findViewById(R.id.txtFunnyMessage2);    //before returning the inflated view.
//        return view;
////        return super.onCreateView(inflater, container, savedInstanceState);
//
//    }

    //Receiving Team
    //It is best practice that this should be accessed via the main activity, not other fragments.
    public void setPicture(String picture){
        TextView foodText = (TextView) view.findViewById(R.id.foodText);
        switch (picture) {
            case "Burger":
                view.setBackgroundResource(R.drawable.squareburger);
                foodText.setText(R.string.Burger);
                break;
            case "Salad":
                view.setBackgroundResource(R.drawable.salad);
                foodText.setText(R.string.Salad);
                break;
            case "Pizza":
                view.setBackgroundResource(R.drawable.pizza);
                foodText.setText(R.string.Pizza);
                break;
            case "Hotpot":
                view.setBackgroundResource(R.drawable.hotpot);
                foodText.setText(R.string.Hotpot);
                break;
            case "Spaghetti":
                view.setBackgroundResource(R.drawable.spaghetti);
                foodText.setText(R.string.Spaghetti);
                break;
        }

    }

}
=======
package com.example.sse.interfragmentcommunication;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//this will get inflated down below.

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends Fragment {

    private View view;


    public BottomFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottom, container, false);  //separate me from return statement.
        return view;

        //       return super.onCreateView(inflater, container, savedInstanceState);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        View view = inflater.inflate(R.layout.fragment_bottom, container, false);  //separate me from return statement.
//        txtFunnyMessage = (TextView)view.findViewById(R.id.txtFunnyMessage);      //need a chance to do this other stuff,
//        txtFunnyMessage2 = (TextView)view.findViewById(R.id.txtFunnyMessage2);    //before returning the inflated view.
//        return view;
////        return super.onCreateView(inflater, container, savedInstanceState);
//
//    }

    //Receiving Team
    //It is best practice that this should be accessed via the main activity, not other fragments.
    public void setPicture(String picture) {
        TextView foodText = (TextView) view.findViewById(R.id.foodText);
        switch (picture) {
            case "Burger":
                view.setBackgroundResource(R.drawable.squareburger);
                foodText.setText(R.string.Burger);
                break;
            case "Salad":
                view.setBackgroundResource(R.drawable.salad);
                foodText.setText(R.string.Salad);
                break;
            case "Pizza":
                view.setBackgroundResource(R.drawable.pizza);
                foodText.setText(R.string.Pizza);
                break;
            case "Hotpot":
                view.setBackgroundResource(R.drawable.hotpot);
                foodText.setText(R.string.Hotpot);
                break;
            case "Spaghetti":
                view.setBackgroundResource(R.drawable.spaghetti);
                foodText.setText(R.string.Spaghetti);
                break;
        }

    }

}
>>>>>>> 246f2d692bd1f39442f4e9f5626fa79be0b0a55b
