package com.example.sse.interfragmentcommratingbar;


import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawableFragment extends Fragment {

    //ArrayList<Drawable> drawables;  //keeping track of our drawables
    ArrayList<Picture> pictures;  //keeping track of our drawables
    private int currDrawableIndex;  //keeping track of which drawable is currently displayed.

    //Boiler Plate Stuff.
    private ImageView imgRateMe;

    private RatingBar ratingBar;

//    public DrawableFragment() {
//        // Required empty public constructor
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_drawable, container, false);  //comment this out, it would return the default view, without our setup/amendments.
        View v = inflater.inflate(R.layout.fragment_drawable, container, false);   //MUST HAPPEN FIRST, otherwise components don't exist.

        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        imgRateMe = (ImageView) v.findViewById(R.id.imgRateMe);


        currDrawableIndex = 0;  //ArrayList Index of Current Drawable.
        getDrawables();         //Retrieves the drawables we want, ie, prefixed with "animal_"
        imgRateMe.setImageDrawable(null);  //Clearing out the default image from design time.
        changePicture();        //Sets the ImageView to the first drawable in the list.


//setting up navigation call backs.  (Left and Right Buttons)


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (b) {
                    pictures.get(currDrawableIndex).setRating(v);
                }
            }
        });

        return v;   //returns the view, with our must happen last, Why? A: ____________
    }

    public void updatePicture(int index) {
        currDrawableIndex = index;

        if (currDrawableIndex <= 0) {
            currDrawableIndex = pictures.size() - 1;
        } else if (currDrawableIndex >= pictures.size() - 1) {
            currDrawableIndex = 0;
        }
        changePicture();
    }

    //Routine to change the picture in the image view dynamically.
    public void changePicture() {

        imgRateMe.setImageDrawable(pictures.get(currDrawableIndex).getImage());  //note, this is the preferred way of changing images, don't worry about parent viewgroup size changes.
        ratingBar.setRating(pictures.get(currDrawableIndex).getRating());
    }

    //Quick and Dirty way to get drawable resources, we prefix with "animal_" to filter out just the ones we want to display.
//REF: http://stackoverflow.com/questions/31921927/how-to-get-all-drawable-resources
    public void getDrawables() {
        Field[] drawablesFields = com.example.sse.interfragmentcommratingbar.R.drawable.class.getFields();  //getting array of ALL drawables.
        pictures = new ArrayList<Picture>();  //we prefer an ArrayList, to store the drawables we are interested in.  Why ArrayList and not an Array here? A: _________

        String fieldName;
        System.out.println(drawablesFields.length);
        System.out.println("=====");
        for (Field field : drawablesFields) {   //1. Looping over the Array of All Drawables...
            try {
                System.out.println(field.toString());
                fieldName = field.getName();    //2. Identifying the Drawables Name, eg, "animal_bewildered_monkey"

//                Log.i("LOG_TAG", "com.your.project.R.drawable." + fieldName);
                if (fieldName.startsWith("animals_")) { //3. Adding drawable resources that have our prefix, specifically "animal_".
                    Picture p = new Picture(getResources().getDrawable(field.getInt(null)), 0);
                    pictures.add(p);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Picture p : pictures) {
            System.out.println(p.toString());
        }
    }
}










