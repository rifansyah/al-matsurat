package com.stud.reef.almatsurat_dhikr;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DhikrActivity extends AppCompatActivity implements View.OnClickListener {
    private DhikrPagerAdapter adapter;
    private ViewPager viewPager;
    private TextView surah;
    private TextView count;
    private DatabaseAccess databaseAccess;
    private LinearLayout toolbar;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhikr);

        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
        }

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        final List<Dhikr> dhikr = databaseAccess.getData(type);
        dhikr.add(new Dhikr("",new ArrayList<String>()));


        adapter = new DhikrPagerAdapter(getSupportFragmentManager(), dhikr);

        toolbar = (LinearLayout)findViewById(R.id.toolbar);
        viewPager = (ViewPager)findViewById(R.id.pager);
        surah = (TextView)findViewById(R.id.surah);
        count = (TextView)findViewById(R.id.count);

        viewPager.setAdapter(adapter);

        surah.setText("Taawudz");
        count.setText("1x");

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position != dhikr.size() - 1) {
                    toolbar.setVisibility(View.VISIBLE);
                    surah.setText(databaseAccess.getSurahName(position + 1, type));
                    count.setText(Integer.toString(databaseAccess.getSurahCount(position + 1, type)) + "x");
                } else {
                    toolbar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    protected void onDestroy(){
        super.onDestroy();
        databaseAccess.close();
        finish();
    }
}
