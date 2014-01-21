package controllers;

import factories.CustomerFactory;
import factories.VehicleFactory;
import models.CustomerModel;
import models.VehicleModel;
import views.CustomerView;
import views.VehicleView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Jasper
 * Class: This controller makes it able to modify and control fields through the view
 */
public class CustomerController extends IpsenController
{
    private CustomerView view; //class variable for the view
    private CustomerFactory factory; //class variable for the factory

    /**
     * Constructor of the Controller, this wil create view and factory objects
     */

    public CustomerController() {
        this.view = new CustomerView(this); //create view object
        this.factory = new CustomerFactory(this.view); //create factory object
        this.view.init(); //init the table content
    }

    /**
     * Get the view object
     *
     * @return view
     */
    public CustomerView getView() {
        return this.view;
    }

    /**
     * Get the factory object
     *
     * @return factory
     */

    public CustomerFactory getFactory() {
        return this.factory;
    }

    /**
     *
     * Convert an American formatted date to a dutch format
     *
     * @param americanDate
     * @return formatted-date
     */

    public String convertToDutchDate(String americanDate)
    {
        if(americanDate.length() > 0) //variable is not empty
        {
            String[] exploded;
            exploded = americanDate.split("-"); //split date on -

            return exploded[2] + "-" + exploded[1] + "-" + exploded[0]; //return date
        }
        else
        {
            return ""; //nothing given so return nothing
        }
    }

    @Override
    /**
     * Save method override
     * @param formFields - Form fields with data
     * @return error code for correct message display
     */
    public int save(HashMap<String, Object> formFields) {
        Iterator it = formFields.entrySet().iterator();

        HashMap<String, Object> _tmp = new HashMap<String, Object>();
        int id = 0;

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            _tmp.put(pairs.getKey().toString(), pairs.getValue().toString());

            if(pairs.getKey().toString().equals("id"))
                id = Integer.parseInt(pairs.getValue().toString());

            //add fields that are not required to complete the form
            if(pairs.getKey().toString().equals("insertion") || pairs.getKey().toString().equals("company") || pairs.getKey().toString().equals("phone")) {
                it.remove(); // avoids a ConcurrentModificationException
                continue;
            }

            //check fields for length
            if(pairs.getValue().toString().length() == 0) {
                return 0;
            }

            it.remove(); // avoids a ConcurrentModificationException
        }

        //check if id equals 0, if so the record is new and should be added to the database
        CustomerModel model; //create new model
        if(id == 0) {
            model = this.factory.addCustomer(_tmp);
            if(model == null)
                return 2;

            if(this.factory.writeChangesFor(model) == 2)
                return 2;
        } else { //model for edit
            model = this.factory.getCustomer(id);
            this.factory.updateCustomer(model, _tmp);
            this.factory.writeChangesFor(model);
        }

        return 1;
    }
}
