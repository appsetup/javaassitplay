package com.naveen.test;

import com.naveen.test.annotation.Loggable;

/**
 * Created by IntelliJ IDEA.
 * User: naveen
 * Date: 13/2/11
 * Time: 2:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Loggable
public class Employee {
    private String name;
    private int age;
    private String department;

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (age != employee.age) return false;
        if (department != null ? !department.equals(employee.department) : employee.department != null) return false;
        if (name != null ? !name.equals(employee.name) : employee.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        result = 31 * result + (department != null ? department.hashCode() : 0);
        return result;
    }
}
