package ru.itfb.oracleaq.Config;

import lombok.extern.slf4j.Slf4j;
import oracle.jms.AQjmsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.sql.DataSource;

@Configuration
@Slf4j
public class DBConnectionFactory {

    @Autowired
    private DataSource dataSource;

    @Bean
    public QueueConnectionFactory connectionFactory() throws JMSException {
        return AQjmsFactory.getQueueConnectionFactory(dataSource);
    }

    @Bean
    public DataSourceTransactionManager transactionManager ()  {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }
}
