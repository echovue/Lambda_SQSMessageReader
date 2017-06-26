package com.example;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class MessageReader implements
        RequestHandler<CustomSQSEvent, String> {

    private String queueUrl = "https://sqs.us-west-2.amazonaws.com/xxxxxxxxx/ExampleMessageQueue";

    public String handleRequest(CustomSQSEvent sqsEvent, final Context context) {
        LambdaLogger logger = context.getLogger();
        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        int messagesProcessed = 0;

        List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();

        for (Message m : messages) {
            logger.log(m.getMessageId() + ": " + m.getBody() + "\n");
            sqs.deleteMessage(queueUrl, m.getReceiptHandle());
            messagesProcessed++;
        }
        return messagesProcessed + " message(s) processed and deleted.";
    }
}
