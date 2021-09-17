import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class MainClass {
  public static void main(String[] args) {
    Connection connection = null;
    Statement statement = null;
    try {
      Class.forName("org.hsqldb.jdbcDriver").newInstance();
      String url = "jdbc:hsqldb:hsqldb\\demoDatabase";
      connection = DriverManager.getConnection(url, "username", "password");
      connection.setAutoCommit(false);

      statement = connection.createStatement();

      String update1 = "UPDATE employees SET email = 'a@b.com' WHERE email = 'a@a.com'";
      statement.executeUpdate(update1);
      Savepoint savepoint1 = connection.setSavepoint("savepoint1");

      String update2 = "UPDATE employees SET email = 'b@b.com' WHERE email = 'b@c.com'";
      statement.executeUpdate(update2);
      Savepoint savepoint2 = connection.setSavepoint("savepoint2");

      String update3 = "UPDATE employees SET email = 'c@c.com' WHERE email = 'c@d.com'";
      statement.executeUpdate(update3);
      Savepoint savepoint3 = connection.setSavepoint("savepoint3");

      String update4 = "UPDATE employees SET email = 'd@d.com' WHERE email = 'd@e.com'";
      statement.executeUpdate(update4);
      Savepoint savepoint4 = connection.setSavepoint("savepoint4");

      String update5 = "UPDATE employees SET email = 'e@e.com' WHERE email = 'e@f.com'";
      statement.executeUpdate(update5);
      Savepoint savepoint5 = connection.setSavepoint("savepoint5");

      connection.rollback(savepoint3);
      connection.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException e) {
        } // nothing we can do
      }
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
        } // nothing we can do
      }
    }
  }
}
