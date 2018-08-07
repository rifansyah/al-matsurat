package com.stud.reef.almatsurat_dhikr;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Dhikr implements Parcelable, Cloneable {
    private String ayah;
    private List<String> translation;

    public Dhikr(String ayah, List<String> translation) {
        this.ayah = ayah;
        this.translation = translation;
    }

    protected Dhikr(Parcel in) {
        ayah = in.readString();
        translation = in.createStringArrayList();
    }

    public static final Creator<Dhikr> CREATOR = new Creator<Dhikr>() {
        @Override
        public Dhikr createFromParcel(Parcel in) {
            return new Dhikr(in);
        }

        @Override
        public Dhikr[] newArray(int size) {
            return new Dhikr[size];
        }
    };

    public String getAyah() {
        return ayah;
    }

    public List<String> getTranslation() {
        return translation;
    }

    @Override
    public Object clone() {
        Parcel parcel = Parcel.obtain();
        this.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();

        Parcel parcel2 = Parcel.obtain();
        parcel2.unmarshall(bytes, 0, bytes.length);
        parcel2.setDataPosition(0);
        return Dhikr.CREATOR.createFromParcel(parcel2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ayah);
        dest.writeStringList(translation);
    }
}
