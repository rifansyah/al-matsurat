package com.stud.reef.almatsurat_dhikr;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView shugro;
    private TextView kubro;
    private TextView quote;
    private ConstraintLayout homeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        shugro = (TextView) findViewById(R.id.shugro);
        kubro = (TextView) findViewById(R.id.kubro);
        quote = (TextView) findViewById(R.id.text_quote);
        homeLayout = (ConstraintLayout) findViewById(R.id.home_layout);

        if (isNight()) {
            homeLayout.setBackgroundResource(R.drawable.background_malam);
        }

        shugro.setOnClickListener(this);
        kubro.setOnClickListener(this);

        randomQuote();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shugro:
                Intent intent = new Intent(this, DhikrActivity.class);
                intent.putExtra("type", "mathurat_sughra");
                startActivity(intent);
                break;
            case R.id.kubro:
                intent = new Intent(this, DhikrActivity.class);
                intent.putExtra("type", "mathurat_kubra");
                startActivity(intent);
                break;
        }
    }

    public void randomQuote() {
        String[] quotes = {"“Dialah yang memberi rahmat kepadamu dan malaikat-Nya (memohonkan ampunan untukmu), supaya Dia mengeluarkan kamu dari kegelapan kepada cahaya (yang terang). Dan adalah Dia Maha Penyayang kepada orang-orang yang beriman” (QS. Al-Ahzab: 42-43).",
        "“Maka bersabarlah kamu, karena sesungguhnya janji Allah itu benar, dan mohonlah ampunan untuk dosamu dan bertasbihlah seraya memuji Tuhanmu pada waktu sore dan pagi” (QS. Ghafir: 55)",
        "“Maka bersabarlah kamu terhadap apa yang mereka katakan dan bertasbihlah sambil memuji Tuhanmu sebelum terbit matahari dan sebelum terbenam(nya)” (QS. Qaf: 39)",
        "Aku duduk bersama orang-orang yang berdzikrullah Ta’ala mulai dari (waktu) sholat shubuh hingga terbit matahari lebih aku cintai daripada memerdekakan empat orang budak dari putra Nabi Isma’il. Dan aku duduk bersama orang-orang yang berdzikrullah mulai dari (waktu) sholat Ashar sampai terbenam matahari lebih aku cintai daripada memerdekakan empat orang budak” (HR. Abu Dawud: 3667, dihasankan oleh Syaikh Al-Albani).",
        "“Maka ingatlah pada-Ku, maka Aku akan mengingat kalian.” (QS. Al Baqarah: 152).",
        "“Dzikir pada hati semisal air yang dibutuhkan ikan. Lihatlah apa yang terjadi jika ikan tersebut lepas dari air?",
        "“Dan janganlah kamu seperti orang-orang yang lupa kepada Allah, lalu Allah menjadikan mereka lupa kepada mereka sendiri. Mereka Itulah orang-orang yang fasik.” (QS. Al Hasyr: 19)",
        "“Sesungguhnya Allah beserta orang-orang yang bertakwa dan orang-orang yang berbuat kebaikan.” (QS. An Nahl: 128)"};

        Random rand = new Random();

        int  n = rand.nextInt(1000);

        int pick = n % quotes.length;
        quote.setText(quotes[pick]);
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
