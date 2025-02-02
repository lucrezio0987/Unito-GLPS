package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.tableManagement.*;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Compito {
    private int id;
    private double tempo;
    private boolean daPreparare;
    private boolean completato;
    private int importanza;
    private int difficolta;
    private int porzioni;
    private Mansione mansione;
    private Cuoco cuoco;
    private Turno turno;

    public Compito(Mansione mansione) {
        this.id = 0;
        this.mansione = mansione;
        this.tempo = mansione.getTempo() + 0.15;
        this.daPreparare = true;
        this.completato = false;
        this.importanza = 0;
        this.difficolta = 0;
        this.porzioni = 1;
        this.turno = null;
        this.cuoco = null;
    }

    public static void saveJobDB(Compito compito, FoglioRiepilogativo foglio) {
        String jobAdd = "INSERT INTO compiti (time, portions, prepare, completed, mansione_id, foglio_id) VALUES (?, ?, ?, ?, ?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(jobAdd, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setDouble(1, compito.getTempo());
                ps.setInt(2, compito.getPorzioni());
                ps.setBoolean(3, compito.isDaPreparare());
                ps.setBoolean(4, compito.isCompletato());
                ps.setInt(5, compito.getMansione().getId());
                ps.setInt(6, foglio.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0)
                    compito.id = rs.getInt(1); // Assuming 1 is the index of generated ID column
            }
        });
    }

    public static void deleteJobDB(FoglioRiepilogativo foglio) {
        String deleteJob = "DELETE FROM compiti WHERE foglio_id = " + foglio.getId();
        PersistenceManager.executeUpdate(deleteJob);
    }

    public static void modifyJobDB(Compito compito) {
        String modifyJob = "UPDATE compiti SET name = ?, time = ?, portions = ?, prepare = ?, completed = ?, mansione_id = ?, turno_id = ? WHERE id = ?";
        PersistenceManager.executeBatchUpdate(modifyJob, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setString(1, compito.getNome());
                ps.setDouble(2, compito.getTempo());
                ps.setInt(3, compito.getPorzioni());
                ps.setBoolean(4, compito.isDaPreparare());
                ps.setBoolean(5, compito.isCompletato());
                ps.setInt(6, compito.getMansione().getId());

                if (compito.turno != null) {
                    ps.setInt(7, compito.getTurno().getId());
                } else {
                    ps.setNull(7, java.sql.Types.INTEGER);
                }

                ps.setInt(8, compito.getId());

            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                // Handle generated IDs if needed
            }
        });
        if (compito.cuoco == null ) {
            return;
        }
            String insertJobCook = "INSERT INTO compito_cuoco (compito_id, cuoco_id) VALUES (?, ?)";
            PersistenceManager.executeBatchUpdate(insertJobCook, 1, new BatchUpdateHandler() {
                @Override
                public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                    ps.setInt(1, compito.id);
                    ps.setInt(2, compito.cuoco.getId());
                }

                @Override
                public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                    // Handle generated IDs if needed
                }
            });

    }

    public static void removeJobDB(Compito compito) {
        String deleteJob = "DELETE FROM compiti WHERE id = ?";
        PersistenceManager.executeBatchUpdate(deleteJob, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, compito.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                // Handle generated IDs if needed
            }
        });
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double getTempo() {
        return tempo;
    }
    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public boolean isDaPreparare() {
        return daPreparare;
    }
    public void setDaPreparare(boolean daPreparare) {
        this.daPreparare = daPreparare;
    }

    public boolean isCompletato() {
        return completato;
    }
    public void setCompletato(boolean completato) {
        this.completato = completato;
    }

    public int getImportanza() {
        return importanza;
    }
    public void setImportanza(int importanza) {
        this.importanza = importanza;
    }

    public int getDifficolta() {
        return difficolta;
    }
    public void setDifficolta(int difficolta) {
        this.difficolta = difficolta;
    }

    public int getPorzioni() {
        return porzioni;
    }
    public void setPorzioni(int porzioni) {
        this.porzioni = porzioni;
    }

    public Mansione getMansione() {
        return mansione;
    }
    public void setMansione(Mansione mansione) {
        this.mansione = mansione;
    }

    public Cuoco getCuoco() {
        return cuoco;
    }
    public void setCuoco(Cuoco cuoco) {
        this.cuoco = cuoco;
    }

    public Turno getTurno() {
        return turno;
    }
    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public String getNome() {
        return mansione.getNome();
    }

    public String getDescrizione() {
        return mansione.getDescrizione();
    }

    public double getQuantita() {
        return mansione.getQuantita();
    }

}
