package be.henallux.masi.pedagogique.model;

import android.os.Bundle;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class Activity {
    private Integer id;
    private String name;
    private Category category;
    private java.lang.Class associatedClass;

    public Activity(Integer id, String name, java.lang.Class associatedClass) {
        this.id = id;
        this.name = name;
        this.associatedClass = associatedClass;
    }

    public Activity(Integer id, String name, Category category, java.lang.Class associatedClass) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.associatedClass = associatedClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public java.lang.Class getAssociatedClass() {
        return associatedClass;
    }

    public void setAssociatedClass(java.lang.Class associatedClass) {
        this.associatedClass = associatedClass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
