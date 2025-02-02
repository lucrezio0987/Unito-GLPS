package catering.persistence;

import catering.businesslogic.KitchenJobManagement.Compito;
import catering.businesslogic.KitchenJobManagement.FoglioRiepilogativo;
import catering.businesslogic.KitchenJobManagement.FoglioRiepilogativoEventReceiver;
import catering.businesslogic.tableManagement.Cuoco;
import catering.businesslogic.tableManagement.Turno;

public class FoglioRiepilogativoPersistence implements FoglioRiepilogativoEventReceiver {

    @Override
    public void updateFoglioRiepilogativoCreato(FoglioRiepilogativo foglio) {
        FoglioRiepilogativo.creaFoglioRiepilogativo(foglio);
    }

    @Override
    public void updateFoglioRiepilogativoModificato(FoglioRiepilogativo foglio) {
        FoglioRiepilogativo.modificaFoglioRiepilogativo(foglio);
    }

    @Override
    public void updateFoglioRiepilogativoRimosso(FoglioRiepilogativo foglio) {
        FoglioRiepilogativo.rimuoviFoglioRiepilogativo(foglio);
    }

    @Override
    public void updateCompitoModificato(Compito compito) {
        FoglioRiepilogativo.aggiornaCompitoDB(compito);
    }

    @Override
    public void updateCompitoRimosso(Compito compito) {
        FoglioRiepilogativo.rimuoviCompitoDB(compito);
    }

    @Override
    public void updateCompitoAggiunto(Compito compito) {
        FoglioRiepilogativo.aggiungiCompitoToDB(compito);
    }

    @Override
    public void updateTurnoImpostato(Compito compito, Turno turno) {
        FoglioRiepilogativo.impostaTurnoToDB(compito, turno);
    }

    @Override
    public void updateCuocoAssegnato(Compito compito, Cuoco cuoco) {
        FoglioRiepilogativo.assegnaCuocoToDB(compito, cuoco);
    }
}
