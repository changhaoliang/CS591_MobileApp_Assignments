package com.example.sse.interfragmentcommunication;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */

//This will get inflated up top.
public class ControlFragment extends Fragment {

    private ListView lvMenu;

    public ControlFragment() {  //todo, why?
        // Required empty public constructor
    }

    //*** MESSAGE PASSING MECHANISM ***//
//Need to create an interface to ensure message passing works between fragments.
//This interface, as with all interfaces serves as a contract.  Implementer of this interface, must implement all of its methods.
//Important Fact: Since the MainActivity will implement this, we are guaranteed to find a sendMessage
//routine there!
    public interface ControlFragmentListener {            //this is just an interface definition.
        public void setPicture(String picture); //it could live in its own file.  placed here for convenience.
    }

    ControlFragmentListener CFL;  //Future reference to an object that implements ControlFragmentListener, Can be anything, as long as it implements all interface methods.
    //Question: Who holds the reference?  Answer: ____________
//*** MESSAGE PASSING MECHANISM ***//


    //onAttach gets called when fragment attaches to Main Activity.  This is the right time to instantiate
    //our ControlFragmentListener, why?  Because we know the Main Activity was successfully created and hooked.
//    @Override
//    public void onAttach(Context context) {   //The onAttach method, binds the fragment to the owner.  Fragments are hosted by Activities, therefore, context refers to: ____________?
//        super.onAttach(context);
//        CFL = (ControlFragmentListener) context;  //context is a handle to the main activity, let's bind it to our interface.
//    }


    //NOTE:
//This old onAttach, still works, but is deprecated,
//better to use the newer one above, which passes a context object, which can also be typecast into an Activity Object.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        CFL = (ControlFragmentListener) activity;
    }


    //onCreateView, called to have the fragment instantiate it's GUI.
//this is when it is "safe" to generate references to UI components,
//they are guaranteed to exist.  DO NOT interact with UI components
//during onCreate of a fragment, they "may not" be ready.
//happens in between onCreate(..) and onActivityCreated(..)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_control, container, false);  //this needs to be separated from return statement,

//IMPORTANT: MUST BREAK APART THE default return statement IF THERE ARE
// ADDITIONAL VIEWS THAT NEED REFERENCES AND EVENT HANDLERS.
//WHY? So we can refer to the views objects before passing view to Activity.
//we need to bind our views to references and create event handlers before
//before returning the inflated fragment.
//this is why we had to break apart the initial return statement.

        //1. get the reference to your ListView
        lvMenu = (ListView) view.findViewById(R.id.lvMenu);

        //2. Create an Adapter to bind to your ListView.
        final String[] Animals = {"Burger", "Salad", "Pizza", "Hotpot", "Spaghetti"};  //array of strings to put into our ListAdapter.
        ArrayAdapter AnimalListAdapter = new ArrayAdapter<String>(getActivity(),           //Context
                android.R.layout.simple_list_item_1, //type of list (simple)
                Animals);                            //Data for the list
        //We will see much more complex Adapters as we go.
        //3. ListViews work (display items) by binding themselves to an adapter.
        lvMenu.setAdapter(AnimalListAdapter);    //Let's put some things in our simple listview by binding it to our adaptor.

        // 4. Create an onClick Handler.  Not for the ListView, but for its items!
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String food;
                food = String.valueOf(parent.getItemAtPosition(position));  //Parent refers to the parent of the item, the ListView.  position is the index of the item clicked.
                CFL.setPicture(food);
            }
        });
        return view;
    }

}
