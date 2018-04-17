package com.example.josh.movie_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Josh on 4/3/2018.
 */

public class AddMovie extends AppCompatActivity {

    private EditText inputTitle;
    private EditText inputCode;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movie);
        inputTitle = findViewById(R.id.newTitle);
        inputCode = findViewById(R.id.newCode);

    }

    public void confirmed(View v){
        String title = inputTitle.getText().toString();
        String code = inputCode.getText().toString();
        String[] newMovie = {title, code};
        Intent data = new Intent();
        data.putExtra("Results", newMovie);
        setResult(RESULT_OK,data);
        finish();
    }




}
