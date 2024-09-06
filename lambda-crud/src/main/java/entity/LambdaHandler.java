package entity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		EmployeeService service = new EmployeeService();
		switch (input.getHttpMethod()) {
		case "POST":
			return service.saveEmployee(input, context);
		case "GET":
			if (null != input.getPathParameters()) {
				return service.getEmployeeById(input, context);
			} else {
				return service.getEmployees(input, context);
			}
		case "DELETE":
			if (null != input.getPathParameters()) {
				return service.deleteEmployeeById(input, context);
			}
			break;

		default:
			throw new Error("Method not supported : " + input.getHttpMethod());
		}
		return null;
	}

}
