package com.example.myapplication.Models;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FavHero")
public class favHero {

    @PrimaryKey(autoGenerate = true)

    private int id;
    private Integer heroId;
    private String url;
    private String Name;

    public favHero(Integer heroId, String url, String Name) {
        this.heroId = heroId;
        this.Name=Name;
        this.url = url;
    }

    public void setHeroId(Integer heroId) {
        this.heroId = heroId;
    }

    public Integer getHeroId() {
        return heroId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return Name;
    }

    public void setName (String name) {
        this.Name = name;
    }
}

