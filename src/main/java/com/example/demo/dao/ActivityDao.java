package com.example.demo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.demo.model.Activity;

@Repository
public class ActivityDao {

	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	// Insert activity
	public Activity save(Activity activity) {
		dynamoDBMapper.save(activity);
		return activity;
	}

	// Find by Id
	public Activity getActivityById(String activityId) {
		return dynamoDBMapper.load(Activity.class, activityId);
	}

	// Delete activity
	public String delete(String activityId) {
		Activity emp = dynamoDBMapper.load(Activity.class, activityId);
		dynamoDBMapper.delete(emp);
		return "Activity Deleted!";
	}

	// Update activity
	public String update(String activityId, Activity activity) {
		dynamoDBMapper.save(activity, new DynamoDBSaveExpression().withExpectedEntry("activityId",
				new ExpectedAttributeValue(new AttributeValue().withS(activityId))));
		return activityId;
	}

	// Find by activityType
	public List<Activity> getActivityByName(String userName, String activityType) {
		HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(userName));
		eav.put(":v2", new AttributeValue().withS(activityType));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				// Filter Expression
				.withFilterExpression("begins_with(customer.userName,:v1) and begins_with(activityType, :v2)")
				.withExpressionAttributeValues(eav);

		List<Activity> activities = dynamoDBMapper.scan(Activity.class, scanExpression);
		return activities;
	}

	// Find by Amount
	public List<Activity> getActivityByAmount(String userName, String from, String to) {
		int numberOfWorkers = 1;
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(userName));
		eav.put(":v2", new AttributeValue().withN(from));
		eav.put(":v3", new AttributeValue().withN(to));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				// Filter Expression
				.withFilterExpression("amount <= :v3 and amount >= :v2 and begins_with(customer.userName,:v1)")
				.withExpressionAttributeValues(eav);

		List<Activity> scanParallelResult = dynamoDBMapper.parallelScan(Activity.class, scanExpression,
				numberOfWorkers);
		return scanParallelResult;
	}

	// Find by Date
	public List<Activity> getActivityByDate(String userName, String from, String to) {
		int numberOfWorkers = 3;
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(userName));
		eav.put(":v2", new AttributeValue().withN(from));
		eav.put(":v3", new AttributeValue().withN(to));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				// Filter Expression
				.withFilterExpression(
						"valueStampTime <= :v3 and valueStampTime >= :v2 and begins_with(customer.userName,:v1)")
				.withExpressionAttributeValues(eav);

		List<Activity> scanParallelResult = dynamoDBMapper.parallelScan(Activity.class, scanExpression,
				numberOfWorkers);
		return scanParallelResult;
	}

	// Find by Movement
	public List<Activity> getActivityByMovement(String userName, String movement) {
		HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(userName));
		eav.put(":v2", new AttributeValue().withS(movement));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				// Filter Expression
				.withFilterExpression("begins_with(customer.userName,:v1) and begins_with(movement, :v2)")
				.withExpressionAttributeValues(eav);

		List<Activity> activities = dynamoDBMapper.scan(Activity.class, scanExpression);
		return activities;
	}

	// filter by: username, activityType, movement
	public List<Activity> getFilterAll(String userName, String activityType, String movement) {
		HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(userName));
		eav.put(":v2", new AttributeValue().withS(activityType));
		eav.put(":v3", new AttributeValue().withS(movement));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				// Filter Expression
				.withFilterExpression(
						"begins_with(customer.userName, :v1) and contains(activityType, :v2) and contains(movement, :v3)")
				.withExpressionAttributeValues(eav);
		List<Activity> activities = dynamoDBMapper.scan(Activity.class, scanExpression);
		return activities;
	}

}
