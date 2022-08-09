import java.util.HashMap;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class InterviewRunner {

    /**
     *  Challenge one: Implement cursor based pagination
     *
     *  Path: GET /employees
     *
     *  Parameters:
     *    page_before
     *    page_after
     *    page_size
     *
     *  returns:
     *    employee[] employees
     *    next_cursor = foobar
     *
     *
     *   Tasks to implement endpoint:
     *       1. Implement returning pageSize results with pageAfter specified
     *       2. Implement returning pageSize results with pageBefore specified
     *       3. Add better error handling for both SQL errors and parameters
     *
     */
    private static HashMap<String, Object> getEmployees(String path, HashMap<String, String> queryString, HashMap<String, String> headers, HashMap<String, String> body) throws SQLException {
        //TODO: Challenge, add cursor based pagination
        ArrayList<HashMap<String, String>> employees = DatabaseUtil.readQuery("select * from employees");
        HashMap<String, Object> response = new HashMap<String, Object>();
        response.put("employees", employees);

        return response;
    }

    /**
     *  Challenge two: Implement payroll-run endpoint
     *
     *  Path: /payroll-run
     *
     *  Body:
     *    hours_worked
     *
     *  returns:
     *    none
     *
     * Tasks to implement endpoint:
     *  1. Create payroll_run object in PENDING state
     *  2. Calcualte rate for all employees with hourly pay records
     *  3. Save the payment contributing in this table
     *  4. Return 200 to client
     *  5. Basic error cases and error handling
     */
    private static void postPayrollRun(String path, HashMap<String, String> queryString, HashMap<String, String> headers, HashMap<String, String> body) throws SQLException {
    }

    /**
     *   Challenge three:  Add employee payment to existing payroll run
     *
     *   Tasks to implement:
     *
     *      1. Increment total payroll run amount for the employee
     *      2. Save, in table just created, this payment as part of contributing to the total
     *      3. Implement API level idempotency (hint: you can modify request parameters)
     *
     *   Error cases:
     *
     *      1. Employee ID is not valid
     *      2. Payroll run ID is not valid
     *      3. One of the database requests fails, what happens if we’re in a partial failure state?
     *
     *
     *
     *  Path:
     *     /payroll-run/{id}/employee-payment/{employee_id}
     *
     *   Body:
     *      amount: number
     *      paymentMemo: text
     *
     *   Return:
     *      none
     *
     */
    private static HashMap<String, Object> postPayrollRunEmployeePayment(String path, HashMap<String, String> queryString, HashMap<String, String> headers, HashMap<String, String> body) throws SQLException {
        return null;
    }

    /**
     *
     *  Challenge four:  Add employee payment to existing payroll run
     *  Path:
     *   /payroll-run/{id}/finalize-run
     *
     *  Body:
     *      None
     *
     *  Returns:
     *      //TODO: Challenge what should this return?
     *
     *  Tasks to implement endpoint
     *
     *  1.  Change status of payroll run from PENDING to COMPLETE
     *  2.  Implement immediate response to client
     *  3.  Implement ability for client to find out result of endpoint after immediate response
     *  4.  Basic solutions to error cases
     *
     *
     *  Error cases
     *
     *  1. Payroll run id is not a PENDING payroll run
     *  2. After immediate response is given, the processing of payroll fails and must be resubmitted.
     *
     */

    private static HashMap<String, Object> postPayrollRunFinalizeRun(String path, HashMap<String, String> queryString, HashMap<String, String> headers, HashMap<String, String> body) throws SQLException {
        return null;
    }

    public static Response handleRequests(String verb, String path, HashMap<String, String> queryString, HashMap<String, String> headers, HashMap<String, String> body) {

        HashMap<String, Object> responseBody = null;
        int responseCode = -1;
        if (verb.equals("GET") && path.matches("/employees")) {

            try {
                responseBody = getEmployees(path, queryString, headers, body);
            } catch (SQLException e) {
                //TODO: challenge, add better error handling
                System.out.printf("There was a sql exception: %s\n", e);
            }
            responseCode = 200;

        } else if (verb.equals("POST") && path.matches("/payroll-run")) {

            try {
                postPayrollRun(path, queryString, headers, body);
                responseBody = new HashMap<String, Object>();
            } catch (SQLException e) {
                //TODO: challenge, add better error handling
                System.out.printf("There was a sql exception: %s\n", e);
            }
            responseCode = 200;

        } else if (verb.equals("POST") && path.matches("/payroll-run/\\d/employee-payment/\\d")) {
            try {
                responseBody = postPayrollRunEmployeePayment(path, queryString, headers, body);
            } catch (SQLException e) {
                //TODO: challenge, add better error handling
                System.out.printf("There was a sql exception: %s\n", e);
            }
            responseCode = 200;
        } else if (verb.equals("POST") && path.matches("/payroll-run/\\d/finalize-run")) {
            try {
                responseBody = postPayrollRunFinalizeRun(path, queryString, headers, body);
            } catch (SQLException e) {
                //TODO: challenge, add better error handling
                System.out.printf("There was a sql exception: %s\n", e);
            }
            responseCode = 200;
        } else {
            responseBody = new HashMap<String, Object>();
            responseBody.put("message", "you entered an invalid path");
            responseCode = 404;
        }

        return new Response(responseCode, responseBody);
    }

    private static ArrayList<String> getPathVariables(String path) {
        Pattern p = Pattern.compile("(\\d)");
        Matcher m = p.matcher(path);

        ArrayList<String> allMatches = new ArrayList<String>();
        while (m.find()) {
            allMatches.add(m.group());
        }

        return allMatches;

    }

}

