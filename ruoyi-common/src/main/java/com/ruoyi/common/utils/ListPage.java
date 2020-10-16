package com.ruoyi.common.utils;

import com.fasterxml.jackson.databind.type.ArrayType;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.ruoyi.common.annotation.FieldMapping;
import com.ruoyi.common.enums.Operator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Sort;

public class ListPage<T> extends SlicePage<T> {

    private static final int FILTER_LIMIT_LENGTH = 3;

    private static Cache<Pair<Class, String>, Class> fieldTypeCache = CacheBuilder.newBuilder()
            .expireAfterWrite(12, TimeUnit.HOURS).build();

    private List<Condition> conditions = Lists.newArrayList();

    public ListPage(Class<T> entityClass) {
        super(entityClass);
    }

    public ListPage<T> parseConditions(String filter) {
        if (StringUtils.isNotBlank(filter)) {
            String[] conditionArray = filter.split("\\s+and\\s+");
            for (String condition : conditionArray) {
                try {
                    conditions.add(this.resolveCondition(condition));
                } catch (Exception ex) {
                    // TODO
                }
            }
        }

        return this;
    }

    public int getConditionSize() {
        if (CollectionUtils.isEmpty(conditions)) {
            return 0;
        } else {
            return conditions.size();
        }
    }

    public ListPage<T> addCondition(Condition condition) {
        if (CollectionUtils.isEmpty(conditions)) {
            this.conditions = Lists.newArrayList();
        }
        this.conditions.add(condition);
        return this;
    }

    public ListPage<T> replaceCondition(Condition condition) {
        if (CollectionUtils.isEmpty(conditions)) {
            this.conditions = Lists.newArrayList();
            this.conditions.add(condition);
        } else {
            removeCondition(condition);
            this.conditions.add(condition);
        }
        return this;
    }

    public ListPage<T> removeCondition(String field) {
        if (CollectionUtils.isNotEmpty(conditions)) {
            Iterator<Condition> it = conditions.iterator();
            while (it.hasNext()) {
                Condition itEntity = it.next();
                if (StringUtils.equalsIgnoreCase(field,
                        itEntity.getField())) {
                    it.remove();
                }
            }
        }
        return this;
    }

    public ListPage<T> removeCondition(Condition condition) {
        this.removeCondition(condition.getField(), condition.getOperator());
        return this;
    }

    public ListPage<T> removeCondition(String field, Operator operator) {
        if (CollectionUtils.isNotEmpty(conditions)) {
            Iterator<Condition> iterator = conditions.iterator();
            while (iterator.hasNext()) {
                Condition itEntity = iterator.next();
                if (itEntity.getField().equals(field)
                        && itEntity.getOperator().equals(operator)) {
                    iterator.remove();
                }
            }
        }
        return this;
    }

    private Condition resolveCondition(String conditionStr) throws IOException {
        String fieldName = "NO_FIELD";
        Operator operator = Operator.EQ;
        Object value = "";

        String[] filterValues = StringUtils.split(conditionStr, " ", 3);
        if (filterValues.length == FILTER_LIMIT_LENGTH) {
            fieldName = this.getFieldName(this.getEntityClass(), filterValues[0], fieldName);

            if (Operator.isValidOperator(filterValues[1].toUpperCase())) {
                operator = Operator.valueOf(filterValues[1].toUpperCase());
            }

            Class fieldType = this.getFieldType(this.getEntityClass(), fieldName);
            if (fieldType != null) {
                value = this.getValue(operator, filterValues[2], fieldType);
            }
        }

        return Condition.eq(fieldName, value);
    }

    private Object getValue(Operator operator, String filterValue, Class fieldType)
            throws IOException {
        if (Operator.LIKE == operator) {
            return StringUtils.strip(filterValue, "'");
        }

        if (this.isMustQuoteType(fieldType)) {
            filterValue = StringUtils.strip(filterValue, "'\"");
            filterValue = '"' + filterValue + '"';
        }

        if (Operator.IN == operator || Operator.NIN == operator) {
            return this.getListValue(filterValue, fieldType);
        }

        return JsonMapper.getMapper().readValue(filterValue, fieldType);
    }

    private Object getListValue(String filterValue, Class fieldType) throws IOException {
        if (this.isMustQuoteType(fieldType)) {
            filterValue = StringUtils.replace(filterValue, ",", "\",\"");
        }
        filterValue = '[' + filterValue + ']';
        if (fieldType.isArray()) {
            return JsonMapper.getMapper().readValue(filterValue, fieldType);
        } else {
            ArrayType fieldArrayType = JsonMapper.getMapper().getTypeFactory().constructArrayType(fieldType);
            return JsonMapper.getMapper().readValue(filterValue, fieldArrayType);
        }
    }

