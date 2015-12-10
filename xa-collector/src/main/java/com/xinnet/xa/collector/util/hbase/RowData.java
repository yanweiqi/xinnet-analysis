package com.xinnet.xa.collector.util.hbase;

import java.util.Map;

public class RowData {

	private String columnFamily;
    private Map<String,Object> column;
	
	public String getColumnFamily() {
		return columnFamily;
	}
	public void setColumnFamily(String columnFamily) {
		this.columnFamily = columnFamily;
	}
	public Map<String, Object> getColumn() {
		return column;
	}
	public void setColumn(Map<String, Object> column) {
		this.column = column;
	}
    
	
}
