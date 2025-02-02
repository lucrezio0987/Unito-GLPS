package catering.businesslogic.recipe;

import catering.businesslogic.KitchenJobManagement.Mansione;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Recipe extends Mansione {
    private static Map<Integer, Recipe> all = new HashMap<>();

    private int id;
    private String name;

    public Recipe(String nome, String descrizione, double tempo, double quantita) {
        super(nome, descrizione, tempo, quantita);
        id = 0;
        this.name = nome;
    }

    public static Map<Integer, Recipe> getAll() {
        return all;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Recipe(String title){
        super(title);
        this.name = title;
    }

    public Recipe(){
        super();
    }

    // STATIC METHODS FOR PERSISTENCE

    public static ArrayList<Recipe> loadAllRecipes() {
        String query = "SELECT r.id, mansione.name FROM recipes r JOIN mansioni mansione ON r.id = mansione.id";
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
                return (o1.getName().compareTo(o2.getName()));
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
