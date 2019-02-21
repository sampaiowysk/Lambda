package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.List;

public class Hello implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent event, Context context)
    {
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        final String myQueueUrlGET = "https://sqs.us-east-1.amazonaws.com/183159870389/TestSQS";
        final String myQueueUrlPST = "https://sqs.us-east-1.amazonaws.com/183159870389/lambdaSQS";

        //Recebendo mensagens
        final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrlGET);
        final List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();

        for (final Message message : messages) {
            String result = message.getBody().toUpperCase();

            //sqs.deleteMessage(new DeleteMessageRequest(myQueueUrlGET, message.getReceiptHandle()));

            sqs.sendMessage(new SendMessageRequest(myQueueUrlPST, result));
        }

        return null;
    }
}