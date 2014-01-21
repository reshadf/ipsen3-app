package helpers;

import org.joda.time.DateTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: Chris
 * Class: This class uses the availible external library joda time and java own external libraries.
 */

public class DatePicker implements ItemListener
{
    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);;
    JLabel l = new JLabel("", JLabel.CENTER);
    String day = "";
    JDialog d;
    JButton[] button = new JButton[49];

    //modifications
    JPanel topPanel = new JPanel();
    JComboBox cbYear;
    JLabel lblYear = new JLabel("Selecteer Jaar: ");
    String[] years = new String[100];

    /**
     * Pick the date
     * @param parent
     */

    public DatePicker(JFrame parent) {
        d = new JDialog();
        d.setModal(true);
        String[] header = { "Zo", "Ma", "Di", "Wo", "Do", "Vr", "Za" };
        JPanel p1 = new JPanel(new GridLayout(7, 7));
        p1.setPreferredSize(new Dimension(450, 160)); //120

        for (int x = 0; x < button.length; x++) {
            final int selection = x;
            button[x] = new JButton();
            button[x].setFocusPainted(false);
            button[x].setBackground(Color.white);
            if (x > 6)
                button[x].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        day = button[selection].getActionCommand();
                        d.dispose();
                    }
                });
            if (x < 7) {
                button[x].setText(header[x]);
                button[x].setForeground(Color.red);
                button[x].setBorderPainted(false);
                button[x].setFocusPainted(false);
            }
            p1.add(button[x]);
        }
        JPanel p2 = new JPanel(new GridLayout(1, 3));
        JButton previous = new JButton("<< Vorige");
        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month--;
                displayDate();
            }
        });
        p2.add(previous);
        p2.add(l);
        JButton next = new JButton("Volgende >>");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month++;
                displayDate();
            }
        });
        p2.add(next);

        //mods

        DateTime dt = new DateTime();

        for(int i = 0; i < 100; i++)
        {
            years[i] = "" + ((dt.getYear() + 1) - i);
        }

        cbYear = new JComboBox(years);
        cbYear.setSelectedIndex(1);
        cbYear.addItemListener(this);
        topPanel.add(lblYear);
        topPanel.add(cbYear);

        //mods

        d.add(topPanel, BorderLayout.NORTH);

        d.add(p1, BorderLayout.CENTER);
        d.add(p2, BorderLayout.SOUTH);
        d.pack();
        d.setLocationRelativeTo(parent);
        displayDate();
        d.setVisible(true);
    }

    /**
     * Displays the date
     */
    public void displayDate() {
        for (int x = 7; x < button.length; x++)
            button[x].setText("");
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "MMMM yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, 1);
        int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)
            button[x].setText("" + day);
        l.setText(sdf.format(cal.getTime()));
        d.setTitle("Date Picker");
    }

    /**
     * Set picked date
     * @return the picked date
     */
    public String setPickedDate() {
        if (day.equals(""))
            return day;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "dd-MM-yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
    }

    /**
     * Shows through event (import) datestuff
     * @param ie
     */
    @Override
    public void itemStateChanged(ItemEvent ie) {
            year = Integer.parseInt(ie.getItem().toString());
            displayDate();
    }
}