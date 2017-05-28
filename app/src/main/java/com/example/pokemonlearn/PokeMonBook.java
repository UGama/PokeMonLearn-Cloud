package com.example.pokemonlearn;

/**
 * Created by Gama on 11/5/17.
 */

public class PokeMonBook {
    private String Id;
    private String Name;
    private String ImageName;
    private int Number;
    private int Price;

    public PokeMonBook() {}

    public PokeMonBook(String Id, String name, String ImageName, int number, int Price) {
        this.Id = Id;
        this.Name = name;
        this.ImageName = ImageName;
        this.Number = number;
        this.Price = Price;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public void setName(String name) {
        Name = name;
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

    public void setPrice(int price) {
        Price = price;
    }

    public int getPrice() {
        return Price;
    }
}
