package controllers;

import models.MainModel;
import views.MainView;

/**
 * User: Jasper
 * Class: This controller controls
 */
public class MainController extends IpsenController
{
    private MainModel model;
    private MainView view;
    /**
     * constructor
     */
    public MainController()
    {
        this.model = new MainModel();
        this.view = new MainView(this.model, this);
    }

    /**
     * returns view
     */
    public MainView getView()
    {
        return this.view;
    }
}
