package catering.businesslogic.shiftManagement;

import catering.businesslogic.CatERing;
import catering.businesslogic.KitchenJobManagement.SummarySheet;
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

    public BoardManager() {}

    // getters and setters
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    // event sender methods
   /* public void addReceiver(BoardPersistence er) {}

   public void removeReceiver(BoardPersistence er) {}
   */

   private void notifyBoardCreated(Board board) {}

    // operations methods
   public Board createBoard(String name, String event, ArrayList<Shift> shifts) throws UseCaseLogicException {
       if (CatERing.getInstance().getUserManager().getUser().isChef()) {
           this.board = new Board(name,event, shifts);
         //  notifyBoardCreated(this.board);
           return this.board;
       }
       else
           throw new UseCaseLogicException();
   }

   public ArrayList<Shift> showBoard() throws UseCaseLogicException {
       User user = CatERing.getInstance().getUserManager().getUser();
       if (user.isChef()) {
           return board.showBoard();
       } else
           throw new UseCaseLogicException();
   }

   public boolean isOrganizer(User user) {
       return true;
   }
}
