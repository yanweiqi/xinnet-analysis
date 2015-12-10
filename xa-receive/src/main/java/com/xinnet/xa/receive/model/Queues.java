package com.xinnet.xa.receive.model;

import org.apache.commons.lang.builder.ToStringBuilder;


public class Queues {

    private long id;

    private String queuename;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQueuename() {
        return queuename;
    }

    public void setQueuename(String queuename) {
        this.queuename = queuename;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
