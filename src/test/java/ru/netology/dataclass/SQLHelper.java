package ru.netology.dataclass;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    public static QueryRunner runner = new QueryRunner();
    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");
    private static Connection conn;

    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return conn;
    }

    public static String getStatusPayment() {
        String querySQL = "SELECT status FROM payment_entity";

        try (var conn = getConnection()) {
            var statusPayment = runner.query(conn, querySQL, new ScalarHandler<String>());
            return statusPayment;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getStatusCredit() {
        String querySQL = "SELECT status FROM credit_request_entity";

        try (var conn = getConnection()) {
            var statusCredit = runner.query(conn, querySQL, new ScalarHandler<String>());
            return statusCredit;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getTransactionId() {
        String querySQL = "SELECT transaction_id FROM payment_entity";

        try (var conn = getConnection()) {
            var transactionId = runner.query(conn, querySQL, new ScalarHandler<String>());
            return transactionId;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getBankId() {
        String querySQL = "SELECT bank_id FROM credit_request_entity";

        try (var conn = getConnection()) {
            var bankId = runner.query(conn, querySQL, new ScalarHandler<String>());
            return bankId;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getPaymentId() {
        String querySQL = "SELECT payment_id FROM order_entity";

        try (var conn = getConnection()) {
            String paymentId = runner.query(conn, querySQL, new ScalarHandler<String>());
            return paymentId;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getCreditId() {
        String querySQL = "SELECT credit_id FROM order_entity";

        try (var conn = getConnection()) {
            String creditId = runner.query(conn, querySQL, new ScalarHandler<String>());
            return creditId;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }


    @SneakyThrows
    public static void cleanDatabase() {
        var conn = getConnection();
        runner.execute(conn, "DELETE FROM app.payment_entity");
        runner.execute(conn, "DELETE FROM app.credit_request_entity");
        runner.execute(conn, "DELETE FROM app.order_entity");
    }
}
