package org.ejmc.android.simplechat.model;


/**
 * Simple message.
 * 
 * @author startic
 * 
 */
public class Message {

    private String nombre;
    private String mensaje;



    private int scr;


    public Message(String nombre, String mensaje, int scr) {
        this.nombre = nombre;
        this.mensaje = mensaje;
        this.scr=scr;
    }


    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNombre() { return nombre; }

    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getMensaje() { return mensaje; }

    public int getScr() {
        return scr;
    }

    public void setScr(int scr) {
        this.scr = scr;
    }
}