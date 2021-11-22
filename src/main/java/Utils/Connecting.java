package Utils;

import models.Equipment;
import models.Well;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Connecting {

    private static final String driverName = "org.sqlite.JDBC";
    private static final String connectionString = "jdbc:sqlite:test_db";
    Connection connection;

    //проверка запуска БД
    public void run() throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.out.println("Can't get class. No driver found");
            e.printStackTrace();
            return;
        }
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("Can't get connection. Incorrect URL");
            e.printStackTrace();
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Can't close connection");
            e.printStackTrace();
            return;
        }

        Statement statement = connection.createStatement();

        String newTable;
        newTable = "CREATE TABLE IF NOT EXISTS well ("
                + "`id`INTEGER PRIMARY KEY, "
                + "`name` VARCHAR(32) NOT NULL UNIQUE);";
        statement.executeUpdate(newTable);

        newTable = "CREATE TABLE IF NOT EXISTS equipment ("
                + "`id` INTEGER PRIMARY KEY, "
                + "`name` VARCHAR(32) NOT NULL UNIQUE,"
                + "`well_id` INTEGER, "
                + "FOREIGN KEY(`well_id`) REFERENCES well(id));";
        statement.executeUpdate(newTable);

    }

    //
    public int createWell(Well well) {
        int count = 0;
        int id = 0;
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            String newTable = "INSERT INTO well "
                    + "VALUES (NULL, '%s');";
            count = statement.executeUpdate(String.format(newTable, well.getName()));
            ResultSet keys = statement.getGeneratedKeys();
            if(keys.next()) {
                id = keys.getInt(1);
            }

            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        };

        return id;
    }

    public int createEquipments(ArrayList<Equipment> equipments) {
        int count = 0;

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            StringJoiner joiner = new StringJoiner(", ");
            for(Equipment equipment : equipments) {
                joiner.add(String.format("(NULL, '%s', %d)", equipment.getName(), equipment.getWell_id()) );
            }
            String q = "INSERT INTO Equipment "
                    + "VALUES " + joiner.toString();
            count = statement.executeUpdate(q);

            statement.close();
            connection.close();


        } catch (Exception e) {
            e.printStackTrace();
        };

        return count;
    }


    public int getMaxEquipmentId(){
        int maxId = 0;

        try {
            Connection c = getConnection();
            Statement s = c.createStatement();
            String q = "SELECT max(`id`) AS `maxId` FROM equipment;";
            ResultSet result = s.executeQuery(q);

            if(result.next()) {
                maxId = result.getInt("maxId");
            }

            s.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return maxId;
    }


    public List<Well> findAllWells(){
        List<Well> wells = new ArrayList<>();

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            String selectWell = "SELECT * FROM Well;";

            ResultSet result = statement.executeQuery(selectWell);

            while(result.next()) {
                wells.add(new Well(
                        result.getInt("id"),
                        result.getString("name")
                ));
            }
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return wells;
    }


    public List<Equipment> findEquipmentByWell(int wellId) {
        List<Equipment> equipments = new ArrayList<>();

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            String selectEqui = "SELECT * FROM equipment WHERE `well_id` = %d;";

            ResultSet result = statement.executeQuery(String.format(selectEqui, wellId));

            while(result.next()) {
                equipments.add (new Equipment(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("well_id")
                ));
            }
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return equipments;
    }


    public Well findWellByName(String name){
        Well well = null;

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            String selectWells = "SELECT * FROM well WHERE `name` = '%s';";

            ResultSet result = statement.executeQuery(String.format(selectWells, name));

            if(result.next()) {
                well = new Well(
                        result.getInt("id"),
                        result.getString("name")
                );
            }

            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return well;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString);
    }
}