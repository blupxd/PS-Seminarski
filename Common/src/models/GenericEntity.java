package models;

import annotations.Column;
import annotations.Table;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public abstract class GenericEntity implements Serializable {

 
    public String getTableName() {
        Class<?> cls = this.getClass();
        while (cls != null && cls != Object.class) {
            if (cls.isAnnotationPresent(Table.class)) {
                return cls.getAnnotation(Table.class).name();
            }
            cls = cls.getSuperclass();
        }
        throw new RuntimeException("@Table anotacija nije pronadjena na " + this.getClass().getSimpleName());
    }
    public List<Field> getAllFields() {
        List<Field> result = new ArrayList<>();
        Class<?> cls = this.getClass();
        while (cls != null && cls != Object.class) {
            for (Field f : cls.getDeclaredFields()) {
                if (f.isAnnotationPresent(Column.class)) {
                    f.setAccessible(true);
                    result.add(f);
                }
            }
            Class<?> parent = cls.getSuperclass();
            if (parent != null && parent != Object.class && parent.isAnnotationPresent(Table.class)) {
                break;
            }
            cls = parent;
        }
        return result;
    }
    public List<Field> getInsertFields() {
        List<Field> result = new ArrayList<>();
        for (Field f : getAllFields()) {
            Column col = f.getAnnotation(Column.class);
            if (!col.isAutoIncrement()) {
                result.add(f);
            }
        }
        return result;
    }
    public List<Field> getUpdateFields() {
        return getInsertFields();
    }
    public List<Field> getPrimaryKeyFields() {
        List<Field> result = new ArrayList<>();
        for (Field f : getAllFields()) {
            Column col = f.getAnnotation(Column.class);
            if (col.isPrimaryKey()) {
                result.add(f);
            }
        }
        return result;
    }
    public abstract GenericEntity fromResultSet(ResultSet rs) throws Exception;
}