    protected Class getFieldType(Class entityClass, String fieldName) {
        try {
            Pair<Class, String> pair = Pair.of(entityClass, fieldName);
            return fieldTypeCache.get(pair, new FieldTypeLoader(pair));
        } catch (Exception ex) {
            // TODO logger
            return null;
        }
    }

    class FieldTypeLoader implements Callable<Class> {

        private Pair<Class, String> pair;

        public FieldTypeLoader(Pair<Class, String> pair) {
            this.pair = pair;
        }

        @Override
        public Class call() {
            return invokeFieldType(pair);
        }
    }

    protected Class invokeFieldType(Pair<Class, String> pair) {
        Class clazz = pair.getLeft();
        String fieldName = pair.getRight();
        Class result;
        result = this.getMappingFieldType(clazz, fieldName);
        if (null == result) {
            Field field = FieldUtils.getField(clazz, fieldName, true);
            if (null != field) {
                result = field.getType();
            }
        }
        if (null != result) {
            if ("id".equals(fieldName) && Serializable.class.equals(result)) {
                return getGenericIdType(clazz);
            }
            return primitive2Box(result);
        }

        return null;
    }

    private Class getMappingFieldType(Class clazz, String fieldName) {
        FieldMapping fieldsMapping = (FieldMapping) clazz.getAnnotation(FieldMapping.class);
        if (fieldsMapping == null
                || fieldsMapping.types().length == 0
                || fieldsMapping.mapping().length == 0) {
            return null;
        }
        String[] mappings = fieldsMapping.mapping();
        int length = Math.min(mappings.length, fieldsMapping.types().length);
        for (int i = 0; i < length; i++) {
            if (fieldName.equals(mappings[i])) {
                return fieldsMapping.types()[i];
            }
        }
        return null;
    }

    private Class getGenericIdType(Class clazz) {
        do {
            Type[] genericParameters = ReflectUtils.getGenericParameter(clazz);
            if (genericParameters.length > 0) {
                return (Class) genericParameters[0];
            }
            clazz = clazz.getSuperclass();
        } while (Object.class != clazz);

        // 字段类型无法推断
        return null;
    }

    private Class<?> primitive2Box(Class fieldType) {
        if (fieldType.equals(Long.TYPE)) {
            return Long.class;
        }
        if (fieldType.equals(Integer.TYPE)) {
            return Integer.class;
        }
        if (fieldType.equals(Boolean.TYPE)) {
            return Boolean.class;
        }
        if (fieldType.equals(Byte.TYPE)) {
            return Byte.class;
        }
        if (fieldType.equals(Double.TYPE)) {
            return Double.class;
        }
        if (fieldType.equals(Float.TYPE)) {
            return Float.class;
        }
        return fieldType;
    }

    private boolean isMustQuoteType(Class fieldType) {
        return Boolean.class != fieldType && !Number.class.isAssignableFrom(fieldType);
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public static ListPage.Builder create() {
        return new Builder();
    }

    public static class Builder<T> extends SlicePage.Builder<T> {

        private int _offset = 0;
        private int _limit = 20;

        private boolean _supportUnlimited = false;
        private boolean _count = false;

        private Sort _sort;
        private String _orderBy;

        private String _filter;

        @Override
        public ListPage<T> build(Class<T> entityClass) {
            ListPage<T> entity = new ListPage<>(entityClass);
            entity.setCount(_count);
            entity.setOffset(_offset);
            entity.setLimit(_limit);
            entity.setSupportUnlimited(_supportUnlimited);

            if (_sort != null) {
                entity.setSort(_sort);
            }
            if (StringUtils.isNotBlank(_orderBy)) {
                entity.parseOrderby(_orderBy);
            }
            if (StringUtils.isNotBlank(_filter)) {
                entity.parseConditions(_filter);
            }

            return entity;
        }

        @Override
        public Builder offset(int offset) {
            this._offset = offset;
            return this;
        }
        @Override
        public Builder limit(int limit) {
            this._limit = limit;
            return this;
        }
        @Override
        public Builder count(boolean count) {
            this._count = count;
            return this;
        }
        @Override
        public Builder supportUnlimited(boolean supportUnlimited) {
            this._supportUnlimited = supportUnlimited;
            return this;
        }
        @Override
        public Builder sort(Sort sort) {
            this._sort = sort;
            return this;
        }
        @Override
        public Builder orderBy(String orderBy) {
            this._orderBy = orderBy;
            return this;
        }
        public Builder filter(String filter) {
            this._filter = filter;
            return this;
        }
    }
}
