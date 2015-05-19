package be.ehb.ipg13.fastjobs;

import java.util.ArrayList;

/**
 * Created by Nadir on 29/04/2015.
 */
public class Profiel_model {
    String profielID;
    String naam;
    String tijdsregeling;
    String soortjob;
    String gemeente;
    String aantalKM;
    ArrayList<String> trefwoorden = new ArrayList<String>();

    public Profiel_model(String naam, String tijdsregeling, String soortjob, String gemeente, String aantalKM) {
        this.naam = naam;
        this.tijdsregeling = tijdsregeling;
        this.soortjob = soortjob;
        this.gemeente = gemeente;
        this.aantalKM = aantalKM;
    }

    public String getProfielID() {
        return profielID;
    }
    public String getNaam() {
        return naam;
    }
    public String getAantalKM() {
        return aantalKM;
    }
    public String getSoortjob() {
        return soortjob;
    }
    public String getGemeente() {
        return gemeente;
    }
    public String getTijdsregeling() {
        return tijdsregeling;
    }
    public ArrayList<String> getTrefwoorden() {
        return trefwoorden;
    }
    //-----------------------------------------------------------------------------------

    public void setProfielID(String profielID) {
        this.profielID = profielID;
    }
    public void setNaam(String naam) {
        naam = naam;
    }
    public void setTijdsregeling(String tijdsregeling) {
        this.tijdsregeling = tijdsregeling;
    }
    public void setSoortjob(String soortjob) {
        this.soortjob = soortjob;
    }
    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }
    public void setAantalKM(String aantalKM) {
        this.aantalKM = aantalKM;
    }
    public void setTrefwoorden(String trefwoord) {
        this.trefwoorden.add(trefwoord);
    }
}
