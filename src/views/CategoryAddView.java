package views;

import controllers.CategoryController;
import helpers.SpringUtilities;
import models.CategoryModel;
import helpers.MenuButton;
import helpers.PageScreenSwitcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * User: Floris
 * Class: View, shows what the user sees (and gets its info through the controller)
 */
public class CategoryAddView extends IpsenView implements ActionListener{

    private CategoryController controller;
    private MenuButton btnBack;
    private JButton btnSave;



    private boolean boolEdit; // if true formfields gets values and database will be updated

    private CategoryModel model;

    //form fields and labels
    HashMap<String, Object> formFields = new HashMap<String, Object>();

    private JTextField tName;
    private JTextField tDescription;
    private JTextField txtId;
    private JPanel buttonBar;

    /**
     * Class Constructor
     * @param controller Category Controller
     */

    public CategoryAddView(CategoryController controller, Object... args)
    {
        this.controller = controller;
        this.buttonBar = new JPanel();

        JPanel frame = new JPanel(new BorderLayout());


        btnBack = new MenuButton("assets/terug_naar_overzicht.png", null, null);
        btnSave = new MenuButton("assets/save.png", null, null);
        btnBack.addActionListener(this);
        btnSave.addActionListener(this);


        buttonBar.add(btnBack);
        buttonBar.add(btnSave);

        JPanel form = new JPanel(new SpringLayout());


        ScrollPane scrollPane = new ScrollPane();
        frame.add(form, BorderLayout.CENTER);

        //Check if form is in add or edit mode.

        if(args.length > 0) {
            this.model = this.controller.getFactory().getCategory(Integer.valueOf(args[0].toString()));
            this.boolEdit = true;
        } else {
            this.model = null;
            System.out.println("model not setted");
            this.boolEdit = false;
        }


        JLabel lblId = new JLabel("ID");
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

        //input : name
        JLabel lName = new JLabel("Name *");
        tName = new JTextField(15);

        if(model != null)
            tName.setText(model.getName());

        form.add(lName);
        lName.setLabelFor(tName);
        form.add(tName);

        //input : description
        JLabel lDescription = new JLabel("Description *");
        tDescription = new JTextField(5);

        if(model != null)
            tDescription.setText(model.getDescription());

        form.add(lDescription);
        lDescription.setLabelFor(tDescription);
        form.add(tDescription);

        SpringUtilities.makeCompactGrid(form,
                3, 2, //rows, cols
                50, 10,        //initX, initY
                50, 10);       //xPad, yPad

        frame.add(buttonBar, BorderLayout.NORTH);
        frame.add(form, BorderLayout.CENTER);

        System.out.println(model);
        System.out.println("model: " + model);
        this.add(frame);
    }

    /**
     * Method to actionPerformed changes the view, by using the controller and displaying the next view
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnBack)
        {
            CategoryController categoryController = new CategoryController();
            PageScreenSwitcher.getInstance().switchScreen(categoryController.getView());
        }
        if (e.getSource() == this.btnSave)
        {
            if(model != null){                          //Edit Category
                System.out.println("edit");

                formFields.put("id", txtId.getText());
                formFields.put("description", tDescription.getText());
                formFields.put("name", tName.getText());


                int code = controller.save(formFields);
                System.out.println("code: " + code);
                switch(code) {
                    case 0 :
                        displayMessage(this, 0, "Alle velden met een asteriks(*) dienen ingevuld te worden.", "Formulier fout!", 0);
                        break;
                    case 1 :
                        displayMessage(this, 1, "Categorie is succesvol gewijzigd!", "Geslaagd!", 0);
                        CategoryController categoryController = new CategoryController();
                        PageScreenSwitcher.getInstance().switchScreen(categoryController.getView());
                        break;
                }


            }
            else{                                   //Make Category

                formFields.put("id", txtId.getText());
                formFields.put("description", tDescription.getText());
                formFields.put("name", tName.getText());



                int code = controller.save(formFields);
                switch(code) {
                    case 0 :
                        displayMessage(this, 0, "Alle velden met een asteriks(*) dienen ingevuld te worden.", "Formulier fout!", 0);
                        break;
                    case 1 :
                        displayMessage(this, 1, "Categorie is succesvol aangemaakt!", "Geslaagd!", 0);
                        CategoryController categoryController = new CategoryController();
                        PageScreenSwitcher.getInstance().switchScreen(categoryController.getView());
                        break;
                }
            }
        }

    }
}
