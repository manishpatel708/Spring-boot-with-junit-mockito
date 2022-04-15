package com.rightpoint.training.controller;

import com.rightpoint.training.model.Employee;
import com.rightpoint.training.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    public ResponseEntity<?> getEmployeeList() {
        return new ResponseEntity<List<Employee>>(employeeService.getEmployeeList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable long id) {
        return new ResponseEntity<Employee>(employeeService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable long id, @RequestBody Employee employee) {
        return new ResponseEntity<Employee>(employeeService.updateEmployee(id, employee), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee deleted ...", HttpStatus.OK);
    }

    @GetMapping("/salary")
    public ResponseEntity<?> getEmployeeAsPerSalary(@RequestParam double salary) {
        return new ResponseEntity<List<Employee>>(employeeService.getEmployeeAsPerSalary(salary), HttpStatus.OK);
    }

}
