package com.example.pokemonlearn;

/**
 * Created by Gama on 29/5/17.
 */

public class Scene {
    private int Number;
    String ImageName;
    private int Price;

    public Scene(int Number, String ImageName, int Price) {
        this.Number = Number;
        this.ImageName = ImageName;
        this.Price = Price;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public int getNumber() {
        return Number;
    }


    public void setPrice(int price) {
        Price = price;
    }

    public int getPrice() {
        return Price;
    }
}

