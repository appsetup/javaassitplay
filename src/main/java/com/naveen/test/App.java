package com.naveen.test;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Employee employee = new Employee();
        employee.setName("Naveen");
        employee.setDepartment("ABC");
        Employee employee1 = new Employee();
        employee.setName("Naveen");
        employee.setDepartment("ABC");
        if(employee.equals(employee1))
        {
            System.out.println("They are equal");
        }
    }
}
