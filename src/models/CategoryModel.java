package models;


import views.CategoryView;

/**
 * User: Floris
 * Class: Model, inserting and getting information to create a model for the factory
 */
public class CategoryModel extends IpsenModel
{
    private CategoryView view;

    private int id = 0;
    private String name = "";
    private String description = "";
    private String date = "";

    /**
     * Constructor of the model
     */
    public CategoryModel(CategoryView view) {
        super();
        this.view = view;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getDate()
    {
        return this.date;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
