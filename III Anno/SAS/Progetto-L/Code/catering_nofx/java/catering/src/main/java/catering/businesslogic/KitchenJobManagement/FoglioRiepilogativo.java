package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.event.*;
import catering.businesslogic.user.*;
import catering.businesslogic.tableManagement.*;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class FoglioRiepilogativo {
    private int id;
    private User proprietario;
    private ServiceInfo servizio;
    private List<Compito> compiti;

    public FoglioRiepilogativo(ServiceInfo servizio, User proprietario) {
        this.id = 0;
        this.proprietario = proprietario;
        this.servizio = servizio;
        this.compiti = new ArrayList<>();

        ArrayList<Mansione> mansioni = servizio.getMansioni();
        for (Mansione mansione: mansioni) {
            Compito compito = new Compito(mansione);
            compiti.add(compito);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getProprietario() {
        return proprietario;
    }

    public ServiceInfo getServizio() {
        return servizio;
    }

    public Compito modificaCompito(int id, double tempo, boolean daPreparare, boolean completato, int difficolta, int importanza, int porzioni) {
        for (Compito compito : compiti)
            if (compito.getId() == id) {
                compito.setTempo(tempo);
                compito.setDaPreparare(daPreparare);
                compito.setCompletato(completato);
                compito.setDifficolta(difficolta);
                compito.setImportanza(importanza);
                compito.setPorzioni(porzioni);
                return compito;
            }
        return null;
    }

    public Compito modificaCompitoTempo(int id, double tempo) {
        for (Compito compito : compiti) {
            if (compito.getId() == id) {
                compito.setTempo(tempo);
                return compito;
            }
        }
        return null;
    }

    public Compito modificaCompitoDaPreparare(int id, boolean daPreparare) {
        for (Compito compito : compiti) {
            if (compito.getId() == id) {
                compito.setDaPreparare(daPreparare);
                return compito;
            }
        }
        return null;
    }

    public Compito modificaCompitoCompletato(int id, boolean completato) {
        for (Compito compito : compiti) {
            if (compito.getId() == id) {
                compito.setCompletato(completato);
                return compito;
            }
        }
        return null;
    }

    public Compito modificaCompitoDifficolta(int id, int difficolta) {
        for (Compito compito : compiti) {
            if (compito.getId() == id) {
                compito.setDifficolta(difficolta);
                return compito;
            }
        }
        return null;
    }

    public Compito modificaCompitoImportanza(int id, int importanza) {
        for (Compito compito : compiti) {
            if (compito.getId() == id) {
                compito.setImportanza(importanza);
                return compito;
            }
        }
        return null;
    }

    public Compito modificaCompitoPorzioni(int id, int porzioni) {
        for (Compito compito : compiti) {
            if (compito.getId() == id) {
                compito.setPorzioni(porzioni);
                return compito;
            }
        }
        return null;
    }

    public Compito rimuoviCompito(int id) {
        Compito c = null;
        for (Compito compito : compiti)
            if (compito.getId() == id)
                c = compito;
        if(c != null) compiti.removeIf(compito -> compito.getId() == id);
        return c;
    }

    public Compito impostaTurno(int id, Turno turno) {
        for (Compito compito : compiti) {
            if (compito.getId() == id) {
                compito.setTurno(turno);
                return compito;
            }
        }
        return null;
    }

    public Compito assegnaCuoco(int id, Cuoco cuoco) {
        for (Compito compito : compiti) {
            if (compito.getId() == id) {
                compito.setCuoco(cuoco);
                return compito;
            }
        }
        return null;
    }

    public ArrayList<Compito> ordinaCompitiImportanza(int metodo) {
        return ordinaCompiti(Comparator.comparingInt(Compito::getImportanza), metodo);
    }

    public ArrayList<Compito> ordinaCompitiDifficolta(int metodo) {
        return ordinaCompiti(Comparator.comparingInt(Compito::getDifficolta), metodo);
    }

    private ArrayList<Compito> ordinaCompiti(Comparator<Compito> comparator, int metodo) {
        ArrayList<Compito> listaCompiti = new ArrayList<>(compiti);
        listaCompiti.sort(metodo < 0 ? comparator : comparator.reversed());
        return listaCompiti;
    }

    public Compito aggiungiCompito(Mansione mansione, double tempo, boolean daPreparare) {
        Compito compito = new Compito(mansione);
        compito.setTempo(tempo);
        compito.setDaPreparare(daPreparare);
        compiti.add(compito);
        return compito;
    }

    //----------------------------

    public static void creaFoglioRiepilogativo(FoglioRiepilogativo foglio) {
        String query = "INSERT INTO fogli (service, owner_id) VALUES (?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(query, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, foglio.servizio.getId());
                ps.setInt(2, foglio.proprietario.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0) {
                    foglio.id = rs.getInt(1);
                }
            }
        });

        if (result[0] > 0) {
            for (Compito compito : foglio.compiti) {
                Compito.saveJobDB(compito, foglio);
            }
        }
    }

    public static void modificaFoglioRiepilogativo(FoglioRiepilogativo foglio) {
        String query = "UPDATE sheets SET service = ?, owner_id = ? WHERE id = ?";
        int[] result = PersistenceManager.executeBatchUpdate(query, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, foglio.servizio.getId());
                ps.setInt(2, foglio.proprietario.getId());
                ps.setInt(3, foglio.id);
            }
            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0) {
                    foglio.id = rs.getInt(1);
                }
            }
        });

        if (result[0] > 0) {
            for (Compito compito : foglio.compiti) {
                Compito.saveJobDB(compito, foglio);
            }
        }
    }

    public static void rimuoviFoglioRiepilogativo(FoglioRiepilogativo foglio) {
        Compito.deleteJobDB(foglio);
        foglio.clearSummarySheet();
        String query = "DELETE FROM sheets WHERE id = " + foglio.getId();
        PersistenceManager.executeUpdate(query);
    }

    public static void aggiornaCompitoDB(Compito job) {
        Compito.modifyJobDB(job);
    }

    public static void rimuoviCompitoDB(Compito job) {
        Compito.removeJobDB(job);
    }

    public static void aggiungiCompitoToDB(Compito compito) {
        Compito.saveJobDB(compito, null);
    }

    public void clearSummarySheet() {
        this.compiti = null;
    }

    public static void impostaTurnoToDB(Compito compito, Turno turno) {
        String query = "UPDATE compiti SET turno_id = ?, tempo = ?, porzioni = ? WHERE id = ?";

        int[] result = PersistenceManager.executeBatchUpdate(query, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, turno.getId());
                ps.setDouble(2, compito.getTempo());
                ps.setInt(3, compito.getPorzioni());
                ps.setInt(4, compito.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                // Nessuna gestione di ID generati, dato che si tratta di un UPDATE
            }
        });

        if (result[0] > 0 && compito.getCuoco() != null) {
            assegnaCuocoToDB(compito, compito.getCuoco());
        }
    }

    public static void assegnaCuocoToDB(Compito compito, Cuoco cuoco) {
        String query = "INSERT INTO compito_cuoco (compito_id, cuoco_id) VALUES (?, ?)";

        PersistenceManager.executeBatchUpdate(query, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, compito.getId());
                ps.setInt(2, cuoco.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                // Nessuna gestione necessaria in questo caso
            }
        });
    }

    public ArrayList<Compito> getCompiti() {
        return new ArrayList<>(compiti);
    }
}
