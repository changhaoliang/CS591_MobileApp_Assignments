package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button buttonNext, buttonPrev;
    ArrayList<String> result;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = new ArrayList<>();
        textView = (TextView) findViewById(R.id.textView);
        buttonNext = (Button) findViewById(R.id.next);
        buttonPrev = (Button) findViewById(R.id.previous);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = firebaseDatabase.getReference("message");
        final ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 1; i <= 5; i++) {
            arrayList.add("This is text " + i);
        }
        myRef.setValue(arrayList);
        textView.setText(arrayList.get(0));
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count == 5) {
                    count = 0;
                }
                textView.setText(result.get(count));
            }
        });
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                if (count == -1) {
                    count = 4;
                }
                textView.setText(result.get(count));
            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                result = (ArrayList<String>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
