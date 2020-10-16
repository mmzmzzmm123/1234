package com.ruoyi.common.utils;

import com.ruoyi.common.annotation.FieldMapping;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
/**
 * @author : yelifeng
 * @date : 2020/10/16 0016 16:39 xuc5566@gmail.com
 */
public class SlicePage <T> implements Pageable {

    private static final int ORDER_BY_LIMIT_LENGTH = 2;

    private int offset = 0;
    private int limit = 20;

    /**
     * support return all
     */
    private boolean supportUnlimited = false;
    private Sort sort;
    private boolean count = false;

    private Class<T> entityClass;

    private static Cache<Pair<Class, String>, String> fieldNameCache = CacheBuilder.newBuilder()
            .expireAfterWrite(20, TimeUnit.MINUTES).build();
    private static final Converter<String, String> CONVERTER = CaseFormat.LOWER_UNDERSCORE
            .converterTo(CaseFormat.LOWER_CAMEL);

    public SlicePage(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public SlicePage<T> parseOrderby(String orderby) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(orderby)) {
            String[] sortArray = orderby.split("\\s+and\\s+");
            List<Sort.Order> orders = Lists.newArrayListWithCapacity(sortArray.length);
            for (String sort : sortArray) {
                orders.add(this.resolveOrderby(sort));
            }
            this.sort = Sort.by(orders);
        }
        return this;
    }

    public SlicePage<T> addSort(Sort sort) {
        if (sort == null) {
            return this;
        }
        if (this.sort == null) {
            this.sort = sort;
        } else {
            this.sort.and(sort);
        }
        return this;
    }

    public SlicePage<T> addDefaultSort(Sort sort) {
        if (sort != null && this.sort != null) {
            this.sort = sort;
        }
        return this;
    }

    private Sort.Order resolveOrderby(String order) {
        String[] sortValues = order.split("\\s+");
        String sortField = "NO_FIELD";
        Sort.Direction direction = Sort.Direction.DESC;
        if (sortValues.length == ORDER_BY_LIMIT_LENGTH) {
            sortField = sortValues[0];
            Optional<Sort.Direction> dirOption =  Sort.Direction.fromOptionalString(sortValues[1]);
            if (dirOption.isPresent()) {
                direction = dirOption.get();
            }
        }
        return new Sort.Order(direction, this.getFieldName(entityClass, sortField, sortField));
    }

    protected String getFieldName(Class entityClass, String fieldName, String defaultName) {
        try {
            Pair<Class, String> pair = Pair.of(entityClass, fieldName);
            return fieldNameCache.get(pair, new FieldNameLoader(pair, defaultName));
        } catch (Exception ex) {
            // TODO logger
            return defaultName;
        }
    }

    class FieldNameLoader implements Callable<String> {

        private Pair<Class, String> pair;
        private String defaultName;

        public FieldNameLoader(Pair<Class, String> pair, String defaultName) {
            this.pair = pair;
            this.defaultName = defaultName;
        }

        @Override
        public String call() throws Exception {
            return invokeFieldName(pair, defaultName);
        }
    }

    protected String invokeFieldName(Pair<Class, String> pair, String defaultName) {
        String fieldName = pair.getRight();
        if (org.apache.commons.lang3.StringUtils.isBlank(fieldName)) {
            return defaultName;
        }

        fieldName = CONVERTER.convert(fieldName);
        Class clazz = pair.getLeft();

        String mappingFieldName = this.getMappingFieldName(clazz, fieldName);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(mappingFieldName)) {
            return mappingFieldName;
        }

        Field field = FieldUtils.getField(clazz, fieldName, true);
        if (field != null) {
            return fieldName;
        }

        return defaultName;
    }

    public String getMappingFieldName(Class clazz, String fieldName) {
        FieldMapping fieldsMapping = (FieldMapping) clazz.getAnnotation(FieldMapping.class);
        if (fieldsMapping == null
                || ArrayUtils.isEmpty(fieldsMapping.fields())
                || ArrayUtils.isEmpty(fieldsMapping.mapping())) {
            return null;
        }
        String[] fields = fieldsMapping.fields();
        int length = Math.min(fields.length, fieldsMapping.mapping().length);
        for (int i = 0; i < length; i++) {
            if (fieldName.equals(fields[i])) {
                return fieldsMapping.mapping()[i];
            }
        }
        return "";
    }


    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit < -1 || limit > 100) {
            return;
        }
        this.limit = limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        if (offset < 0) {
            return;
        }
        this.offset = offset;
    }

    public boolean isSupportUnlimited() {
        return supportUnlimited;
    }

    public void setSupportUnlimited(boolean supportUnlimited) {
        this.supportUnlimited = supportUnlimited;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public boolean isCount() {
        return count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return SlicePage.create()
                .count(this.count)
                .offset(offset + limit)
                .limit(limit)
                .sort(sort)
                .build(entityClass);
    }

    @Override
    public Pageable previousOrFirst() {
        if (offset < limit) {
            return first();
        }

        return SlicePage.create()
                .count(this.count)
                .offset(offset - limit)
                .limit(limit)
                .sort(sort)
                .build(entityClass);
    }

    @Override
    public Pageable first() {
        return SlicePage.create()
                .count(this.count)
                .offset(0)
                .limit(limit)
                .sort(sort)
                .build(entityClass);
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

    public static SlicePage.Builder create() {
        return new SlicePage.Builder();
    }

    public static class Builder<T> {

        private int _offset = 0;
        private int _limit = 20;

        private boolean _supportUnlimited = false;
        private Sort _sort;
        private boolean _count = false;

        private String _orderBy;

        public SlicePage<T> build(Class<T> entityClass) {
            SlicePage<T> entity = new SlicePage<>(entityClass);
            entity.setOffset(_offset);
            entity.setLimit(_limit);
            entity.setSupportUnlimited(_supportUnlimited);
            entity.setCount(_count);

            if (StringUtils.isNotBlank(_orderBy)) {
                entity.parseOrderby(_orderBy);
            }
            if (_sort != null) {
                entity.setSort(_sort);
            }

            return entity;
        }
        public Builder offset(int offset) {
            this._offset = offset;
            return this;
        }
        public Builder limit(int limit) {
            this._limit = limit;
            return this;
        }
        public Builder count(boolean count) {
            this._count = count;
            return this;
        }
        public Builder supportUnlimited(boolean supportUnlimited) {
            this._supportUnlimited = supportUnlimited;
            return this;
        }
        public Builder sort(Sort sort) {
            this._sort = sort;
            return this;
        }
        public Builder orderBy(String orderBy) {
            this._orderBy = orderBy;
            return this;
        }
    }
}

