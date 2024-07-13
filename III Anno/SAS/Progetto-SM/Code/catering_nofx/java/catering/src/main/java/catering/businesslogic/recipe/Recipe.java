package catering.businesslogic.recipe;

import catering.businesslogic.KitchenJobManagement.Duty;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Recipe extends Duty {
    private static Map<Integer, Recipe> all = new HashMap<>();

    private int id;
    private String name;

    public Recipe(String name, String description, int difficult, int importance, int time, int quantity, int portions) {
        super(name, description, difficult, importance, time, quantity, portions);
        id = 0;
        this.name = name;
    }

    public Recipe(String title){
        super(title);
        this.name = title;
    }

    public Recipe(){
        super();
    }

    public String getTitle() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return name;
    }

    // STATIC METHODS FOR PERSISTENCE

    public static ArrayList<Recipe> loadAllRecipes() {
        String query = "SELECT * FROM recipes";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                if (all.containsKey(id)) {
                    Recipe rec = all.get(id);
                    rec.name = rs.getString("name");
                } else {
                    Recipe rec = new Recipe(rs.getString("name"));
                    rec.id = id;
                    all.put(rec.id, rec);
                }
            }
        });
        ArrayList<Recipe> ret = new ArrayList<Recipe>(all.values());
        Collections.sort(ret, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe o1, Recipe o2) {
                return (o1.getTitle().compareTo(o2.getTitle()));
            }
        });
        return ret;
    }

    public static ArrayList<Recipe> getAllRecipes() {
        return new ArrayList<Recipe>(all.values());
    }

    public static Recipe loadRecipeById(int id) {
        if (all.containsKey(id)) return all.get(id);
        Recipe rec = new Recipe();
        String query = "SELECT * FROM recipes WHERE id = " + id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                    rec.name = rs.getString("name");
                    rec.id = id;
                    all.put(rec.id, rec);
            }
        });
        return rec;
    }


}
