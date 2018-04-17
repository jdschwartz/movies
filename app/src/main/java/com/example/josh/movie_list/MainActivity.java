package com.example.josh.movie_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private static final String[] movies = {"JAWS", "Airplane!", "Raiders of the Lost Ark", "Ghostbusters", "Groundhog Day", "Redline"};
    String[] codes = {"tt0073195/", "tt0080339/", "tt0082971/", "tt0087332/", "tt0107048/", "tt1483797/"};
    private ArrayList<String> movieTitles;
    private ArrayList<String> movieCodes;
    int i1;
    private SharedPreferences p;
    private Map<String, ?> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p = getPreferences(Context.MODE_PRIVATE);
        map = p.getAll();

        listView = findViewById(R.id.ListView);
        movieTitles = new ArrayList<String>();
        movieCodes = new ArrayList<String>();
        if(map.isEmpty()) {
            for (int i = 0; i < movies.length; i++) {
                movieTitles.add(movies[i]);
            }
            for (int i = 0; i < codes.length; i++) {
                movieCodes.add(codes[i]);
            }
        }else {
            for (Map.Entry entry : map.entrySet()) {
                movieTitles.add(entry.getKey().toString());
                movieCodes.add(entry.getValue().toString());
            }
        }
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_view, movieTitles);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String url = "https://www.imdb.com/title/" + codes[i];
                        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(in);
                    }
                }

        );
        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        i1 = i;
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Deletion");
                        builder.setMessage("Delete Item?");
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                movieTitles.remove(i1);
                                movieCodes.remove(i1);
                                ArrayAdapter<String> adapter;
                                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_view, movieTitles);
                                listView.setAdapter(adapter);

                            }
                        });

                        AlertDialog dialog1 = builder.create();
                        dialog1.show();
                        return true;
                    }
                }
        );

    }

    public void moviePressed(View v){
        Intent i = new Intent(this, AddMovie.class);
        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK) {
            String[] newData = data.getStringArrayExtra("Results");
            movieTitles.add(newData[0]);
            movieCodes.add(newData[1]);
            ArrayAdapter<String> adapter;
            adapter = new ArrayAdapter<String>(this, R.layout.list_item_view, movieTitles);
            listView.setAdapter(adapter);
        }

    }
    @Override
    public void onStop(){
        super.onStop();
        SharedPreferences.Editor editor = p.edit();
        for(int i = 0; i < movieTitles.size(); i++){
            editor.putString(movieTitles.get(i), movieCodes.get(i));

        }
        editor.apply();
    }


}
