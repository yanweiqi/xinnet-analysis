package com.xinnet.xa.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ReceiveContant {
	public final static List<String> QUEUES = new ArrayList<String>();
	public final static AtomicBoolean run   = new AtomicBoolean(false);
	public final static AtomicInteger RECE_COUNT = new AtomicInteger();

}
