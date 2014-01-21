package views;

import controllers.CustomerController;
import helpers.*;
import models.CustomerModel;
import helpers.MenuButton;
import helpers.PageScreenSwitcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * User: Jasper
 * Class: View, shows what the user sees (and gets its info through the controller)
 */
public class CustomerEditView extends IpsenView implements ActionListener
{
    private CustomerController controller; //controller
    private JButton btnBack = new MenuButton("assets/terug_naar_overzicht.png"); //submenu buttons
    private JButton btnSave = new MenuButton("assets/save.png");

    //form fields and labels
    private HashMap<String, Object> formFields = new HashMap<String, Object>(); //formfields

    private JLabel lblId = new JLabel("ID");
    private JTextField txtId = new JTextField(5);

    private JLabel lblEmail = new JLabel("E-mail *"); //formfields for getting our data
    private JTextField txtEmail = new JTextField(15);
    private JLabel lblFirstname = new JLabel("Voornaam *");
    private JTextField txtFirstname = new JTextField(15);
    private JLabel lblInsertion = new JLabel("Tussenvoegsel");
    private JTextField txtInsertion = new JTextField(5);
    private JLabel lblLastname = new JLabel("Achternaam *");
    private JTextField txtLastname = new JTextField(15);
    private JLabel lblCompany = new JLabel("Bedrijf");
    private JTextField txtCompany = new JTextField(15);
    private JLabel lblKvknr = new JLabel("Kvknr");
    private JTextField txtKvknr = new JTextField(15);
    private JLabel lblPhone = new JLabel("Telefoonnummer");
    private JTextField txtPhone = new JTextField(15);
    private JLabel lblAddress = new JLabel("Adres *");
    private JTextField txtAddress = new JTextField(15);
    private JLabel lblZip = new JLabel("Postcode *");
    private JTextField txtZip = new JTextField(15);
    private JLabel lblCity = new JLabel("Plaats *");
    private JTextField txtCity = new JTextField(15);
    private JLabel lblBirthdate = new JLabel("Geboortedatum *");
    private JTextField txtBirthdate = new JTextField(15);
    private JLabel lblPassportnumber = new JLabel("Paspoortnummer *");
    private JTextField txtPasportnumber = new JTextField(15);

    private JPanel buttonBar = new JPanel();

    private Boolean boolEdit; //boolean if this is an insert of update

    /**
     * Constructor, args contains a model if this is an update of an existing item
     * @param controller
     * @param args
     */

