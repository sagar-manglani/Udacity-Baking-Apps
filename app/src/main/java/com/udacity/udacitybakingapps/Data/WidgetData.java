package com.udacity.udacitybakingapps.Data;

public class WidgetData {


    Recipe recipe;
    int position;

    public WidgetData(Recipe recipe, int   position){
        this.recipe=recipe;
        this.position=position;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
