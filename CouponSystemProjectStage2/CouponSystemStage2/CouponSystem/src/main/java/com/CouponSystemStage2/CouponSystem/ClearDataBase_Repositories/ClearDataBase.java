/**
 * ClearDataBase - is used to clear all the data in tables: coupons,companies,customer_vs_coupons,categories,customers.
 * This class is used in testAll class so that there is no need to clear all the old data by hand.
 * if not used, it is not possible to repeat running the testAll class without clearing the database by hand.
 * The class holds three functions :
 * clearDataBase - it creates categories table and handles clearing the data.
 * clearAutoIncrement - which handles resetting the auto-increment to 1.
 * LoadCategories - it provides the table with the necessary values of enum categories only for viewing.
 */
        package com.CouponSystemStage2.CouponSystem.ClearDataBase_Repositories;
import com.CouponSystemStage2.CouponSystem.DesignColors.TextColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class ClearDataBase {
    final
    DataSource dataSource;

    public ClearDataBase(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    public void clearDataBase() throws SQLException {

        String url = "jdbc:sqlserver://DESKTOP-CTLB9AI\\SQLEXPRESS:1433;databaseName=CouponSystemProject;encrypt=true;trustServerCertificate=true";
        String username = "Aml";
        String password = "johnny2001";

        Connection connection = DriverManager.getConnection(url, username, password);

        try {
            String sql = "CREATE TABLE categories " +
                    "(id INT NOT NULL IDENTITY(1,1), " +
                    " Name VARCHAR(255), " +
                    " PRIMARY KEY (id))";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {

        }

        String sql = "DELETE FROM coupons_vs_customers where (customer_id > 0)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "DELETE FROM coupons WHERE (ID > 0)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "DELETE FROM companies WHERE (ID > 0)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "DELETE FROM customers WHERE (ID > 0);";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "DELETE FROM categories WHERE (ID > 0);";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        connection.close();
    }

    public void clearAutoIncrement() throws SQLException {
        Connection connection = dataSource.getConnection();

        String sql = "DBCC CHECKIDENT ('companies', RESEED, 0)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();

        sql = "DBCC CHECKIDENT ('customers', RESEED, 0)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();

        sql = "DBCC CHECKIDENT ('coupons', RESEED, 0)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();

        sql = "DBCC CHECKIDENT ('categories', RESEED, 0)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();

        connection.close();
    }

    public void LoadCategories() throws SQLException {

        Connection connection = dataSource.getConnection();
        String sql = "INSERT INTO categories (Name) VALUES ('FOOD')";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "INSERT INTO categories (Name) VALUES ('ELECTRICITY')";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "INSERT INTO categories (Name) VALUES ('FURNITURE')";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "INSERT INTO categories (Name) VALUES ('TOYS')";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        connection.close();
    }

    public void Clear() {
        try {
            clearDataBase();
            clearAutoIncrement();
            LoadCategories();
        } catch (Exception e) {
            System.out.println(TextColors.ANSI_RED + "Exception: there is either problem in sql functions or threads functionality!" + TextColors.ANSI_RESET);

        }
    }
}