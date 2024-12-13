package com.viveksinghspringsecurity.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Component

public class AuthorizationEvents {

    private static final Logger log = LogManager.getLogger(AuthorizationEvents.class);


    @EventListener
    public void onFailure(AuthorizationDeniedEvent deniedEvent) {

        log.error("Authorisation Failed for the user: {} , due to {}", deniedEvent.getAuthentication().get().getName(),
                deniedEvent.getAuthorizationDecision().toString());
    }

}
