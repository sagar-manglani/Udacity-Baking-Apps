package com.udacity.udacitybakingapps.Data;

import org.parceler.Parcel;

@Parcel
public class Ingredients{
     String quantity;
     String measure;
     String ingredient;

    public Ingredients(){

    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
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

}
