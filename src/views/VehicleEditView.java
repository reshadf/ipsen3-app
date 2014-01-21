package views;

import controllers.VehicleController;
import factories.CategoryFactory;
import models.CategoryModel;
import helpers.MenuButton;
import helpers.PageScreenSwitcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.SpringLayout;
import helpers.SpringUtilities;
import models.VehicleModel;

/**
 * User: Reinier
 * Class: View, shows what the user sees (and gets its info through the controller)
 */
public class VehicleEditView extends IpsenView implements ActionListener
{
    private VehicleController controller;
    private JButton btnBack;         //the three lines creates the buttons of this menu
    private JButton btnSave;

    //form fields and labels for the vehicle edit view so that you can type in the data necessary for the car
    HashMap<String, Object> formFields = new HashMap<String, Object>();

    JLabel lblId;
    JTextField txtId = null;

    JLabel lblBrand;
    JTextField txtBrand = null;
    JLabel lblType;
    JTextField txtType = null;
    JLabel lblLicense;
    JTextField txtLicense = null;
    JLabel lblDescription;
    JTextArea txtDescription = null;
    JLabel lblAirco;
    JCheckBox cbAirco = null;
    JLabel lblTowbar;
    JCheckBox cbTowbar = null;
    JLabel lblSeats;
    JTextField txtSeats = null;
    JLabel lblHourlyRent;
    JTextField txtHourlyrent = null;
    JComboBox cbCategory;
    JLabel lblCategory = new JLabel("Categorie");
    java.util.List<CategoryModel> categoryModelList;

    private JPanel buttonBar = new JPanel();

    private Boolean boolEdit;  //this boolean is necessary to make it able for you to change already inserted info if its wrongly inserted after you submitted it.

    public VehicleEditView(VehicleController controller, Object... args) {
        this.controller = controller;

        VehicleModel model;
        if(args.length > 0) {
            model = this.controller.getFactory().getVehicle(Integer.valueOf(args[0].toString()));
            this.boolEdit = true;
        } else {
            model = null;
            this.boolEdit = false;
        }

        JPanel frame = new JPanel(new BorderLayout());       // sets the layout for this menu (how the buttons and content are placed)

        //back button
        btnBack = new MenuButton("assets/terug_naar_overzicht.png", null, null);
        btnSave = new MenuButton("assets/save.png", null, null);       //link images to the button which are situated in the folder assets
        btnBack.addActionListener(this);
        btnSave.addActionListener(this);                    // gives action for clicking on the availible buttons on the menu

        buttonBar.add(btnBack);      // adds the buttons to the menu
        buttonBar.add(btnSave);

        JPanel form = new JPanel(new SpringLayout());

        //input : id        all the necessary information for every field you can fill in or select the right category
        lblId = new JLabel("ID");
        txtId = new JTextField(5);
        txtId.setEditable(false);
        txtId.setBorder(null);
        txtId.setOpaque(false);
        if(model != null)
            txtId.setText(String.valueOf(model.getId()));
        else
            txtId.setText("0");

        form.add(lblId);
        lblId.setLabelFor(txtId);
        form.add(txtId);

        //input : brand       all the necessary information for every field you can fill in or select the right category
        lblBrand = new JLabel("Merk *");
        txtBrand = new JTextField(15);
        if(model != null)
            txtBrand.setText(model.getBrand());

        form.add(lblBrand);
        lblBrand.setLabelFor(txtBrand);
        form.add(txtBrand);

        //input : type          all the necessary information for every field you can fill in or select the right category
        lblType = new JLabel("Type *");
        txtType = new JTextField(15);
        if(model != null)
            txtType.setText(model.getType());

        form.add(lblType);
        lblType.setLabelFor(txtType);
        form.add(txtType);

        //input : license            all the necessary information for every field you can fill in or select the right category
        lblLicense = new JLabel("Kenteken *");
        txtLicense = new JTextField(5);
        if(model != null)
            txtLicense.setText(model.getLicenseplate());

        form.add(lblLicense);
        lblLicense.setLabelFor(txtLicense);
        form.add(txtLicense);

        //input : description             all the necessary information for every field you can fill in or select the right category
        lblDescription = new JLabel("Omschrijving *");
        txtDescription = new JTextArea(5, 30);
        if(model != null)
            txtDescription.append(model.getDescription());

        form.add(lblDescription);
        lblDescription.setLabelFor(txtDescription);
        form.add(txtDescription);

        //input : airco                all the necessary information for every field you can fill in or select the right category
        lblAirco = new JLabel("Airco");
        cbAirco = new JCheckBox();
        if(model != null)
            cbAirco.setSelected(model.hasAirco());
        cbAirco.addActionListener(this);

        form.add(lblAirco);
        lblAirco.setLabelFor(cbAirco);
        form.add(cbAirco);

        //input : tow bar           all the necessary information for every field you can fill in or select the right category
        lblTowbar = new JLabel("Trekhaak");
        cbTowbar = new JCheckBox();
        if(model != null)
            cbTowbar.setSelected(model.hasTowbar());
        cbTowbar.addActionListener(this);

        form.add(lblTowbar);
        lblTowbar.setLabelFor(cbTowbar);
        form.add(cbTowbar);

        //input : seats            all the necessary information for every field you can fill in or select the right category
        lblSeats = new JLabel("Zitplaatsen *");
        txtSeats = new JTextField(5);
        if(model != null)
            txtSeats.setText(String.valueOf(model.getSeats()));

        form.add(lblSeats);
        lblSeats.setLabelFor(txtSeats);
        form.add(txtSeats);

        //input : rent         all the necessary information for every field you can fill in or select the right category
        lblHourlyRent = new JLabel("Uurtarief *");
        txtHourlyrent = new JTextField(5);
        if(model != null)
            txtHourlyrent.setText(String.valueOf(model.getHourly_rent()));

        form.add(lblHourlyRent);       // all the necessary information for every field you can fill in or select the right category
        lblHourlyRent.setLabelFor(txtHourlyrent);
        form.add(txtHourlyrent);

        categoryModelList = this.controller.getCategoryFactory().getCategories();     // adds the extra option to categorize a car (gets info from another table in the database)

        cbCategory = new JComboBox();

        form.add(lblCategory);
        lblCategory.setLabelFor(cbCategory);
        form.add(cbCategory);

        for(CategoryModel cml : categoryModelList)
            cbCategory.addItem(cml);

        if(this.boolEdit)                       // makes choosing a category editable like being able to select another after you saved and checked another.
        {
            int i = 0;
            for(CategoryModel cml : categoryModelList)
            {
                if(model.getCategory_id() == cml.getId())
                {
                    cbCategory.setSelectedIndex(i);
                }
                i++;
            }
        }

        SpringUtilities.makeCompactGrid(form,
                10, 2, //rows, cols
                50, 10,        //initX, initY
                50, 10);       //xPad, yPad

        frame.add(buttonBar, BorderLayout.NORTH);                 // the buttons are situated up in the application
        frame.add(form, BorderLayout.CENTER);                     // the rest is shown in the middle/center

        this.add(frame);
    }


