package com.example.pokemonlearn;

/**
 * Created by Gama on 28/5/17.
 */

public class PokeMonBall {
    private String Id;
    private String Name;
    private String ImageName;
    private int Number;
    private Double rate;
    private int Price;

    public PokeMonBall() {}

    public PokeMonBall(String Id, String Name, String ImageName, int Number, Double rate, int price) {
        this.Id = Id;
        this.Name = Name;
        this.ImageName = ImageName;
        this.rate = rate;
        this.Price = price;
    }

    public String getId() {
        return Id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public String getImageName() {
        return ImageName;
    }

    public int getNumber() {
        return Number;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRate() {
        return rate;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
