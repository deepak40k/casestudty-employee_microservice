package com.casestudy.skilltracker.employee.mq.sender;

public interface MessageSender<T> {
     void send(T t);
}
