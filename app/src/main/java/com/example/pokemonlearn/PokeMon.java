package com.example.pokemonlearn;

/**
 * Created by Gama on 28/5/17.
 */

public class PokeMon {
    private String Id;
    private int Number;
    private String Name;
    private String ImageName2;
    private int Weight;
    private String ImageName;
    private String Senior;

    public PokeMon() {}

    public PokeMon(int Number, String Name, String imageName2, int Weight, String imageName, String senior) {
        this.Number = Number;
        this.Name = Name;
        this.ImageName2 = imageName2;
        this.Weight = Weight;
        this.ImageName = imageName;
        this.Senior = senior;
    }

    public String getId() {
        return Id;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public int getWeight() {
        return Weight;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public String getSenior() {
        return Senior;
    }

    public void setSenior(String senior) {
        Senior = senior;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public void setImageName2(String imageName2) {
        ImageName2 = imageName2;
    }

    public String getImageName() {
        return ImageName;
    }

    public String getImageName2() {
        return ImageName2;
    }
}

