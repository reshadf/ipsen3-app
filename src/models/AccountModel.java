package models;

import views.AccesAccountView;

/**
 * User: Reinier
 * Class: Model, inserting and getting information to create a model for the factory
 */
public class AccountModel extends IpsenModel
{
    private AccesAccountView view;
    //alle vakjes die in de database voorkomen onder het kopje [right]
    private int id = 0;
    private String firstname = "";
    private String insertion = "";
    private String lastname = "";
    private String email = "";
    private String password = "";
    private int rightGroupId;


    public int getRightGroupId()
    {
        return this.rightGroupId;
    }

    public void setRightGroupId(int rightGroupId)
    {
        this.rightGroupId = rightGroupId;
    }

    //elk vakje opvragen in database en hervullen

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    //elk vakje opvragen in database en hervullen

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    //elk vakje opvragen in database en hervullen

    public String getInsertion()
    {
        return insertion;
    }

    public void setInsertion(String insertion)
    {
        this.insertion = insertion;
    }

    //elk vakje opvragen in database en hervullen

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }
    //elk vakje opvragen in database en hervullen

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
    //elk vakje opvragen in database en hervullen

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Object validate(Object key, Object value)
    {
        return true;
    }

    //Dit is de superklasse -> de opperview voor deze model

    public AccountModel(AccesAccountView view)
    {
        super();
        this.view = view;
    }


}