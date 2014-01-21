package views;

import controllers.ManagementController;
import controllers.ReservationController;
import helpers.DatePicker;
import helpers.MyMouseListener;
import helpers.MenuButton;
import helpers.PageScreenSwitcher;
import models.ReservationModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * User: Floris
 * Class: View, shows what the user sees (and gets its info through the controller)
 */
public class StatisticsView extends IpsenView implements ActionListener{

    private JTable reservationTable;
    private DefaultTableModel reservationTableModel;
    private JPanel topPanel;
    private MenuButton btnBack;

    private ReservationController reservationController;

    //textfields for start and end date
    private JLabel lblStartDate;
    private JTextField txtStartDate;
    private JLabel lblEndDate;
    private JTextField txtEndDate;
    private JButton btnConfirm;


    public StatisticsView(ReservationController reservationController) {
        this.reservationController = reservationController;

        this.topPanel = new JPanel();


        this.setLayout(new BorderLayout());

        btnBack = new MenuButton("assets/terug_naar_overzicht.png", null, null);
        btnBack.addActionListener(this);
        topPanel.add(btnBack, BorderLayout.NORTH);


        //declare table and fill with content
        this.reservationTable = new JTable();
        setModel();

        //Make panel for date-selection
        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.yellow);

        txtStartDate = new JTextField(10);
        txtEndDate = new JTextField(10);


        //input : startdate
        lblStartDate = new JLabel("Van");
        txtStartDate = new JTextField(10);
        txtStartDate.addMouseListener(new MyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                txtStartDate.setText(new DatePicker((JFrame) JFrame.getFrames()[0]).setPickedDate());
            }
        });
        txtStartDate.setText("");

        southPanel.add(lblStartDate);
        lblStartDate.setLabelFor(txtStartDate);
        southPanel.add(txtStartDate);

        //input : enddate
        lblEndDate = new JLabel("Tot");
        txtEndDate = new JTextField(10);
        txtEndDate.addMouseListener(new MyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                txtEndDate.setText(new DatePicker((JFrame) JFrame.getFrames()[0]).setPickedDate());
            }
        });

        txtEndDate.setText("");

        this.btnConfirm = new JButton("Zoeken");
        this.btnConfirm.addActionListener(this);
        southPanel.add(lblEndDate);
        lblEndDate.setLabelFor(txtEndDate);
        southPanel.add(txtEndDate);
        southPanel.add(btnConfirm);

        JScrollPane tableSP = new JScrollPane(reservationTable);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(tableSP, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnBack) {
            ManagementController managementController = new ManagementController();                    //New instance of RightGroupController
            PageScreenSwitcher.getInstance().switchScreen(managementController.getView());   //Switch screen to AccessRightGroupView
        }

        if (e.getSource() == this.btnConfirm) {
            resetTableModel();
            this.setModel(txtStartDate.getText(), txtEndDate.getText());
        }
    }


    public void setModel()
    {
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

        List<ReservationModel> reservations = this.reservationController.getFactory().getReservations("reservations");

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

        this.reservationTableModel = new DefaultTableModel(data, columnNames);
        this.reservationTable.setModel(reservationTableModel);
    }

    /**
     * (Re)sets the tablemodel with boundary dates
     * @param dateFrom
     * @param dateTo
     */
    public void setModel(String dateFrom, String dateTo) {
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

        String[] formattedFromDate = dateFrom.split("-");
        String[] formattedToDate = dateTo.split("-");

        List<ReservationModel> reservations = reservationController.getFactory().getReservations(formattedFromDate[2]+"-"+formattedFromDate[1]+"-"+formattedFromDate[0], formattedToDate[2]+"-"+formattedToDate[1]+"-"+formattedToDate[0]);

        System.out.println("StatisticsView:setModel = "+reservations.size());

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

        if (reservationTableModel.getRowCount() > 0) {
            for (int i = reservationTableModel.getRowCount() - 1; i > -1; i--) {
                reservationTableModel.removeRow(i);
            }
        }

        this.reservationTableModel = new DefaultTableModel(data, columnNames);
        this.reservationTable.setModel(reservationTableModel);
    }

    public void resetTableModel() {
        this.reservationTableModel.setRowCount(0);
    }
}
