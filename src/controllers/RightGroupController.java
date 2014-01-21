package controllers;

import factories.RightGroupFactory;
import models.RightGroupModel;
import views.AccessRightGroupView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Reinier
 * Class: This controller makes it able to modify and control fields through the view
 */

public class RightGroupController extends IpsenController
{
    private AccessRightGroupView view;
    private RightGroupFactory factory;

    /**
     *
     * Constructor, define factory, view, etc
     *
     */

    public RightGroupController()
    {
        this.view = new AccessRightGroupView(this);
        this.factory = new RightGroupFactory(this.view);
        this.view.init();
    }

    /**
     *
     * Getter for our view
     *
     * @return view
     */

    public AccessRightGroupView getView()
    {
        return this.view;
    }

    /**
     *
     * Getter for our factory
     *
     * @return factory
     */

    public RightGroupFactory getFactory()
    {
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

        while (it.hasNext()) { //while collection has next item
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
        RightGroupModel model;
        if(id == 0) {
            model = this.factory.addRightGroup( _tmp );
            if(model == null)
                return 2;

            if(this.factory.writeChangesFor(model) == 2) //2
                return 2;
        } else {
            model = this.factory.getRightGroup( id );
            this.factory.updateRightGroup( model, _tmp );
            this.factory.writeChangesFor(model);
        }

        return 1;
    }
}
