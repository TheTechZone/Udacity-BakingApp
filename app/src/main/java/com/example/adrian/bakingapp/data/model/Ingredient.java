package com.example.adrian.bakingapp.data.model;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Ingredient {

    @SerializedName("quantity")
    @Expose
    public float quantity;
    @SerializedName("measure")
    @Expose
    public String measure;
    @SerializedName("ingredient")
    @Expose
    public String ingredient;

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getListing(Context context) {
        String qty = "";
        if (quantity == (int) quantity) {
            qty += (int) quantity;
        } else {
            qty += quantity;
        }
        int measureId = context.getResources().getIdentifier(measure.toLowerCase(), "string", context.getPackageName());
        String measure = context.getResources().getString(measureId);

        return String.format("%s %s %s", qty, measure, ingredient);
    }
}