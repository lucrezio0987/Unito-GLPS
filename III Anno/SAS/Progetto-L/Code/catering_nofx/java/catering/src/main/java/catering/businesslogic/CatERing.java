package catering.businesslogic;

import catering.businesslogic.KitchenJobManagement.*;
import catering.businesslogic.event.EventManager;
import catering.businesslogic.menu.MenuManager;
import catering.businesslogic.recipe.RecipeManager;
import catering.businesslogic.tableManagement.TabelloneManager;
import catering.businesslogic.user.UserManager;
import catering.persistence.*;

public class CatERing {
    private static CatERing singleInstance;

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private MenuManager menuMgr;
    private RecipeManager recipeMgr;
    private UserManager userMgr;
    private EventManager eventMgr;

    private FoglioRiepilogativoManager foglioRiepilogativoMng;
    private TabelloneManager tabelloneMng;
    private MenuPersistence menuPersistence;

    private FoglioRiepilogativoPersistence foglioRiepilogativoPersistence;

    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new RecipeManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();

        foglioRiepilogativoMng = new FoglioRiepilogativoManager();
        tabelloneMng = new TabelloneManager();


        menuPersistence = new MenuPersistence();
        menuMgr.addEventReceiver(menuPersistence);

        foglioRiepilogativoPersistence = new FoglioRiepilogativoPersistence();
        foglioRiepilogativoMng.addEventReceiver(foglioRiepilogativoPersistence);
    }


    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public RecipeManager getRecipeManager() {
        return recipeMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }

    public EventManager getEventManager() { return eventMgr; }

    public FoglioRiepilogativoManager getFoglioRiepilogativoMng() {return foglioRiepilogativoMng;}

}
