package org.vanilladb.core.query.algebra;

import java.util.Collection;

import org.vanilladb.core.sql.Constant;
import org.vanilladb.core.sql.VarcharConstant;
import org.vanilladb.core.sql.Type;

/**
 * The scan class corresponding to the EXPLAIN relational algebra operator.
 * It returns a single row with a descriptive plan string.
 */
public class ExplainScan implements Scan {
	private Scan s; // underlying scan, not actually used
	private Collection<String> fieldList;
	private String explain;
	private boolean first;

	public ExplainScan(Scan s, Collection<String> fieldList, String explain) {
		this.s = s;
		this.fieldList = fieldList;
		this.explain = explain;
		this.first = true;
	}

	@Override
	public void beforeFirst() {
		first = true; // We control only one row
	}

	@Override
	public boolean next() {
		if (first) {
			first = false;
			return true;
		}
		return false;
	}

	@Override
	public void close() {
		if (s != null)
			s.close();
	}

	@Override
	public Constant getVal(String fldName) {
		if (hasField(fldName))
			return new VarcharConstant(explain);
		throw new RuntimeException("Field not found: " + fldName);
	}

	@Override
	public boolean hasField(String fldName) {
		return fieldList.contains(fldName);
	}
/*     
	@Override
	public int getInt(String fldName) {
		throw new UnsupportedOperationException("ExplainScan does not support int");
	}

	@Override
	public long getLong(String fldName) {
		throw new UnsupportedOperationException("ExplainScan does not support long");
	}

	@Override
	public double getDouble(String fldName) {
		throw new UnsupportedOperationException("ExplainScan does not support double");
        
                

	}*/
}
