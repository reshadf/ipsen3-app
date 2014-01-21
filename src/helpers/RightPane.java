package helpers;

import helpers.ListPane;
import helpers.MenuButton;
import helpers.checkbox.JCheckboxWithId;
import models.RightModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * User: Jasper
 * Class: creates checkboxes for other classes to use
 */

public class RightPane extends JPanel implements ActionListener {

    ArrayList<JCheckboxWithId> checkboxes = new ArrayList<JCheckboxWithId>();
    MenuButton btnSelectAll = new MenuButton("assets/alles_selecteren.png");
    MenuButton btnDeselectAll = new MenuButton("assets/alles_deselecteren.png");

    /**
     *
     * Constructor create list of checkboxes
     *
     * @param listItems
     */

    public RightPane(java.util.List<RightModel> listItems) {
        ListPane listPanel = new ListPane();
        listPanel.setBackground(Color.WHITE);

        listPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        for(int i = 0; i < listItems.size(); i++) { //put all items in our aray
            JCheckboxWithId tempCheckbox = new JCheckboxWithId(listItems.get(i).getId(), "" + listItems.get(i).getDescription());
            checkboxes.add(tempCheckbox);
            listPanel.add(tempCheckbox, gbc);
        }

        JScrollPane sp = new JScrollPane(listPanel);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPane = new JPanel(new GridBagLayout());

        btnSelectAll.addActionListener(this);
        btnDeselectAll.addActionListener(this);

        buttonPane.add(btnSelectAll, gbc);
        buttonPane.add(btnDeselectAll, gbc);

        gbc.weighty = 1;
        buttonPane.add(new JPanel(), gbc);

        mainPanel.add(sp, BorderLayout.CENTER);
        mainPanel.add(buttonPane, BorderLayout.EAST);

        setLayout(new BorderLayout());

        add(mainPanel);
    }

    /**
     *
     * Check checkboxes that are listed in database for this item
     *
     * @param checkedItems
     */

    public void fillCheckboxes(ArrayList<Integer> checkedItems) {
        System.out.println(checkedItems);
        for(JCheckboxWithId checkbox : checkboxes)
        {
            System.out.println(checkbox.getId());
            if(checkedItems.contains(checkbox.getId()))
                checkbox.setSelected(true);
        }
    }

    /**
     *
     * Get a list of all checkboxes that are checked
     *
     * @return ArrayList
     */

    public ArrayList<Integer> getSelectedCheckboxes()
    {
        ArrayList<Integer> checked = new ArrayList<Integer>();

        for(JCheckboxWithId checkbox : checkboxes)
        {
            if(checkbox.isSelected())
                checked.add(Integer.valueOf("" + checkbox.getId()));
        }

        return checked;
    }

    /**
     *
     * Get a list of all checkboxes that are not checked
     *
     * @return ArrayList
     */

    public ArrayList<Integer> getUnSelectedCheckboxes()
    {
        ArrayList<Integer> unchecked = new ArrayList<Integer>();

        for(JCheckboxWithId checkbox : checkboxes)
        {
            if(!checkbox.isSelected())
                unchecked.add(Integer.valueOf(checkbox.getId() + ""));
        }

        return unchecked;
    }

    /**
     *
     * Select al or select none
     *
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnSelectAll)
        {
            for(JCheckboxWithId checkbox : checkboxes)
            {
                checkbox.setSelected(true);
            }
        }
        else if (e.getSource() == btnDeselectAll)
        {

            for(JCheckboxWithId checkbox : checkboxes)
            {
                checkbox.setSelected(false);
            }
        }
    }
}