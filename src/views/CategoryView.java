package views;

import controllers.CategoryController;
import helpers.ACL;
import models.CategoryModel;
import helpers.MenuButton;
import helpers.PageScreenSwitcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Reinier
 * Class: View to show categories
 */
public class CategoryView extends IpsenView implements ActionListener
{
    private CategoryController controller;
    private JTable jTableCategories;
    private MenuButton btnAddCategory;
    private MenuButton btnDelCategory;
    private MenuButton btnEditCategory;
    private JLabel lblAddCategory = new JLabel("Categorie naam");

    private JPanel topPanel;

    /**
     * Class Constructor
     * @param controller Category Controller
     */

    public CategoryView(CategoryController controller) {
        this.controller = controller;

        this.setLayout(new BorderLayout());
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(1000, 68));

        btnAddCategory = new MenuButton("assets/nw_category_btn.png",null, null);
        btnAddCategory.addActionListener(this);

        btnDelCategory = new MenuButton("assets/del_category_btn.png",null, null);
        btnDelCategory.addActionListener(this);

        btnEditCategory = new MenuButton("assets/edit_category_btn.png",null, null);
        btnEditCategory.addActionListener(this);

        if(ACL.getInstance().hasPermissionFor("CAN_CREATE_CATEGORY"))
            this.topPanel.add(btnAddCategory);

        if(ACL.getInstance().hasPermissionFor("CAN_EDIT_CATEGORY"))
            this.topPanel.add(btnEditCategory);

        if(ACL.getInstance().hasPermissionFor("CAN_DELETE_CATEGORY"))
            this.topPanel.add(btnDelCategory);

        this.add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Method to init all the elements in the view, this method is called later by the controller
     */

    public void init() {

        Dimension viewSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,viewSize.width, viewSize.height);

        setVisible(true);

//        this.jTableCategories = this.controller.getCategoriesTable();

        String[] columnNames = {"id",
                "date",
                "name",
                "description"
        };

        java.util.List<CategoryModel> categories = controller.getFactory().getCategories();

        Object[][] data = new Object[categories.size()][4];
        for(int i = 0; i < categories.size(); i++) {

            data[i][0] = new Integer(categories.get(i).getId());
            data[i][1] = categories.get(i).getDate();
            data[i][2] = categories.get(i).getName();
            data[i][3] = categories.get(i).getDescription();

        }


        this.jTableCategories = new JTable(data, columnNames) {
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }

        };




        JScrollPane tableSP = new JScrollPane(jTableCategories);

        this.add(tableSP, BorderLayout.CENTER);
    }
    /**
     * Method to actionPerformed changes the view, by using the controller and displaying the next view
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnAddCategory)
        {
            CategoryAddView viewAddCategory = new CategoryAddView(this.controller);
            PageScreenSwitcher.getInstance().switchScreen(viewAddCategory);

        }
        if(e.getSource() == this.btnDelCategory)
        {
            //delete vehicle
            int confirm = JOptionPane.YES_NO_OPTION;
            confirm = displayMessage(this, 2, "Categorie verwijderen?", "Warning", confirm);
            if(confirm == JOptionPane.YES_OPTION) {
                try {
                    CategoryModel model = this.controller.getFactory().getCategory(Integer.parseInt(jTableCategories.getValueAt(jTableCategories.getSelectedRow(), 0).toString()));
                    this.controller.getFactory().delete(model);

                    this.displayMessage(this, 1, "Categorie succesvol verwijderd!", "Geslaagd", 0);

                    //reload view
                    CategoryController categoryController = new CategoryController();
                    PageScreenSwitcher.getInstance().switchScreen(categoryController.getView());
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Exception: "+ex.getMessage());
                }
            }
        }
        if(e.getSource() == this.btnEditCategory)
        {
//            CategoryAddView viewAddCategory = new CategoryAddView(this.controller, jTableCategories.getValueAt(jTableCategories.getSelectedRow(),0));
//            PageScreenSwitcher.getInstance().switchScreen(viewAddCategory);


            //edit category
            CategoryAddView categoryAddView;
            try {
                categoryAddView = new CategoryAddView(this.controller, jTableCategories.getValueAt(jTableCategories.getSelectedRow(),0));
                PageScreenSwitcher.getInstance().switchScreen(categoryAddView);
            } catch(ArrayIndexOutOfBoundsException ex) {
                this.displayMessage(this, 0, "Geen category geselecteerd!", "Foutmelding", 0);
            }
        }
    }
}
