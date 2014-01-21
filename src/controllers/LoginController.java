package controllers;

import helpers.ACL;
import helpers.MessageQueue;
import models.LoginModel;
import views.LoginView;

/**
 * User: Jasper
 * Class: This controller to check login process of the application
 */
public class LoginController extends IpsenController {

    private LoginModel model;
    private LoginView view;
    /**
     * constructor
     */
    public LoginController()
    {
        this.model = new LoginModel();
        this.view = new LoginView(this);
    }

    public LoginView getView()
    {
        return this.view;
    }

    /**
     * Set userrights when login
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username, String password)
    {
        if(username.trim().length() == 0) {
            MessageQueue.getInstance().addMessage("Gebruikersnaam is verplicht!", 1);
        }

        if(password.trim().length() == 0) {
            MessageQueue.getInstance().addMessage("Wachtwoord is verplicht!", 1);
        }

        if(MessageQueue.getInstance().getMessages().size() > 0)
            return false;

        int tempId = this.model.getUserIdByCredentials(username, password);
        ACL.getInstance().setRights(tempId);

        boolean result = this.model.login(username, password);
        if(result) {
            return result;
        } else {
            MessageQueue.getInstance().addMessage("Foutieve gegevens, probeer het opnieuw!", 1);
        }

        return false;
    }
}
