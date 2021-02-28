package ch.appbrew.wichtelapp;

public class MyWishListItem {

    private String productImage;
    private String productName;
    private String productDescription;


    public MyWishListItem(String image, String name, String description){
        productImage = image;
        productName = name;
        productDescription = description;
    }
    public void changeProductName(String name){
        productName = name;
    }
    public String getProductImage(){
        return productImage;
    }
    public String getProductName(){
        return productName;
    }
    public String getProductDescription(){
        return productDescription;
    }
}
