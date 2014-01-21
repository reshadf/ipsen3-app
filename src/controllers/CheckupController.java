package controllers;

import factories.CheckupFactory;
import factories.VehicleFactory;
import models.CheckupModel;
import models.VehicleModel;
import views.CheckupView;
import views.VehicleView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Jasper
 * Class: This controller makes it able to modify and control fields through the view
 */
public class CheckupController extends IpsenController
{
    private CheckupView view;
    private CheckupFactory factory;
    private VehicleFactory vehicleFactory;
    /**
     * constructor
     */
    public CheckupController(Object... args) {
        this.view = new CheckupView(this);
        this.factory = new CheckupFactory(this.view);
        this.vehicleFactory = new VehicleFactory(null);
        this.view.init(args);
    }

    /**
     * returns view
     */
    public CheckupView getView() {
        return this.view;
    }

    /**
     * returns view
     */
    public CheckupFactory getFactory() {
        return this.factory;
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
            if(pairs.getKey().toString().equals("cleaned")) {
                it.remove(); // avoids a ConcurrentModificationException
                continue;
            }
            //add fields that are not required to complete the form
            if(pairs.getKey().toString().equals("damage")) {
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
        CheckupModel model;
        if(id == 0) {
            model = this.factory.addCheckup(_tmp);
            if(model == null)
                return 2;
            System.out.println(2);
            if(this.factory.writeChangesFor(model) == 2)
                return 2;
        } else {
            model = this.factory.getCheckup(id);
            this.factory.updateVehicle(model, _tmp);
            this.factory.writeChangesFor(model);
        }
        System.out.println(1);

        return 1;
    }

    public VehicleFactory getVehicleFactory() {
        return this.vehicleFactory;
    }
}
