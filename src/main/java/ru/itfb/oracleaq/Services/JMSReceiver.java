package ru.itfb.oracleaq.Services;

import javax.jms.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;
@Component
@Configuration
@EnableJms
@Slf4j
public class JMSReceiver implements SessionAwareMessageListener {


    /**
     * Callback for processing a received JMS message.
     * <p>Implementors are supposed to process the given Message,
     * typically sending reply messages through the given Session.
     *
     * @param message the received JMS message (never {@code null})
     * @param session the underlying JMS Session (never {@code null})
     * @throws JMSException if thrown by JMS methods
     */

        @Override
        public void onMessage(Message message, Session session) throws JMSException {
            TextMessage txtMessage = (TextMessage) message;
            log.info("JMS Text Message received: " + txtMessage.getText());
        }


    }
