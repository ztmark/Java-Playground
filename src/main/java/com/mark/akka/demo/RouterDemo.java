package com.mark.akka.demo;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.BalancingPool;
import akka.routing.RoundRobinPool;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Router;

/**
 * Author: Mark
 * Date  : 2017/6/29
 */
public class RouterDemo {

    public static void main(String[] args) {
        final ActorSystem actorSystem = ActorSystem.create(RouterDemo.class.getName());
        final ActorRef actorRef = actorSystem.actorOf(Props.create(RouterActor.class)
                .withRouter(new RoundRobinPool(Runtime.getRuntime().availableProcessors()))
                .withDispatcher("my-dispatcher"));

    }


    class RouterActor extends AbstractActor {

        @Override
        public Receive createReceive() {
            return null;
        }
    }

}
