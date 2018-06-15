package com.example.adrian.bakingapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Recipe {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("ingredients")
    @Expose
    public List<Ingredient> ingredients = null;
    @SerializedName("steps")
    @Expose
    public List<Step> steps = null;
    @SerializedName("servings")
    @Expose
    public Integer servings;
    @SerializedName("image")
    @Expose
    public String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        if (image.equals("")) {
            switch (this.name) {
                case "Nutella Pie":
                    image = "https://assets.epicurious.com/photos/54ac95e819925f464b3ac37e/master/pass/51229210_nutella-pie_1x1.jpg";
                    break;
                case "Brownies":
                    image = "https://assets.marthastewart.com/styles/wmax-750/d28/brownie_331_bg_6138982_bkt_w/brownie_331_bg_6138982_bkt_w_horiz.jpg?itok=vpXCPx2f";
                    break;
                case "Yellow Cake":
                    image = "https://d2gk7xgygi98cy.cloudfront.net/6169-4-facebook.jpg";
                    break;
                case "Cheesecake":
                    image = "https://assets.marthastewart.com/styles/wmax-750/d26/classic-cheesecake-d104417/classic-cheesecake-d104417_horiz.jpg?itok=x6TIrh3N";
                    break;
                default:
                    break;
            }
        }
        this.image = image;
    }
}