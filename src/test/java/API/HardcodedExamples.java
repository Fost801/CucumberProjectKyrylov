package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HardcodedExamples {

    //the intention is to learn how API's work
    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MjA2MjM0NTIsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTcyMDY2NjY1MiwidXNlcklkIjoiNjYzNSJ9.6Ts3HjeJf5yViStQwt_Ojz3L8IUn0q2fVHKmdoYe8p4";
    static String employee_id;

    @Test
    //in order to create the employee
    //first prepare the request, then hit the endpoint, validate the response
    public void acreateEmployee(){
        //preparing the request
        RequestSpecification preparedRequest = given().
                header("Content-Type","application/json").
                header("Authorization", token).
                body("{\n" +
                        "  \"emp_firstname\": \"Fost\",\n" +
                        "  \"emp_lastname\": \"Fostov\",\n" +
                        "  \"emp_middle_name\": \"MN\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"2086-03-29\",\n" +
                        "  \"emp_status\": \"Tester\",\n" +
                        "  \"emp_job_title\": \"Testing\"\n" +
                        "}");
        //hitting the endpoint, sending the request
        Response response = preparedRequest.when().post("/createEmployee.php");

        //validate status code
        response.then().assertThat().statusCode(201);
        // System.out.println(response);
        response.prettyPrint();
        //json - keys
        employee_id=response.jsonPath().getString("Employee.employee_id");
        System.out.println(employee_id);
        //validate response header
        response.then().assertThat().header("Connection","Keep-Alive");
        //confirm body
        response.then().assertThat().body("Employee.emp_firstname",equalTo("Fost"));
        response.then().assertThat().body("Employee.emp_lastname",equalTo("Fostov"));
        response.then().assertThat().body("Employee.emp_middle_name",equalTo("MN"));

    }
    @Test
    //getting one employee
    public void bgetOneEmployee(){
        //prepare the request
        RequestSpecification request = given().
                header("Content-Type","application/json").
                header("Authorization", token).
                queryParam("employee_id", employee_id);

        //hitting the endpoint
        Response response = request.when().get("/getOneEmployee.php");
        response.prettyPrint();

        //validate the status code
        response.then().assertThat().statusCode(200);
        //get the employee id from  body
        String empId = response.jsonPath().getString("employee.employee_id");
        Assert.assertEquals(empId, employee_id);
        response.then().assertThat().
                body("employee.emp_firstname",equalTo("Fost"));
        response.then().assertThat().
                body("employee.emp_middle_name",equalTo("MN"));
        response.then().assertThat().
                body("employee.emp_lastname",equalTo("Fostov"));

    }

    @Test
    public void cupdateEmployee(){
        //prepare the request
        RequestSpecification request = given().
                header("Content-Type","application/json").
                header("Authorization", token).
                body("{\n" +
                        "  \"employee_id\": \""+employee_id+"\",  \n" +
                        "  \"emp_firstname\": \"Fostik\",\n" +
                        "  \"emp_lastname\": \"Fostovich\",\n" +
                        "  \"emp_middle_name\": \"MN\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"2086-03-26\",\n" +
                        "  \"emp_status\": \"permanent\",\n" +
                        "  \"emp_job_title\": \"Tester\"\n" +
                        "}");

        Response response = request.when().put("/updateEmployee.php");
        response.then().assertThat().statusCode(200);
        response.prettyPrint();
    }
    @Test
    //getting one employee
    public void dgetUpdatedEmployee(){
        //prepare the request
        RequestSpecification request = given().
                header("Content-Type","application/json").
                header("Authorization", token).
                queryParam("employee_id", employee_id);

        //hitting the endpoint
        Response response = request.when().get("/getOneEmployee.php");
        response.prettyPrint();

        //validate the status code
        response.then().assertThat().statusCode(200);
//        //get the employee id from  body
//        String empId = response.jsonPath().getString("employee.employee_id");
//        Assert.assertEquals(empId, employee_id);
//        response.then().assertThat().
//                body("employee.emp_firstname",equalTo("Fostik"));
//        response.then().assertThat().
//                body("employee.emp_middle_name",equalTo("MN"));
//        response.then().assertThat().
//                body("employee.emp_lastname",equalTo("Fostovich"));

    }
    @Test
    public void eGetAllJobTitles(){
        RequestSpecification request = given().
                header("Content-Type","application/json").
                header("Authorization", token);
        Response response = request.when().get("/jobTitle.php");
        response.prettyPrint();

        //validate the status code
        response.then().assertThat().statusCode(200);
    }
    @Test
    public void fGetAllEmployees(){
        RequestSpecification request = given().
                header("Content-Type","application/json").
                header("Authorization", token);
        Response response = request.when().get("/getAllEmployees.php");
        response.prettyPrint();

        //validate the status code
        response.then().assertThat().statusCode(200);
    }
}
