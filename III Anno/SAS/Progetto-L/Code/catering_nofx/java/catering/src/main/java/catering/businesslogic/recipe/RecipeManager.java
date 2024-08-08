package catering.businesslogic.recipe;

import java.util.ArrayList;

public class RecipeManager {

    public RecipeManager() {
        Recipe.loadAllRecipes();
    }

    public ArrayList<Recipe> getRecipes() {
        return Recipe.getAllRecipes();
    }
}
