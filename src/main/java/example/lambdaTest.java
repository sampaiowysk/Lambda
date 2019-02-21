import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class LambdaFunctionHandler implements RequestHandler<SQSEvent, String> {

    @Override
    public String handleRequest(SQSEvent event, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("Entrou");

        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        final String myQueueUrlPST = "https://sqs.us-east-1.amazonaws.com/183159870389/lambdaSQS";

        logger.log("Recebeu");
        for(SQSMessage msg : event.getRecords()){
            String result = msg.getBody().toUpperCase();
            sqs.sendMessage(new SendMessageRequest(myQueueUrlPST, result));
        }

        logger.log("Terminou");
        return null;

    }
}
