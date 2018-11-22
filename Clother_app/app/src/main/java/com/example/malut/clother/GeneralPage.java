package com.example.malut.clother;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.malut.clother.FontSet.TypefaceUtil;

public class GeneralPage extends AppCompatActivity {

    private ImageButton secondPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_general_page);
        TypefaceUtil.overrideFont(getApplicationContext(), "SANS_SERIF", "fonts/anaheim.ttf");

        secondPage = findViewById(R.id.movePage);
        secondPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralPage.this, MainActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.bottom_in,R.anim.top_out);
            }
        });

    }
}
