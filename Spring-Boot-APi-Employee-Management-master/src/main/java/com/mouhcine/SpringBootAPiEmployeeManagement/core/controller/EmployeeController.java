package com.mouhcine.SpringBootAPiEmployeeManagement.core.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mouhcine.SpringBootAPiEmployeeManagement.core.bo.Employee;
import com.mouhcine.SpringBootAPiEmployeeManagement.core.service.IEmployeeService;

@RestController
@RequestMapping("/")
public class EmployeeController {

    @Autowired
    IEmployeeService employeeService;
    
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("employees")
    public List<Employee> getEmployees(){
        return employeeService.getEmployees();
    }

    @GetMapping("employees/{id}")
    public Optional<Employee> getEmployee(@PathVariable("id") Long id){
    	Optional<Employee> emp = employeeService.getEmployeeById(id);
        return emp.isPresent()? emp: Optional.empty();
    }

    @PostMapping("/employees")
    public Employee saveEmployee(@RequestBody Employee employee){
        return employeeService.save(employee);
    }
    @PutMapping("employees/{id}")
    public Employee updateEmployee(@RequestBody Employee employee,@PathVariable long id){
        Employee emp=employeeService.getEmployeeById(id).get();
        emp.setFirstName(employee.getFirstName());
        emp.setLastName(employee.getLastName());
        emp.setEmailAddress(employee.getEmailAddress());
        emp.setPhone(employee.getPhone());
        emp.setBirthDate(employee.getBirthDate());
        emp.setJobTitle(employee.getJobTitle());
        emp.setDepartment(employee.getDepartment());
        emp.setStartDate(employee.getStartDate());
        emp.setReportingManager(employee.getReportingManager());
        return employeeService.update(emp);
    }
    @DeleteMapping("employees/{id}")
    public String deleteEmployee(@PathVariable("id") Long id){
        employeeService.delete(id);
        return "Delete successFully";
    }
}