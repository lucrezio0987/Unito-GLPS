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

    public BoardManager(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void addReceiver(SummarySheetManager er) {

   }

   public void removeReceiver(SummarySheetManager er) {

   }

   private void notifyBoardCreated(Board board) {

   }

   public Board createBoard(String event) {
       return null;
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
