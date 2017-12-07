package com.example.alesis.myapplication.services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by alesis on 25/08/2016.
 */

public class GoogleMapsService {

    private static GoogleMapsService instance = null;

    public static GoogleMapsService getInstance() {
        if(instance == null) {
            instance = new GoogleMapsService();
        }
        return instance;
    }

    public static String getFullStreetNameByLocation(Location location, Context context) throws IOException {
        String streetName = "";

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();


        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        try {
            streetName = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        }
        catch (Exception e){

        }
        /*String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();*/

        return streetName;
    }

    public static String getStreetNumberByLocation(Location location, Context context) throws IOException {
        String number = getAddress(location, context).getSubThoroughfare();
        if(number.contains("-")){
            int number1 = Integer.valueOf(number.split("-")[0]);
            int number2 = Integer.valueOf(number.split("-")[1]);
            return String.valueOf((number1+number2)/2);
        }else{
            return number;
        }

    }

    public static Address getAddress(Location location, Context context) throws IOException {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();


        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        List<Address> addresses = null;
        addresses = geocoder.getFromLocation(latitude, longitude,1);
        if(addresses != null && addresses.size() > 0 ){
            Address address = addresses.get(0);
            // Thoroughfare seems to be the street name without numbers
            return address;
        }
        return null;
    }

    public static String getStreetNameByLocation(Location location, Context context) throws IOException {
       return getAddress(location, context).getThoroughfare();
    }

}
