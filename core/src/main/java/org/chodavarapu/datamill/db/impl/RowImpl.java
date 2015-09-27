package org.chodavarapu.datamill.db.impl;

import org.chodavarapu.datamill.Value;
import org.chodavarapu.datamill.db.DatabaseException;
import org.chodavarapu.datamill.db.Row;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

/**
 * @author Ravi Chodavarapu (rchodava@gmail.com)
 */
public class RowImpl implements Row {
    private final ResultSet resultSet;

    @FunctionalInterface
    private interface ResultSetValueRetriever<K, R> {
        R retrieve(K key) throws SQLException;
    }

    private abstract class KeyedColumnValue<K> implements Value {
        protected final K key;

        public KeyedColumnValue(K key) {
            this.key = key;
        }

        protected <T> T safeRetrieve(ResultSetValueRetriever<K, T> retriever) {
            try {
                return retriever.retrieve(key);
            } catch (SQLException e) {
                throw new DatabaseException(e);
            }
        }

        @Override
        public <T> T map(Function<Value, T> mapper) {
            return mapper.apply(this);
        }
    }


    private class IndexedColumnValue extends KeyedColumnValue<Integer> {
        public IndexedColumnValue(int index) {
            super(index);
        }

        @Override
        public double asDouble() {
            return safeRetrieve(k -> resultSet.getDouble(key));
        }

        @Override
        public long asLong() {
            return safeRetrieve(k -> resultSet.getLong(key));
        }

        @Override
        public int asInteger() {
            return safeRetrieve(k -> resultSet.getInt(key));
        }

        @Override
        public float asFloat() {
            return safeRetrieve(k -> resultSet.getFloat(key));
        }

        @Override
        public boolean asBoolean() {
            return safeRetrieve(k -> resultSet.getBoolean(key));
        }

        @Override
        public String asString() {
            return safeRetrieve(k -> resultSet.getString(key));
        }
    }

    private class LabeledColumnValue extends KeyedColumnValue<String> {
        public LabeledColumnValue(String label) {
            super(label);
        }

        @Override
        public double asDouble() {
            return safeRetrieve(k -> resultSet.getDouble(key));
        }

        @Override
        public long asLong() {
            return safeRetrieve(k -> resultSet.getLong(key));
        }

        @Override
        public int asInteger() {
            return safeRetrieve(k -> resultSet.getInt(key));
        }

        @Override
        public float asFloat() {
            return safeRetrieve(k -> resultSet.getFloat(key));
        }

        @Override
        public boolean asBoolean() {
            return safeRetrieve(k -> resultSet.getBoolean(key));
        }

        @Override
        public String asString() {
            return safeRetrieve(k -> resultSet.getString(key));
        }
    }

    public RowImpl(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public Value column(int index) {
        return new IndexedColumnValue(index);
    }

    @Override
    public Value column(String name) {
        return new LabeledColumnValue(name);
    }

    @Override
    public int size() {
        try {
            return resultSet.getMetaData().getColumnCount();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}