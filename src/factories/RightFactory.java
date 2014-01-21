package factories;


import data.RightTable;
import models.Right2GroupModel;
import models.RightModel;
import views.AccessRightView;

import java.util.*;

/**
 * User: Reinier
 * Class: This factory uses the models from the modelclasses & creates them
 */
public class RightFactory
{
    private AccessRightView view;
    private List<RightModel> right;
    private List<Right2GroupModel> right2group;

    public RightFactory(AccessRightView view)
    {
        this.view = view;
        this.right = new ArrayList<RightModel>();
        this.right2group = new ArrayList<Right2GroupModel>();
    }

//    getAssignedRights

    public List<Right2GroupModel> getRight2Group(int id)
    {
//        if(right2group.size() > 0)
//            return this.right2group;

        ArrayList<HashMap<String, Object>> results;
        RightTable tbl = new RightTable();
        results = tbl.getAssignedRights(id);

        for(HashMap map : results) {
            Iterator it = map.entrySet().iterator();
            Right2GroupModel _model = new Right2GroupModel(this.view);
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                this.addRight2GroupFields(pairs.getKey(), pairs.getValue(), _model);

                it.remove();
            }
            right2group.add( _model );
        }

        return right2group;
    }

    public ArrayList<Integer> getRight2GroupList(int id)
    {
        List<Right2GroupModel> temp = this.getRight2Group(id);
        ArrayList<Integer> tempReturn = new ArrayList<Integer>();

        for(Right2GroupModel model : temp)
        {
            tempReturn.add(model.getRightId());
        }

        return tempReturn;
    }


    public List<RightModel> getRights()
    {
        if(right.size() > 0)
            return this.right;

        ArrayList<HashMap<String, Object>> results;
        RightTable tbl = new RightTable();
        results = tbl.getRights();

        for(HashMap map : results) {
            Iterator it = map.entrySet().iterator();
            RightModel _model = new RightModel(this.view);
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                this.addField(pairs.getKey(), pairs.getValue(), _model);

                it.remove();
            }
            right.add( _model );
        }

        return right;
    }

    private boolean addRight2GroupFields(Object key, Object value, Right2GroupModel model) {

        if(key.toString().equals("id")) {
            model.setId(Integer.valueOf(value.toString()));
        }
        if(key.toString().equals("right_id")) {
            model.setRightId(Integer.valueOf(value.toString()));
        }
        if(key.toString().equals("group_id")) {
            model.setGroupId(Integer.valueOf(value.toString()));
        }

        return true;
    }

    private boolean addField(Object key, Object value, RightModel model) {
        Object ret = model.validate(key, value);
        if(ret.equals(false))
            return false;

        if(!ret.toString().equals("false") && !ret.toString().equals("true")) {
            value = ret.toString();
        }

        if(key.toString().equals("id")) {
            model.setId(Integer.valueOf(value.toString()).intValue());
        }
        if(key.toString().equals("description")) {
            model.setDescription(value.toString());
        }
        if(key.toString().equals("value")) {
            model.setValue(value.toString());
        }

        return true;
    }

    /**
     *
     * Update exsisting rights or add new
     *
     * @param groupId
     * @param checkedItems
     */

    public void updateRights(int groupId, ArrayList<Integer> checkedItems)
    {
        RightTable tbl = new RightTable();
        tbl.updateRights(groupId, checkedItems);
    }

    /**
     *
     * Remove rights
     *
     * @param groupId
     * @param uncheckedItems
     */

    public void removeRights(int groupId, ArrayList<Integer> uncheckedItems)
    {
        RightTable tbl = new RightTable();
        tbl.removeRights(groupId, uncheckedItems);
    }

}