package com.example.alesis.myapplication.services;

import android.content.Context;
import android.location.Location;

import com.example.alesis.myapplication.model.Clave;
import com.example.alesis.myapplication.model.Semaforo;
import com.example.alesis.myapplication.repo.SemaforoRepository;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by alesis on 21/08/2016.
 */
public class SemaforoService {

    LinkedHashMap<Clave, Semaforo> redSemaforos = new LinkedHashMap<>();

    SemaforoRepository semaforoRepository = SemaforoRepository.getInstance();

    private static SemaforoService instance = null;

    public static SemaforoService getInstance() {
        if(instance == null) {
            instance = new SemaforoService();
        }
        return instance;
    }

    public Semaforo getSemaforoById(String id){
        return  semaforoRepository.getSemaforoById(id);
    }

    public void saveSemaforo(Semaforo semaforo){
        semaforoRepository.saveSemaforo(semaforo);
    }

    public Semaforo saveSemaforo(Location location, Date date, Context context){
        Semaforo semaforo = semaforoRepository.getSemaforoById(String.valueOf(date.getTime()));
        if(semaforo==null){
            semaforo = new Semaforo();
            semaforo.setId(date.getTime());
            semaforo.setLocation(location);
        }
        semaforo.setGreenStart(date.getTime());
        semaforo.setGreenStartAgain(date.getTime());
        this.saveSemaforo(semaforo);
        return semaforo;
    }

    private String getCurrentCalle1(Location location, Context context){
        String calle1 = "";
        try {
            calle1 = GoogleMapsService.getStreetNameByLocation(location, context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return calle1;
    }

    private String getCurrentCalleNumber(Location location, Context context){
        String number = "";
        try {
            number = GoogleMapsService.getStreetNumberByLocation(location, context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return number;
    }

    public List<Semaforo> getAllSemaforosProcessed(){
        List<Semaforo> semaforos = semaforoRepository.getAllSemaforos();
        if(semaforos!=null) {
            redSemaforos = new LinkedHashMap<>();
            for (Semaforo s : semaforos) {
                if (s.isProcessed()) {
                    redSemaforos.put(new Clave(s.getStreetName(), s.getOrder()), s);
                }
            }
        }
        return semaforos;
    }


    public List<Semaforo> getAllSemaforos(){
        return semaforoRepository.getAllSemaforos();
    }

    public Semaforo getNearSemaforo(Location location, Context context){
        String calle = getCurrentCalle1(location, context );
        int orden = Integer.valueOf(getCurrentCalleNumber(location, context)) / 100;
        return redSemaforos.get(new Clave(calle, orden));
    }

    public void etl(Context context){
        List<Semaforo> semaforos = this.getAllSemaforos();
//        TODO tengo que trabajar sobre el semaforo inverso..
//        TODO trabajar sobre la vista, manejo de errores
        for(Semaforo s: semaforos){
            if(!s.isProcessed()){
                s.setStreetName(getCurrentCalle1(s.getLocation(),  context));
                s.setOrder(Integer.valueOf(getCurrentCalleNumber(s.getLocation(), context))/100);
                s.setProcessed(true);
                this.saveSemaforo(s);
            }
        }
    }
}
