package com.example.pokemonlearn;

/**
 * Created by Gama on 29/5/17.
 */

public class OwnItem {
    private String Id;
    private String Name;
    private int Number;
    private int Type;
    private String ImageName;
    private int Dex;

    public OwnItem() {
    }

    public OwnItem(String Id, String name, int number, int type, String ImageName, int numberInDex) {
        this.Id = Id;
        Name = name;
        Number = number;
        Type = type;
        this.ImageName = ImageName;
        Dex = numberInDex;
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

    public void setType(int type) {
        Type = type;
    }

    public int getDex() {
        return Dex;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public void setDex(int dex) {
        Dex = dex;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public int getNumber() {
        return Number;
    }

    public int getType() {
        return Type;
    }

}
