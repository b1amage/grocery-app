package com.example.myapplication.content;

import com.example.myapplication.model.Location;

import java.util.ArrayList;

public class Locations {
    private ArrayList<Location> locations;

    public Locations(){
        this.locations = new ArrayList<>();
        this.locations.add(new Location("123 HVB, P.13, Q.PN", 0, 0));
        this.locations.add(new Location("123 HVB, P.13, Q.PN", 0, 0));
        this.locations.add(new Location("123 HVB, P.13, Q.PN", 0, 0));
        this.locations.add(new Location("123 HVB, P.13, Q.PN", 0, 0));
        this.locations.add(new Location("123 HVB, P.13, Q.PN", 0, 0));
        this.locations.add(new Location("123 HVB, P.13, Q.PN", 0, 0));
        this.locations.add(new Location("123 HVB, P.13, Q.PN", 0, 0));

    }

    public ArrayList<Location> getLocations() {
        return locations;
    }
}
