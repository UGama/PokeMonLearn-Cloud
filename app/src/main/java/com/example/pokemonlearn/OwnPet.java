package com.example.pokemonlearn;

/**
 * Created by Gama on 29/5/17.
 */

public class OwnPet {
    private String Id;
    private int Dex;
    private String Name;
    private String ImageName;
    private String BallImageName;

    public OwnPet() {
    }

    public OwnPet(String Id, String name, String ImageName, int Dex, String ballImageName) {
        this.Id = Id;
        Name = name;
        this.ImageName = ImageName;
        this.Dex = Dex;
        this.BallImageName = ballImageName;
    }

    public String getId() {
        return Id;
    }

    public String getImageName() {
        return ImageName;
    }

    public int getDex() {
        return Dex;
    }

    public String getBallImageName() {
        return BallImageName;
    }

    public String getName() {
        return Name;
    }
}
