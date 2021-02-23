package com.example.myapplication.roomdatabase;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "Locations")
public class LocationModel implements Serializable {
    @ColumnInfo(name = "place")
    private String place;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "rate")
    private String rate;


    @ColumnInfo(name = "description")
    private String description;


    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "idAuto")
    private int idAuto;


    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }
}