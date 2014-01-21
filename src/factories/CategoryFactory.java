package factories;

import controllers.CategoryController;
import data.CategoryTable;
import data.VehicleTable;
import models.VehicleModel;
import views.CategoryView;
import models.CategoryModel;

import java.util.*;

/**
 * User: Floris
 * Class: This factory uses the models from the modelclasses & creates them
 */
public class CategoryFactory {
    private CategoryView view;
    private List<CategoryModel> categories;

    /**
     *
     * Constuctor of our category factory
     *
     * @param view
     */

    public CategoryFactory(CategoryView view)
    {
        this.view = view;
        this.categories = new ArrayList<CategoryModel>();

    }

    /**
     *
     * Get all checkups by categories
     *
     * @return
     */
    public List<CategoryModel> getCategories() {
        if(categories.size() > 0)
            return this.categories;

        ArrayList<HashMap<String, Object>> results;
        CategoryTable tbl = new CategoryTable();
        results = tbl.getCategories();

        for(HashMap map : results) {
            Iterator it = map.entrySet().iterator();
            CategoryModel _category = new CategoryModel(this.view);
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                if(pairs.getKey().toString().equals("id")) {
                    _category.setId(Integer.valueOf(pairs.getValue().toString()).intValue());
                }
                if(pairs.getKey().toString().equals("date")) {
                    _category.setDate(pairs.getValue().toString());
                }
                if(pairs.getKey().toString().equals("name")) {
                    _category.setName(pairs.getValue().toString());
                }
                if(pairs.getKey().toString().equals("description")) {
                    _category.setDescription(pairs.getValue().toString());
                }

                it.remove();
            }
            categories.add(_category);
        }

        return categories;
    }
    /**
     *
     * Get category model by id
     *
     * @param id
     * @return
     */

    public CategoryModel getCategory(int id) {
        for(CategoryModel model : this.categories) {
            if(model.getId() == id)
                return model;
        }
        return null;
    }

    /**
     *
     * Add new category to list
     *
     * @param tmp
     * @return
     */
    public CategoryModel addCategory(HashMap<String, Object> tmp) {
        CategoryModel model = new CategoryModel(this.view);
        Iterator it = tmp.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            this.addField(pairs.getKey(), pairs.getValue(), model);

            it.remove(); // avoids a ConcurrentModificationException
        }

        //add model to the collection
        this.categories.add(model);

        return model;
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
    private void addField(Object key, Object value, CategoryModel model) {

        if(key.toString().equals("name")) {
            model.setName(value.toString());
        }
        if(key.toString().equals("description")) {
            model.setDescription(value.toString());
        }

    }

    /**
     * updates fields
     * @param model
     * @param data
     */

    public void updateCategory(CategoryModel model, HashMap<String, Object> data) {
        //update the model
        Iterator it = data.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            this.addField(pairs.getKey(), pairs.getValue(), model);

            it.remove(); // avoids a ConcurrentModificationException
        }
    }



    public int writeChangesFor(CategoryModel model) {
        //write changes to the database
        CategoryTable tbl = new CategoryTable();
        tbl.writeChangesFor(model);

        return 0;
    }
    /**
     *
     * Delete category
     *
     * @param model
     */
    public void delete(CategoryModel model)
    {
        CategoryTable tbl = new CategoryTable();
        tbl.deleteObject(model);
    }

}
