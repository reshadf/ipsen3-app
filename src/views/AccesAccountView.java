package views;

import controllers.AccountController;
import controllers.ManagementController;
import models.AccountModel;
import helpers.MenuButton;
import helpers.PageScreenSwitcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Floris
 * Class: Makes a View with account information and menu
 */
public class AccesAccountView extends IpsenView implements ActionListener
{
    private AccountController controller;

    private JTable table;
    private MenuButton btnNewAccount, btnDeleteAccount, btnEditAccount, btnBack;
    private JPanel topPanel;

    /**
     * Class Constructor
     * @param controller Account Controller
     */

    public AccesAccountView(AccountController controller) //controller
    {
        //link to controller
        this.controller = controller;

        this.setLayout( new BorderLayout() );
        topPanel = new JPanel();
        topPanel.setPreferredSize( new Dimension(1000, 68) );

        //buttons afbeelding nog toewijzen

        btnBack = new MenuButton("assets/terug_naar_overzicht.png");
        btnNewAccount = new MenuButton("assets/nw_account_btn.png", null, null);
        btnDeleteAccount = new MenuButton("assets/del_account_btn.png", null, null);
        btnEditAccount= new MenuButton("assets/edit_account_btn.png", null, null);

        //buttons laten werken als je erop klikt

        btnBack.addActionListener(this);
        btnNewAccount.addActionListener( this );
        btnDeleteAccount.addActionListener( this );
        btnEditAccount.addActionListener( this );

        this.topPanel.add(btnBack);
        this.topPanel.add( btnNewAccount );
        this.topPanel.add( btnEditAccount );
        this.topPanel.add( btnDeleteAccount );

        this.add(topPanel, BorderLayout.NORTH);
        //test
    }

    /**
     * Method to init all the elements in the view, this method is called later by the controller
     */

    public void init(){
        Dimension viewSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,viewSize.width, viewSize.height);

        setVisible(true);

//        this.jTableCategories = this.controller.getCategoriesTable();

        String[] columnNames = {"id",
                "Voornaam",
                "Tussenvoegsel",
                "Achternaam",
                "Email"
        };

        java.util.List<AccountModel> accounts = controller.getFactory().getAccount();

        Object[][] data = new Object[accounts.size()][5];
        for(int i = 0; i < accounts.size(); i++) {

            data[i][0] = new Integer(accounts.get(i).getId());
            data[i][1] = accounts.get(i).getFirstname();
            data[i][2] = accounts.get(i).getInsertion();
            data[i][3] = accounts.get(i).getLastname();
            data[i][4] = accounts.get(i).getEmail();

        }


        this.table = new JTable(data, columnNames) {
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }

        };




        JScrollPane tableSP = new JScrollPane(table);

        this.add(tableSP, BorderLayout.CENTER);

    }
    /**
     * Method to actionPerformed changes the view, by using the controller and displaying the next view
     */
    @Override
    public void actionPerformed( ActionEvent e )
    {
        Object s = e.getSource();
        if(s == btnBack)
        {
            ManagementController managementController = new ManagementController();
            PageScreenSwitcher.getInstance().switchScreen(managementController.getView());
        }
        if (s == btnNewAccount)
        {
            //Accountgroup create group
            AccessAccountEditView accesNewAccountEditView = new AccessAccountEditView( this.controller );
            PageScreenSwitcher.getInstance().switchScreen( accesNewAccountEditView );
        }

        if (s == btnDeleteAccount)
        {
            //Accountgroup create group
            int confirm = JOptionPane.YES_NO_OPTION;
            confirm = displayMessage( this, 2, "Account verwijderen?", "Warning", confirm );
            if (confirm == JOptionPane.YES_OPTION)
            {
                try
                {
                    AccountModel model = this.controller.getFactory().getAccount( Integer.parseInt( table.getValueAt( table.getSelectedRow(), 0 ).toString() ) );
                    this.controller.getFactory().delete( model );

                    this.displayMessage( this, 1, "Account succesvol verwijderd!", "Geslaagd", 0 );
                    //reload view
                    AccountController accountController = new AccountController();
                    PageScreenSwitcher.getInstance().switchScreen( accountController.getView() );
                } catch (ArrayIndexOutOfBoundsException ex)
                {
                    System.out.println( "Exception: " + ex.getMessage() );
                }
            }
        }

        if (s == btnEditAccount)
        {
            //Account button geselecteerd -> ga naar dit submenu
//            AccesNewAccountEditView accesNewAccountEditView;
            try
            {
//                accesNewAccountEditView = new AccesNewAccountEditView( this.controller, table.getValueAt( table.getSelectedRow(), 0 ) );
//                PageScreenSwitcher.getInstance().switchScreen( accesNewAccountEditView );

                //Accountgroup create group
                AccessAccountEditView accesNewAccountEditView = new AccessAccountEditView( this.controller, table.getValueAt( table.getSelectedRow(), 0 ) );
                PageScreenSwitcher.getInstance().switchScreen( accesNewAccountEditView );


            } catch (ArrayIndexOutOfBoundsException ex)
            {
                this.displayMessage( this, 0, "Geen account geselecteerd!", "Foutmelding", 0 );
            }
        }
    }

}
