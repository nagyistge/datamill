package org.chodavarapu.datamill.db;

import rx.Observable;

/**
 * @author Ravi Chodavarapu (rchodava@gmail.com)
 */
public interface Connection {
    Observable<Row> query(String sql);
}