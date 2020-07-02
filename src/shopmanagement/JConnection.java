package shopmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class JConnection {

    public static Connection connectdb() {
//        String currentDir = System.getProperty("user.dir");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost/shopmanagement", "root", "");
            System.out.println("Connected");
            return c;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }

    }
}
