package ch.appbrew.wichtelapp.model;

public class ModelGroup {

    private String groupName;
    private String groupAdmin;


    public ModelGroup() {
    }

    public ModelGroup(String name, String admin){
        groupName = name;
        groupAdmin = admin;

    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupAdmin() {
        return groupAdmin;
    }
}
