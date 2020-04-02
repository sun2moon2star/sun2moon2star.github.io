package jp.co.archTest.rest.ps.common;

import org.hibernate.cfg.ImprovedNamingStrategy;

public class UpperCaseStrategy extends ImprovedNamingStrategy {

	private static final long serialVersionUID = 1383021413247872469L;

	@Override
	public String tableName(String tableName) {
		String temp = super.tableName(tableName);
		return temp.toUpperCase();
	}

	@Override
	public String columnName(String columnName) {
		String temp = super.columnName(columnName);
		return temp.toUpperCase();
	}
}
