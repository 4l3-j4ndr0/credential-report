/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

/**
 *
 * @author 4l3
 */
public class Clase_solapa_oficial {
    
    String nombre, cargo, carnet, pp;

    public Clase_solapa_oficial(String nombre, String cargo, String carnet, String pp) {
        this.nombre = nombre;
        this.cargo = cargo;
        this.carnet = carnet;
        this.pp = pp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }


}
