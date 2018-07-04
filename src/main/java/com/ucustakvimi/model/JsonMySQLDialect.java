package com.ucustakvimi.model;

import java.sql.Types;

import org.hibernate.dialect.MySQL5InnoDBDialect;

public class JsonMySQLDialect extends MySQL5InnoDBDialect {
	public String getTableTypeString() {
		return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
	}

	public JsonMySQLDialect() {
		super();
		registerColumnType(Types.VARCHAR, "json");
	}

}