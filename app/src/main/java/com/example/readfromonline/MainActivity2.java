package com.example.readfromonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity2 extends AppCompatActivity {

    //private TextView passed_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent newnew = getIntent();
        String FinalString = getString("text");
        TextView Final = (TextView) findViewById(R.id.FinalSelection);
        Final.setText(FinalString);

    }

    private String getString(String final_text) {
        return final_text;
    }


}