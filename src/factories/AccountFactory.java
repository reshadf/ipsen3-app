package factories;

import data.AccountTable;
import data.RightGroupTable;
import models.AccountModel;
import models.AccountModel;
import models.RightGroupModel;
import views.AccesAccountView;

import java.util.*;

/**
 * User: Reinier
 * Class: This factory uses the models from the modelclasses & creates them
 */
public class AccountFactory
{
    private AccesAccountView view;
    private List<AccountModel> account;

    /**
     *
     * Constuctor of our account factory
     *
     * @param view
     */

    public AccountFactory(AccesAccountView view)
    {
        this.view = view;
        this.account = new ArrayList<AccountModel>();

    }

    /**
     *
     * Get all checkups by account
     * @return
     */

    public List<AccountModel> getAccount()
    {
        if(account.size() > 0)
            return this.account;

        ArrayList<HashMap<String, Object>> results;
        AccountTable tbl = new AccountTable();
        results = tbl.getAccount();

        for(HashMap map : results) {
            Iterator it = map.entrySet().iterator();
            AccountModel _model = new AccountModel(this.view);
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                this.addField(pairs.getKey(), pairs.getValue(), _model);

                it.remove();
            }

            int rightGroupId = tbl.getAccountRightGroup(_model.getId());
            _model.setRightGroupId(rightGroupId);

            account.add(_model);
        }

        return account;
    }

    /**
     *
     * Get account model by id
     * @param id
     * @return
     */

    public AccountModel getAccount(int id) {
        for(AccountModel model : this.account) {
            if(model.getId() == id)
                return model;
        }
        return null;
    }
    /**
     *
     * Update account
     *
     * @param model
     * @param data
     */
    public void updateAccount(AccountModel model, HashMap<String, Object> data) {
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
     * Add new account to list
     *
     * @param tmp
     * @return
     */
    public AccountModel addAccount(HashMap<String, Object> tmp) {
        AccountModel _model = new AccountModel(this.view);
        Iterator it = tmp.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            if(!this.addField(pairs.getKey(), pairs.getValue(), _model))
                return null;

            it.remove(); // avoids a ConcurrentModificationException
        }

        //add model to the collection
        this.account.add(_model);

        return _model;
    }
    /**
     *
     * Write changes for account model
     *
     * @param model
     * @return
     */

    public int writeChangesFor(AccountModel model) {
        //write changes to the database
        AccountTable tbl = new AccountTable();
        tbl.writeChangesFor(model);
        model.setId(tbl.getLastInsertId());
        tbl.writeChangesForUserGroup(model);
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

    private boolean addField(Object key, Object value, AccountModel model) {
        Object ret = model.validate(key, value);
        if(ret.equals(false))
            return false;

        if(!ret.toString().equals("false") && !ret.toString().equals("true")) {
            value = ret.toString();
        }

        if(key.toString().equals("id")) {
            model.setId(Integer.valueOf(value.toString()).intValue());
        }
        if(key.toString().equals("firstname")) {
            model.setFirstname( value.toString() );
        }
        if(key.toString().equals("insertion")) {
            model.setInsertion( value.toString() );
        }
        if(key.toString().equals("lastname")) {
            model.setLastname( value.toString() );
        }
        if(key.toString().equals("email")) {
            model.setEmail( value.toString() );
        }
        if(key.toString().equals("password")) {
            model.setPassword( value.toString() );
        }
        if(key.toString().equals("rightGroupId")){
            model.setRightGroupId( Integer.valueOf(value.toString()).intValue() );
        }

        //test
        
        return true;
    }

    /**
     *
     * Delete account
     *
     * @param model
     */

    public void delete(AccountModel model)
    {
        AccountTable tbl = new AccountTable();
        tbl.deleteObject(model);
    }

}