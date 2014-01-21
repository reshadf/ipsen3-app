package views;

import controllers.RightGroupController;
import helpers.SpringUtilities;
import helpers.MenuButton;
import helpers.PageScreenSwitcher;
import models.RightGroupModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * User: Jasper
 * Class: View, shows what the user sees (and gets its info through the controller)
 */

public class AccessRightGroupEditView extends IpsenView implements ActionListener {

    private RightGroupController controller;

    private MenuButton btnBack, btnSave;

    private JPanel topPanel;
    //form fields and labels
    private HashMap<String, Object> formFields = new HashMap<String, Object>();

    //define fields jeee
    private JLabel lblId = new JLabel("ID");
    private JTextField txtId = new JTextField(5);

    private JLabel lblName = new JLabel("Naam *");
    private JTextField txtName = new JTextField(15);

    private JLabel lblDescription = new JLabel("Beschrijving *");
    private JTextArea txtDescription =  new JTextArea(5, 30);

    private boolean boolEdit = false; //editting or new one

    /**
     *
     * Constructor
     *
     * @param controller RightGroup Controller
     * @param args
     */

    public AccessRightGroupEditView(RightGroupController controller, Object... args) //Constructor Mr. White
    {
        this.controller = controller;
        JPanel frame = new JPanel(new BorderLayout());

        RightGroupModel model;
        if(args.length > 0) { //we have a model so we do edit
            model = this.controller.getFactory().getRightGroup(Integer.valueOf(args[0].toString()));
            this.boolEdit = true;
        } else {
            model = null;
            this.boolEdit = false;
        }

        this.topPanel = new JPanel();
        //menu items
        btnBack = new MenuButton("assets/terug_naar_overzicht.png");
        btnSave = new MenuButton("assets/save.png");
        btnBack.addActionListener(this);
        btnSave.addActionListener(this);
        topPanel.add(btnBack);
        topPanel.add(btnSave);

        JPanel form = new JPanel(new SpringLayout()); //spring
        txtId.setEditable(false);
        txtId.setBorder(null);
        txtId.setOpaque(false);
        if(model != null)
            txtId.setText(String.valueOf(model.getId()));
        else
            txtId.setText("0");

        form.add(lblId);  //add id field
        lblId.setLabelFor(txtId);
        form.add(txtId);

        if(model != null) //we are editting
            txtName.setText(model.getName());

        form.add(lblName);
        lblName.setLabelFor(txtName);
        form.add(txtName);

        if(model != null)
            txtDescription.setText(model.getDescription()); //fill description

        form.add(lblDescription);
        lblName.setLabelFor(txtDescription);
        form.add(txtDescription);

        SpringUtilities.makeCompactGrid(form, //define our grid
                3, 2, //rows, cols
                50, 10,        //initX, initY
                50, 10);       //xPad, yPad

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(form, BorderLayout.CENTER);

        this.add(frame);

    }

    /**
     *
     * When button is pressed
     *
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.btnBack) //back
        {
            RightGroupController rightGroupController = new RightGroupController();                    //New instance of rightcontroller
            PageScreenSwitcher.getInstance().switchScreen(rightGroupController.getView());   //Switch screen to AccessRightView
        }
        if(e.getSource() == btnSave) { //save

            formFields.put("id", txtId.getText());
            formFields.put("name", txtName.getText());
            formFields.put("description", txtDescription.getText());

            int code = controller.save(formFields);
            switch(code) {
                case 0 :
                    displayMessage(this, 0, "Alle velden met een asteriks(*) dienen ingevuld te worden.", "Formulier fout!", 0); //user is moron and fails
                    break;
                case 1 :
                    if(this.boolEdit)
                    {
                        displayMessage(this, 1, "Rechtengroep is succesvol opgeslagen!", "Geslaagd!", 0); //saved
                    }
                    else
                    {
                        displayMessage(this, 1, "Rechtengroep is succesvol aangemaakt!", "Geslaagd!", 0); //new one
                    }
                    RightGroupController rightGroupController = new RightGroupController();
                    PageScreenSwitcher.getInstance().switchScreen(rightGroupController.getView());//poep
                    break;
                case 2 :
                    displayMessage(this, 0, "Fout opgetreden in formulier!", "Formulier fout!", 0);
                    break;
            }
        }
    }
}
