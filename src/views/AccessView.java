package views;

import controllers.AccountController;
import helpers.MenuButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Reinier Koops
 * Class: View, shows what the user sees (and gets its info through the controller)
 */

public class AccessView extends IpsenView implements ActionListener
{
    private AccountController controller;

    private JTable table;
    private JButton btnRightGrp, btnRight, btnAccount, btnStatistics;
    private JPanel topPanel;

    /**
     * Class Constructor
     * @param controller Account Controller
     */

    public AccessView(AccountController controller) // controller mist
    {
        //link aan betreffende controller

        this.setLayout( new BorderLayout() );
        topPanel = new JPanel();
        topPanel.setPreferredSize( new Dimension( 1000, 68 ) );

        //buttons afbeelding nog toewijzen

        btnRight = new MenuButton( "assets/rechten.png", null, null );
        btnRightGrp = new MenuButton( "assets/nw_rightgroep_btn.png", null, null );
        btnAccount = new MenuButton( "assets/accounts.png", null, null );
        btnStatistics = new MenuButton( "assets/statistiek_inzien", null, null );

        //buttons laten werken als je erop klikt

        btnRight.addActionListener( this );
        btnAccount.addActionListener( this );
        btnRightGrp.addActionListener( this );
        btnStatistics.addActionListener( this );

        //buttons toevoegen aan het topPanel

        this.topPanel.add( btnRight );
        this.topPanel.add( btnRightGrp );
        this.topPanel.add( btnAccount );
        this.topPanel.add( btnStatistics );

        this.add( topPanel, BorderLayout.NORTH );

    }

    /**
     * Method to actionPerformed changes the view, by using the controller and displaying the next view
     *
     * the actionPerfomed performs no action in this class only showing the buttons
     */

    @Override
    public void actionPerformed( ActionEvent e )
    {
        Object s = e.getSource();
        if (s == btnRight)
        {
            //Right button geselecteerd -> ga naar dit submenu
//            AccessRightView accesRightView;
//            {
//                accesRightView = new AccessRightView();
//            }
        }

        if (s == btnRightGrp)
        {
            //Rightgroup button geselecteerd -> ga naar dit submenu
//            AccessRightGroupView accesRightGroupView;
//            {
//                accesRightGroupView = new AccessRightGroupView();
//            }
        }

        if (s == btnAccount)
        {
            //Account button geselecteerd -> ga naar dit submenu
//            AccesAccountView accesAccountView;
//            {
//                accesAccountView = new AccesAccountView();
//            }
        }

        if (s == btnStatistics)
        {
            //Statistics button geselecteerd -> ga naar dit submenu
//            AccessStatisticsView accesStatisticsView;
//            {
//                accesStatisticsView = new AccessStatisticsView();
//            }
        }
    }

}

