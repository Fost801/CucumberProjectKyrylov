package utils;

import org.json.JSONObject;

public class APIPayloadConstants {

    public static String createEmployeePayload(){
        String createEmployeePayload = "{\n" +
                "  \"emp_firstname\": \"Fost\",\n" +
                "  \"emp_lastname\": \"Fostov\",\n" +
                "  \"emp_middle_name\": \"MN\",\n" +
                "  \"emp_gender\": \"M\",\n" +
                "  \"emp_birthday\": \"2086-03-29\",\n" +
                "  \"emp_status\": \"Tester\",\n" +
                "  \"emp_job_title\": \"Testing\"\n" +
                "}";
        return createEmployeePayload;
    }

    public static String createEmployeePayloadJson(){

        JSONObject obj = new JSONObject();
        obj.put("emp_firstname","Fost");
        obj.put("emp_lastname","Fostov");
        obj.put("emp_middle_name","MN");
        obj.put("emp_gender","M");
        obj.put("emp_birthday","2086-03-29");
        obj.put("emp_status","Tester");
        obj.put("emp_job_title","Testing");

        return obj.toString();

    }
    public static String createEmployeePayloadJsonDynamic(String emp_firstname,
                                                          String emp_lastname,
                                                          String emp_middle_name,
                                                          String emp_gender,
                                                          String emp_birthday,
                                                          String emp_status,
                                                          String emp_job_title){
        JSONObject obj = new JSONObject();
        obj.put("emp_firstname",emp_firstname);
        obj.put("emp_lastname",emp_lastname);
        obj.put("emp_middle_name",emp_middle_name);
        obj.put("emp_gender",emp_gender);
        obj.put("emp_birthday",emp_birthday);
        obj.put("emp_status",emp_status);
        obj.put("emp_job_title",emp_job_title);
        return obj.toString();

    }



}
