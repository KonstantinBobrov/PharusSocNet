package ru.pharus.socnetwork.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class connection {
    public static void main(String[] args) {
/*        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            //Class.forName("org.gjt.mm.mysql.Driver");
            con = DriverManager.getConnection("com.pharus.socnetwork.dao.socnet.dao:mysql://127.0.0.1/test",
                    "root", "123456");
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM STUDENTS");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + "" +
                        rs.getInt(3));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }*/
    }
}
