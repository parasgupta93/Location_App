package com.example.myapplication.roomdatabase;

import android.content.Context;
import androidx.room.Room;
import com.example.myapplication.model.LocationStructure;
import java.util.ArrayList;
import java.util.List;

public class LocationRepository {


    public interface LocationRepositoryListener {
        void onLocalRepoData(ArrayList<LocationStructure> list);

        void pnLocalRePoFail(String msg);
    }


    private String DB_NAME = "db_task";
    private LocationRepositoryListener listener;
    private static Appdatabase database;

    public LocationRepository(Context context, LocationRepositoryListener listener) {
        database = Room.databaseBuilder(context, Appdatabase.class, DB_NAME).build();
        this.listener = listener;
    }

    public void insertItem(final ArrayList<LocationStructure> mLocation) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.getLocationDao().clearData();
                for (int i = 0; i < mLocation.size(); i++) {
                    LocationStructure location = mLocation.get(i);
                    LocationModel model = new LocationModel();
                    model.setPlace(location.getPlace());
                    model.setDate(location.getDate());
                    model.setUrl(location.getUrl());
                    model.setRate(location.getRate());
                    model.setDescription(location.getDescription());
                    database.getLocationDao().insert(model);
                }
            }
        }).start();
    }

    public void getItem() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<LocationModel> modelist = database.getLocationDao().loadAllItem();
                final ArrayList<LocationStructure> structuresList = new ArrayList<LocationStructure>();
                for (int i = 0; i < modelist.size(); i++) {
                    LocationModel model = modelist.get(i);
                    LocationStructure structures = new LocationStructure();
                    structures.setPlace(model.getPlace());
                    structures.setDate(model.getDate());
                    structures.setUrl(model.getUrl());
                    structures.setRate(model.getRate());
                    structures.setDescription(model.getDescription());
                    structuresList.add(structures);
                }

                if (listener != null) {
                    if (structuresList.size() > 0)
                        listener.onLocalRepoData(structuresList);
                    else
                        listener.pnLocalRePoFail("No Data in Local Repo");
                }
            }
        }).start();
    }

}