package ch.appbrew.wichtelapp;

public class ModelCreateGroup {

    private String benutzer;
    private String name;

    public ModelCreateGroup() {
    }

    public ModelCreateGroup(String benutzer, String name){
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
