package be.henallux.masi.pedagogique.model;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class Class {
    private Integer id;
    private String descritpion;

    public Class(Integer id, String descritpion) {
        this.id = id;
        this.descritpion = descritpion;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }
}
