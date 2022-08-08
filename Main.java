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

    //Test case: no cursor or page size
    private static void cursorTestOne() {
        HashMap<String, String> testCaseOneParameters = new HashMap<String, String>();
        HashMap<String, String> testCaseOneBody = new HashMap<String, String>();
        HashMap<String, String> testCaseOneHeaders = new HashMap<String, String>();
        Response response = InterviewSolution.handleRequests("GET", "/employees", testCaseOneParameters, testCaseOneBody, testCaseOneHeaders);
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

        Response response = InterviewSolution.handleRequests("GET", "/employees", testCaseTwoParameters, testCaseTwoBody, testCaseTwoHeaders);
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

        Response response = InterviewSolution.handleRequests("GET", "/employees", testCaseParameters, testCaseBody, testCaseHeaders);
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

        Response response = InterviewSolution.handleRequests("GET", "/employees", testCaseParameters, testCaseBody, testCaseHeaders);
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

    }

    private static void payrollRunTestOne() {
        HashMap<String, String> testCaseParameters = new HashMap<String, String>();
        HashMap<String, String> testCaseBody = new HashMap<String, String>();
        HashMap<String, String> testCaseHeaders = new HashMap<String, String>();

        Response response = InterviewSolution.handleRequests("POST", "/payroll-run", testCaseParameters, testCaseBody, testCaseHeaders);
    }

    private static void payrollRunTests() {
        payrollRunTestOne();
    }

    private static void addEmployeePaymentTestOne() {
    }

    private static void addEmployeePayment() {
        addEmployeePaymentTestOne();
    }

    private static void finalizePayrollRunTestOne() {
    }

    private static void finalizePayrollRun() {
        finalizePayrollRunTestOne();
    }

    public static void testSolutions() {
        cursorTests();

        // TODO: uncomment once payroll run is implemented
        // payrollRunTests();

        // TODO: uncomment once payroll run is implemented
        // addEmployeePayment();

        // TODO: uncomment once payroll run is implemented
        // finalizePayrollRun();
    }


}