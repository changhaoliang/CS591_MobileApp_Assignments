package com.example.sse.customlistview_sse;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Rating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import java.io.IOException;
import java.lang.reflect.Field;
import java.security.spec.ECPoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Step-By-Step, Setting up the ListView

    ListView lvEpisodes;     //Reference to the listview GUI component
    ListAdapter lvAdapter;   //Reference to the Adapter used to populate the listview.

    HashMap<String,Episode> epsiodes_map;
    ArrayList<Episode> episodes;
    boolean showSelected;
    boolean byTitle;
    boolean byRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvEpisodes = (ListView) findViewById(R.id.lvEpisodes);
        showSelected = false;
        byTitle = true;
        byRating = true;
        episodes = getEpisodes();
        epsiodes_map = getEpisodesMap();
        retrieveSharedPreferenceInfo();

        lvAdapter = new MyCustomAdapter(this.getBaseContext(), episodes);  //instead of passing the boring default string adapter, let's pass our own, see class MyCustomAdapter below!
        lvEpisodes.setAdapter(lvAdapter);
    }


    public void saveSharedPreferenceInfo() {
        SharedPreferences info = getSharedPreferences("ActivityInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = info.edit();

        //String[] titles = getApplication().getResources().getStringArray(R.array.episodes);
        System.out.println("-------Saving ---------");
        for(int i = 0; i < episodes.size();i++){
            editor.putFloat(episodes.get(i).getTitle(), episodes.get(i).getRating());
            String msg = episodes.get(i).getTitle()+", Rating"+ Float.toString(episodes.get(i).getRating());
            Log.e("save",msg);
            //System.out.printf("%s, Rating  %d \n",episodes.get(i).getTitle(),  episodes.get(i).getRating());
        }

        editor.apply();
    }

    public void retrieveSharedPreferenceInfo() {
        SharedPreferences info = getSharedPreferences("ActivityInfo", Context.MODE_PRIVATE);
        String[] titles = getApplication().getResources().getStringArray(R.array.episodes);
        System.out.println("---------Retrieve ---------");
        for(int i = 0; i < episodes.size(); i++) {
            Float fRating = info.getFloat(titles[i], 0);
            epsiodes_map.get(titles[i]).setRating(fRating);
            String msg = titles[i]+", Rating"+ Float.toString(epsiodes_map.get(titles[i]).getRating());
            Log.e("Retrieve",msg);
            //System.out.printf("%s, Rating  %d \n",titles[i], epsiodes_map.get(titles[i]).getRating());
        }
    }

    @Override
    protected void onDestroy() {
        saveSharedPreferenceInfo();
        super.onDestroy();
    }

    public HashMap<String,Episode>  getEpisodesMap() {
        HashMap<String,Episode> epsiodes_map = new HashMap<>();
        String[] titles = getApplication().getResources().getStringArray(R.array.episodes);

        for(int i = 0; i < episodes.size(); i++) {

            epsiodes_map.put(episodes.get(i).getTitle(),episodes.get(i));
        }

        return epsiodes_map;
    }



    public ArrayList<Episode> getEpisodes() {
        ArrayList<Episode> episodes = new ArrayList<>();

        String[] titles = getApplication().getResources().getStringArray(R.array.episodes);
        String[] descriptions = getApplication().getResources().getStringArray(R.array.episode_descriptions);
        ArrayList<Integer> episodeImages = new ArrayList<>();   //Could also use helper function "getDrawables(..)" below to auto-extract drawable resources, but keeping things as simple as possible.
        episodeImages.add(R.drawable.st_spocks_brain);
        episodeImages.add(R.drawable.st_arena__kirk_gorn);
        episodeImages.add(R.drawable.st_this_side_of_paradise__spock_in_love);
        episodeImages.add(R.drawable.st_mirror_mirror__evil_spock_and_good_kirk);
        episodeImages.add(R.drawable.st_platos_stepchildren__kirk_spock);
        episodeImages.add(R.drawable.st_the_naked_time__sulu_sword);
        episodeImages.add(R.drawable.st_the_trouble_with_tribbles__kirk_tribbles);

        for (int i = 0; i < titles.length; i++) {
            Episode episode = new Episode(titles[i], descriptions[i], episodeImages.get(i), 3);
            episodes.add(episode);
        }

        return episodes;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);   //get rid of default behavior.

        // Inflate the menu; this adds items to the action bar
        getMenuInflater().inflate(R.menu.my_test_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.mnu_zero) {
//            Toast.makeText(getBaseContext(), "Menu Zero.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.mnu_one) {
            fourStarsOrMore();
            return true;
        }

        if (id == R.id.mnu_two) {
            sortByTitle();
            return true;
        }

        if (id == R.id.mnu_three) {
            sortByRating();
            return true;
        }


        return super.onOptionsItemSelected(item);  //if none of the above are true, do the default and return a boolean.
    }

    public void fourStarsOrMore(){
        if (!showSelected){
            ArrayList<Episode> highStars = new ArrayList<Episode>();
            for (int i=0; i<episodes.size(); i++){
                if (episodes.get(i).getRating() >= 4)
                    highStars.add(episodes.get(i));
            }
            lvEpisodes.setAdapter(new MyCustomAdapter(this.getBaseContext(), highStars));
        }
        else
            lvEpisodes.setAdapter(lvAdapter);
        showSelected = !showSelected;
    }

    public void sortByTitle() {
        Collections.sort(episodes, new Comparator<Episode>() {
            @Override
            public int compare(Episode t1, Episode t2) {
                if (byTitle)
                    return t1.getTitle().compareTo(t2.getTitle());
                else
                    return -t1.getTitle().compareTo(t2.getTitle());
            }
        });
        lvEpisodes.setAdapter(lvAdapter);
        byTitle = !byTitle;
    }

    public void sortByRating() {
        Collections.sort(episodes, new Comparator<Episode>() {
            @Override
            public int compare(Episode t1, Episode t2) {
                if (byRating)
                    return (int) (t2.getRating() - t1.getRating());
                else
                    return (int) (t1.getRating() - t2.getRating());
            }
        });
        lvEpisodes.setAdapter(lvAdapter);
        byRating = !byRating;
    }

}


