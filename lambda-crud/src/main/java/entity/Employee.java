package entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@lombok.Data
@lombok.ToString
@DynamoDBTable(tableName = "employee")
public class Employee {

	@DynamoDBHashKey(attributeName = "empId")
	private String empId;

	@DynamoDBAttribute(attributeName = "name")
	private String name;

	@DynamoDBAttribute(attributeName = "email")
	private String email;

}
