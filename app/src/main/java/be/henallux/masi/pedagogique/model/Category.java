package be.henallux.masi.pedagogique.model;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class Category {

    private Integer id;
    private String descritption;
    private int ageMin;
    private int ageMax;

    public Category(Integer id, String descritption, int ageMin, int ageMax) {
        this.id = id;
        this.descritption = descritption;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
    }

    public String getDescritption() {
        return descritption;
    }

    public void setDescritption(String descritption) {
        this.descritption = descritption;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(int ageMax) {
        this.ageMax = ageMax;
    }
}
