package com.acxiom.microservice.json;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import com.acxiom.microservice.commons.Immutable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@Immutable
public class ValueObjectList<T extends ValueObject> extends ValueObject {

    static final ValueObjectList<ValueObject> EMPTY = ValueObjectList.newBuilder()
            .setData(Collections.<ValueObject>emptyList())
            .setTotal(0L)
            .build();

    @JsonProperty("total")
    private final long total;

    @JsonProperty("data")
    private final List<T> data;

    private final int hashCode;

    @JsonCreator
    public ValueObjectList(@JsonProperty("total") long total, @JsonProperty("data") List<T> data) {
        this.total = total;
        this.data = ImmutableList.copyOf(data);

        this.hashCode = Objects.hash(total, data);
    }

    private ValueObjectList(Builder<T> builder) {
        this(builder.total, builder.data);
    }

    public static <U extends ValueObject> Builder<U> newBuilder() {
        return new Builder<U>();
    }

    @SuppressWarnings("unchecked")
    public static <U extends ValueObject> ValueObjectList<U> empty() {
        return (ValueObjectList<U>) EMPTY;
    }

    public static final class Builder<U extends ValueObject> {

        private List<U> data = Lists.newArrayList();
        private long total;

        private Builder() {
        }

        public Builder<U> setData(List<U> data) {
            this.data = Lists.newArrayList(data);
            return this;
        }

        public Builder<U> addData(U valueObject) {
            this.data.add(valueObject);
            return this;
        }

        public Builder<U> setTotal(long total) {
            this.total = total;
            return this;
        }

        public Builder<U> copy(ValueObjectList<U> valueObjectList) {
            return this.setData(valueObjectList.getData())
                    .setTotal(valueObjectList.getTotal());
        }

        public ValueObjectList<U> build() {
            return new ValueObjectList<>(this);
        }

    }

    public long getTotal() {
        return total;
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && obj instanceof ValueObjectList) {
            ValueObjectList<?> other = (ValueObjectList<?>) obj;
            return Objects.equals(this.total, other.total) && Objects.deepEquals(this.data, other.data);
        } else {
            return false;
        }
    }
}
