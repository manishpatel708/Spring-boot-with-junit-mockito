package com.rightpoint.training.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rightpoint.training.model.Employee;
import com.rightpoint.training.service.EmployeeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    ObjectMapper mapper;

    Employee record_1 = new Employee(1, "Manish", "backend", 150000.00, 27);
    Employee record_2 = new Employee(2, "Chintal", "seo", 75000, 24);
    Employee record_3 = new Employee(3, "Maitri", "backend", 45000, 22);

    @Autowired
    private MockMvc mocMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void getAllEmployeesTest() throws Exception {
        ArrayList<Employee> employeesList = new ArrayList<>(Arrays.asList(record_1, record_2, record_3));

        Mockito.when(employeeService.getEmployeeList()).thenReturn(employeesList);

        mocMvc.perform(get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[1].name", Matchers.is("Chintal")));
    }

    @Test
    public void getByIdTest() throws Exception {
        Mockito.when(employeeService.findById(1)).thenReturn(record_1);

        mocMvc.perform(get("/api/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.name", Matchers.is("Manish")));
    }

    @Test
    public void saveEmployeeTest() throws Exception {
        Employee record = Employee.builder()
                .id(4)
                .name("Chhotu")
                .department("Art")
                .salary(25000)
                .age(14)
                .build();

        Mockito.when(employeeService.saveEmployee(record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/employee/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record));

        mocMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.name", Matchers.is("Chhotu")));
    }

    @Test
    public void updateEmployeeDataTest() throws Exception {
        Employee updateEmployeerecord = Employee.builder()
                .id(4)
                .name("Chhotu")
                .department("Art")
                .salary(25000)
                .age(14)
                .build();

        Mockito.when(employeeService.findById(updateEmployeerecord.getId())).thenReturn(updateEmployeerecord);
        Mockito.when(employeeService.updateEmployee(updateEmployeerecord.getId(), updateEmployeerecord)).thenReturn(updateEmployeerecord);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/employee/update/4")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updateEmployeerecord));

        mocMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.name", Matchers.is("Chhotu")));
    }
}