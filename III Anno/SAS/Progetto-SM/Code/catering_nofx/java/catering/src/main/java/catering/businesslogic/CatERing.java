package catering.businesslogic;

import catering.businesslogic.KitchenJobManagement.JobManager;
import catering.businesslogic.KitchenJobManagement.SummarySheetManager;
import catering.businesslogic.event.EventManager;
import catering.businesslogic.menu.MenuManager;
import catering.businesslogic.recipe.RecipeManager;
import catering.businesslogic.shiftManagement.BoardManager;
import catering.businesslogic.user.UserManager;
import catering.persistence.JobPersistence;
import catering.persistence.MenuPersistence;
import catering.persistence.SummarySheetPersistence;

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

    private SummarySheetManager sheetMgr;
    private JobManager jobMgr;
    private BoardManager boardMgr;
    private MenuPersistence menuPersistence;
    private SummarySheetPersistence summarySheetPersistence;
    private JobPersistence jobPersistence;

    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new RecipeManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        sheetMgr = new SummarySheetManager();
        jobMgr = new JobManager();
        boardMgr = new BoardManager();
        menuPersistence = new MenuPersistence();
        summarySheetPersistence = new SummarySheetPersistence();
        jobPersistence = new JobPersistence();

        menuMgr.addEventReceiver(menuPersistence);
        sheetMgr.addEventReceiver(summarySheetPersistence);
        jobMgr.addReceiver(jobPersistence);
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

    public SummarySheetManager getSheetMgr() {return sheetMgr;}

    public JobManager getJobMgr() {return jobMgr;}

    public BoardManager getBoardMgr() {return boardMgr;}
}
