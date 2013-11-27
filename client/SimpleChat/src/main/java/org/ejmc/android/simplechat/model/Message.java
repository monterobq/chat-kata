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

    public Message(String nombre, String mensaje) {
        this.nombre = nombre;
        this.mensaje = mensaje;
    }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNombre() { return nombre; }

    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getMensaje() { return mensaje; }
}
