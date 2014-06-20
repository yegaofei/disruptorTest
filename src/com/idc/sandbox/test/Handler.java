package com.idc.sandbox.test;

import com.lmax.disruptor.EventHandler;

public class Handler implements EventHandler<Event>
{
    private String name;

    public Handler(String name)
    {
        this.name = name;
    }

    @Override
    public void onEvent(Event event, long sequence, boolean batchEnd) throws Exception
    {
        event.add(name + "-" + sequence + "|");
//        log(event);
        System.out.println("Handle:" + event);
    }
}