class Solution {

    //Testing and

    public static void main(String[] args) {

        testSolutions();
    }

    //Test case: no cursor or page size
    private static void cursorTestOne() {
        HashMap<String, String> testCaseOneParameters = new HashMap<String, String>();
        HashMap<String, String> testCaseOneBody = new HashMap<String, String>();
        HashMap<String, String> testCaseOneHeaders = new HashMap<String, String>();
        Response response = InterviewRunner.handleRequests("GET", "/employees", testCaseOneParameters, testCaseOneHeaders, testCaseOneBody);
        ArrayList<HashMap<String, String>> employees = (ArrayList<HashMap<String, String>>) response.body.get("employees");
        System.out.printf("In cursor test one, the employees are: %s \n", employees);
        assert employees.size() == 4 : "Cursor test two: The wrong number of employees was returned";
        assert employees.get(0).get("name").equals("David") : "Cursor test two: The wrong number of employees was returned";
    }

    //Test case: no cursor or page size
    private static void cursorTestTwo() {
        HashMap<String, String> testCaseTwoParameters = new HashMap<String, String>();
        HashMap<String, String> testCaseTwoBody = new HashMap<String, String>();
        HashMap<String, String> testCaseTwoHeaders = new HashMap<String, String>();

        testCaseTwoParameters.put("page_size", "2");
        testCaseTwoParameters.put("page_before", "1");

        Response response = InterviewRunner.handleRequests("GET", "/employees", testCaseTwoParameters, testCaseTwoHeaders, testCaseTwoBody);
        ArrayList<HashMap<String, String>> employees = (ArrayList<HashMap<String, String>>) response.body.get("employees");
        System.out.printf("In cursor test two, the employees are: %s \n", employees);
        assert employees.size() == 2 : "Cursor test two: The wrong number of employees was returned";
        assert employees.get(0).get("name").equals("Anthony") : "Cursor test two: The wrong first employee was returned";
        assert employees.get(1).get("name").equals("Aman") : "Cursor test two: The wrong second employee was returned";
    }

