import java.util.HashMap;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

class Main {

    //Testing and

    public static void main(String[] args) {


        testSolutions();
    }


    private static void cursorTests() {

        //Test case one
        HashMap<String, String> testCaseOneParameters = new HashMap<String, String>();
        HashMap<String, String> testCaseOneBody = new HashMap<String, String>();
        HashMap<String, String> testCaseOneHeaders = new HashMap<String, String>();
        Response response = InterviewSolution.handleRequests("GET", "/employees", testCaseOneParameters, testCaseOneBody, testCaseOneHeaders);
        ArrayList<HashMap<String, String>> employees = (ArrayList<HashMap<String, String>>) response.body.get("employees");
        System.out.printf("The value at index zero is: %s \n", employees.get(0));
        assert employees.size() == 4 : "the wrong number of employees was returned";
        assert employees.get(0).get("name").equals("David") : "the wrong number of employees was returned";
    }

    private static void payrollRunTests() {

    }

    private static void addEmployeePayment() {
        HashMap<String, String> testCaseOneParameters = new HashMap<String, String>();
        HashMap<String, String> testCaseOneBody = new HashMap<String, String>();
        HashMap<String, String> testCaseOneHeaders = new HashMap<String, String>();
        Response response = InterviewSolution.handleRequests("POST", "/payroll-run/2/finalize-run", testCaseOneParameters, testCaseOneBody, testCaseOneHeaders);
    }


    private static void finalizePayrollRun() {
    }

    public static void testSolutions() {
        // cursorTests();

        // TODO: uncomment once payroll run is implemented
        // payrollRunTests();

        // TODO: uncomment once payroll run is implemented
        addEmployeePayment();

        // TODO: uncomment once payroll run is implemented
        // finalizePayrollRun();
    }


}