package com.example.pokemonlearn;

/**
 * Created by Gama on 28/5/17.
 */

public class PokeMonStone {
    private String Id;
    private String Name;
    private String ImageName;
    private int Number;
    private int Price;

    public PokeMonStone() {}

    public PokeMonStone(String name, String imageName, int number, int price) {
        this.Name = name;
        this.ImageName = imageName;
        this.Number = number;
        this.Price = price;
    }


    public String getId() {
        return Id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getImageName() {
        return ImageName;
    }

    public String getName() {
        return Name;
    }

    public int getNumber() {
        return Number;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

}