    //Test case: no cursor or page size
    private static void cursorTestThree() {
        HashMap<String, String> testCaseParameters = new HashMap<String, String>();
        HashMap<String, String> testCaseBody = new HashMap<String, String>();
        HashMap<String, String> testCaseHeaders = new HashMap<String, String>();

        testCaseParameters.put("page_size", "2");
        testCaseParameters.put("page_after", "4");

        Response response = InterviewRunner.handleRequests("GET", "/employees", testCaseParameters, testCaseHeaders, testCaseBody);
        ArrayList<HashMap<String, String>> employees = (ArrayList<HashMap<String, String>>) response.body.get("employees");
        System.out.printf("In cursor test three, the employees are: %s \n", employees);
        assert employees.size() == 2 : "Cursor test three: The wrong number of employees was returned";
        assert employees.get(0).get("name").equals("Aman") : "Cursor test three: The wrong first employee was returned";
        assert employees.get(1).get("name").equals("Anthony") : "Cursor test three: The wrong second employee was returned";
    }

    //Test case: no cursor or page size
    private static void cursorTestFour() {
        HashMap<String, String> testCaseParameters = new HashMap<String, String>();
        HashMap<String, String> testCaseBody = new HashMap<String, String>();
        HashMap<String, String> testCaseHeaders = new HashMap<String, String>();

        testCaseParameters.put("page_size", "1");
        testCaseParameters.put("page_after", "3");

        Response response = InterviewRunner.handleRequests("GET", "/employees", testCaseParameters, testCaseHeaders, testCaseBody);
        ArrayList<HashMap<String, String>> employees = (ArrayList<HashMap<String, String>>) response.body.get("employees");
        System.out.printf("In cursor test four, the employees are: %s \n", employees);
        assert employees.size() == 1 : "Cursor test four: The wrong number of employees was returned";
        assert employees.get(0).get("name").equals("Anthony") : "Cursor test four: The wrong second employee was returned";
    }

    private static void cursorTests() {
        cursorTestOne();
        cursorTestTwo();
        cursorTestThree();
        cursorTestFour();
        System.out.println("All cursor test cases pass!");
    }

    private static void payrollRunTestOne() {
        HashMap<String, String> testCaseParameters = new HashMap<String, String>();
        HashMap<String, String> testCaseBody = new HashMap<String, String>();
        HashMap<String, String> testCaseHeaders = new HashMap<String, String>();

        testCaseBody.put("hours_worked", "40");

        InterviewRunner.handleRequests("POST", "/payroll-run", testCaseParameters, testCaseHeaders, testCaseBody);

        try {
            ArrayList<HashMap<String, String>> databaseResult = DatabaseUtil.readQuery("select * from payroll_run_employee_payments");
            System.out.printf("The database result is: %s\n", databaseResult);
            assert databaseResult.size() == 4 : "Wrong number of records in database result";
            assert Double.parseDouble(databaseResult.get(0).get("amount")) == 40*10.01 : "David has the wrong amount paid in the pay period for the hours worked";
        } catch(SQLException e) {
            System.out.printf("[payrollRunTestONe] Got a SQL exception %s\n", e);
        }
    }

    private static void payrollRunTests() {
        payrollRunTestOne();
    }

    public static void testSolutions() {
        cursorTests();
        System.out.println("All cursor test cases pass!");

        // TODO: uncomment once payroll run is implemented
        payrollRunTests();
        System.out.println("All payroll run test cases pass!");

    }


}

class Response {
    int code;
    HashMap<String, Object> body;

    public Response(int code, HashMap<String, Object> body) {
        this.code = code;
        this.body = body;
    }

}

class DatabaseUtil {

    public static ArrayList<HashMap<String, String>> readQuery(String dbQuery) throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/coderpad","coderpad","");
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

    public static HashMap<String, String> insertQuery(String dbQuery, String table) throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/coderpad","coderpad","");
        Statement statement = connection.createStatement();
        statement.execute(dbQuery);
        HashMap<String, String> recordResult = readQuery("select * from " + table + " order by id desc limit 1").get(0);

        return recordResult;
    }


    public static ArrayList<HashMap<String, String>> getMostRecentlyInsertedRecord(String table) throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/coderpad","coderpad","");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from " + table + " order by id desc limit 1;");
        ArrayList<HashMap<String, String> > results = getResultsFromResultSet(resultSet);
        return results;
    }

}
