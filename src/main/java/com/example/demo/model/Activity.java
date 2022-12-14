package com.example.demo.model;


import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "Activities")
public class Activity {

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String activityId;

    @DynamoDBAttribute
    private String activityType;

    @DynamoDBAttribute
    private Long valueStampTime = new Date().getTime();

    @DynamoDBAttribute
    private Double amount;
    
    @DynamoDBAttribute
    private String movement;

    @DynamoDBAttribute
    private Customer customer;
}