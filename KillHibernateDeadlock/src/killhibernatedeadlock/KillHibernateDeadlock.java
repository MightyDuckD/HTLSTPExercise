package killhibernatedeadlock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Copyright (c) 2016 Simon Lehner-Dittenberger
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
 
/**
 *
 * @author Simon Lehner-Dittenberger <simon.lehnerd@gmail.com>
 */
public class KillHibernateDeadlock {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5433/";
    static final String DB = "5A_10_Lab3";
    //  Database credentials
    static final String USER = "5AHIF_16";
    static final String PASS = "*******";

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
        Class.forName(JDBC_DRIVER);

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
