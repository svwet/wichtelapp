package ch.appbrew.wichtelapp;

public class ModelCreateGroupe {

    private String benutzer;
    private String name;

    public ModelCreateGroupe() {
    }

    public ModelCreateGroupe(String benutzer, String name){
        this.benutzer = benutzer;
        this.name = name;

    }

    public String getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(String benutzer) {
        this.benutzer = benutzer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
