/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author anhqu
 */
public class Employee extends BaseModel {

    private String name;                
    private Employee manager;           
    private Department department;      

    private ArrayList<Employee> employees = new ArrayList<>(); 
    private ArrayList<LeaveRequest> requests = new ArrayList<>(); 
    private ArrayList<Account> accounts = new ArrayList<>(); 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public ArrayList<LeaveRequest> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<LeaveRequest> requests) {
        this.requests = requests;
    }

    // Getter & Setter cho các tài khoản liên kết
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
}
