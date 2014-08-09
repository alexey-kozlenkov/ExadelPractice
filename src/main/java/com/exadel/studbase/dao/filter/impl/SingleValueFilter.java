package com.exadel.studbase.dao.filter.impl;

import com.exadel.studbase.dao.filter.Filter;

/**
 * Created by Алексей on 05.08.2014.
 */
public abstract class SingleValueFilter<TYPE> implements Filter<TYPE> {
    private TYPE value;

    protected SingleValueFilter(TYPE value) {
        this.value = value;
    }

    public TYPE getValue() {
        return value;
    }
}
