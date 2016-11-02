package com.example.rafael.myrecipes.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rafaeldouglas on 03/10/2016.
 */
public class Recipe implements Serializable {

    /**
     * publisher : My Baking Addiction
     * f2f_url : http://food2fork.com/view/e7fdb2
     * ingredients : ["1 tablespoon vegetable oil","1 pound dried rigatoni","1 quart heavy cream","2 tablespoons chopped fresh rosemary","1 clove fresh garlic, crushed","8 ounces goat cheese","2 cups shredded roasted chicken"]
     * source_url : http://www.mybakingaddiction.com/mac-and-cheese-roasted-chicken-and-goat-cheese/
     * recipe_id : e7fdb2
     * image_url : http://static.food2fork.com/MacandCheese1122b.jpg
     * social_rank : 100.0
     * publisher_url : http://www.mybakingaddiction.com
     * title : Mac and Cheese with Roasted Chicken, Goat Cheese, and Rosemary
     */
    private long id;
    @SerializedName("publisher")
    private String publisher;
    @SerializedName("f2f_url")
    private String f2f_url;
    @SerializedName("source_url")
    private String source_url;
    @SerializedName("recipe_id")
    private String recipe_id;
    @SerializedName("image_url")
    private String image_url;
    @SerializedName("social_rank")
    private double social_rank;
    @SerializedName("publisher_url")
    private String publisher_url;
    @SerializedName("title")
    private String title;
    @SerializedName("ingredients")
    private String[] ingredients;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getF2f_url() {
        return f2f_url;
    }

    public void setF2f_url(String f2f_url) {
        this.f2f_url = f2f_url;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public double getSocial_rank() {
        return social_rank;
    }

    public void setSocial_rank(double social_rank) {
        this.social_rank = social_rank;
    }

    public String getPublisher_url() {
        return publisher_url;
    }

    public void setPublisher_url(String publisher_url) {
        this.publisher_url = publisher_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
