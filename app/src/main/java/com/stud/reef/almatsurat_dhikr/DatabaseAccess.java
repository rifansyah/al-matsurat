package com.stud.reef.almatsurat_dhikr;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public int getPageNum(String type) {
        int pageNum = 0;
        String query = String.format("SELECT COUNT(DISTINCT page) FROM %s;", type);
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        pageNum = cursor.getInt(0);
        cursor.close();
        return pageNum;
    }

    public List<Dhikr> getData(String type) {
        int num = getPageNum(type);
        List<Dhikr> data = new ArrayList<>();
        for(int i = 1; i <= num; i++) {
            data.add(getDataOfPage(i, type));
        }
        return data;
    }

    public String getSurahName(int position, String type) {
        String name = "";
        String query = String.format("SELECT name FROM %s WHERE page = %d;", type, position);
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        name = cursor.getString(0);
        cursor.close();
        return name;
    }

    public int getSurahCount(int position, String type) {
        int pageCount = 0;
        String query = String.format("SELECT repeat_count FROM %s WHERE page = %d;", type, position);
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        pageCount = cursor.getInt(0);
        cursor.close();
        return pageCount;
    }

    public Dhikr getDataOfPage(int page, String type) {
        List<String> ayahList = new ArrayList<>();
        List<String> translationList = new ArrayList<>();
        String query = "";

        if (type.equalsIgnoreCase("mathurat_sughra")) {
            query = String.format("SELECT * FROM mathurat_sughra ms left join translation t ON ms.id = t.mathurat_sughra_id WHERE page = %d;", page);
        } else {
            query = String.format("SELECT * FROM mathurat_kubra ms left join translation_kubra t ON ms.id = t.mathurat_kubra_id WHERE page = %d;", page);
        }

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String ayahTemp = cursor.getString(4);
//            String ayahTemp = "لَآ إِكۡرَاهَ فِي ٱلدِّينِۖ قَد تَّبَيَّنَ ٱلرُّشۡدُ مِنَ ٱلۡغَيِّۚ فَمَن يَكۡفُرۡ بِٱلطَّٰغُوتِ وَيُؤۡمِنۢ بِٱللَّهِ فَقَدِ ٱسۡتَمۡسَكَ بِٱلۡعُرۡوَةِ ٱلۡوُثۡقَىٰ لَا ٱنفِصَامَ لَهَاۗ وَٱللَّهُ سَمِيعٌ عَلِيمٌ ٢٦٥";
            String translationTemp = cursor.getString(10);
            ayahList.add(ayahTemp);

            if(translationTemp != null) {
                translationList.add(translationTemp);
            } else {
                translationList.add("");
            }

            cursor.moveToNext();
        }
        cursor.close();

        String ayah = TextUtils.join(" ", ayahList);
        Dhikr dhikr = new Dhikr(ayah, translationList);
        return dhikr;
    }
}
