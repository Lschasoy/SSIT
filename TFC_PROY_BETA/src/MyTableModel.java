import javax.swing.table.DefaultTableModel;
/**
 * @web http://www.jc-mouse.net/
 * @author Mouse
 */
public class MyTableModel extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}