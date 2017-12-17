package be.henallux.masi.pedagogique.model;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class Activity {
    private Integer id;
    private String name;
    private Category category;

    public Activity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Activity(Integer id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
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
}
