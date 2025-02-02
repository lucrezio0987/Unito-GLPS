package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.tableManagement.*;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoglioRiepilogativoManager {
    private List<FoglioRiepilogativoEventReceiver> eventReceivers;
    private FoglioRiepilogativo foglio;

    // Constructor
    public FoglioRiepilogativoManager() {
        this.eventReceivers = new ArrayList<>();
    }

    // Getters and Setters
    public List<FoglioRiepilogativoEventReceiver> getEventReceivers() {
        return eventReceivers;
    }
    public void setEventReceivers(List<FoglioRiepilogativoEventReceiver> eventReceivers) {
        this.eventReceivers = eventReceivers;
    }

    public FoglioRiepilogativo getFoglioRiepilogativo() {
        return foglio;
    }
    public void setFoglioRiepilogativo(FoglioRiepilogativo foglio) {
        this.foglio = foglio;
    }

    // Event sender methods
    public void addEventReceiver(FoglioRiepilogativoEventReceiver er) {
        this.eventReceivers.add(er);
    }
    public void removeEventReceiver(FoglioRiepilogativoEventReceiver er) {
        this.eventReceivers.remove(er);
    }

    private void notifyFoglioRiepilogativoCreato(FoglioRiepilogativo foglio) {
        for (FoglioRiepilogativoEventReceiver er : eventReceivers) {
            er.updateFoglioRiepilogativoCreato(foglio);
        }
    }

    private void notifyFoglioRiepilogativoModificato(FoglioRiepilogativo foglio) {
        for (FoglioRiepilogativoEventReceiver er : eventReceivers) {
            er.updateFoglioRiepilogativoModificato(foglio);
        }
    }

    private void notifyFoglioRiepilogativoRimosso(FoglioRiepilogativo foglio) {
        for (FoglioRiepilogativoEventReceiver er : eventReceivers) {
            er.updateFoglioRiepilogativoRimosso(foglio);
        }
    }

    private void notifyCompitoAggiunto(Compito compito) {
        for (FoglioRiepilogativoEventReceiver er : eventReceivers) {
            er.updateCompitoAggiunto(compito);
        }
    }

    private void notifyCompitoModificato(Compito compito) {
        for (FoglioRiepilogativoEventReceiver er : eventReceivers) {
            er.updateCompitoModificato(compito);
        }
    }

    private void notifyCompitoRimosso(Compito compito) {
        for (FoglioRiepilogativoEventReceiver er : eventReceivers) {
            er.updateCompitoRimosso(compito);
        }
    }

    private void notifyTurnoImpostato(Compito compito, Turno turno) {
        for (FoglioRiepilogativoEventReceiver er : eventReceivers) {
            er.updateTurnoImpostato(compito, turno);
        }
    }

    private void notifyCuocoAssegnato(Compito compito, Cuoco cuoco) {
        for (FoglioRiepilogativoEventReceiver er : eventReceivers) {
            er.updateCuocoAssegnato(compito, cuoco);
        }
    }

    // Operations methods
    public FoglioRiepilogativo creaFoglioRiepilogativo(ServiceInfo service) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();

        if (isChef(user)) {
            this.foglio = new FoglioRiepilogativo(service, user);
            notifyFoglioRiepilogativoCreato(this.foglio);
            return this.foglio;
        }
        else
            throw new UseCaseLogicException();
    }

    public boolean isChef(User user) {
        return user.isChef();
    }

    public boolean isOwner(User user) {
        return this.foglio.getProprietario().equals(user);
    }

    public FoglioRiepilogativo modificaFoglioRiepilogativo(FoglioRiepilogativo foglio) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();

        if (CatERing.getInstance().getFoglioRiepilogativoMng().isOwner(user)) {
            foglio.setId(this.foglio.getId());
            this.foglio = foglio;
            notifyFoglioRiepilogativoModificato(foglio);
            return this.foglio;
        } else
            throw new UseCaseLogicException();
    }

    public FoglioRiepilogativo rimuoviFoglioRiepilogativo(FoglioRiepilogativo foglio) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();

        if (!isOwner(user))
            throw new UseCaseLogicException();
        else{
            if (foglio != null) {
                this.foglio = null;
                notifyFoglioRiepilogativoRimosso(foglio);
                return this.foglio;
            } else
                throw new UseCaseLogicException();
        }
    }

    public Compito aggiungiCompito(Mansione mansione, double tempo, boolean daPreparare) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();
        if (foglio != null && isOwner(user)) {
            Compito compito = foglio.aggiungiCompito(mansione,tempo,daPreparare);
            notifyCompitoAggiunto(compito);
            notifyFoglioRiepilogativoModificato(foglio);
            return compito;
        }
        else
            throw new UseCaseLogicException();
    }

    public Compito modificaCompito(int id, double tempo, boolean daPreparare, boolean completato, int difficolta, int importanza, int porzioni) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();
        if (foglio != null && isOwner(user)) {
            Compito compito = foglio.modificaCompito(id,tempo,daPreparare,completato,difficolta,importanza,porzioni);
            notifyCompitoModificato(compito);
            notifyFoglioRiepilogativoModificato(foglio);
            return compito;
        } else
            throw new UseCaseLogicException();
    }
    public boolean rimuoviCompito(int id) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();
        if (foglio != null && isOwner(user)) {
            Compito compito = foglio.rimuoviCompito(id);
            notifyCompitoRimosso(compito);
            notifyFoglioRiepilogativoRimosso(foglio);
            return compito != null;
        } else
            throw new UseCaseLogicException();
    }

    public ArrayList<Compito> ordinaCompitiImportanza(int metodo) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();

        if (isChef(user) && foglio != null) {
            return foglio.ordinaCompitiImportanza(metodo);
        } else {
            throw new UseCaseLogicException();
        }
    }
    public ArrayList<Compito> ordinaCompitiDifficolta(int metodo) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();

        if (isChef(user) && foglio != null) {
            return foglio.ordinaCompitiDifficolta(metodo);
        } else {
            throw new UseCaseLogicException();
        }
    }


    public boolean impostaTurno(int id, Turno turno) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();

        if (isChef(user) && foglio != null) {
            Compito compito = foglio.impostaTurno(id, turno);
            notifyCompitoModificato(compito);
            notifyTurnoImpostato(compito, turno);
            notifyFoglioRiepilogativoRimosso(foglio);
            return compito != null;
        } else {
            throw new UseCaseLogicException();
        }
    }

    public boolean assegnaCuoco(int id, Cuoco cuoco) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();

        if (isChef(user) && foglio != null) {
            Compito compito = foglio.assegnaCuoco(id, cuoco);
            notifyCompitoModificato(compito);
            notifyCuocoAssegnato(compito, cuoco);
            notifyFoglioRiepilogativoRimosso(foglio);
            return compito != null;

        } else {
            throw new UseCaseLogicException();
        }
    }

    // utility methods
    public ArrayList<FoglioRiepilogativo> getAllSheet(User user) {
        String getAllSheet = "SELECT * FROM sheets WHERE owner_id = " + user.getId();
        ArrayList<FoglioRiepilogativo> fogliRiepilogativi = new ArrayList<>();
        PersistenceManager.executeQuery(getAllSheet, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                FoglioRiepilogativo sheet = new FoglioRiepilogativo(ServiceInfo.getService(rs.getInt("service")), user);
                sheet.setId(rs.getInt("id"));
                fogliRiepilogativi.add(sheet);
            }
        });
        return fogliRiepilogativi;
    }
}
