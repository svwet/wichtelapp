package ch.appbrew.wichtelapp;

public class MyWishListItem {

    private int productImage;
    private String productName;
    private String productDescription;


    public MyWishListItem(int image, String name, String description){
        productImage = image;
        productName = name;
        productDescription = description;
    }
    public void changeProductName(String name){
        productName = name;
    }
    public int getProductImage(){
        return productImage;
    }
    public String getProductName(){
        return productName;
    }
    public String getProductDescription(){
        return productDescription;
    }
}
