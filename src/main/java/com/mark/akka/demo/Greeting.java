package com.mark.akka.demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

import java.io.Serializable;

/**
 * Author: Mark
 * Date  : 2016/10/7
 */
public class Greeting implements Serializable {
    private static final long serialVersionUID = 4361578947625733595L;

    private String who;

    public Greeting(String who) {
        this.who = who;
    }

    public String getWho() {
        return who;
    }
}

class GreetingActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Greeting) {
            System.out.println("Hello " + ((Greeting) message).getWho());
        }
    }
}

