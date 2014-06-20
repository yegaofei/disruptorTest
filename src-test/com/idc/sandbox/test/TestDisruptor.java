package com.idc.sandbox.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.*;

import com.lmax.disruptor.dsl.Disruptor;

public class TestDisruptor
{

    private ExecutorService executor;
    private Disruptor<Event> disruptor;

    @Before
    public void setup()
    {
        executor = Executors.newCachedThreadPool();
    }

    @Test 
    public void shouldRunWithMultiplePublishersAndLinkedMultipleHandlers() throws Exception
    {
        disruptor = new Disruptor<>(Event.factory, 1024, executor);
        disruptor.handleEventsWith(new Handler("h1")).then(new Handler("h2")).then(new Handler("h3"));

        Publisher publisher1 = new Publisher("p1", 600, disruptor).goSlow();
        Publisher publisher2 = new Publisher("p2", 600, disruptor);
        disruptor.publishEvent(publisher1);
        disruptor.publishEvent(publisher2);
        disruptor.start();
        executor.submit(publisher1);
        executor.submit(publisher2);

        sleep();
        publisher1.stop();
        publisher2.stop();
    }

    @After
    public void shutDown()
    {
        disruptor.shutdown();
        executor.shutdown();
    }

    private void sleep() throws InterruptedException
    {
        Thread.sleep(3000);
    }
}
