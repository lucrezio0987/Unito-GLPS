package catering.businesslogic.tableManagement;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Cuoco {
    private int id;
    private String name;

    public Cuoco(String name) {
        this.id = 0;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // utility methods
    public ArrayList<Cuoco> loadAllCooks(){
        ArrayList<Cuoco> cuochi = new ArrayList<>();
        String query = "SELECT * FROM Cooks";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    Cuoco cook = new Cuoco(rs.getString("name"));
                    cook.setId(rs.getInt("id"));
                    cuochi.add(cook);
                }
            }
        });
        return cuochi;
    }
}