    public CustomerEditView(CustomerController controller, Object... args) {
        this.controller = controller; //controller

        CustomerModel model;
        if(args.length > 0) { //new or edit
            model = this.controller.getFactory().getCustomer(Integer.valueOf(args[0].toString()));
            this.boolEdit = true;
        } else {
            model = null;
            this.boolEdit = false;
        }

        JPanel frame = new JPanel(new BorderLayout());

        btnBack.addActionListener(this);
        btnSave.addActionListener(this);

        buttonBar.add(btnBack);
        buttonBar.add(btnSave); //buttons

        JPanel form = new JPanel(new SpringLayout());

        txtId.setEditable(false);
        txtId.setBorder(null);
        txtId.setOpaque(false);
        if(model != null)
            txtId.setText(String.valueOf(model.getId()));
        else
            txtId.setText("0");

        form.add(lblId); //add field
        lblId.setLabelFor(txtId);
        form.add(txtId);

        if(model != null)
            txtEmail.setText(model.getEmail());

        form.add(lblEmail); //add field
        lblEmail.setLabelFor(txtEmail);
        form.add(txtEmail);

        if(model != null)
            txtFirstname.setText(model.getFirstname());

        form.add(lblFirstname); //add field
        lblFirstname.setLabelFor(txtFirstname);
        form.add(txtFirstname);

        if(model != null)
            txtInsertion.setText(model.getInsertion());

        form.add(lblInsertion); //add field
        lblInsertion.setLabelFor(txtInsertion);
        form.add(txtInsertion);

        if(model != null)
            txtLastname.setText(model.getLastname());

        form.add(lblLastname); //add field
        lblLastname.setLabelFor(txtLastname);
        form.add(txtLastname);

        if(model != null)
            txtCompany.setText(model.getCompany());

        form.add(lblCompany); //add field
        lblCompany.setLabelFor(txtCompany);
        form.add(txtCompany);

        if(model != null)
            txtKvknr.setText(model.getCompany());

        form.add(lblKvknr); //add field
        lblKvknr.setLabelFor(txtKvknr);
        form.add(txtKvknr);

        if(model != null)
            txtPhone.setText(model.getPhone());

        form.add(lblPhone); //add field
        lblPhone.setLabelFor(txtPhone);
        form.add(txtPhone);

        if(model != null)
            txtAddress.setText(model.getAddress());

        form.add(lblAddress); //add field
        lblAddress.setLabelFor(txtAddress);
        form.add(txtAddress);

        if(model != null)
            txtZip.setText(model.getZip());

        form.add(lblZip); //add field
        lblZip.setLabelFor(txtZip);
        form.add(txtZip);

        if(model != null)
            txtCity.setText(model.getCity());

        form.add(lblCity); //add field
        lblCity.setLabelFor(txtCity);
        form.add(txtCity);

        if(model != null)
            txtBirthdate.setText(this.controller.convertToDutchDate(model.getBirthdate()));

        txtBirthdate.addMouseListener(new MyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                txtBirthdate.setText(new DatePicker((JFrame) JFrame.getFrames()[0]).setPickedDate());
            }
        });

        form.add(lblBirthdate); //add field
        lblBirthdate.setLabelFor(txtBirthdate);
        form.add(txtBirthdate);

        if(model != null)
            txtPasportnumber.setText(model.getPassportnumber());

        form.add(lblPassportnumber); //add field
        lblPassportnumber.setLabelFor(txtPasportnumber);
        form.add(txtPasportnumber);

        SpringUtilities.makeCompactGrid(form, //create grid
                12, 2, //rows, cols
                50, 10,        //initX, initY
                50, 10);       //xPad, yPad

        frame.add(buttonBar, BorderLayout.NORTH);
        frame.add(form, BorderLayout.CENTER);

        this.add(frame);
    }

    /**
     * When button is pressed
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if(s == btnBack)
        { //button back, load other view
            CustomerController customerController = new CustomerController();
            PageScreenSwitcher.getInstance().switchScreen(customerController.getView());
        }
        if(s == btnSave)
        {

            formFields.put("id", txtId.getText()); //add formfields
            formFields.put("email", txtEmail.getText());
            formFields.put("firstname", txtFirstname.getText());
            formFields.put("insertion", txtInsertion.getText());
            formFields.put("lastname", txtLastname.getText());
            formFields.put("company", txtCompany.getText());
            formFields.put("kvknr", txtKvknr.getText());
            formFields.put("phone", txtPhone.getText());
            formFields.put("address", txtAddress.getText());
            formFields.put("zip", txtZip.getText());
            formFields.put("city", txtCity.getText());
            formFields.put("birthdate", txtBirthdate.getText());
            formFields.put("passportnumber", txtPasportnumber.getText());

            int code = controller.save(formFields); //save form using formfields
            switch(code) { //save status, show message
                case 0 :
                    displayMessage(this, 0, "Alle velden met een asteriks(*) dienen ingevuld te worden.", "Formulier fout!", 0);
                    break;
                case 1 :
                    if(this.boolEdit)
                    {
                        displayMessage(this, 1, "Klant is succesvol opgeslagen!", "Geslaagd!", 0);
                    }
                    else
                    {
                        displayMessage(this, 1, "Klant is succesvol aangemaakt!", "Geslaagd!", 0);
                    }
                    CustomerController customerController = new CustomerController();
                    PageScreenSwitcher.getInstance().switchScreen(customerController.getView());//poep
                    break;
                case 2 :
                    String messageSet = "";
                    System.out.println(MessageQueue.getInstance().getMessages().size());
                    for(Message m : MessageQueue.getInstance().getMessages()) {
                        messageSet += m.toString();
                    }
                    MessageQueue.getInstance().clearQueue();

                    displayMessage(this, 0, messageSet, "Formulier fout!", 0);
                    break;
            }
        }
    }
}
