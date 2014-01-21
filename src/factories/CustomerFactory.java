package factories;

import data.CustomerTable;
import data.VehicleTable;
import models.CustomerModel;
import models.VehicleModel;
import views.CustomerView;

import java.util.*;

/**
 * User: Jasper
 * Class: This factory uses the models from the modelclasses & creates them
 */

public class CustomerFactory
{
    private CustomerView view; //view class variable
    private List<CustomerModel> customers; //customer model list

    /**
     *
     * Factory constructor, a view is needed for the creation of models
     *
     * @param view
     */

    public CustomerFactory(CustomerView view) {
        this.view = view; //define view
        this.customers = new ArrayList<CustomerModel>(); // create new list for customer
    }

    /**
     * Get a list of all customers
     *
     * @return list of customers
     */

    public List<CustomerModel> getCustomers() {
        if(customers.size() > 0) //customerlist already filled
            return this.customers;

        ArrayList<HashMap<String, Object>> results;
        CustomerTable tbl = new CustomerTable();
        results = tbl.getCustomers(); //fill results

        for(HashMap map : results) { //create object for all items
            Iterator it = map.entrySet().iterator();
            CustomerModel _model = new CustomerModel(this.view);
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                this.addField(pairs.getKey(), pairs.getValue(), _model);

                it.remove();
            }
            customers.add(_model); //add objects to list
        }

        return customers;
    }

    /**
     * Get customer by id
     *
     * @param id
     * @return customer model object
     */

    public CustomerModel getCustomer(int id) {
        for(CustomerModel model : this.customers) {
            if(model.getId() == id) //if id matches return the model
                return model;
        }
        return null;
    }

    /**
     *
     * Update an existing customer
     *
     * @param model
     * @param data
     */

    public void updateCustomer(CustomerModel model, HashMap<String, Object> data) {
        //update the model
        Iterator it = data.entrySet().iterator();

        while (it.hasNext()) { //update each field using an iterator
            Map.Entry pairs = (Map.Entry)it.next();

            this.addField(pairs.getKey(), pairs.getValue(), model);

            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    /**
     * Add customer
     *
     * @param tmp
     * @return model
     */

    public CustomerModel addCustomer(HashMap<String, Object> tmp) {
        CustomerModel _model = new CustomerModel(this.view); //create model
        Iterator it = tmp.entrySet().iterator();

        while (it.hasNext()) { //for each field
            Map.Entry pairs = (Map.Entry)it.next();

            if(!this.addField(pairs.getKey(), pairs.getValue(), _model))
                return null;

            it.remove(); // avoids a ConcurrentModificationException
        }

        //add model to the collection
        this.customers.add(_model); //add the model to the list

        return _model;
    }

    /**
     * Update a database entry by using a model
     *
     * @param model
     * @return
     */

    public int writeChangesFor(CustomerModel model) {
        //write changes to the database
        CustomerTable tbl = new CustomerTable();
        tbl.writeChangesFor(model); //write changes

        return 0;
    }

    /**
     *
     * Fields for iterator
     *
     * @param key
     * @param value
     * @param model
     * @return true or false
     */

    private boolean addField(Object key, Object value, CustomerModel model) {
        boolean ret = model.validate(key, value);
        if(!ret)
            return false;

        if(key.toString().equals("id")) {
            model.setId(Integer.valueOf(value.toString()).intValue());
        }
        if(key.toString().equals("email")) {
            model.setEmail(value.toString());
        }
        if(key.toString().equals("firstname")) { //firstname
            model.setFirstname(value.toString());
        }
        if(key.toString().equals("insertion")) {
            model.setInsertion(value.toString());
        }
        if(key.toString().equals("lastname")) {
            model.setLastname(value.toString());
        }
        if(key.toString().equals("company")) {
            model.setCompany(value.toString());
        }
        if(key.toString().equals("kvknr")) {
            model.setCompany(value.toString());
        }
        if(key.toString().equals("phone")) { //phone
            model.setPhone(value.toString());
        }
        if(key.toString().equals("address")) {
            model.setAddress(value.toString());
        }
        if(key.toString().equals("zip")) {
            model.setZip(value.toString());
        }
        if(key.toString().equals("city")) { //city
            model.setCity(value.toString());
        }
        if(key.toString().equals("birthdate")) {
            model.setBirthdate(value.toString());
        }
        if(key.toString().equals("passportnumber")) {
            model.setPassportnumber(value.toString());
        }

        return true;
    }

    /**
     * Delete customer from database using a model
     *
     * @param model
     */

    public void delete(CustomerModel model)
    {
        CustomerTable tbl = new CustomerTable();
        tbl.deleteObject(model);
    }
}
