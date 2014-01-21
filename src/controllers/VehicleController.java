package controllers;

import factories.CategoryFactory;
import factories.VehicleFactory;
import models.VehicleModel;
import views.VehicleView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Chris
 * Class: This controller makes it able to modify and control fields through the view
 */
public class VehicleController extends IpsenController
{
    private VehicleView view;
    private VehicleFactory factory;       //create and set their names
    private CategoryController categoryController;

    public VehicleController() {
        this.view = new VehicleView(this);         // gives them values
        this.factory = new VehicleFactory(this.view);

        categoryController = new CategoryController();
        this.view.init();
    }

    /**
     * Get the category factory
     * @return category Factory
     */
    public CategoryFactory getCategoryFactory(){ return categoryController.getFactory(); }

    /**
     * Get the vehicle view
     * @return Vehicle view
     */
    public VehicleView getView() {
        return this.view;               // shows you it
    }

    /**
     * Get the vehicle factory
     * @return Vehicle Factory
     */
    public VehicleFactory getFactory() {
        return this.factory;           // factory yeah
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
            if(pairs.getKey().toString().equals("airco") || pairs.getKey().toString().equals("towbar")) {
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
        VehicleModel model;
        if(id == 0) {
            model = this.factory.addVehicle(_tmp);
            if(model == null)
                return 2;

            if(this.factory.writeChangesFor(model) == 2)
                return 2;
        } else {
            model = this.factory.getVehicle(id);
            this.factory.updateVehicle(model, _tmp);
            this.factory.writeChangesFor(model);
        }

        return 1;
    }
}
