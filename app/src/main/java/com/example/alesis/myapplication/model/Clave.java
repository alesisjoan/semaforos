package com.example.alesis.myapplication.model;

/**
 * Created by alesis on 11/1/2017.
 */

public class Clave {
    private final String calle;
    private final int orden;

    public Clave(String calle, int orden) {
        this.calle = calle;
        this.orden = orden;
    }

    @Override
    public int hashCode() {
        return (calle+Integer.toString(orden)).hashCode();
    }

    public String getCalle() {
        return calle;
    }

    public int getOrden() {
        return orden;
    }

    @Override
    public boolean equals(Object obj) {
        String str1 = (calle+Integer.toString(orden));
        Clave clave2 = (Clave) obj;
        String str2 = clave2.getCalle()+clave2.getOrden();
        return str1.equalsIgnoreCase(str2);
    }
}
