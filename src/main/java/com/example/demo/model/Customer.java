package com.example.demo.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class Customer {

    @DynamoDBAttribute
    private String userName;

    @DynamoDBAttribute
    private  String password;
    
    @DynamoDBAttribute
    private String fullname;

    @DynamoDBAttribute
    private String numberPhone;
}