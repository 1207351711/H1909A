package com.xiaoshu.jms;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author shkstart
 * @create 2020-06-23 9:47
 */
public class MyMessageListener implements MessageListener {
    @Autowired
        private RedisTemplate redisTemplate;

        @Override
        public void onMessage(Message message) {

            try {
                String text = ((TextMessage) message).getText();
                System.out.println(text.toString()+"-------------------text");
                /*Hospital hospital = JSON.parseObject(text, Hospital.class);
                redisTemplate.boundValueOps(hospital.getName()).set(text);*/
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
}
