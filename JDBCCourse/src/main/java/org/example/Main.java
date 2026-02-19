package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        // 1️⃣ Database connection details
        String url = "jdbc:postgresql://localhost:5432/JDBCDemo";
        String username = "postgres";
        String password = "Rajat@1803";

        // 2️⃣ SQL queries
        String createTableQuery =
                "CREATE TABLE IF NOT EXISTS jdbctable (" +
                        "id INT PRIMARY KEY, " +
                        "name VARCHAR(50)" +
                        ")";

        String insertQuery =
                "INSERT INTO jdbctable (id, name) VALUES (?, ?)";

        String selectQuery =
                "SELECT * FROM jdbctable";

        String updateQuery =
                "UPDATE jdbctable SET name = ? WHERE id = ?";

        String deleteQuery =
                "DELETE FROM jdbctable WHERE id = ?";

        try (
                // 3️⃣ Create connection
                Connection con = DriverManager.getConnection(url, username, password);
                Statement st = con.createStatement()
        ) {

            /* =======================
               CREATE TABLE
               ======================= */
            st.executeUpdate(createTableQuery);
            System.out.println("Table created (if not exists)");

            /* =======================
               CREATE (INSERT MULTIPLE ROWS)
               ======================= */
            PreparedStatement insertPs = con.prepareStatement(insertQuery);

            // ---- Entry 1
            insertPs.setInt(1, 1);
            insertPs.setString(2, "Rajat");
            insertPs.executeUpdate();

            // ---- Entry 2
            insertPs.setInt(1, 2);
            insertPs.setString(2, "Amit");
            insertPs.executeUpdate();

            // ---- Entry 3
            insertPs.setInt(1, 3);
            insertPs.setString(2, "Neha");
            insertPs.executeUpdate();

            // ---- Entry 4
            insertPs.setInt(1, 4);
            insertPs.setString(2, "Priya");
            insertPs.executeUpdate();

            // ---- Entry 5
            insertPs.setInt(1, 5);
            insertPs.setString(2, "Karan");
            insertPs.executeUpdate();

            System.out.println("5 records inserted");

            /* =======================
               READ (SELECT)
               ======================= */
            PreparedStatement selectPs = con.prepareStatement(selectQuery);
            ResultSet rs = selectPs.executeQuery();

            System.out.println("\nFetching data...");
            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("id") +
                                ", Name: " + rs.getString("name")
                );
            }

            /* =======================
               UPDATE (Example)
               ======================= */
            PreparedStatement updatePs = con.prepareStatement(updateQuery);
            updatePs.setString(1, "Rajat Gore");
            updatePs.setInt(2, 1);
            updatePs.executeUpdate();
            System.out.println("\nRecord updated");



            /* =======================
               DELETE (Example)
               ======================= */
            PreparedStatement deletePs = con.prepareStatement(deleteQuery);
            deletePs.setInt(1, 5);
            deletePs.executeUpdate();
            System.out.println("Record deleted");

            // 4️⃣ Close resources
            rs.close();
            insertPs.close();
            selectPs.close();
            updatePs.close();
            deletePs.close();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
