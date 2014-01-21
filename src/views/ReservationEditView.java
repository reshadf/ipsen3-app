package views;

import controllers.ReservationController;
import helpers.*;
import helpers.combobox.Item;
import helpers.combobox.ItemRenderer;
import models.*;
import org.joda.time.DateTime;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * User: Chris
 * Class: Reservation Edit View for creation and edit of reservations
 */
public class ReservationEditView extends IpsenView implements ActionListener {
    private ReservationController controller;

    private JButton btnBack;
    private JButton btnSave;

    //form fields and labels
    HashMap<String, Object> formFields = new HashMap<String, Object>();

    private JLabel lblId;
    private JTextField txtId;
    private JLabel lblStartDate;
    private JTextField txtStartDate;
    private JLabel lblEndDate;
    private JTextField txtEndDate;
    private JLabel lblVehicle;
    private JComboBox cbVehicle;
    private JLabel lblCustomer;
    private JComboBox cbCustomer;
    private JLabel lblPaymentType;
    private JComboBox cbPaymentType;
    private JLabel lblStatus;
    private JComboBox cbStatus;

    ReservationModel model;

    private JPanel buttonBar = new JPanel();
    private Boolean boolEdit;

    /**
     * Class constructor
     * @param controller The controller that processes certain tasks
     * @param args Can contain an id of the model that is edited
     */
    public ReservationEditView(ReservationController controller, Object... args) {
        this.controller = controller;

        if(args.length > 0) {
            model = this.controller.getFactory().getSingleReservation(Integer.valueOf(args[0].toString()));
            this.boolEdit = true;
        } else {
            model = null;
            this.boolEdit = false;
        }

        JPanel frame = new JPanel(new BorderLayout());

        //back button
        btnBack = new MenuButton("assets/terug_naar_overzicht.png", null, null);
        btnSave = new MenuButton("assets/save.png", null, null);
        btnBack.addActionListener(this);
        btnSave.addActionListener(this);

        buttonBar.add(btnBack);
        buttonBar.add(btnSave);

        JPanel form = new JPanel(new SpringLayout());

        //input : id
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

        //input : startdate
        lblStartDate = new JLabel("Ingangsdatum");
        txtStartDate = new JTextField();
        txtStartDate.addMouseListener(new MyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                txtStartDate.setText(new DatePicker((JFrame) JFrame.getFrames()[0]).setPickedDate());
            }
        });
        if(model != null)
            txtStartDate.setText(String.valueOf(model.getStartdate().toString("d-M-y")));

        txtStartDate.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(txtEndDate.getText().length() > 0)
                    fillVehicleDropbox();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                cbVehicle.removeAllItems();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //do nothing
            }
        });

        form.add(lblStartDate);
        lblStartDate.setLabelFor(txtStartDate);
        form.add(txtStartDate);

        //input : enddate
        lblEndDate = new JLabel("Verloopt op");
        txtEndDate = new JTextField(10);
        txtEndDate.addMouseListener(new MyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                txtEndDate.setText(new DatePicker((JFrame) JFrame.getFrames()[0]).setPickedDate());
            }
        });
        if(model != null)
            txtEndDate.setText(String.valueOf(model.getEnddate().toString("d-M-y")));

        txtEndDate.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(txtStartDate.getText().length() > 0)
                    fillVehicleDropbox();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                cbVehicle.removeAllItems();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //do nothing
            }
        });

        form.add(lblEndDate);
        lblEndDate.setLabelFor(txtEndDate);
        form.add(txtEndDate);

        //input : vehicle id
        lblVehicle = new JLabel("Selecteer voertuig");
        cbVehicle = new JComboBox();
        cbVehicle.addActionListener( this );

        form.add(lblVehicle);
        lblVehicle.setLabelFor(cbVehicle);
        form.add(cbVehicle);

        //input : customer id
        lblCustomer = new JLabel("Selecteer klant");

        Vector cbModelCustomers = new Vector();
        for(CustomerModel tmp : this.controller.getCustomerFactory().getCustomers()) {
            String name = "";
            name += tmp.getFirstname();
            name += (tmp.getInsertion().length() > 0) ? " "+tmp.getInsertion(): "";
            name += " "+tmp.getLastname();

            cbModelCustomers.addElement( new Item(tmp.getId(), name) );
        }

        cbCustomer = new JComboBox(cbModelCustomers);
        cbCustomer.setRenderer( new ItemRenderer() );
        if(this.boolEdit) {
            for(int i=0; i<cbCustomer.getItemCount(); i++) {
                Item item = (Item)cbModelCustomers.elementAt(i);
                if(item.getId() == model.getCustomer_id())
                    cbCustomer.setSelectedItem(item);
            }
        }
        cbCustomer.addActionListener( this );

        form.add(lblCustomer);
        lblCustomer.setLabelFor(cbCustomer);
        form.add(cbCustomer);

        //input : payment type id
        HashMap<Integer, Object> cboxFill = this.controller.getFactory().getPaymentTypes();
        Vector cbModelPaymentTypes = new Vector();
        Iterator it = cboxFill.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            Item i = new Item(Integer.parseInt(pairs.getKey().toString()), pairs.getValue().toString());
            cbModelPaymentTypes.addElement( i );
        }

        lblPaymentType = new JLabel("Selecteer betaalmethode");
        cbPaymentType = new JComboBox(cbModelPaymentTypes);
        cbPaymentType.setRenderer( new ItemRenderer() );
        if(this.boolEdit) {
            for(int i=0; i<cbPaymentType.getItemCount(); i++) {
                Item item = (Item)cbModelPaymentTypes.elementAt(i);
                if(item.getId() == model.getPayment_type_id())
                    cbPaymentType.setSelectedItem(item);
            }
        }
        cbPaymentType.addActionListener( this );

        form.add(lblPaymentType);
        lblPaymentType.setLabelFor(cbPaymentType);
        form.add(cbPaymentType);

        //input : payment type id
        HashMap<Integer, Object> cboxFill2 = this.controller.getFactory().getReservationStatus();
        Vector cbModelStatusTypes = new Vector();
        Iterator it2 = cboxFill2.entrySet().iterator();
        while(it2.hasNext()) {
            Map.Entry pairs = (Map.Entry)it2.next();
            cbModelStatusTypes.addElement( new Item(Integer.parseInt(pairs.getKey().toString()), pairs.getValue().toString()) );
        }

        //input : payment type id
        lblStatus = new JLabel("Selecteer status");
        cbStatus = new JComboBox(cbModelStatusTypes);
        cbStatus.setRenderer( new ItemRenderer() );
        if(this.boolEdit) {
            for(int i=0; i<cbStatus.getItemCount(); i++) {
                Item item = (Item)cbModelStatusTypes.elementAt(i);
                if(item.getId() == model.getStatus_id())
                    cbStatus.setSelectedItem(item);
            }
        }
        cbStatus.addActionListener( this );

        form.add(lblStatus);
        lblStatus.setLabelFor(cbStatus);
        form.add(cbStatus);

        SpringUtilities.makeCompactGrid(form,
                7, 2, //rows, cols
                50, 10,        //initX, initY
                50, 10);       //xPad, yPad

        if(txtStartDate.getText().length() > 0 && txtEndDate.getText().length() > 0)
            fillVehicleDropbox();

        frame.add(buttonBar, BorderLayout.NORTH);
        frame.add(form, BorderLayout.CENTER);

        form.setPreferredSize(new Dimension(700, 250));

        this.add(frame);
    }

    /**
     * Method to retrieve vehicles within the range of the start and end date.
     */
    private void fillVehicleDropbox() {
        Vector cbModelVehicles = new Vector();

        String[] fromFormatted = txtStartDate.getText().split("-");
        String[] tillFormatted = txtEndDate.getText().split("-");

        DateTime dtFrom = new DateTime(fromFormatted[2] + "-" + fromFormatted[1] + "-" + fromFormatted[0] + "T00:00:00.0");
        DateTime dtTill = new DateTime(tillFormatted[2] + "-" + tillFormatted[1] + "-" + tillFormatted[0] + "T00:00:00.0");

        //check if date till is before date from
        if( dtTill.isBefore(dtFrom) ) {
            MessageQueue.getInstance().addMessage("Einddatum is ingesteld voor de begindatum", 1);
            String messageSet = "";
            for(Message m : MessageQueue.getInstance().getMessages())
                messageSet += m.toString();
            MessageQueue.getInstance().clearQueue();
            displayMessage(this, 0, messageSet, "Formulier fout!", 0);

            return;
        }

        //create correct list of vehicles to rent
        List<VehicleModel> tmpVehicleList;
        if(model != null) {
            tmpVehicleList = this.controller.getVehicleFactory().getVehiclesExcludedBetween(
                    dtFrom,
                    dtTill,
                    model.getVehicle_id());

            VehicleModel tmpVehicle = this.controller.getVehicleFactory().getSingleVehicle(model.getVehicle_id());
            tmpVehicleList.add(tmpVehicle);
        } else {
            tmpVehicleList = this.controller.getVehicleFactory().getVehiclesExcludedBetween(
                    dtFrom,
                    dtTill,
                    0);
        }

        //push list in the combobox
        for(VehicleModel tmp : tmpVehicleList)
            cbModelVehicles.addElement( new Item(tmp.getId(), tmp.getBrand()+" ("+tmp.getType()+") - "+tmp.getLicenseplate() ) );

        //set the correct item as selected
        cbVehicle.setModel(new DefaultComboBoxModel(cbModelVehicles));
        if(this.boolEdit) {
            for(int i=0; i<cbVehicle.getItemCount(); i++) {
                Item item = (Item)cbModelVehicles.elementAt(i);
                if(item.getId() == model.getVehicle_id())
                    cbVehicle.setSelectedItem(item);
            }
        }
    }

    /**
     * Formats the date given to an american format
     * @param date
     * @return American formatted date
     */
    private String dateToAmericanFor(String date) {
        String[] formatted = date.split("-");
        try {
            return formatted[2]+"-"+formatted[1]+"-"+formatted[0];
        } catch(IndexOutOfBoundsException e) {
            return "";
        }
    }

    /**
     * This method processes the actions from buttons and other elements
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if(s == btnSave) {

            Item vehicleItem = (Item)cbVehicle.getSelectedItem();
            Item customerItem = (Item)cbCustomer.getSelectedItem();
            Item paymentItem = (Item)cbPaymentType.getSelectedItem();
            Item statusItem = (Item)cbStatus.getSelectedItem();

            //set all the data
            formFields.put("id", txtId.getText());
            formFields.put("startdate", dateToAmericanFor(txtStartDate.getText()));
            formFields.put("enddate", dateToAmericanFor(txtEndDate.getText()));
            formFields.put("vehicle_id", (vehicleItem != null) ? vehicleItem.getId() : 0);
            formFields.put("customer_id", customerItem.getId());
            formFields.put("payment_type_id", paymentItem.getId());
            formFields.put("status_id", statusItem.getId());

            int code = controller.save(formFields);
            switch(code) {
                case 0 :
                    displayMessage(this, 0, "Alle velden met een asteriks(*) dienen ingevuld te worden.", "Formulier fout!", 0);
                    break;
                case 1 :
                    if(this.boolEdit)
                    {
                        displayMessage(this, 1, "Reservering is succesvol opgeslagen!", "Geslaagd!", 0);
                    } else {
                        displayMessage(this, 1, "Reservering is succesvol aangemaakt!", "Geslaagd!", 0);
                    }
                    ReservationController reservationController = new ReservationController();
                    PageScreenSwitcher.getInstance().switchScreen(reservationController.getView());
                    break;
                case 2 :
                    String messageSet = "";
                    for(Message m : MessageQueue.getInstance().getMessages())
                        messageSet += m.toString();
                    MessageQueue.getInstance().clearQueue();
                    displayMessage(this, 0, messageSet, "Formulier fout!", 0);
                    break;
            }
        }
        else if(s == btnBack)
        {
            ReservationController reservationController = new ReservationController();
            PageScreenSwitcher.getInstance().switchScreen(reservationController.getView());
        }
    }
}
