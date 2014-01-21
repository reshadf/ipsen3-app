package views;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import controllers.ReservationController;
import data.CustomerTable;
import data.UserTable;
import helpers.ACL;
import helpers.MenuButton;
import helpers.PDFHelper;
import helpers.PageScreenSwitcher;
import models.*;
import org.joda.time.DateTime;
import org.joda.time.Days;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: Chris
 * Class: View to show all the reservations and give the user the possibility to alter, delete or create a reservation
 */
public class ReservationView extends IpsenView implements ActionListener
{
    private ReservationController controller;

    private JTable table;
    private JButton btnNewReservation;
    private JButton btnEditReservation;
    private JButton btnGenerateInvoice;
    private JButton btnCancelReservation;
    private JButton btnCreatePrepareList;
    private JPanel topPanel;

    private HashMap<String, Object> customer;

    /**
     * Class Constructor
     * @param controller Reservation Controller
     */
    public ReservationView(ReservationController controller) {
        this.controller = controller;

        this.setLayout(new BorderLayout());
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(1000, 68));

        btnNewReservation = new MenuButton("assets/nw_reservation_btn.png", null, null); //add vehicle knop of zoals Reshad zou zeggen vihicle
        btnEditReservation = new MenuButton("assets/edit_reservation_btn.png", null, null); //edit this bastard
        btnGenerateInvoice = new MenuButton("assets/generate_final_invoice.png", null, null); //generates an invoice
        btnCancelReservation = new MenuButton("assets/annuleren.png", null, null); //cancel reservation
        btnCreatePrepareList = new MenuButton("assets/generate_uitleenlijst.png", null, null); //generates prepare list

        btnNewReservation.addActionListener(this);
        btnEditReservation.addActionListener(this);
        btnGenerateInvoice.addActionListener(this);
        btnCancelReservation.addActionListener(this);
        btnCreatePrepareList.addActionListener(this);

        //check through ACL which permissions the user has, disable buttons appropriately
        if(ACL.getInstance().hasPermissionFor("CAN_CREATE_RESERVATION"))
            this.topPanel.add(btnNewReservation);

        if(ACL.getInstance().hasPermissionFor("CAN_EDIT_RESERVATION"))
            this.topPanel.add(btnEditReservation);

        if(ACL.getInstance().hasPermissionFor("CAN_INVOICE_RESERVATION"))
            this.topPanel.add(btnGenerateInvoice);

        if(ACL.getInstance().hasPermissionFor("CAN_CANCEL_RESERVATION"))
            this.topPanel.add(btnCancelReservation);

        if(ACL.getInstance().hasPermissionFor("CAN_PREPARE_LIST_RESERVATION"))
            this.topPanel.add(btnCreatePrepareList);

