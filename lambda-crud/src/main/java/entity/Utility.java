package entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utility {

	public static Map<String, String> createHeaders() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("X-amazon-author", "Pankaj");
		headers.put("X-amazon-apiVersion", "v1");
		return headers;
	}

	public static String convertObjectToString(Employee employee, Context context) {
		String jsonBody = null;
		try {
			jsonBody = new ObjectMapper().writeValueAsString(employee);
		} catch (JsonProcessingException e) {
			context.getLogger().log("Error while converting obj to string" + e.getMessage());
		}
		return jsonBody;
	}

	public static Employee convertStringToObj(String jsonBody, Context context) {
		Employee employee = null;
		try {
			employee = new ObjectMapper().readValue(jsonBody, Employee.class);
		} catch (JsonProcessingException e) {
			context.getLogger().log("Error while converting string to obj" + e.getMessage());
		}
		return employee;
	}

	public static String convertListOfObjectToString(List<Employee> employee, Context context) {
		String jsonBody = null;
		try {
			jsonBody = new ObjectMapper().writeValueAsString(employee);
		} catch (JsonProcessingException e) {
			context.getLogger().log("Error while converting obj to string" + e.getMessage());
		}
		return jsonBody;
	}

}
