package views;

import controllers.AccountController;
import data.RightGroupTable;
import helpers.SpringUtilities;
import helpers.combobox.Item;
import helpers.combobox.ItemRenderer;
import models.AccountModel;
import helpers.MenuButton;
import helpers.PageScreenSwitcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * User: Floris
 * Class: View, shows what the user sees (and gets its info through the controller)
 */
public class AccessAccountEditView extends IpsenView implements ActionListener {

    private AccountController controller;
    private AccountModel model;
    private boolean boolEdit;

    //form fields and labels
    HashMap<String, Object> formFields = new HashMap<String, Object>();

    private MenuButton btnBack, btnSave;
    private JTextField txtId;
    private JTextField txtFirstName;
    private JTextField txtInsertion;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField pwPass;

    private JLabel lblRightGroup;
    private JComboBox cbRightGroup;


    private JPanel topPanel;

    /**
     * Class Constructor
     * @param controller Account Controller
     */

    public AccessAccountEditView(AccountController controller, Object... args)
    {
        this.controller = controller;

        JPanel frame = new JPanel(new BorderLayout());


        this.topPanel = new JPanel();

        btnBack = new MenuButton("assets/terug_naar_overzicht.png", null, null);
        btnSave = new MenuButton("assets/save.png", null, null);
        btnBack.addActionListener(this);
        btnSave.addActionListener(this);
        topPanel.add(btnBack);
        topPanel.add(btnSave);


        JPanel form = new JPanel(new SpringLayout());


        ScrollPane scrollPane = new ScrollPane();
        frame.add(form, BorderLayout.CENTER);

        //Check if form is in add or edit mode.
        if(args.length > 0) {
            this.model = this.controller.getFactory().getAccount(Integer.valueOf(args[0].toString()));
            this.boolEdit = true;
        } else {
            this.model = null;
            System.out.println("model not set");
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

        //input : firstname
        JLabel fName = new JLabel("Voornaam *");
        txtFirstName = new JTextField(15);

        if(model != null)
            txtFirstName.setText(model.getFirstname());

        form.add(fName);
        fName.setLabelFor(txtFirstName);
        form.add(txtFirstName);

        //input : insertion
        JLabel lInsertion = new JLabel("Tussenvoegsel ");
        txtInsertion = new JTextField(5);

        if(model != null)
            txtInsertion.setText(model.getInsertion());

        form.add(lInsertion);
        lInsertion.setLabelFor(txtInsertion);
        form.add(txtInsertion);


        //input : lastname
        JLabel lName = new JLabel("Achternaam *");
        txtLastName = new JTextField(15);

        if(model != null)
            txtLastName.setText(model.getLastname());

        form.add(lName);
        lName.setLabelFor(txtLastName);
        form.add(txtLastName);

        //input : email
        JLabel lEmail = new JLabel("Email *");
        txtEmail = new JTextField(15);

        if(model != null)
            txtEmail.setText(model.getEmail());

        form.add(lEmail);
        lEmail.setLabelFor(txtEmail);
        form.add(txtEmail);


        JLabel lPw = new JLabel();
        //input : password
        if(model != null){
            lPw = new JLabel("Password ");
        }
        else
        {
            lPw = new JLabel("Password *");
        }
        pwPass = new JTextField(15);

        form.add(lPw);
        lPw.setLabelFor(pwPass);
        form.add(pwPass);



        //input : payment type id
        HashMap<Integer, Object> cboxFill = this.getRightGroupsNames();
        Vector cbRightGroups = new Vector();
        Iterator it = cboxFill.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            Item i = new Item(Integer.parseInt(pairs.getKey().toString()), pairs.getValue().toString());
            cbRightGroups.addElement( i );
        }


        lblRightGroup = new JLabel("Selecteer Rechtengroep");
        cbRightGroup = new JComboBox(cbRightGroups);
        cbRightGroup.setRenderer( new ItemRenderer() );
        if(this.boolEdit) {
            for(int i=0; i<cbRightGroup.getItemCount(); i++) {
                Item item = (Item)cbRightGroups.elementAt(i);
                if(item.getId() == model.getRightGroupId())
                    cbRightGroup.setSelectedItem(item);
            }

        }
        cbRightGroup.addActionListener( this );

        form.add(lblRightGroup);
        lblRightGroup.setLabelFor(cbRightGroup);
        form.add(cbRightGroup);

        SpringUtilities.makeCompactGrid(form,
                7, 2,           //rows, cols
                50, 10,        //initX, initY
                50, 10);       //xPad, yPad


        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(form, BorderLayout.SOUTH);
        this.add(frame);
    }

    /**
     * Method getting the RightGroupsNames for the account to show, getting data from the database
     */
    public HashMap<Integer, Object> getRightGroupsNames() {
        ArrayList<HashMap<String, Object>> results;
        RightGroupTable tbl = new RightGroupTable();
        results = tbl.getRightGroupsNames();

        HashMap<Integer, Object> cboxFill = new HashMap<Integer, Object>();

        for(HashMap map : results) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();

                if(pairs.getKey().toString().equals("id"))
                    cboxFill.put(Integer.parseInt(pairs.getValue().toString()), ((Map.Entry) it.next()).getValue().toString());

                it.remove();
            }
        }

        return cboxFill;
    }
    /**
     * Method to actionPerformed changes the view, by using the controller and displaying the next view
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.btnBack)
        {
            AccountController accountController = new AccountController();                    //New instance of rightcontroller
            PageScreenSwitcher.getInstance().switchScreen(accountController.getView());   //Switch screen to AccessRightView
        }

        if(e.getSource() == this.btnSave)
        {
            Item rightGroupItem = (Item)cbRightGroup.getSelectedItem();

            formFields.put("id", txtId.getText());
            formFields.put("firstname", txtFirstName.getText());
            formFields.put("insertion", txtInsertion.getText());
            formFields.put("lastname", txtLastName.getText());
            formFields.put("email", txtEmail.getText());
            formFields.put("password", pwPass.getText());
            formFields.put("rightGroupId", rightGroupItem.getId());


            int code = controller.save(formFields);
            switch(code) {
                case 0 :
                    displayMessage(this, 0, "Alle velden met een asteriks(*) dienen ingevuld te worden.", "Formulier fout!", 0);
                    break;
                case 1 :
                    if(model != null){
                        displayMessage(this, 1, "Account is succesvol gewijzigd!", "Geslaagd!", 0);
                        AccountController accountController = new AccountController();
                        PageScreenSwitcher.getInstance().switchScreen(accountController.getView());
                        break;
                    }
                    else{
                        displayMessage(this, 1, "Account is succesvol aangemaakt!", "Geslaagd!", 0);
                        AccountController accountController = new AccountController();
                        PageScreenSwitcher.getInstance().switchScreen(accountController.getView());
                        break;
                    }
            }
        }

    }
}
