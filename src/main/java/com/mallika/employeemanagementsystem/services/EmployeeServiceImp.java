package com.mallika.employeemanagementsystem.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mallika.employeemanagementsystem.entity.EmployeeEntity;
import com.mallika.employeemanagementsystem.model.Employee;
import com.mallika.employeemanagementsystem.repository.EmployeeRepository;

@Service
public class EmployeeServiceImp implements EmployeeService {

	private EmployeeRepository employeeRepository;

	public EmployeeServiceImp(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee createEmployee(Employee employee) {
		EmployeeEntity employeeEntity = new EmployeeEntity();

		BeanUtils.copyProperties(employee, employeeEntity);
		employeeRepository.save(employeeEntity);
		return employee;
	}

	@Override
	public List<Employee> getAllEmployees() {
		List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
//		employeeEntities.get(0).getEmailId();
		List<Employee> employees = employeeEntities
				.stream()
				.map(emp -> Employee.builder()
						.id(emp.getId())
						.firstName(emp.getFirstName())
						.lastName(emp.getLastName())
						.emailId(emp.getEmailId())
						.build())
				.collect(Collectors.toList());
		return employees;
	}
	
	@Override
	public boolean deleteEmployee(Long id) {
		EmployeeEntity employee = employeeRepository.findById(id).get();
		employeeRepository.delete(employee);
		return true;
	}

	@Override
	public Employee getEmployeeById(Long id) {
		EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeEntity, employee);
		return employee;
	}

	@Override
	public Employee updateEmployee(Long id, Employee employee) {
		EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
		employeeEntity.setEmailId(employee.getEmailId());
		employeeEntity.setFirstName(employee.getFirstName());
		employeeEntity.setLastName(employee.getLastName());
		
		employeeRepository.save(employeeEntity);
		return employee;
	}
	

}
