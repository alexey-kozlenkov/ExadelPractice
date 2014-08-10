package com.exadel.studbase.service.filter.impl;

import com.exadel.studbase.service.filter.Filter;

public abstract class SingleValueFilter<TYPE> implements Filter<TYPE> {
    private TYPE value;

    protected SingleValueFilter(TYPE value) {
        this.value = value;
    }

    public TYPE getValue() {
        return value;
    }
}
