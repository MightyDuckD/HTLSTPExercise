package killhibernatedeadlock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Simon
 */
public class KillHibernateDeadlock {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5433/";
    static final String DB = "5A_10_Lab3";
    //  Database credentials
    static final String USER = "5AHIF_16";
    static final String PASS = "jeke18";

    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        String query = ""
                + "SELECT \n"
                + "    pg_terminate_backend(pid),datname,usename,application_name \n"
                + "FROM \n"
                + "    pg_stat_activity \n"
                + "WHERE \n"
                + "    -- don't kill my own connection!\n"
                + "    pid <> pg_backend_pid()\n"
                + "    -- don't kill the connections to other databases\n"
                + "    AND datname = '" + DB + "'\n"
                + "    -- don't kill the connections of pgAdmin \n"
                + "    AND NOT (lower(application_name) LIKE '%pgadmin%')\n"
                + "    ;";
        Connection conn = null;
        Statement stmt = null;
        //STEP 2: Register JDBC driver
        Class.forName("org.postgresql.Driver");

        //STEP 3: Open a connection
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL + DB, USER, PASS);

        //STEP 4: Execute a query
        System.out.println("Creating statement...");
        stmt = conn.createStatement();

        System.out.println("Executing statement...");
        ResultSet rs = stmt.executeQuery(query);
        int cnt = 0;
        while (rs.next()) {
            cnt++;
            System.out.printf("killing (%s,%s,%s) ...\n",
                    rs.getString("datname"),
                    rs.getString("usename"),
                    rs.getString("application_name")
            );
        }
        System.out.println("Killed " + cnt + " sessions");

    }
}
