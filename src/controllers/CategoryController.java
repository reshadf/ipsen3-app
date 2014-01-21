package controllers;


import factories.CategoryFactory;
import models.CategoryModel;
import views.CategoryView;

import javax.swing.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: Floris
 * Class: This controller makes it able to modify and control fields through the view
 */
public class CategoryController extends IpsenController
{
    private CategoryView view;
    private CategoryFactory factory;
    /**
     * constructor
     */
    public CategoryController() {
        this.view = new CategoryView(this);
        this.factory = new CategoryFactory(this.view);
        this.view.init();
    }

//    public JTable getCategoriesTable()
//    {
//        String[] columnNames = {"id",
//                "date",
//                "name",
//                "description"
//        };
//
//
//        List<CategoryModel> categories = this.factory.getCategories();
//
//        Object[][] data = new Object[categories.size()][4];
//        for(int i = 0; i < categories.size(); i++) {
////            System.out.println(vehicles.get(i).getBrand());
//            data[i][0] = new Integer(categories.get(i).getId());
//            data[i][1] = categories.get(i).getDate();
//            data[i][2] = categories.get(i).getName();
//            data[i][3] = categories.get(i).getDescription();
//
//        }
//
//
//
//        JTable jTable = new JTable(data, columnNames) {
//            public boolean isCellEditable(int row, int col)
//            {
//                return false;
//            }
//
//        };
//        return jTable;
//    }


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

        System.out.println(formFields);

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
        CategoryModel model;
        if(id == 0) {
            System.out.println("id == 0");
            model = this.factory.addCategory(_tmp);
            if(model == null)
                return 2;

            if(this.factory.writeChangesFor(model) == 2)
                return 2;
        } else {
            System.out.println("hieeer");
            model = this.factory.getCategory(id);
            this.factory.updateCategory(model, _tmp);
            this.factory.writeChangesFor(model);
        }

        return 1;
    }

    /**
     * returns view
     */
    public CategoryView getView()
    {
        return this.view;
    }

    /**
     * returns view
     */
    public CategoryFactory getFactory() {
        return this.factory;
    }

}
