package br.edu.infnet.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import br.edu.infnet.models.Product;
import br.edu.infnet.models.Quotation;

public class QuotationRepository {

    private String Url = null;
    private String User = null;
    private String Password = null;
    private String Database = null;
    private Connection Connection = null;

    private static QuotationRepository instance = null;

    private QuotationRepository() {
    }

    public static QuotationRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new QuotationRepository();
            instance.getProperties();
            instance.getConnection();
        }
        return instance;
    }

    private void getProperties() {
        Properties props = new Properties();
        String propsPath = "dbconfig.properties";
        try {
            props.load(QuotationRepository.class.getResourceAsStream(propsPath));
            this.Url = props.getProperty("DB_URL");
            this.User = props.getProperty("DB_USER");
            this.Password = props.getProperty("DB_PASSWORD");
            this.Database = props.getProperty("DB_DATABASE");
        } catch (IOException e) {
            System.out.println("Exception Generated: " + e);

        }
    }

    private void getConnection() throws SQLException {
        this.Connection = DriverManager.getConnection(Url, User, Password);
    }

    public void close() {
        try {
            this.Connection.close();
            instance = null;
        } catch (SQLException e) {
            System.out.println("Exception Generated: " + e);
        }
    }

    public QuotationRepository init() throws SQLException {
        Statement statement = Connection.createStatement();
        statement.execute("CREATE DATABASE IF NOT EXISTS " + Database);
        Connection.setCatalog(Database);
        createTable();
        return this;
    }

    private void createTable() throws SQLException {
        String sqlStatement = """
                CREATE TABLE IF NOT EXISTS quotations (
                    ID VARCHAR(255) NOT NULL,
                    PRODUCT VARCHAR(255) NOT NULL,
                    AMOUNT NUMERIC NOT NULL,
                    TOTAL NUMERIC NOT NULL
                    )
                    """;
        Statement statement = Connection.createStatement();
        statement.execute(sqlStatement);
    }

    public boolean add(Quotation quotation) throws SQLException {
        String sqlStatement = """
                INSERT INTO quotations (ID, PRODUCT, AMOUNT, TOTAL)
                VALUES (?, ?, ?, ?)
                """;

        PreparedStatement preparedStatement = Connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, quotation.GetId());
        preparedStatement.setString(2, quotation.GetProduct().GetName());
        preparedStatement.setInt(3, quotation.GetAmount());
        preparedStatement.setDouble(4, quotation.GetTotal());
        return preparedStatement.execute();

    }

    public List<Quotation> getAll() throws SQLException {
        List<Quotation> quotations = new ArrayList<>();
        String sqlStatement = """
                SELECT * FROM quotations
                """;

        PreparedStatement preparedStatement = Connection.prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            quotations.add(
                    new Quotation(result.getString(1), result.getString(2), result.getInt(3), result.getDouble(4)));

        }
        return quotations;
    }

    public Quotation getById(String id) throws SQLException {
        String sqlStatement = """
                SELECT * FROM quotations WHERE ID = (?)
                """;

        PreparedStatement preparedStatement = Connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, id);
        ResultSet result = preparedStatement.executeQuery();
        var quotation = new Quotation();
        while (result.next()) {
            quotation = new Quotation(result.getString(1), result.getString(2), result.getInt(3), result.getDouble(4));
        }
        return quotation;
    }

    public ArrayList<Quotation> getByProductName(String id) throws SQLException {
        String sqlStatement = """
                SELECT * FROM quotations WHERE PRODUCT = (?)
                """;

        PreparedStatement preparedStatement = Connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, id);
        ResultSet result = preparedStatement.executeQuery();
        ArrayList<Quotation> quotations = new ArrayList<>();
        while (result.next()) {
            quotations.add(new Quotation(result.getString(1), result.getString(2), result.getInt(3), result.getDouble(4)));
        }
        return quotations;
    }

    public boolean edit(Quotation quotation) throws SQLException {
        String sqlStatement = """
                UPDATE quotations
                SET (PRODUCT, TOTAL)
                VALUES (?, ?)
                WHERE Id = (?)
                """;

        PreparedStatement preparedStatement = Connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, quotation.GetProduct().GetName());
        preparedStatement.setDouble(2, quotation.GetTotal());
        preparedStatement.setString(3, quotation.GetId());
        return preparedStatement.execute();
    }

    public boolean removeById(int id) throws SQLException {
        String sqlStatement = """
                DELETE FROM quotations
                WHERE Id = (?)
                """;

        PreparedStatement preparedStatement = Connection.prepareStatement(sqlStatement);
        preparedStatement.setInt(1, id);
        return preparedStatement.execute();
    }

}
