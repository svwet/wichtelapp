package ch.appbrew.wichtelapp.model;

public class WichtelWishlist {

    private String wichtelName;

    public WichtelWishlist(){
    }

    public WichtelWishlist(String name){
        wichtelName = name;
    }

    public String getWichtelName() {
        return wichtelName;
    }

    public void setWichtelName(String wichtelName) {
        this.wichtelName = wichtelName;
    }
}
