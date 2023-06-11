package ru.netology.dataclass;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    public static String getTransactionIdApp() {
        var paymentSQL = "SELECT transaction_id FROM payment_entity WHERE status = 'APPROVED'";

        try (var conn = getConn()) {
            var transactionId = runner.query(conn, paymentSQL, new ScalarHandler<String>());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getPaymentIdApp() {
        var id = getTransactionIdApp();
        var orderSQL = "SELECT payment_id FROM order_entity WHERE payment_id = ?";

        try (
                var conn = getConn();
                var orderStmt = conn.prepareStatement(orderSQL);) {
            orderStmt.setString(1, id);
            var resultSet = orderStmt.executeQuery();
            var paymentId = resultSet.getString("payment_id");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM payment_entity");
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");
    }
}
