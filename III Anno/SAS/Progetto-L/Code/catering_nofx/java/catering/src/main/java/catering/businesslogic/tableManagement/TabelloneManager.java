package catering.businesslogic.tableManagement;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class TabelloneManager {
    public Tabellone tabellone;

    public TabelloneManager() {
    }

    public Tabellone getTabellone() {
        return tabellone;
    }

    public void setTabellone(Tabellone tabellone) {
        this.tabellone = tabellone;
    }

    private void notifyTabelloneCreato(Tabellone tabellone) {}

    public Tabellone creaTabellone(String name, String event, ArrayList<Turno> turni) throws UseCaseLogicException {
        if (CatERing.getInstance().getUserManager().getUser().isChef()) {
            this.tabellone = new Tabellone(name,event, turni);
            return this.tabellone;
        }
        else
            throw new UseCaseLogicException();
    }

    public ArrayList<Turno> mostraTabellone() throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();
        if (user.isChef()) {
            return tabellone.mostraTabellone();
        } else
            throw new UseCaseLogicException();
    }

    public boolean isOrganizer(User user) {
        return true;
    }
}
