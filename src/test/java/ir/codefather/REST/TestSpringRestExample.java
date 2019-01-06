package ir.codefather.REST;

import ir.codefather.REST.controller.EmpRestURIConstants;
import ir.codefather.REST.model.Employee;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

public class TestSpringRestExample {

    public static final String SERVER_URI = "http://localhost:8080/";

    public static void main(String args[]) {

        testGetDummyEmployee();
        System.out.println("*****");
        testCreateEmployee();
        System.out.println("*****");
        testGetEmployee();
        System.out.println("*****");
        testGetAllEmployee();
    }

    /**
     * when invoking rest method returning a Collection, we need to use LinkedHashMap
     * because JSON to object conversion doesn’t know about the Employee object and converts it to the collection of LinkedHashMap.
     * We can write a utility method to convert from LinkedHashMap to our Java Bean object.
     */
    private static void testGetAllEmployee() {
        RestTemplate restTemplate = new RestTemplate();
        //we can't get List<Employee> because JSON convertor doesn't know the type of
        //object in the list and hence convert it to default JSON object type LinkedHashMap
        List<LinkedHashMap> emps = restTemplate.getForObject(SERVER_URI + EmpRestURIConstants.GET_ALL_EMP, List.class);

        for (LinkedHashMap map : emps) {
            System.out.println("ID=" + map.get("id") + ",Name=" + map.get("name") + ",CreatedDate=" + map.get("createdDate"));
            ;
        }
    }

    private static void testCreateEmployee() {
        RestTemplate restTemplate = new RestTemplate();
        Employee emp = new Employee();
        emp.setId(1);
        emp.setName("Pankaj Kumar");
        Employee response = restTemplate.postForObject(SERVER_URI + EmpRestURIConstants.CREATE_EMP, emp, Employee.class);
        printEmpData(response);
    }

    private static void testGetEmployee() {
        RestTemplate restTemplate = new RestTemplate();
        Employee emp = restTemplate.getForObject(SERVER_URI + "/rest/emp/1", Employee.class);
        printEmpData(emp);
    }

    private static void testGetDummyEmployee() {
        RestTemplate restTemplate = new RestTemplate();
        Employee emp = restTemplate.getForObject(SERVER_URI + EmpRestURIConstants.DUMMY_EMP, Employee.class);
        printEmpData(emp);
    }

    public static void printEmpData(Employee emp) {
        System.out.println("ID=" + emp.getId() + ",Name=" + emp.getName() + ",CreatedDate=" + emp.getCreatedDate());
    }
}