//***************************************************************//
//create a  class that extends BaseAdapter
//Hit Alt-Ins to easily implement required BaseAdapter methods.
//***************************************************************//
//
//class m2 extends BaseAdapter{
//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        return null;
//    }
//}

//STEP 1: Create references to needed resources for the ListView Object.  String Arrays, Images, etc.

class MyCustomAdapter extends BaseAdapter {

    private
//    String episodes[];             //Keeping it simple.  Using Parallel arrays is the introductory way to store the List data.
//    String episodeDescriptions[];  //the "better" way is to encapsulate the list items into an object, then create an arraylist of objects.
//    //     int episodeImages[];         //this approach is fine for now.
//    ArrayList<Integer> episodeImages;  //Well, we can use one arrayList too...  Just mixing it up, Arrays or Templated ArrayLists, you choose.

    ArrayList<Episode> episodes;

//    ArrayList<String> episodes;
//    ArrayList<String> episodeDescriptions;

    Context context;   //Creating a reference to our context object, so we only have to get it once.  Context enables access to application specific resources.
    // Eg, spawning & receiving intents, locating the various managers.

    //STEP 2: Override the Constructor, be sure to:
    // grab the context, we will need it later, the callback gets it as a parm.
    // load the strings and images into object references.
    public MyCustomAdapter(Context aContext, ArrayList<Episode> episodes) {
//initializing our data in the constructor.
        context = aContext;  //saving the context we'll need it again.
        this.episodes = episodes;

//        episodes = aContext.getResources().getStringArray(R.array.episodes);  //retrieving list of episodes predefined in strings-array "episodes" in strings.xml
//        episodeDescriptions = aContext.getResources().getStringArray(R.array.episode_descriptions);
//This is how you would do it if you were using an ArrayList, leaving code here for reference, though we could use it instead of the above.
//        episodes = (ArrayList<String>) Arrays.asList(aContext.getResources().getStringArray(R.array.episodes));  //retrieving list of episodes predefined in strings-array "episodes" in strings.xml
//        episodeDescriptions = (ArrayList<String>) Arrays.asList(aContext.getResources().getStringArray(R.array.episode_descriptions));  //Also casting to a friendly ArrayList.


//        episodeImages = new ArrayList<Integer>();   //Could also use helper function "getDrawables(..)" below to auto-extract drawable resources, but keeping things as simple as possible.
//        episodeImages.add(R.drawable.st_spocks_brain);
//        episodeImages.add(R.drawable.st_arena__kirk_gorn);
//        episodeImages.add(R.drawable.st_this_side_of_paradise__spock_in_love);
//        episodeImages.add(R.drawable.st_mirror_mirror__evil_spock_and_good_kirk);
//        episodeImages.add(R.drawable.st_platos_stepchildren__kirk_spock);
//        episodeImages.add(R.drawable.st_the_naked_time__sulu_sword);
//        episodeImages.add(R.drawable.st_the_trouble_with_tribbles__kirk_tribbles);
    }

