package controllers;

import views.ManagementView;

/**
 * User: Floris
 * Class: This controller makes it able to modify and control fields through the view
 */
public class ManagementController extends IpsenController {

    private ManagementView view;

    /**
     * constructor
     */
    public ManagementController()
    {
        this.view = new ManagementView(this);
    }

    /**
     * returns view of management
     */
    public ManagementView getView()
    {
        return this.view;
    }
}
