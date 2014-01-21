package controllers;

import factories.RightFactory;
import models.RightModel;
import views.AccessRightView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Reinier
 * Class: This controller makes it able to modify and control fields through the view
 */
public class RightController extends IpsenController
{
    private AccessRightView view;
    private RightFactory factory;

    /**
     * Class Constructor
     * @param args
     */
    public RightController(Object... args)
    {
        this.view = new AccessRightView(this);
        this.factory = new RightFactory(this.view);
        this.view.init(args);
    }

    /**
     * Get the accesrightview factory
     * @return accesrightview Factory
     */
    public AccessRightView getView()
    {
        return this.view;
    }

    /**
     * Get the right factory
     * @return right Factory
     */
    public RightFactory getFactory()
    {
        return this.factory;
    }

    /**
     * Method to change rights to certain group
     * @param groupId
     * @param checkedItems
     * @param uncheckedItems
     */
    public void save(int groupId, ArrayList<Integer>checkedItems, ArrayList<Integer> uncheckedItems)
    {
        this.getFactory().updateRights(groupId, checkedItems);
        this.getFactory().removeRights(groupId, uncheckedItems);
    }

}
