package factories;

import data.ReservationTable;
import models.ReservationModel;
import org.joda.time.*;
import views.ReservationView;

import java.util.*;

/**
 * User: Chris
 * Class: This factory creates and is responsible for all the models
 */
public class ReservationFactory
{
    private List<ReservationModel> reservations;
    private ReservationView view;

    /**
     * Class Constructor
     * @param view
     */
    public ReservationFactory(ReservationView view) {
        this.reservations = new ArrayList<ReservationModel>();
        this.view = view;
    }

    /**
     * get all the reservations from the database, except when they already exists in the applicatie, then return the set
     * @param view
     * @return list of reservations
     */
    public List<ReservationModel> getReservations(String view) {
        if(reservations.size() > 0)
            return this.reservations;

        ArrayList<HashMap<String, Object>> results;
        ReservationTable tbl = new ReservationTable();
        results = tbl.getReservations(view);

        //loop through all the rows in the hashmap
        for(HashMap map : results) {
            Iterator it = map.entrySet().iterator();
            ReservationModel _model = new ReservationModel(this.view);
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                //add value to the model
                this.addField(pairs.getKey(), pairs.getValue(), _model, "view");

                it.remove();
            }
            reservations.add(_model);
        }

        //return the reservations
        return reservations;
    }

    /**
     * Get a reservation from the list of reservations
     * @param id
     * @return model of reservation
     */
    public ReservationModel getSingleReservationAdvanced(int id) {
        List<ReservationModel> reservations = this.getReservations("reservations");

        for(ReservationModel rm : reservations)
        {
            if(rm.getId() == id) return rm;
        }

        //if not found, return null
        return null;
    }

    /**
     * Get a reservation from the database
     * @param id
     * @return model of reservation
     */
    public ReservationModel getSingleReservation(int id) {
        ArrayList<HashMap<String, Object>> results;
        ReservationTable tbl = new ReservationTable();
        results = tbl.getReservation(id);

        for(HashMap map : results) {
            Iterator it = map.entrySet().iterator();
            ReservationModel _model = new ReservationModel(this.view);
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                this.addField(pairs.getKey(), pairs.getValue(), _model, "");

                it.remove();
            }
            return _model;
        }

        return null;
    }

    public HashMap<Integer, Object> getPaymentTypes() {
        ArrayList<HashMap<String, Object>> results;
        ReservationTable tbl = new ReservationTable();
        results = tbl.getPaymentTypes();

        HashMap<Integer, Object> cboxFill = new HashMap<Integer, Object>();

        for(HashMap map : results) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                if(pairs.getKey().toString().equals("id"))
                    cboxFill.put(Integer.parseInt(pairs.getValue().toString()), ((Map.Entry) it.next()).getValue().toString());

                it.remove();
            }
        }

        return cboxFill;
    }

    public HashMap<Integer, Object> getReservationStatus() {
        ArrayList<HashMap<String, Object>> results;
        ReservationTable tbl = new ReservationTable();
        results = tbl.getReservationStatus();

        HashMap<Integer, Object> cboxFill = new HashMap<Integer, Object>();

        for(HashMap map : results) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                if(pairs.getKey().toString().equals("id"))
                    cboxFill.put(Integer.parseInt(pairs.getValue().toString()), ((Map.Entry) it.next()).getValue().toString());

                it.remove();
            }
        }

        return cboxFill;
    }

    public List<ReservationModel> getPrepareListFor(DateTime date) {
        List<ReservationModel> _tmpList = new ArrayList<ReservationModel>();

        ArrayList<HashMap<String, Object>> results;
        ReservationTable tbl = new ReservationTable();
        results = tbl.getPrepareListFor(date);

        for(HashMap map : results) {
            Iterator it = map.entrySet().iterator();
            ReservationModel _model = new ReservationModel(this.view);
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                this.addField(pairs.getKey(), pairs.getValue(), _model, "view");

                it.remove();
            }
            _tmpList.add(_model);
        }

        return _tmpList;
    }

    public ReservationModel getReservation(Integer id) {
        for(ReservationModel model : this.reservations) {
            if(model.getId() == id)
                return model;
        }
        return null;
    }

    public int writeChangesFor(ReservationModel model) {
        //write changes to database
        ReservationTable table = new ReservationTable();
        table.writeChangesFor(model);

        return 0;
    }

    public void updateReservation(ReservationModel model, HashMap<String, Object> tmp) {
        //update the model
        Iterator it = tmp.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            this.addField(pairs.getKey(), pairs.getValue(), model, "");

            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public ReservationModel addReservation(HashMap<String, Object> tmp) {
        ReservationModel _model = new ReservationModel(this.view);
        Iterator it = tmp.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            if(!this.addField(pairs.getKey(), pairs.getValue(), _model, ""))
                return null;

            it.remove(); //avoids a ConcurrentModificationException
        }

        //add model to the collection
        this.reservations.add(_model);

        return _model;
    }

    /**
     *  Get Reservations between dates
     **/
    public List<ReservationModel> getReservations(String from, String to) {
        reservations.clear();

        ArrayList<HashMap<String, Object>> results;
        ReservationTable tbl = new ReservationTable();
        results = tbl.getReservations(from, to);

        for(HashMap map : results) {
            Iterator it = map.entrySet().iterator();
            ReservationModel _model = new ReservationModel(this.view);
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                this.addField(pairs.getKey(), pairs.getValue(), _model, "view");

                it.remove();
            }
            reservations.add(_model);
        }

        return reservations;
    }

    /**
     * Fill a field in the model with a value
     * @param key
     * @param value
     * @param model
     * @param type
     * @return true or false
     */
    private boolean addField(Object key, Object value, ReservationModel model, String type) {
        //validate the value
        Object ret = model.validate(key, value);
        if(ret.equals(false))
            return false;

        //check the key to set the right field in the model
        if(key.toString().equals("id")) {
            model.setId(Integer.valueOf(value.toString()).intValue());
        }
        if(key.toString().equals("startdate")) {
            String[] date = value.toString().split("-");
            date[2] = date[2].split(" ")[0];
            DateTime dt = new DateTime(date[0]+"-"+date[1]+"-"+date[2]+"T00:00:00.0");
            model.setStartdate(new DateTime(dt));
        }
        if(key.toString().equals("enddate")) {
            String[] date = value.toString().split("-");
            date[2] = date[2].split(" ")[0];
            DateTime dt = new DateTime(date[0]+"-"+date[1]+"-"+date[2]+"T00:00:00.0");
            model.setEnddate(new DateTime(dt));
        }

        //if its for the view add these things to the model
        if(type.equals("view")) {
            if(key.toString().equals("brand")) {
                model.setBrand(value.toString());
            }
            if(key.toString().equals("type")) {
                model.setType(value.toString());
            }
            if(key.toString().equals("licenseplate")) {
                model.setLicenseplate(value.toString());
            }
            if(key.toString().equals("name")) {
                model.setName(value.toString());
            }
            if(key.toString().equals("company")) {
                model.setCompany(value.toString());
            }
            if(key.toString().equals("firstname")) {
                model.setFirstname(value.toString());
            }
            if(key.toString().equals("insertion")) {
                model.setInsertion(value.toString());
            }
            if(key.toString().equals("lastname")) {
                model.setLastname(value.toString());
            }
            if(key.toString().equals("payment")) {
                model.setPayment(value.toString());
            }
            if(key.toString().equals("status")) {
                model.setStatus(value.toString());
            }
        }
        //else add these
        else {
            if(key.toString().equals("vehicle_id")) {
                model.setVehicle_id(Integer.valueOf(value.toString()).intValue());
            }
            if(key.toString().equals("customer_id")) {
                model.setCustomer_id(Integer.valueOf(value.toString()).intValue());
            }
            if(key.toString().equals("payment_type_id")) {
                model.setPayment_type_id(Integer.valueOf(value.toString()).intValue());
            }
            if(key.toString().equals("status_id")) {
                model.setStatus_id(Integer.valueOf(value.toString()).intValue());
            }
        }

        return true;
    }

    /**
     * Change the status of a reservation, i.e. after making an invoice
     * @param model
     * @return true or false
     */
    public boolean changeStatus(ReservationModel model) {
        ReservationTable tbl = new ReservationTable();
        boolean result = tbl.changeStatus(model);
        //test
        return result;
    }
}
