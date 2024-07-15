package catering.businesslogic.shiftManagement;

import catering.businesslogic.CatERing;
import catering.businesslogic.KitchenJobManagement.SummarySheetEventReceiver;
import catering.businesslogic.KitchenJobManagement.SummarySheetManager;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.user.User;

import java.util.ArrayList;
import java.util.List;

public class BoardManager {
    public Board board;

    // constructor
    public BoardManager(Board board) {
        this.board = board;
    }

    // getters and setters
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    // event sender methods
    public void addReceiver(SummarySheetManager er) {}

   public void removeReceiver(SummarySheetManager er) {}

   private void notifyBoardCreated(Board board) {}

    // operations methods
   public Board createBoard(String name, String event, ArrayList<Shift> shifts) {
        return new Board(name, event, shifts);
   }

   public ArrayList<Shift> showBoard(String event) throws UseCaseLogicException {
       User user = CatERing.getInstance().getUserManager().getUser();
       if (user.isChef()) {
           return board.showBoard(event);
       } else
           throw new UseCaseLogicException();
   }

   public boolean isOrganizer(User user) {
       return true;
   }
}
