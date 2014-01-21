package controllers;

import factories.AccountFactory;
import factories.RightGroupFactory;
import models.AccountModel;
import views.AccesAccountView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Floris
 * Class: This controller makes it able to modify and control fields through the view
 */
public class AccountController extends IpsenController
{
    private AccesAccountView view;
    private AccountFactory factory;
    /**
     * constructor
     */
    public AccountController()
    {
        this.view = new AccesAccountView(this);
        this.factory = new AccountFactory(this.view);
        this.view.init();

    }
    /**
     * returns view
     */
    public AccesAccountView getView()
    {
        return this.view;
    }
    /**
     * returns view
     */
    public AccountFactory getFactory()
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
        String password = "";


        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            _tmp.put(pairs.getKey().toString(), pairs.getValue().toString());

            if(pairs.getKey().toString().equals("id"))
                id = Integer.parseInt(pairs.getValue().toString());

            //add fields that are not required to complete the form
            if(pairs.getKey().toString().equals("insertion") ) {
                it.remove(); // avoids a ConcurrentModificationException
                continue;
            }


            if(id != 0 )
            {
                //add passwordfield that is not required to complete the form
                if(pairs.getKey().toString().equals("password") ) {
                    it.remove(); // avoids a ConcurrentModificationException
                    continue;
                }
            }

            //check fields for length
            if(pairs.getValue().toString().length() == 0) {
                return 0;
            }

            it.remove(); // avoids a ConcurrentModificationException
        }

        //check if id equals 0, if so the record is new and should be added to the database
        AccountModel model;
        if(id == 0) {
            model = this.factory.addAccount( _tmp );
            if(model == null)
                return 2;

            if(this.factory.writeChangesFor(model) == 2)
                return 2;
        } else {
            model = this.factory.getAccount( id );

            this.factory.updateAccount( model, _tmp );
            this.factory.writeChangesFor(model);
        }

        return 1;
    }
}
