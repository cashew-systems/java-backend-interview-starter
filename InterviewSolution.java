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

class InterviewSolution {

   /**
    *  Challenge one: Implement cursor based pagination
    *
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
        ArrayList<HashMap<String, String>> employees = DatabaseUtil.queryDb("select * from employees");
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
    *    hours worked
    *
    *  returns:
    *    none
    *
    * Tasks to implement endpoint:
    *  1. Create payroll_run object in PENDING state
    *  2. Initialize hours for all employees with hourly pay records
    *  3. Save in totals table
    *  4. Create table for storing details of payments contributing to total
    *  5. Save the payment contributing in this table
    *  6. Return 200 to client
    *  7. Basic error cases and error handling
    */
    private static HashMap<String, Object> postPayrollRun(String path, HashMap<String, String> queryString, HashMap<String, String> headers, HashMap<String, String> body) {
        return null;
    }

    /**
     *   Challenge three:  Add employee payment to existing payroll run
     *
     *   Tasks to implement:
     *
     *      1. Increment total payroll run amount for the employee
     *      2. Save, in table just created, this payment as part of contributing to the total
     *      3. Implement API level idempotency
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
    private static HashMap<String, Object> postPayrollRunEmployeePayment(String path, HashMap<String, String> queryString, HashMap<String, String> headers, HashMap<String, String> body) {
        return null;
    }

    /**
     *
     *  Challenge three:  Add employee payment to existing payroll run
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

    private static HashMap<String, Object> postPayrollRunFinalizeRun(String path, HashMap<String, String> queryString, HashMap<String, String> headers, HashMap<String, String> body) {
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

            responseBody = postPayrollRun(path, queryString, headers, body);
            responseCode = 200;

        } else if (verb.equals("POST") && path.matches("/payroll-run/\\d/employee-payment/\\d")) {
            responseBody = postPayrollRunEmployeePayment(path, queryString, headers, body);
            responseCode = 200;
        } else if (verb.equals("POST") && path.matches("/payroll-run/\\d/finalize-run")) {
            responseBody = postPayrollRunFinalizeRun(path, queryString, headers, body);
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

