package com.example.course.easylease;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {


    private ArrayList<House>  houseList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        HouseAdapter adapter = new HouseAdapter();
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        houseList= (ArrayList) getIntent().getExtras().get("houses");

    }


    private class HouseAdapter extends ArrayAdapter<House> {
        public HouseAdapter() {
            super(ResultActivity.this, R.layout.list_row, houseList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View itemView = convertView;
            if(itemView != null){
                itemView = getLayoutInflater().inflate(R.layout.list_row,parent, false);
            }
            //TODO: more code need to be filled in

            return itemView;
        }
    }
}
