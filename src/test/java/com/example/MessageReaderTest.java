package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MessageReaderTest {

    @Ignore
    @Test
    public void testReader() throws Exception {
        Context context = mock(Context.class);
        LambdaLogger logger = mock(LambdaLogger.class);
        when(context.getLogger()).thenReturn(logger);
        MessageReader messageReader = new MessageReader();
        CustomSQSEvent sqsEvent = new CustomSQSEvent();
        String result = messageReader.handleRequest(sqsEvent, context);
        verify(logger).log("Message #1 placed on queue.");
        assertEquals("1 message(s) placed on queue [Test Message]", result);
    }
}
                