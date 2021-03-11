package ch.appbrew.wichtelapp;

public class ModelGroup {

    private String groupname;

    public ModelGroup() {
    }

    public ModelGroup(String name){
        this.groupname = name;

    }

    public String getName() {
        return groupname;
    }

    public void setName(String name) {
        this.groupname = name;
    }
}
