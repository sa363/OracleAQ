package ru.itfb.oracleaq.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import ru.itfb.oracleaq.Services.JMSReceiver;

@Slf4j
@Configuration
public class OracleAQConfiguration {


//    private DataSource dataSource;

    @Autowired
    private JMSReceiver jmsReceiver;
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    private static final String QUEUENAME_READ = "AQ_IN";
    private static final String QUEUENAME_WRITE = "AQ_IN";
    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestinationName(QUEUENAME_WRITE);
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    /**
     * Spring bean to READ/RECEIVE/DEQUEUE messages of a queue with a certain name
     * All of this happens under a code managed transaction
     * to commit the change on Oracle (remove of the message from the queue table)
     * Reference the application custom code handling the message here
     */
    @Bean
    public DefaultMessageListenerContainer messageListenerContainer() {
        DefaultMessageListenerContainer dmlc = new DefaultMessageListenerContainer();
        dmlc.setDestinationName(QUEUENAME_READ);
        dmlc.setSessionTransacted(true);
        dmlc.setConnectionFactory(connectionFactory);
        dmlc.setTransactionManager(transactionManager);
        dmlc.setConcurrency("1-1000");
//        dmlc.setConcurrentConsumers(50);
//        dmlc.setMaxConcurrentConsumers(1000);

        // Add here our self-written JMS Receiver
        dmlc.setMessageListener(jmsReceiver);
        return dmlc;
    }
}
