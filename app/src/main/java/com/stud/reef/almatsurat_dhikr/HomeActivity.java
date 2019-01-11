package com.stud.reef.almatsurat_dhikr;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView shugro;
    private CardView kubro;
    private CardView about;
    private ConstraintLayout homeLayout;

    Dialog aboutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        shugro = (CardView) findViewById(R.id.shugro_cardView);
        kubro = (CardView) findViewById(R.id.kubro_cardView);
        about = (CardView) findViewById(R.id.about_cardView);
        homeLayout = (ConstraintLayout) findViewById(R.id.home_layout);

        shugro.setOnClickListener(this);
        kubro.setOnClickListener(this);
        about.setOnClickListener(this);

        aboutDialog = new Dialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shugro_cardView:
                Intent intent = new Intent(this, DhikrActivity.class);
                intent.putExtra("type", "mathurat_sughra");
                startActivity(intent);
                break;
            case R.id.kubro_cardView:
                intent = new Intent(this, DhikrActivity.class);
                intent.putExtra("type", "mathurat_kubra");
                startActivity(intent);
                break;
            case R.id.about_cardView:
                showPopup();
                break;
        }
    }

    public void showPopup() {
        aboutDialog.setContentView(R.layout.about_dialog);

        aboutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        aboutDialog.show();
    }
}
