/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.infnet.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import br.edu.infnet.models.Product;

/**
 *
 * @author 55229
 */
public class ProductRepository {

    private String Url = null;
    private String User = null;
    private String Password = null;
    private String Database = null;
    private Connection Connection = null;

    private static ProductRepository instance = null;

    private ProductRepository() {
    }

    public static ProductRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProductRepository();
            instance.getProperties();
            instance.getConnection();
        }
        return instance;
    }

    private void getProperties() {
        Properties props = new Properties();
        String propsPath = "dbconfig.properties";
        try {
            props.load(ProductRepository.class.getResourceAsStream(propsPath));
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

    public ProductRepository init() throws SQLException {
        Statement statement = Connection.createStatement();
        statement.execute("CREATE DATABASE IF NOT EXISTS " + Database);
        Connection.setCatalog(Database);
        createTable();
        return this;
    }

    private void createTable() throws SQLException {
        String sqlStatement = """
                CREATE TABLE IF NOT EXISTS products (
                    ID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    NAME VARCHAR(100) NOT NULL,
                    AMOUNT INT NOT NULL,
                    PRICE REAL NOT NULL,
                    TOTAL REAL NOT NULL
                    )
                    """;
        Statement statement = Connection.createStatement();
        statement.execute(sqlStatement);
    }

    public boolean add(Product product) throws SQLException {
        String sqlStatement = """
                INSERT INTO products (NAME, AMOUNT, PRICE, TOTAL)
                VALUES (?, ?, ?, ?)
                """;

        PreparedStatement preparedStatement = Connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, product.GetName());
        preparedStatement.setInt(2, product.GetAmount());
        preparedStatement.setDouble(3, product.GetPrice());
        preparedStatement.setDouble(4, product.GetTotalPrice());
        return preparedStatement.execute();
    }

    public List<Product> getAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sqlStatement = "SELECT * FROM products";

        Statement statement = Connection.createStatement();
        ResultSet result = statement.executeQuery(sqlStatement);
        while(result.next()){
            products.add(new Product(result.getInt(1), result.getString(2),result.getInt(3), result.getDouble(4)));
        }
        return products;
    }

    public Product getById(int id) throws SQLException {
        String sqlStatement = """
                SELECT * FROM products WHERE ID = (?)
                """;

        PreparedStatement preparedStatement = Connection.prepareStatement(sqlStatement);
        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        Product returnedProduct = new Product();
        while(result.next())
        {
            returnedProduct = new Product(result.getInt(1), result.getString(2), result.getInt(3), result.getDouble(4));
        }
        return returnedProduct;
    }

    public ArrayList<Product> getByName(String productName) throws SQLException {
        String sqlStatement = """
                SELECT * FROM products WHERE NAME = (?)
                """;

        PreparedStatement preparedStatement = Connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, productName);
        ResultSet result = preparedStatement.executeQuery();
        ArrayList<Product> returnedProducts = new ArrayList<>();
        while(result.next())
        {
            returnedProducts.add(new Product(result.getInt(1), result.getString(2), result.getInt(3), result.getDouble(4)));
        }
        return returnedProducts;
    }

    public boolean edit(Product product) throws SQLException {
        String sqlStatement = """
                UPDATE products
                SET NAME=(?), AMOUNT=(?), PRICE=(?), TOTAL=(?)
                WHERE ID=(?)
                """;

        PreparedStatement preparedStatement = Connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, product.GetName());
        preparedStatement.setInt(2, product.GetAmount());
        preparedStatement.setDouble(3, product.GetPrice());
        preparedStatement.setDouble(4, product.GetTotalPrice());
        preparedStatement.setInt(5, product.GetId());

        return preparedStatement.execute();
    }

    public boolean removeById(int id) throws SQLException {
        String sqlStatement = """
                DELETE FROM products
                WHERE Id = (?)
                """;

        PreparedStatement preparedStatement = Connection.prepareStatement(sqlStatement);
        preparedStatement.setInt(1, id);
        return preparedStatement.execute();
    }

}