    @Override
    public void actionPerformed(ActionEvent e) {      // if you complete the form and click a button this will run
        Object s = e.getSource();
        if(s == btnBack) {                               //  gets you back to the menu you were before you were editing/creating the vehicle
            VehicleController vehicleController = new VehicleController();
            PageScreenSwitcher.getInstance().switchScreen(vehicleController.getView());
        }
        if(s == btnSave) {                           // saves the changes/creation you made in the menu of vehicle edit/creating menu and saves em in the database

            formFields.put("id", txtId.getText());
            formFields.put("brand", txtBrand.getText());
            formFields.put("type", txtType.getText());
            formFields.put("licenseplate", txtLicense.getText());
            formFields.put("description", txtDescription.getText());
            formFields.put("airco", (cbAirco.isSelected())?1:0);
            formFields.put("towbar", (cbTowbar.isSelected())?1:0);
            formFields.put("seats", txtSeats.getText());
            formFields.put("hourly_rent", txtHourlyrent.getText());
            formFields.put("category_id", categoryModelList.get(cbCategory.getSelectedIndex()).getId());

            int code = controller.save(formFields);
            switch(code) {
                case 0 :                                     // if you forget a certain field a message will pop up, shown underneath here in the green
                    displayMessage(this, 0, "Alle velden met een asteriks(*) dienen ingevuld te worden.", "Formulier fout!", 0);
                    break;
                case 1 :
                    if(this.boolEdit) {
                        displayMessage(this, 1, "Voertuig is succesvol opgeslagen!", "Geslaagd!", 0);     // changes made while editing a vehicle, done succesfull: the message in green shown before here will be shown
                    } else {
                        displayMessage(this, 1, "Voertuig is succesvol aangemaakt!", "Geslaagd!", 0);     // creating a vehicle succesfully will show the green letters shown before this sentence in a dialogbox.
                    }
                    VehicleController vehicleController = new VehicleController();
                    PageScreenSwitcher.getInstance().switchScreen(vehicleController.getView());         // after you done all the above (creating or editing a vehicle) this command will change the window to where it was before.
                    break;
                case 2 :
                    displayMessage(this, 0, "Fout opgetreden in formulier!", "Formulier fout!", 0);     // shown when an error was made by giving wrong information which are not able to be processed by the application
                    break;
            }
        }
    }
}
