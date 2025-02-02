package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.tableManagement.*;

public interface FoglioRiepilogativoEventReceiver {
    void updateFoglioRiepilogativoCreato(FoglioRiepilogativo foglio);
    void updateFoglioRiepilogativoModificato(FoglioRiepilogativo foglio);
    void updateFoglioRiepilogativoRimosso(FoglioRiepilogativo foglio);
    void updateCompitoRimosso(Compito compito);
    void updateCompitoAggiunto(Compito compito);
    void updateCompitoModificato(Compito compito);
    void updateTurnoImpostato(Compito compito, Turno turno);
    void updateCuocoAssegnato(Compito compito, Cuoco cuoco);
}

