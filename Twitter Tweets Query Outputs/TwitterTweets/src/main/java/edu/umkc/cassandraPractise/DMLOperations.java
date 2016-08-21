package edu.umkc.cassandraPractise;

/**
 * Created by VARSHA-PC on 2/27/2016.
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class DMLOperations {
    private static Properties properties;

    Cluster cluster = null;
    Session session = null;

    /**
     * Disconnect from the current cluster
     */
    public void disconnect() {
        this.session.close();
        this.cluster.close();
        System.out.println("DisConnected!!");
    }

    /**
     * @param ip
     * @param keySpace
     *            Connected to the keyspace and node
     */
    public void connect(String ip, String keySpace) {
        this.cluster = Cluster.builder().addContactPoints(ip).build();
        this.session = cluster.connect(keySpace);
        System.out.println("Connected!!");
    }

    /**
     * Select all the rows from the given columnfamily
     */
    public void selectALL() {

        BoundStatement boundStatement = null;
        PreparedStatement prepare_statement = null;

        prepare_statement = this.session.prepare(properties
                .getProperty("SELECT_ALL"));
        boundStatement = new BoundStatement(prepare_statement);
        ResultSet rs = this.session.execute(boundStatement);

        for (Row row : rs) {
            System.out.println(row.toString());
        }
    }

    /**
     * @param deptName
     * @param EmployeeId
     *            insert the data to column family
     */
    public void insertAll(String deptName, int EmployeeId, String EmployeeName) {

        BoundStatement boundStatement = null;
        PreparedStatement prepare_statement = null;
        prepare_statement = this.session.prepare(properties
                .getProperty("INSERT_ALL"));
        boundStatement = new BoundStatement(prepare_statement);
        this.session.execute(boundStatement.bind(deptName, EmployeeId,
                EmployeeName));
    }

    /**
     * @param deptName
     * @param id
     *            update the data to using the deptname
     */
    public void update(String deptName, String EmployeeName, int id) {
        BoundStatement boundStatement = null;
        PreparedStatement prepare_statement = null;
        prepare_statement = this.session.prepare(properties
                .getProperty("UPDATE_NAME"));
        boundStatement = new BoundStatement(prepare_statement);
        this.session.execute(boundStatement.bind(EmployeeName, deptName, id));
    }

    public void delete(String deptName, int id) {
        BoundStatement boundStatement = null;
        PreparedStatement prepare_statement = null;
        prepare_statement = this.session.prepare(properties
                .getProperty("DELETE_EMP"));
        boundStatement = new BoundStatement(prepare_statement);
        this.session.execute(boundStatement.bind(deptName, id));
    }

    /**
     * @param propertiesFileName
     * @return java.util.Properties Object Load the values from File to Property
     *         Object
     */
    private Properties loadProperties(String propertiesFileName) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(propertiesFileName + ".properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }

        return prop;
    }

    public static void main(String[] args) {
        DMLOperations object = new DMLOperations();
        properties = object.loadProperties("queries");
        object.connect(properties.getProperty("SERVER_IP"),
                properties.getProperty("keyspace"));
        object.insertAll("bigdata", 03, "sam");
        object.insertAll("bigdata", 05, "santhosh");
        object.insertAll("java", 04, "joe");
        System.err.println("Inserted ");
        object.selectALL();
        object.update("bigdata", "samKrish", 03);
        System.err.println("Updated ");
        object.selectALL();
        object.delete("bigdata", 03);
        System.err.println("Deleted");
        object.selectALL();
        object.disconnect();
    }
}
