package helpers;

import javax.swing.table.AbstractTableModel;

/**
 * User: Chris
 * Class: This class is used to act as a main abstract model for other classes
 */
public class MyTableModel extends AbstractTableModel {

    public MyTableModel(Object[][] data, String[] columnNames) {

    }

    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

}
