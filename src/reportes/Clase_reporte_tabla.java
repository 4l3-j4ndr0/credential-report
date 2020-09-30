/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

/**
 *
 * @author 4L3
 */
public class Clase_reporte_tabla {
    
    String mitad1_nombre,mitad1_cargo,mitad1_ci;

    public Clase_reporte_tabla(String mitad1_nombre, String mitad1_cargo, String mitad1_ci) {
        this.mitad1_nombre = mitad1_nombre;
        this.mitad1_cargo = mitad1_cargo;
        this.mitad1_ci = mitad1_ci;
    }


    public String getMitad1_nombre() {
        return mitad1_nombre;
    }

    public void setMitad1_nombre(String mitad1_nombre) {
        this.mitad1_nombre = mitad1_nombre;
    }

    public String getMitad1_cargo() {
        return mitad1_cargo;
    }

    public void setMitad1_cargo(String mitad1_cargo) {
        this.mitad1_cargo = mitad1_cargo;
    }

    public String getMitad1_ci() {
        return mitad1_ci;
    }

    public void setMitad1_ci(String mitad1_ci) {
        this.mitad1_ci = mitad1_ci;
    }

   
    
    
}
