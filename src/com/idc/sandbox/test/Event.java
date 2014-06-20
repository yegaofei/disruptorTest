package com.idc.sandbox.test;

import com.lmax.disruptor.EventFactory;

public class Event
{
    private String value;

    public void setValue(String value)
    {
        this.value = value;
    }

    public void add(String suffix)
    {
        value = value + suffix;
    }

    public String toString()
    {
        return value;
    }

    public final static EventFactory<Event> factory = new EventFactory<Event>()
    {
        public Event newInstance()
        {
            return new Event();
        }
    };

}
