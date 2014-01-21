package controllers;

import javax.swing.*;
import java.util.HashMap;

/**
 * User: Chris
 * Class: used as super class for every controller. Contains methodes for more than just 1 controller
 * Add more methods along the way
 * */
abstract class IpsenController {

    /**
     * Constructor for the controller class
     */
    public IpsenController() {

    }

    /**
     * Save method for forms, used by all the *EditViews
     * @param formFields - Form fields with data
     * @return error code for correct message display
     */
    public int save(HashMap<String, Object> formFields) {
        return 0;
    }
}
