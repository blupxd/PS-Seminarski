package dbb;

import annotations.Column;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.GenericEntity;

public class GenericRepository<T extends GenericEntity> implements DBRepository {

    private final DBBroker db = DBBroker.getInstance();

    @Override
    public void connect() throws Exception {
        db.connect();
    }

    @Override
    public void disconnect() throws Exception {
        db.disconnect();
    }

    @Override
    public void commit() throws Exception {
        db.commit();
    }

    @Override
    public void rollback() throws Exception {
        db.rollback();
    }

    @SuppressWarnings("unchecked")
    public T create(T entity) throws Exception {
        List<Field> insertFields = entity.getInsertFields();

        StringBuilder cols = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < insertFields.size(); i++) {
            Column col = insertFields.get(i).getAnnotation(Column.class);
            cols.append(col.name());
            placeholders.append("?");
            if (i < insertFields.size() - 1) {
                cols.append(", ");
                placeholders.append(", ");
            }
        }

        String sql = "INSERT INTO " + entity.getTableName()
                + " (" + cols + ") VALUES (" + placeholders + ")";

        PreparedStatement ps = db.prepareStatementWithKeys(sql);
        setParameters(ps, insertFields, entity, 1);
        ps.executeUpdate();

        List<Field> pkFields = entity.getPrimaryKeyFields();
        if (pkFields.size() == 1) {
            Column pkCol = pkFields.get(0).getAnnotation(Column.class);
            if (pkCol.isAutoIncrement()) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    pkFields.get(0).set(entity, keys.getInt(1));
                }
            }
        }

        return entity;
    }

    public void update(T entity) throws Exception {
        List<Field> allUpdateFields = entity.getUpdateFields();
        List<Field> pkFields = entity.getPrimaryKeyFields();

        List<Field> setFields = new ArrayList<>();
        for (Field f : allUpdateFields) {
            if (!f.getAnnotation(Column.class).isPrimaryKey()) {
                setFields.add(f);
            }
        }

        StringBuilder sql = new StringBuilder("UPDATE " + entity.getTableName() + " SET ");
        for (int i = 0; i < setFields.size(); i++) {
            sql.append(setFields.get(i).getAnnotation(Column.class).name()).append("=?");
            if (i < setFields.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(" WHERE ");
        for (int i = 0; i < pkFields.size(); i++) {
            sql.append(pkFields.get(i).getAnnotation(Column.class).name()).append("=?");
            if (i < pkFields.size() - 1) {
                sql.append(" AND ");
            }
        }

        PreparedStatement ps = db.prepareStatement(sql.toString());
        int idx = setParameters(ps, setFields, entity, 1);
        setParameters(ps, pkFields, entity, idx);
        ps.executeUpdate();
    }

    public void delete(T entity) throws Exception {
        List<Field> pkFields = entity.getPrimaryKeyFields();

        StringBuilder sql = new StringBuilder("DELETE FROM " + entity.getTableName() + " WHERE ");
        for (int i = 0; i < pkFields.size(); i++) {
            sql.append(pkFields.get(i).getAnnotation(Column.class).name()).append("=?");
            if (i < pkFields.size() - 1) {
                sql.append(" AND ");
            }
        }

        PreparedStatement ps = db.prepareStatement(sql.toString());
        setParameters(ps, pkFields, entity, 1);
        ps.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll(T prototype) throws Exception {
        String sql = "SELECT * FROM " + prototype.getTableName();
        ResultSet rs = db.executeQuery(sql);
        List<T> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add((T) prototype.fromResultSet(rs));
        }
        return lista;
    }

    @SuppressWarnings("unchecked")
    public T getByPK(T prototype) throws Exception {
        List<Field> pkFields = prototype.getPrimaryKeyFields();

        StringBuilder sql = new StringBuilder("SELECT * FROM " + prototype.getTableName() + " WHERE ");
        for (int i = 0; i < pkFields.size(); i++) {
            sql.append(pkFields.get(i).getAnnotation(Column.class).name()).append("=?");
            if (i < pkFields.size() - 1) {
                sql.append(" AND ");
            }
        }

        PreparedStatement ps = db.prepareStatement(sql.toString());
        setParameters(ps, pkFields, prototype, 1);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return (T) prototype.fromResultSet(rs);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<T> getBy(T prototype, String columnName, Object value) throws Exception {
        String sql = "SELECT * FROM " + prototype.getTableName() + " WHERE " + columnName + "=?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setObject(1, toSqlValue(value));
        ResultSet rs = ps.executeQuery();
        List<T> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add((T) prototype.fromResultSet(rs));
        }
        return lista;
    }

    public List<T> getByFields(T prototype, Map<String, Object> fields) throws Exception {
        return getByCondition(prototype, fields, false);
    }

    public List<T> getByCondition(T prototype, Map<String, Object> criteria) throws Exception {
        return getByCondition(prototype, criteria, true);
    }

    @SuppressWarnings("unchecked")
    private List<T> getByCondition(T prototype, Map<String, Object> criteria, boolean useLike)
            throws Exception {
        StringBuilder sql = new StringBuilder("SELECT * FROM " + prototype.getTableName() + " WHERE 1=1");
        List<Object> params = new ArrayList<>();

        for (Map.Entry<String, Object> entry : criteria.entrySet()) {
            if (useLike) {
                sql.append(" AND ").append(entry.getKey()).append(" LIKE ?");
                params.add("%" + entry.getValue() + "%");
            } else {
                sql.append(" AND ").append(entry.getKey()).append("=?");
                params.add(toSqlValue(entry.getValue()));
            }
        }

        PreparedStatement ps = db.prepareStatement(sql.toString());
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }
        ResultSet rs = ps.executeQuery();

        List<T> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add((T) prototype.fromResultSet(rs));
        }
        return lista;
    }

    private int setParameters(PreparedStatement ps, List<Field> fields, T entity, int startIdx)
            throws Exception {
        int idx = startIdx;
        for (Field f : fields) {
            Object value = f.get(entity);
            ps.setObject(idx++, toSqlValue(value));
        }
        return idx;
    }

    private Object toSqlValue(Object value) {
        if (value instanceof java.util.Date && !(value instanceof java.sql.Date)) {
            return new Timestamp(((java.util.Date) value).getTime());
        }
        return value;
    }
}
