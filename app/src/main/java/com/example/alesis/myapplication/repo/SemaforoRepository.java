package com.example.alesis.myapplication.repo;

import android.location.Location;
import android.util.Log;

import com.example.alesis.myapplication.enums.Orientacion;
import com.example.alesis.myapplication.model.Semaforo;
import com.example.alesis.myapplication.services.JSONService;
import com.example.alesis.myapplication.services.SemaforoService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alesis on 21/08/2016.
 */
public class SemaforoRepository {

    private String semaforosJson = "semaforo_{id}.json";

    JSONService jsonService = JSONService.getInstance();

    private static SemaforoRepository instance = null;

    public static SemaforoRepository getInstance() {
        if(instance == null) {
            instance = new SemaforoRepository();
        }
        return instance;
    }

    public Semaforo getSemaforoById(String id){
        String fileName = semaforosJson.replace("{id}", id);
        return mapJsonToSemaforo(jsonService.getJsonFromFile(fileName));
    }

    public void saveSemaforo(Semaforo semaforo){
        String s_json = new Gson().toJson(semaforo);
        String fileName = semaforosJson.replace("{id}", semaforo.getId().toString());
        jsonService.saveJsonFile(fileName, s_json);
    }

    public List<Semaforo> getAllSemaforos(){
        List<Semaforo> semaforoList = null;
        try{
            return this.mapListToSemaforo(jsonService.getAll());
        }catch (Exception e){
            Log.d("EXCEPTION","getAllSemaforos");
            e.printStackTrace();
        }

        return semaforoList;
    }

    private Semaforo mapJsonToSemaforo(JsonObject jsonObject){
        if(jsonObject==null) return null;
        Semaforo semaforo = new Semaforo();
        semaforo.setId(jsonObject.get("id").getAsLong());
        semaforo.setProcessed(jsonObject.get("processed").getAsBoolean());
        if(semaforo.isProcessed()){
            semaforo.setStreetName(jsonObject.get("streetName").getAsString());
            semaforo.setOrder(jsonObject.get("order").getAsInt());
        }
        semaforo.setGreenStart(jsonObject.get("greenStart").getAsLong());
        semaforo.setGreenStartAgain(jsonObject.get("greenStartAgain").getAsLong());
        Double longitude = jsonObject.getAsJsonObject("location").get("mLongitude").getAsDouble();
        Double latitude = jsonObject.getAsJsonObject("location").get("mLatitude").getAsDouble();
        Location location = new Location("");
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        semaforo.setLocation(location);
        return semaforo;
    }

    private List<Semaforo> mapListToSemaforo(List<JsonObject> listJson){
        List<Semaforo> list = new ArrayList<>();
        for(JsonObject json: listJson){
            list.add(this.mapJsonToSemaforo(json));
        }
        return list;
    }

}
