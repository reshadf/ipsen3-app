package controllers;

import factories.CustomerFactory;
import factories.ReservationFactory;
import factories.VehicleFactory;
import models.ReservationModel;
import views.ReservationView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Chris
 * Class: This controller makes it able to modify and control fields through the view
 */
public class ReservationController
{
    private ReservationView view;
    private ReservationFactory factory;
    private VehicleFactory vehicleFactory;
    private CustomerFactory customerFactory;

    /**
     * Class Constructor
     */
    public ReservationController() {
        this.view = new ReservationView(this);
        this.factory = new ReservationFactory(this.view);
        this.vehicleFactory = new VehicleFactory(null);
        this.customerFactory = new CustomerFactory(null);
        this.view.init();
    }

    /**
     * Get the reservation factory
     * @return Reservation Factory
     */
    public ReservationFactory getFactory() {
        return factory;
    }

    /**
     * Get the vehicle factory
     * @return Vehicle Factory
     */
    public VehicleFactory getVehicleFactory() {
        return vehicleFactory;
    }

    /**
     * Get the customer factory
     * @return Customer Factory
     */
    public CustomerFactory getCustomerFactory() {
        return customerFactory;
    }

    /**
     * Get the reservation view
     * @return Reservation View
     */
    public ReservationView getView() {
        return view;
    }

    /**
     * Method to process data filled in through the form, and push it to the database
     * @param formFields - Data from the form, checked and processed to save
     * @return error code 0, 1 or 2
     */
    public int save(HashMap<String, Object> formFields) {
        Iterator it = formFields.entrySet().iterator();

        HashMap<String, Object> _tmp = new HashMap<String, Object>();
        int id = 0;

        //go on while there is a next element
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            _tmp.put(pairs.getKey().toString(), pairs.getValue().toString());

            if(pairs.getKey().toString().equals("id"))
                id = Integer.parseInt(pairs.getValue().toString());

            //check fields for length
            if(pairs.getValue().toString().length() == 0) {
                return 0;
            }

            it.remove(); // avoids a ConcurrentModificationException
        }

        //check if id equals 0, if so the record is new and should be added to the database
        ReservationModel model;
        if(id == 0) {
            model = this.factory.addReservation(_tmp);
            if(model == null)
                return 2;

            if(this.factory.writeChangesFor(model) == 2)
                return 2;
        } else {
            model = this.factory.getReservation(id);
            this.factory.updateReservation(model, _tmp);
            this.factory.writeChangesFor(model);
        }

        return 1;
    }
}
