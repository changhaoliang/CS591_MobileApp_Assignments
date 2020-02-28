package com.example.sse.interfragmentcommratingbar;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class NavigateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private Button btnLeft;
    private Button btnRight;

    private int currDrawableIndex;
    private NavifateFragmentListner navifateFragmentListner;

    public NavigateFragment() {
        // Required empty public constructor
    }

    public interface NavifateFragmentListner {
        public void updateIndex(int index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_navigate, container, false);

        btnRight = (Button) v.findViewById(R.id.btnRight);
        btnLeft = (Button) v.findViewById(R.id.btnLeft);

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currDrawableIndex--;
                navifateFragmentListner.updateIndex(currDrawableIndex);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currDrawableIndex++;
                navifateFragmentListner.updateIndex(currDrawableIndex);
            }
        });

        return v;
    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    public void onAttach(Activity activity) {
        super.onAttach(getActivity());
        navifateFragmentListner = (NavifateFragmentListner) activity;
    }
}