        this.add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Method to init all the elements in the view, this method is called later by the controller
     */
    public void init() {
        String[] columnNames = {
            "#",
            "Ingangsdatum",
            "Verloopt op",
            "Merk voertuig",
            "Type voertuig",
            "Nummerbord",
            "Bedrijf",
            "Naam",
            "Betaling",
            "Status"
        };

        //get all the reservations
        List<ReservationModel> reservations = this.controller.getFactory().getReservations("reservations");

        //set each row in the table
        Object[][] data = new Object[reservations.size()][10];
        for(int i = 0; i < reservations.size(); i++) {
            data[i][0] = new Integer(reservations.get(i).getId());

            data[i][1] = reservations.get(i).getStartdate().toString("d-M-y");
            if(reservations.get(i).getEnddate() != null)
                data[i][2] = reservations.get(i).getEnddate().toString("d-M-y");
            data[i][3] = reservations.get(i).getBrand();
            data[i][4] = reservations.get(i).getType();
            data[i][5] = reservations.get(i).getLicenseplate();
            if(reservations.get(i).getCompany().equals("null"))
                data[i][6] = "";
            else
                data[i][6] = reservations.get(i).getCompany();
            data[i][7] = reservations.get(i).getName();
            data[i][8] = reservations.get(i).getPayment();
            data[i][9] = reservations.get(i).getStatus();
        }

        //disable cell editing
        table = new JTable(data, columnNames) {
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tableSP = new JScrollPane(table);

        this.add(tableSP, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if(s == btnNewReservation) {
            //new vehicle
            ReservationEditView reservationEditView = new ReservationEditView(this.controller);
            PageScreenSwitcher.getInstance().switchScreen(reservationEditView);
        }
        else if(s == btnEditReservation) {
            try {
                ReservationEditView reservationEditView = new ReservationEditView(this.controller, table.getValueAt(table.getSelectedRow(),0));
                PageScreenSwitcher.getInstance().switchScreen(reservationEditView);
            } catch(ArrayIndexOutOfBoundsException ex) {
                this.displayMessage(this, 0, "Geen voertuig geselecteerd!", "Foutmelding", 0);
            }
        }
        else if(s == btnGenerateInvoice) {
            if(table.getSelectedRow() == -1)
            {
                this.displayMessage(this, 0, "Geen reservering geselecteerd!", "Foutmelding", 0);
            }
            else
            {
                try {
                    System.out.println("Invoice genereren");
                    try {
                        //create an instance of PDF Helper to create a PDF
                        String fileName = new DateTime().toString(table.getValueAt(table.getSelectedRow(),0)+"_dMyHms")+".pdf";
                        PDFHelper pdfHelper = new PDFHelper(new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK), fileName, "pdf");
                        Document document = pdfHelper.openInstance();

                        ReservationModel rAdvModel = this.controller.getFactory().getSingleReservationAdvanced(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
                        ReservationModel rModel = this.controller.getFactory().getSingleReservation(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));

                        VehicleModel vModel = this.controller.getVehicleFactory().getSingleVehicle(rModel.getVehicle_id());

                        int days = Days.daysBetween(rModel.getStartdate().toDateMidnight(), rModel.getEnddate().toDateMidnight()).getDays()+1;
                        System.out.println("dagen: " + days);
                        double total =  days * vModel.getHourly_rent();

                        customer = new HashMap<String, Object>();
                        CustomerTable customerTable = new CustomerTable();

                        ArrayList<HashMap<String, Object>> customers = customerTable.getCustomers();

                        for(int i = 0; i < customers.size(); i++) {
                            if(Integer.valueOf((String) customers.get(i).get("id")) == Integer.valueOf(rModel.getCustomer_id())) {
                                customer = customers.get(i);
                            }
                        }

                        //First table fixed TODO Other 2 tables need to be converted
                        document.add(pdfHelper.setInformation(rAdvModel, customer));
                        document.add(pdfHelper.invoiceTable(days, vModel, rModel)); //this one
                        document.add(pdfHelper.priceTable(total)); //and this one

                        pdfHelper.closeInstance();

                        if (Desktop.isDesktopSupported()) {
                            try {
                                File myFile = new File("pdf/"+fileName);
                                Desktop.getDesktop().open(myFile);
                            } catch (IOException ex) {
                                // no application registered for PDFs
                            }
                        }

                        //TODO Change reservation State in DATABASE

    //                    this.displayMessage(this, 1, "Factuur aangemaakt!", "Succesvol", 0);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } catch(ArrayIndexOutOfBoundsException ex) {
                    this.displayMessage(this, 0, "Geen reservering geselecteerd!", "Foutmelding", 0);
                }
            }
        }
        else if(s == btnCancelReservation) {
            //TODO Cancel Reservation Logic
            try {
                //delete vehicle
                int confirm = JOptionPane.YES_NO_OPTION;
                confirm = displayMessage(this, 2, "Reservering annuleren?", "Warning", confirm);
                if(confirm == JOptionPane.YES_OPTION) {
                    ReservationModel model = this.controller.getFactory().getSingleReservation(Integer.valueOf(table.getValueAt(table.getSelectedRow(),0).toString()));
                    model.setStatus_id(5); //canceled
                    if(!this.controller.getFactory().changeStatus(model)) {
                        this.displayMessage(this, 0, "Status veranderen is mislukt!", "Foutmelding", 0);
                    } else {
                        this.displayMessage(this, 1, "Status veranderen is gelukt!", "Succesvol", 0);
                        ReservationController reservationController = new ReservationController();
                        PageScreenSwitcher.getInstance().switchScreen(reservationController.getView());
                    }
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Exception: "+ex.getMessage());
            }
        }
        else if(s == btnCreatePrepareList) {
            System.out.println("generate prepare list");

            String fileName = "prepare_list_"+new DateTime().toString("dMyHms")+".pdf";
            try {
                DateTime prepareListDate = new DateTime().plusDays(1);

                PDFHelper pdfHelper = new PDFHelper(new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK), fileName, "pdf");
                Document document = pdfHelper.openInstance();
                List<ReservationModel> items = this.controller.getFactory().getPrepareListFor(prepareListDate);

                UserTable uTbl = new UserTable();
                ArrayList<HashMap<String, Object>> user = uTbl.getUserById(ACL.getInstance().getUserId());

                //add items to list
                document.add(pdfHelper.defaultInformationPrepareList(user));
                document.add(pdfHelper.createPrepareList(items, prepareListDate));
                document.add(pdfHelper.createSignatureSpace(prepareListDate));

                //close the document
                pdfHelper.closeInstance();

                if (Desktop.isDesktopSupported()) {
                    try {
                        File myFile = new File("pdf/"+fileName);
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex) {
                        // no application registered for PDFs
                    }
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

}
