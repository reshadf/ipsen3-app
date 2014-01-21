package factories;

import data.CheckupTable;
import data.VehicleTable;
import models.CheckupModel;
import models.VehicleModel;
import org.joda.time.DateTime;
import views.CheckupView;

import java.util.*;

/**
 * User: Jasper
 * Class: This factory uses the models from the modelclasses & creates them
 */
public class CheckupFactory
{
    private CheckupView view; //the view
    private List<CheckupModel> checkups; //list of checkups

    /**
     *
     * Constuctor of our checkup factory
     *
     * @param view
     */

    public CheckupFactory(CheckupView view) {
        this.view = view;
        this.checkups = new ArrayList<CheckupModel>();
    }

    /**
     *
     * Get all checkups by vehicle id
     *
     * @param id
     * @return
     */

    public List<CheckupModel> getCheckups(int id) {
        if(checkups.size() > 0) //no bitches found, eh I mean checkups
            return this.checkups;

        ArrayList<HashMap<String, Object>> results; //result
        CheckupTable tbl = new CheckupTable(); //create table object
        results = tbl.getCheckupsByVehicleId(id); //put the results in our arraylist

        if(results == null) //noes no results, fuckers ....
            return null;

        for(HashMap map : results) { //for each result in the map
            Iterator it = map.entrySet().iterator(); //iterator Design Pattern, getting horny already
            CheckupModel _model = new CheckupModel(this.view);
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                this.addField(pairs.getKey(), pairs.getValue(), _model);

                it.remove();
            }
            checkups.add(_model);
        }

        return checkups;
    }

    /**
     *
     * Get checkup model by id
     *
     * @param id
     * @return
     */

    public CheckupModel getCheckup(int id) {
        for(CheckupModel model : this.checkups) {
            if(model.getId() == id)
                return model;
        }
        return null;
    }

    /**
     *
     * Update vehicle
     *
     * @param model
     * @param data
     */

    public void updateVehicle(CheckupModel model, HashMap<String, Object> data) {
        //update the model
        Iterator it = data.entrySet().iterator(); //oeh lala mmmm

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            this.addField(pairs.getKey(), pairs.getValue(), model);

            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    /**
     *
     * Add new checkup to list
     *
     * @param tmp
     * @return
     */

    public CheckupModel addCheckup(HashMap<String, Object> tmp) {
        CheckupModel _model = new CheckupModel(this.view);
        Iterator it = tmp.entrySet().iterator(); // u probably still like checkups

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            if(!this.addField(pairs.getKey(), pairs.getValue(), _model))
                return null;

            it.remove(); // avoids a ConcurrentModificationException
        }

        //add model to the collection
        this.checkups.add(_model);

        return _model;
    }

    /**
     *
     * Write changes for checkup model
     *
     * @param model
     * @return
     */

    public int writeChangesFor(CheckupModel model) {
        //write changes to the database
        CheckupTable tbl = new CheckupTable();
        tbl.writeChangesFor(model);

        return 0;
    }

    /**
     *
     * add fields
     *
     * @param key
     * @param value
     * @param model
     * @return
     */

    private boolean addField(Object key, Object value, CheckupModel model) {
        Object ret = model.validate(key, value);
        if(ret.equals(false))
            return false;

        if(!ret.toString().equals("false") && !ret.toString().equals("true")) {
            value = ret.toString();
        }

        if(key.toString().equals("id")) {
            model.setId(Integer.valueOf(value.toString()).intValue()); //convert to int
        }
        if(key.toString().equals("date_detection")) {
            String[] date = value.toString().split("-");
            date[2] = date[2].split(" ")[0];
            DateTime dt = new DateTime(date[0]+"-"+date[1]+"-"+date[2]+"T00:00:00.0"); //format time
            model.setDate_detection(new DateTime(dt));
        }
        if(key.toString().equals("distance_driven")) { //distance driven
            model.setDistance_driven(new Double(value.toString()));
        }
        if(key.toString().equals("cleaned")) {
            boolean flag = (value.toString().equals("1")) ? true : false;
            model.setCleaned(flag);
        }
        if(key.toString().equals("damage")) {
            model.setDamage(value.toString());
        }
        if(key.toString().equals("vehicle_id")) {
            model.setVehicle_id(Integer.valueOf(value.toString()));
        }

        return true;
    }

    /**
     *
     * Delete checkup
     *
     * @param model
     */

    public void delete(CheckupModel model)
    {
        CheckupTable tbl = new CheckupTable();
        tbl.deleteObject(model);
    }
}
