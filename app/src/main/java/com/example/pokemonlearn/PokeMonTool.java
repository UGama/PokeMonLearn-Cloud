package com.example.pokemonlearn;

/**
 * Created by Gama on 11/5/17.
 */

public class PokeMonTool {
    private String Id;
    private String Name;
    private String ImageName;
    private int Number;
    private int Price;

    public PokeMonTool() {}

    public PokeMonTool(String Id, String name, String imageName, int number, int price) {
        this.Id = Id;
        this.Name = name;
        this.ImageName = imageName;
        this.Number = number;
        this.Price = price;
    }

    public int getNumber() {
        return Number;
    }

    public String getName() {
        return Name;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getPrice() {
        return Price;
    }
}
