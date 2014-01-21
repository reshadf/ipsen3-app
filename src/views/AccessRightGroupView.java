package views;

import controllers.ManagementController;
import controllers.RightController;
import controllers.RightGroupController;
import helpers.Message;
import helpers.MessageQueue;
import helpers.MenuButton;
import helpers.PageScreenSwitcher;
import models.RightGroupModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * User: Jasper
 * Class: View, shows what the user sees (and gets its info through the controller)
 */

public class AccessRightGroupView extends IpsenView implements ActionListener
{
    private RightGroupController controller;

    private JTable table;
    private JButton btnNewRightGrp, btnDeleteRightGrp, btnEditRightGrp, btnRights, btnBack;
    private JPanel topPanel;

    /**
     *
     * Constructor, create buttons and layout
     *
     * @param controller
     */

    public AccessRightGroupView(RightGroupController controller) //controller
    {
        this.controller = controller;

        this.setLayout(new BorderLayout());
        topPanel = new JPanel();

        //buttons afbeelding nog toewijzen

        btnBack = new MenuButton("assets/terug_naar_overzicht.png");
        btnNewRightGrp = new MenuButton( "assets/nw_rightgroep_btn.png"); //create buttons
        btnEditRightGrp = new MenuButton( "assets/edit_rightgroep_btn.png");
        btnDeleteRightGrp = new MenuButton( "assets/del_rightgroep_btn.png");
        btnRights = new MenuButton( "assets/rechten.png");

        //buttons laten werken als je erop klikt

        btnBack.addActionListener(this);
        btnNewRightGrp.addActionListener( this );
        btnDeleteRightGrp.addActionListener( this );
        btnEditRightGrp.addActionListener( this );
        btnRights.addActionListener( this );

        this.topPanel.add(btnBack);
        this.topPanel.add( btnNewRightGrp );
        this.topPanel.add( btnEditRightGrp );
        this.topPanel.add( btnDeleteRightGrp );
        this.topPanel.add( btnRights );

        this.add(topPanel, BorderLayout.NORTH);
    }

    /**
     *
     * Init method for initing the table
     *
     */

    public void init() {
        String[] columnNames =
                {
                        "#",
                        "Naam",
                        "Omschrijving"
                }; //column names

        List<RightGroupModel> rightGroups = this.controller.getFactory().getRightGroups();

        Object[][] data = new Object[rightGroups.size()][3]; //data array
        for(int i = 0; i < rightGroups.size(); i++) { //put all items in our aray
            data[i][0] = new Integer(rightGroups.get(i).getId());
            data[i][1] = rightGroups.get(i).getName();
            data[i][2] = rightGroups.get(i).getDescription();
        }

        table = new JTable(data, columnNames) { //create table bitch
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }

        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //only one row can be slected

        JScrollPane tableSP = new JScrollPane(table);

        this.add(tableSP, BorderLayout.CENTER);
    }

    /**
     *
     * On click of out buttons
     *
     * @param e
     */

    @Override
    public void actionPerformed( ActionEvent e ) //button is clicked, can't touch this oehoehoehoehoe
    {
        Object s = e.getSource();
        if(s == btnBack)
        {
            ManagementController managementController = new ManagementController();
            PageScreenSwitcher.getInstance().switchScreen(managementController.getView());
        }
        if (s == btnNewRightGrp) //new rightgroup
        {
            //Rightgroup create group
            AccessRightGroupEditView accesNewRightGrpEditView = new AccessRightGroupEditView(this.controller);
            PageScreenSwitcher.getInstance().switchScreen(accesNewRightGrpEditView); //switch screen
        }

        if (s == btnDeleteRightGrp) //delete
        {
            //Rightgroup create group
            int confirm = JOptionPane.YES_NO_OPTION;
            confirm = displayMessage( this, 2, "Rechtengroep verwijderen?", "Warning", confirm ); //Are u shure
            if(confirm == JOptionPane.YES_OPTION)
            {
                try
                {
                    RightGroupModel model = this.controller.getFactory().getRightGroup( Integer.parseInt( table.getValueAt( table.getSelectedRow(), 0 ).toString() ) );
                    this.controller.getFactory().delete(model);

                    this.displayMessage( this, 1, "Rechtengroep succesvol verwijderd!", "Geslaagd", 0 ); //hurray mission accomplished

                    //reload view
                    RightGroupController rightGroupController = new RightGroupController();
                    PageScreenSwitcher.getInstance().switchScreen( rightGroupController.getView() );
                }
                catch (ArrayIndexOutOfBoundsException ex)
                {
                    System.out.println("Exception: "+ex.getMessage());
                }
            }
        }

        if (s == btnEditRightGrp) //edit jeeeee
        {
            //Account button geselecteerd -> ga naar dit submenu
            AccessRightGroupEditView accesNewRightGrpEditView;
            try
            {
                accesNewRightGrpEditView = new AccessRightGroupEditView(this.controller, table.getValueAt(table.getSelectedRow(),0));
                PageScreenSwitcher.getInstance().switchScreen( accesNewRightGrpEditView );
            }
            catch (ArrayIndexOutOfBoundsException ex)
            {
                MessageQueue.getInstance().addMessage("Geen rechtengroep geselecteerd!", 1);

                String messageSet = "";
                for(Message message : MessageQueue.getInstance().getMessages()) {
                    messageSet += message.toString();
                }
                MessageQueue.getInstance().clearQueue();

                displayMessage(this, 0, messageSet, "Foutmelding", 0);
            }
        }

        if (s == btnRights)
        {

            if(table.getSelectedRow() == -1)
            {
                MessageQueue.getInstance().addMessage("Geen rechtengroep geselecteerd!", 1);

                String messageSet = "";
                for(Message message : MessageQueue.getInstance().getMessages()) {
                    messageSet += message.toString();
                }
                MessageQueue.getInstance().clearQueue();

                displayMessage(this, 0, messageSet, "Foutmelding", 0);
            }
            else
            {
                RightController rightController = new RightController(table.getValueAt(table.getSelectedRow(), 0));
                PageScreenSwitcher.getInstance().switchScreen(rightController.getView());
            }

        }
    }

}
