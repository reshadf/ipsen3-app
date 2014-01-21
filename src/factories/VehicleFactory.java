package factories;

import data.VehicleTable;
import models.VehicleModel;
import org.joda.time.DateTime;
import views.VehicleView;

import java.util.*;

/**
 * User: Floris
 * Class: This factory uses the models from the modelclasses & creates them
 */

public class VehicleFactory
{
    private VehicleView view;
    private List<VehicleModel> vehicles;

    /**
     * Class Constructor
     *
     * @param view
     */
    public VehicleFactory(VehicleView view) {
        this.view = view;
        this.vehicles = new ArrayList<VehicleModel>();
    }

    /**
     * Get the vehicles from the database and make models of them.
     * Application can called this method to retrieve all the vehicles.
     *
     * @return List of vehicles
     */
    public List<VehicleModel> getVehicles() {
        if(vehicles.size() > 0)
            return this.vehicles;

        ArrayList<HashMap<String, Object>> results;
        VehicleTable tbl = new VehicleTable();
        results = tbl.getVehicles();

        for(HashMap map : results) {
            Iterator it = map.entrySet().iterator();
            VehicleModel _model = new VehicleModel(this.view);
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                this.addField(pairs.getKey(), pairs.getValue(), _model);

                it.remove();
            }
            vehicles.add(_model);
        }

        return vehicles;
    }

    /**
     * Get vehicles between two dates from the database
     *
     * @param from
     * @param till
     * @param vehicle_id
     * @return List of vehicles
     */
    public List<VehicleModel> getVehiclesExcludedBetween(DateTime from, DateTime till, int vehicle_id) {
        List<VehicleModel> tmpList = new ArrayList<VehicleModel>();

        ArrayList<HashMap<String, Object>> results;
        VehicleTable tbl = new VehicleTable();
        results = tbl.getVehiclesExcludedBetween(from, till, vehicle_id);

        for(HashMap map : results) {
            Iterator it = map.entrySet().iterator();
            VehicleModel _model = new VehicleModel(this.view);
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                this.addField(pairs.getKey(), pairs.getValue(), _model);

                it.remove();
            }
            tmpList.add(_model);
        }

        return tmpList;
    }

    /**
     * Get a vehicle from the collection that has already been retrieved from the database.
     * This is done to prevent unnecessary calls to the database and faster loading
     *
     * @param id
     * @return Model of the requested vehicle or else null if the vehicle does not exist.
     */
    public VehicleModel getVehicle(int id) {
        for(VehicleModel model : this.vehicles) {
            if(model.getId() == id)
                return model;
        }
        return null;
    }

    /**
     * Update a vehicle model with the data that has been given through the form.
     *
     * @param model
     * @param data
     */
    public void updateVehicle(VehicleModel model, HashMap<String, Object> data) {
        //update the model
        Iterator it = data.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            this.addField(pairs.getKey(), pairs.getValue(), model);

            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    /**
     * Add a vehicle to the collection, used to build the list of vehicles
     *
     * @param tmp
     * @return Model of the vehicle
     */
    public VehicleModel addVehicle(HashMap<String, Object> tmp) {
        VehicleModel _model = new VehicleModel(this.view);
        Iterator it = tmp.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            if(!this.addField(pairs.getKey(), pairs.getValue(), _model))
                return null;

            it.remove(); // avoids a ConcurrentModificationException
        }

        //add model to the collection
        this.vehicles.add(_model);

        return _model;
    }

    /**
     * Writes changes to the model and passes it to the table to store in the database
     *
     * @param model
     * @return 0;
     */
    public int writeChangesFor(VehicleModel model) {
        //write changes to the database
        VehicleTable tbl = new VehicleTable();
        tbl.writeChangesFor(model);

        return 0;
    }

    /**
     * Method to set data to each field in the model
     *
     * @param key
     * @param value
     * @param model
     * @return true if value is valid, false if it is invalid
     */
    private boolean addField(Object key, Object value, VehicleModel model) {
        Object ret = model.validate(key, value);
        if(ret.equals(false))
            return false;

        if(!ret.toString().equals("false") && !ret.toString().equals("true")) {
            value = ret.toString();
        }

        if(key.toString().equals("id")) {
            model.setId(Integer.valueOf(value.toString()).intValue());
        }
        if(key.toString().equals("brand")) {
            model.setBrand(value.toString());
        }
        if(key.toString().equals("type")) {
            model.setType(value.toString());
        }
        if(key.toString().equals("licenseplate")) {
            model.setLicenseplate(value.toString());
        }
        if(key.toString().equals("description")) {
            model.setDescription(value.toString());
        }
        if(key.toString().equals("airco")) {
            boolean flag = (value.toString().equals("1")) ? true : false;
            model.setAirco(flag);
        }
        if(key.toString().equals("towbar")) {
            boolean flag = (value.toString().equals("1")) ? true : false;
            model.setTowbar(flag);
        }
        if(key.toString().equals("seats")) {
            model.setSeats(Integer.valueOf(value.toString()).intValue());
        }
        if(key.toString().equals("hourly_rent")) {
            model.setHourly_rent(Double.valueOf(value.toString()).doubleValue());
        }
        if(key.toString().equals("category_id")) {
            model.setCategory_id(Integer.valueOf(value.toString()));
        }

        return true;
    }

    /**
     * Delete a vehicle from the database
     *
     * @param model
     */
    public void delete(VehicleModel model)
    {
        VehicleTable tbl = new VehicleTable();
        tbl.deleteObject(model);
    }

    /**
     * Get a vehicle from the database by ID
     *
     * @param id
     * @return Model of the vehicle
     */
    public VehicleModel getSingleVehicle(int id) {
        ArrayList<HashMap<String, Object>> results;
        VehicleTable tbl = new VehicleTable();
        results = tbl.getVehicle(id);

        for(HashMap map : results) {
            Iterator it = map.entrySet().iterator();
            VehicleModel _model = new VehicleModel(this.view);
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                this.addField(pairs.getKey(), pairs.getValue(), _model);

                it.remove();
            }
            return _model;
        }

        return null;
    }
}
