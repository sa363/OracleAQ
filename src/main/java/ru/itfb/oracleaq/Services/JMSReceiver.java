package ru.itfb.oracleaq.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j

@Component
public class JMSReceiver  {

    @JmsListener(destination = "FromSiebel")
    public void receive(String msg) {
        log.info("MSG: " + msg);
    }
}
