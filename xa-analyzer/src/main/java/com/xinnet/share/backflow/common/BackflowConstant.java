package com.xinnet.share.backflow.common;

public class BackflowConstant {
	public static enum BackflowDataType{
		UV("BackflowUV"),REGISTER("BackflowRegister"),ORDER("BackflowOrder");
		private String tableName;

		private BackflowDataType(String tableName) {
			this.tableName = tableName;
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		
		
	}

}
