package entity;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class EmployeeService {

	private DynamoDBMapper dynamoDBMapper;

	private String jsonBody = null;

	public APIGatewayProxyResponseEvent saveEmployee(APIGatewayProxyRequestEvent request, Context context) {
		initDynamoDB();
		Employee employee = Utility.convertStringToObj(request.getBody(), context);
		dynamoDBMapper.save(employee);
		jsonBody = Utility.convertObjectToString(employee, context);
		context.getLogger().log("Saved successfully : " + jsonBody);
		return createAPIResponse(jsonBody, 201, Utility.createHeaders());

	}

	public APIGatewayProxyResponseEvent getEmployeeById(APIGatewayProxyRequestEvent request, Context context) {
		initDynamoDB();
		String empId = request.getPathParameters().get("empId");
		Employee employee = dynamoDBMapper.load(Employee.class, empId);
		int status = 404;
		if (null != employee) {
			jsonBody = Utility.convertObjectToString(employee, context);
			status = 200;
			context.getLogger().log("Fetch employee by id : " + jsonBody);
		} else {
			jsonBody = "Employee not found for empId : " + empId;
			status = 400;
		}
		return createAPIResponse(jsonBody, status, Utility.createHeaders());
	}

	public APIGatewayProxyResponseEvent getEmployees(APIGatewayProxyRequestEvent request, Context context) {
		initDynamoDB();
		List<Employee> employees = dynamoDBMapper.scan(Employee.class, new DynamoDBScanExpression());
		int status = 404;
		if (null != employees) {
			jsonBody = Utility.convertListOfObjectToString(employees, context);
			status = 200;
			context.getLogger().log("Fetch employee by id : " + jsonBody);
		} else {
			jsonBody = "Employee table is empty : ";
			status = 400;
		}
		return createAPIResponse(jsonBody, status, Utility.createHeaders());
	}
	
	public APIGatewayProxyResponseEvent deleteEmployeeById(APIGatewayProxyRequestEvent request, Context context) {
		initDynamoDB();
		String empId = request.getPathParameters().get("empId");
		Employee employee = dynamoDBMapper.load(Employee.class, empId);
		int status = 404;
		if (null != employee) {
			dynamoDBMapper.delete(employee);
			jsonBody = Utility.convertObjectToString(employee, context);
			status = 200;
			context.getLogger().log("Employee is deleted : " + jsonBody);
		} else {
			jsonBody = "Employee not found for empId : " + empId;
			status = 400;
		}
		return createAPIResponse(jsonBody, status, Utility.createHeaders());
	}

	private void initDynamoDB() {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
		dynamoDBMapper = new DynamoDBMapper(client);
	}

	private APIGatewayProxyResponseEvent createAPIResponse(String body, int status, Map<String, String> headers) {
		APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
		responseEvent.setBody(body);
		responseEvent.setHeaders(headers);
		responseEvent.setStatusCode(status);
		return responseEvent;
	}

}
