package com.caique.APICRUD.controller;


import com.caique.APICRUD.repository.EmployeeRepository;
import com.caique.APICRUD.exception.EmployeeNotFoundException;
import com.caique.APICRUD.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository ;

    @GetMapping()
    public List<Employee> getAllEmployees () {
        return employeeRepository.findAll();
    }

    @PostMapping("{id}")
    public Employee createEmployee (@PathVariable("id") Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById (@PathVariable("id") String id) {
        Employee entity = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not exist with id: " + id));
        return ResponseEntity.ok(entity);
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee (@PathVariable("id") String id, @RequestBody Employee employeeDetails) {
        Employee updateEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not exists with id: " + id));
        updateEmployee.setName(employeeDetails.getName());
        updateEmployee.setEmail(employeeDetails.getEmail());
        updateEmployee.setAge(employeeDetails.getAge());
        updateEmployee.setSalary(employeeDetails.getSalary());

        employeeRepository.save(updateEmployee);

        return ResponseEntity.ok(updateEmployee);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee (@PathVariable("id") String id) {
        Employee deleteEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not exist with id: " + id));

        employeeRepository.delete(deleteEmployee);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
