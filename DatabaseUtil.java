import java.util.HashMap;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;


class DatabaseUtil {

    public static ArrayList<HashMap<String, String>> readQuery(String dbQuery) throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:postgresql://payroll_database:5432/postgres","postgres","cashew");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(dbQuery);

        return getResultsFromResultSet(resultSet);
    }

    private static ArrayList<HashMap<String, String>> getResultsFromResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metadata = resultSet.getMetaData();
        int numberOfCols = metadata.getColumnCount();
        ArrayList<HashMap<String, String>> resultValues = new ArrayList<HashMap<String, String>>();
        while (resultSet.next()) {
            HashMap<String, String> rowResults = new HashMap<String, String>();

            for(int i = 1; i <= numberOfCols; i++) {
                String columnValue = resultSet.getString(i);
                String columnName = metadata.getColumnLabel(i);
                // System.out.printf("Placing column name: %s and column value: %s\n", columnName, columnValue);
                rowResults.put(columnName, columnValue);
            }

            resultValues.add(rowResults);
        }

        return resultValues;
    }

    public static boolean writeQuery(String dbQuery) throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:postgresql://payroll_database:5432/postgres","postgres","cashew");
        Statement statement = connection.createStatement();
        boolean rowInserted = statement.execute(dbQuery);
        return rowInserted;
    }


    public static ArrayList<HashMap<String, String>> getMostRecentlyInsertedRecord(String table) throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:postgresql://payroll_database:5432/postgres","postgres","cashew");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from " + table + " order by id desc limit 1;");
        ArrayList<HashMap<String, String> > results = getResultsFromResultSet(resultSet);
        return results;
    }

}