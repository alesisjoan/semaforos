package com.example.alesis.myapplication.enums;

/**
 * Created by alesis on 21/08/2016.
 */
public enum Orientacion{
    Null, OesteEste, EsteOeste, NorteSur, SurNorte;



    public static Orientacion getType(String value) {

        if(value == null || value.isEmpty()){
            // assume this is the default
            return Null;
        }

        for(Orientacion a : values()){
            if(a.getName().equalsIgnoreCase(value)){ // or you can use value.equals(a.value)
                return a;
            }
        }
        // assume this is the default
        return Null;
    }

    public String getName() {
        return this.name();
    }
}
