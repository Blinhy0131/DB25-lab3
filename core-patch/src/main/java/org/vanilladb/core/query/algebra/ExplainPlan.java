package org.vanilladb.core.query.algebra;

import org.vanilladb.core.query.algebra.Plan;
import org.vanilladb.core.query.algebra.Scan;
import org.vanilladb.core.query.algebra.ExplainScan;
import org.vanilladb.core.query.parse.QueryData;
import org.vanilladb.core.sql.Schema;
import java.util.Arrays;
import org.vanilladb.core.sql.Type;
import org.vanilladb.core.storage.metadata.statistics.Histogram;


public class ExplainPlan implements Plan {
    private Plan p;

    public ExplainPlan(Plan p) {
        this.p = p;
    }

    @Override
    public Scan open() {
        return new ExplainScan(null, Arrays.asList("query-plan"), p.toString(0));
    }
    
    @Override
    public long blocksAccessed() {
        return 0; // EXPLAIN 本身不會讀資料塊
    }

    @Override
    public long recordsOutput() {
        return 1;
    }

    @Override
    public Schema schema() {
        Schema schema = new Schema();
        schema.addField("query-plan", Type.VARCHAR(500)); // 可拉長一點避免截斷
        return schema;
    }

    @Override
    public Histogram histogram() {
        return null; // 沒有 histogram
    }
}