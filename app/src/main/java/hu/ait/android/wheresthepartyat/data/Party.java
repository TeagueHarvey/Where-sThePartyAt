package hu.ait.android.wheresthepartyat.data;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by teagu_000 on 7/12/2017.
 */

public class Party {
    private String uid;
    private String name;
    private String description;
    private boolean byob;
    private boolean bar;
    private MyLatLng latLng;




    public Party(){
    }

    public Party(String uid, String name, String description, boolean byob, boolean bar, LatLng latlng) {
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.byob = byob;
        this.bar = bar;
        this.latLng = new MyLatLng(latlng.latitude,latlng.longitude);

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isByob() {
        return byob;
    }

    public void setByob(boolean byob) {
        this.byob = byob;
    }

    public boolean isBar() {
        return bar;
    }

    public void setBar(boolean bar) {
        this.bar = bar;
    }

    public MyLatLng getLatLng() {
        return latLng;
    }


}
