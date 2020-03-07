package com.example.sse.fragmenttransactions_sse;

//import android.app.Activity;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

//Step-By-Step, Fragment Transactions

    // Two basic ways of working with fragments.
    //
    // 1. Just include them in the Activity's layout.
    //
    // 2. Instantatiate and work with them in code.
    // in code you have much more control.

    // 3. create objects to reference the views, including fragments.
    private
    Frag_One f1;
    Frag_Two f2;
    Frag_Three f3;

    FragmentManager fm;  // we will need this later.

    private Button btnFrag1;
    private Button btnFrag2;
    private Button btnFrag3;
    private LinearLayout FragLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 4. get references for our views.
        btnFrag1 = (Button) findViewById(R.id.btnFrag1);
        btnFrag2 = (Button) findViewById(R.id.btnFrag2);
        btnFrag3 = (Button) findViewById(R.id.btnFrag3);
        FragLayout = (LinearLayout) findViewById(R.id.FragLayout);

        /*
         * f1 = (Frag_One) findViewById(R.id.frag1);
         * Q: Why won't this work for fragments?  Does the fragment even exist in R.java?
         * A: Because frag1 is not existed in R.java. Currently, the lifecycle is at MainActivity.onCreate and is before Fragment.onAttach.
         */

        // 5a.  We actually have to create the fragments ourselves.  We left R behind when we took control of rendering.
        f1 = new Frag_One();
        f2 = new Frag_Two();
        f3 = new Frag_Three();

        /*
         * 5b. Grab a reference to the Activity's Fragment Manager, Every Activity has one!
         * fm = getSupportFragmentManager();
         * Q: When would you use this instead??
         * A: In higher API such as API 28, getFragmentManager() is deprecated, android.support.v4.app.Fragment will replace android.app.Fragment
         */
        fm = getFragmentManager();  //that was easy.

        // 5c. Now we can "plop" fragment(s) into our container.
        FragmentTransaction ft = fm.beginTransaction();  //Create a reference to a fragment transaction.
        ft.add(R.id.FragLayout, f1, "tag1");  //now we have added our fragement to our Activity programmatically.  The other fragments exist, but have not been added yet.

        /*
         * Q: why do we do this?
         * A: To remeber the transaction in back stack
         */
        ft.addToBackStack("myFrag1");
        ft.commit();  //don't forget to commit your changes.  It is a transaction after all.

        btnFrag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrag1();
            }
        });

        btnFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrag2();
            }
        });

        btnFrag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrag3();
            }
        });

    }

    public void showFrag1() {
        f1 = (Frag_One) fm.findFragmentByTag("tag1");
        /*
         * Q: what should we do if f1 doesn't exist anymore?  How do we check and how do we fix?
         * A: if f1 doesn't exist anymore, if (f1 == null) f1 = new FragOne();
         */
        FragmentTransaction ft = fm.beginTransaction();  //Create a reference to a fragment transaction.

        if (!f1.isAdded()) {
            ft.add(R.id.FragLayout, f1, "tag1");
        }
        if (f1.isDetached()) {
            ft.attach(f1);
        }
        ft.hide(f2);
        ft.hide(f3);

        /*
         * Q: why does this not *always* crash?
         * A: If f1 is not in view tree, we need to add it back and show.
         *    For example, it f1 is detached, its lifecycle will be at onDestroyView(), if then show(), it will crash.
         *    If f1 is already added in view tree, show() will not crash.
         */
        ft.show(f1);
        ft.addToBackStack("myFrag1");

        ft.commit();
    }

    public void showFrag2() {
        if (f2 == null)
            f2 = new Frag_Two();

        FragmentTransaction ft = fm.beginTransaction();  //Create a reference to a fragment transaction and start the transaction.
        if (!f2.isAdded()) {
            ft.replace(R.id.FragLayout, f2);
        }
        if (f2.isDetached()) {
            ft.attach(f2);
        }
        ft.hide(f1);
        ft.show(f2);

        /*
         * Q: What is the back stack and why do we do this?
         * A: Back Stack will record transactions after it is committed, and will reverse its operation when later popped off the stack in onBackPresses().
         */
        ft.addToBackStack("myFrag2");
        ft.commit();
    }


    public void showFrag3() {
        FragmentTransaction ft = fm.beginTransaction();  //Create a reference to a fragment transaction.
        if (f1.isAdded()) {
            /*
             * Q: what would happen if f1, f2, or f3 were null?  how would we check and fix this?
             * A: If it is null, we just ignore them because we only want to show fragment3 here. We will handle null situation in itself corresponding function.
             *    Besides, we cannot detach an fragment not in view tree, so we check if f1 is added into view tree, if it is, detach it, it not, ignore.
             */
            ft.detach(f1);
        }
        if (f2.isAdded()) {
            ft.detach(f2);
        }
        if (!f3.isAdded()) {
            ft.add(R.id.FragLayout, f3, "tag3");
        }
        ft.attach(f3);
        ft.show(f3);

        ft.addToBackStack("myFrag3");
        ft.commit();
    }

    /**
     Press back button to get previous fragment
     */
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() <= 0)
            finish();
        else
            getFragmentManager().popBackStack();
    }
}