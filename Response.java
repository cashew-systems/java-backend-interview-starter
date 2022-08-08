import java.util.HashMap;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

class Response {
    int code;
    HashMap<String, Object> body;

    public Response(int code, HashMap<String, Object> body) {
        this.code = code;
        this.body = body;
    }

}