    //STEP 3: Override and implement getCount(..), ListView uses this to determine how many rows to render.
    @Override
    public int getCount() {
//        return episodes.size(); //all of the arrays are same length, so return length of any... ick!  But ok for now. :)
        return episodes.size();   //all of the arrays are same length, so return length of any... ick!  But ok for now. :)
        //Q: How else could we have done this (better)? ________________
    }

    //STEP 4: Override getItem/getItemId, we aren't using these, but we must override anyway.
    @Override
    public Object getItem(int position) {
//        return episodes.get(position);  //In Case you want to use an ArrayList
        return episodes.get(position);        //really should be returning entire set of row data, but it's up to us, and we aren't using this call.
    }

    @Override
    public long getItemId(int position) {
        return position;  //Another call we aren't using, but have to do something since we had to implement (base is abstract).
    }

    //THIS IS WHERE THE ACTION HAPPENS.  getView(..) is how each row gets rendered.
//STEP 5: Easy as A-B-C
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {  //convertView is Row (it may be null), parent is the layout that has the row Views.

//STEP 5a: Inflate the listview row based on the xml.
        final View row;  //this will refer to the row to be inflated or displayed if it's already been displayed. (listview_row.xml)
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        row = inflater.inflate(R.layout.listview_row, parent, false);  //

// Let's optimize a bit by checking to see if we need to inflate, or if it's already been inflated...
        if (convertView == null) {  //indicates this is the first time we are creating this row.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  //Inflater's are awesome, they convert xml to Java Objects!
            row = inflater.inflate(R.layout.listview_row, parent, false);
        } else {
            row = convertView;
        }


//STEP 5b: Now that we have a valid row startActivity(intent);                                                                                    instance, we need to get references to the views within that row and fill with the appropriate text and images.
        ImageView imgEpisode = (ImageView) row.findViewById(R.id.imgEpisode);  //Q: Notice we prefixed findViewByID with row, why?  A: Row, is the container.
        TextView tvEpisodeTitle = (TextView) row.findViewById(R.id.tvEpisodeTitle);
        TextView tvEpisodeDescription = (TextView) row.findViewById(R.id.tvEpisodeDescription);
        final RatingBar rbEpisode = (RatingBar) row.findViewById(R.id.rbEpisode);
        Button btnRandom;

        tvEpisodeTitle.setText(episodes.get(position).getTitle());
        tvEpisodeDescription.setText(episodes.get(position).getDescription());
        imgEpisode.setImageResource(episodes.get(position).getImage());
        btnRandom = (Button) row.findViewById(R.id.btnRandom);
        rbEpisode.setRating(episodes.get(position).getRating());

        final String randomMsg = ((Integer) position).toString() + ": " + episodes.get(position).getDescription();
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, randomMsg, Toast.LENGTH_LONG).show();
            }
        });

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = episodes.get(position).getTitle();
                Intent intent = new Intent(context, WebActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("message", title);

                context.startActivity(intent);
            }
        });


        final Episode curEpisode = episodes.get(position);
        rbEpisode.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (b) {
                    curEpisode.setRating(v);
                }
            }
        });


//STEP 5c: That's it, the row has been inflated and filled with data, return it.
        return row;  //once the row is fully constructed, return it.  Hey whatif we had buttons, can we target onClick Events within the rows, yep!
//return convertView;

    }

    ///Helper method to get the drawables...///
    ///this might prove useful later...///

//    public ArrayList<Drawable> getDrawables() {
//        Field[] drawablesFields =com.example.sse.customlistview_sse.R.drawable.class.getFields();
//        ArrayList<Drawable> drawables = new ArrayList<Drawable>();
//
//        String fieldName;
//        for (Field field : drawablesFields) {
//            try {
//                fieldName = field.getName();
//                Log.i("LOG_TAG", "com.your.project.R.drawable." + fieldName);
//                if (fieldName.startsWith("animals_"))  //only add drawable resources that have our prefix.
//                    drawables.add(context.getResources().getDrawable(field.getInt(null)));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}