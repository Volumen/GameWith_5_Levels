package com.example.endproject.levelTwo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.endproject.DatabaseHelper;
import com.example.endproject.R;

import java.util.ArrayList;

public class levelTwoLeaderboard extends Activity {

    private static final String TAG = "Nickname: ";

    DatabaseHelperTwo mDatabaseHelper;

    private ListView mListView;
    Button button;

    SharedPreferences sharedPreferences;
    String nickname ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lederboardtwo_activity);

        mListView = findViewById(R.id.listViewTwo);
        mDatabaseHelper = new DatabaseHelperTwo(this);


        button = findViewById(R.id.backButtonTwo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        populateListView();
        sharedPreferences = this.getSharedPreferences("A", Context.MODE_PRIVATE);
        nickname = sharedPreferences.getString("Nickname","");
        //Log.d(TAG,"LederNickaname: "+ nickname);
    }
    @Override
    public void onStop()
    {
        super.onStop();
    }
    private void populateListView() {

        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();

        listData.add(mDatabaseHelper.COL1+" | "+ mDatabaseHelper.COL2);
        while(data.moveToNext()){
            //add it to the ArrayList
            listData.add(data.getString(0)+"   |   "+data.getString(1));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
    }
}

