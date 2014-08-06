package com.exadel.studbase.dao.filter.impl;

import com.exadel.studbase.dao.filter.Filter;

/**
 * Created by Алексей on 05.08.2014.
 */
public class NumbersBetweenFilter implements Filter<Long> {

    private Long from;
    private Long to;

    public NumbersBetweenFilter(String value) {
        if(value == null) {
            return;
        }

        String[] values = value.split(";");
        if(values.length!=2) {
            return;
        }

        from = Long.parseLong(values[0]);
        to = Long.parseLong(values[1]);
    }

    @Override
    public Long getValue() {
        return from;
    }

    public Long getFromValue() {
        return from;
    }

    public Long getToValue() {
        return to;
    }
}
