package ru.pharus.socnetwork.controllers.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class UserStatusListener implements HttpSessionListener {
    // TODO: 17.03.2017 добавить слушателя на добавление онлайн/офлайн статуса

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
