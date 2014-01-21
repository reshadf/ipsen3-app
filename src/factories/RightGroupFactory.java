package factories;

import data.RightGroupTable;
import models.RightGroupModel;
import views.AccessRightGroupView;

import java.util.*;

/**
 * User: Jasper
 * Class: This factory uses the models from the modelclasses & creates them
 */

public class RightGroupFactory
{
    private AccessRightGroupView view;
    private List<RightGroupModel> rightgroup;

    /**
     *
     * Constructor of our factory
     *
     * @param view
     */

    public RightGroupFactory(AccessRightGroupView view)
    {
        this.view = view;
        this.rightgroup = new ArrayList<RightGroupModel>();
    }

    /**
     *
     * Get all rightgroups
     *
     * @return rightgroup
     */

    public List<RightGroupModel> getRightGroups()
    {
        if(rightgroup.size() > 0)
            return this.rightgroup;

        ArrayList<HashMap<String, Object>> results;
        RightGroupTable tbl = new RightGroupTable();
        results = tbl.getRightGroups();

        for(HashMap map : results) { //for each item in our hashmap
            Iterator it = map.entrySet().iterator();
            RightGroupModel _model = new RightGroupModel(this.view);
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                this.addField(pairs.getKey(), pairs.getValue(), _model);

                it.remove();
            }
            rightgroup.add( _model ); //add it to return
        }

        return rightgroup;
    }

    /**
     *
     * Get specific rightgroup
     *
     * @param id
     * @return
     */

    public RightGroupModel getRightGroup(int id) {
        for(RightGroupModel model : this.rightgroup) {
            if(model.getId() == id)
                return model;
        }
        return null;
    }

    /**
     *
     * Update rightgroup
     *
     * @param model
     * @param data
     */

    public void updateRightGroup(RightGroupModel model, HashMap<String, Object> data) {
        //update the model
        Iterator it = data.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            this.addField(pairs.getKey(), pairs.getValue(), model);

            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    /**
     *
     * Add new rightgroup
     *
     * @param tmp
     * @return
     */

    public RightGroupModel addRightGroup(HashMap<String, Object> tmp) {
        RightGroupModel _model = new RightGroupModel(this.view);
        Iterator it = tmp.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            if(!this.addField(pairs.getKey(), pairs.getValue(), _model))
                return null;

            it.remove(); // avoids a ConcurrentModificationException
        }

        //add model to the collection
        this.rightgroup.add(_model);

        return _model;
    }

    /**
     *
     * Write changes for item
     *
     * @param model
     * @return
     */

    public int writeChangesFor(RightGroupModel model) {
        //write changes to the database
        RightGroupTable tbl = new RightGroupTable();
        tbl.writeChangesFor(model);

        return 0;
    }

    /**
     *
     * list of fields for adding
     *
     * @param key
     * @param value
     * @param model
     * @return
     */

    private boolean addField(Object key, Object value, RightGroupModel model) {
        Object ret = model.validate(key, value);
        if(ret.equals(false))
            return false;

        if(!ret.toString().equals("false") && !ret.toString().equals("true")) {
            value = ret.toString();
        }

        if(key.toString().equals("id")) {
            model.setId(Integer.valueOf(value.toString()).intValue()); //yeah magnets bitch
        }
        if(key.toString().equals("name")) {
            model.setName(value.toString());
        }
        if(key.toString().equals("description")) {
            model.setDescription(value.toString());
        }

        return true;
    }

    /**
     *
     * Delete rightgroup
     *
     * @param model
     */

    public void delete(RightGroupModel model) //u are fired get out
    {
        RightGroupTable tbl = new RightGroupTable();
        tbl.deleteObject(model);
    }

}