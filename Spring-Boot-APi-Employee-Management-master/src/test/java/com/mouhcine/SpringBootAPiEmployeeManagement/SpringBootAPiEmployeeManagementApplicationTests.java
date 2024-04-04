package com.mouhcine.SpringBootAPiEmployeeManagement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.mouhcine.SpringBootAPiEmployeeManagement.core.bo.Employee;
import com.mouhcine.SpringBootAPiEmployeeManagement.core.controller.EmployeeController;
import com.mouhcine.SpringBootAPiEmployeeManagement.core.service.IEmployeeService;


@SpringBootTest
class SpringBootAPiEmployeeManagementApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private IEmployeeService employeeService;

	@InjectMocks
	private EmployeeController employeeController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
	}

	@Test
	public void testIndex() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(content().string("index"));
	}

	@Test
	public void testGetEmployees() throws Exception {
		// Mocking service method
		when(employeeService.getEmployees()).thenReturn(Collections.singletonList(new Employee()));

		// Performing GET request
		mockMvc.perform(get("/employees")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
	}

	@Test
	public void testGetEmployee() throws Exception {
		Long id = 1L;
		Employee employee = new Employee();
		employee.setEmployeeId(id);

		// Mocking service method
		when(employeeService.getEmployeeById(id)).thenReturn(Optional.of(employee));

		// Performing GET request
		mockMvc.perform(get("/employees/{id}", id)).andExpect(status().isOk());
	}

	@Test
	public void testUpdateEmployee() throws Exception {
		Long id = 1L;
		Employee employee = new Employee();
		employee.setEmployeeId(id);
		employee.setFirstName("John");

		// Mocking service method
		when(employeeService.getEmployeeById(id)).thenReturn(Optional.of(employee));
		when(employeeService.update(any(Employee.class))).thenReturn(employee);

		// Performing PUT request
		mockMvc.perform(put("/employees/{id}", id).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content("{\"firstName\":\"UpdatedName\"}")).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("UpdatedName"));
	}

	@Test
	public void testDeleteEmployee() throws Exception {
		Long id = 1L;

		// Performing DELETE request
		mockMvc.perform(delete("/employees/{id}", id)).andExpect(status().isOk())
				.andExpect(content().string("Delete successFully"));

		// Verifying service method invocation
		verify(employeeService, times(1)).delete(id);
	}
}
