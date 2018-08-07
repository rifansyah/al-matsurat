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
    private ImageView prev;
    private ImageView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
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
        prev = (ImageView)findViewById(R.id.prev);
        next = (ImageView)findViewById(R.id.next);

        if (isNight()) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryNight));
        }

        viewPager.setAdapter(adapter);

        surah.setText("Taawudz");
        count.setText("1x");

        prev.setOnClickListener(this);
        next.setOnClickListener(this);

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
            case R.id.prev:
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                break;
            case R.id.next:
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                break;
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        databaseAccess.close();
        finish();
    }

    public void setTheme() {
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        Date twelve = null;
        try {
            twelve = parser.parse("12:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Date userDate = new Date();
            Date now = parser.parse(parser.format(userDate));
            if (now.after(twelve)) {
                setTheme(R.style.OverlayPrimaryColorBlue);
            }
        } catch (ParseException e) {
            // Invalid date was entered
        }
    }

    public boolean isNight() {
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        Date twelve = null;
        try {
            twelve = parser.parse("12:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Date userDate = new Date();
            Date now = parser.parse(parser.format(userDate));
            if (now.after(twelve)) {
                return true;
            }
        } catch (ParseException e) {
            // Invalid date was entered
        }
        return false;
    }
}
