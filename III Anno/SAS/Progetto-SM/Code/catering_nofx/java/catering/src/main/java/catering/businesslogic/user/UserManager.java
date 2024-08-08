package catering.businesslogic.user;

public class UserManager {
    private User user;

    public void fakeLogin(String username) //TODO: bisogna implementare il login vero!
    {
        this.user = User.loadUser(username);
    }

    public User getUser() {
        return this.user;
    }
}
