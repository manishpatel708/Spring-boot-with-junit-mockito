package com.rightpoint.training.service;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rightpoint.training.model.Employee;
import com.rightpoint.training.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@CachePut(value = "employees")
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Cacheable(value = "employees", key = "#id")
	public Employee findById(long id) {
		return employeeRepository.findById(id).orElse(null);
	}

	@CachePut(value = "employees")
	public Employee updateEmployee(long id, Employee employee) {
		Employee updatedEmployee = new Employee();
		try {
			Optional<Employee> optional = employeeRepository.findById(id);
			if (optional.isPresent()) {
				employee.setId(optional.get().getId());
				updatedEmployee = employeeRepository.save(employee);
			} else {
				updatedEmployee = employeeRepository.save(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updatedEmployee;
	}

	@CacheEvict(value = "employees", key = "#id")
	public void deleteEmployee(long id) {
		employeeRepository.deleteById(id);
	}

	@Cacheable(sync = true, value = "employees")
	public List<Employee> getEmployeeList() {
//		sleep(2);
		return employeeRepository.findAll();
	}
//
//	private void sleep(int seconds) {
//		try {
//			SECONDS.sleep(seconds);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}

	@Cacheable(value = "employees")
	public List<Employee> getEmployeeAsPerSalary(double salary) {
		try {
//			sleep(3);
			List<Employee> employeeList = getEmployeeList();
			return employeeList.stream().filter(e -> e.getSalary() > 25000).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
