package com.casestudy.skilltracker.employee.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories
        (basePackages = "com.casestudy.skilltracker.employee.repository")
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.aws.accessKey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretKey}")
    private String amazonAWSSecretKey;

    @Value("${amazon.dynamodb.region}")
    private String amazonAWSRegion;

    @Bean
    public AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider awsCredentialsProvider) {
        AmazonDynamoDB amazonDynamoDB
                = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, amazonAWSRegion))
                .withCredentials(awsCredentialsProvider).build();
        return amazonDynamoDB;
    }

    @Bean
    public AWSCredentialsProvider amazonAWSCredentials() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));
    }


}
