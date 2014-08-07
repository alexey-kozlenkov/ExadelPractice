package com.exadel.studbase.service.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ala'n on 07.08.2014.
 */

public class FilterDescription {

    public abstract static class FilterDescriptor{
        enum FilterType{
            number,
            text,
            bool,
            list,
            enumeration
        }
        public final String field;
        public final FilterType type;
        public Boolean multiset;
        public String name;

        protected FilterDescriptor(String field, FilterType type, String name, Boolean multiset) {
            this.field = field;
            this.type = type;
            this.name = name;
            this.multiset = multiset;
        }
    }

    public static class NumberFilter extends FilterDescriptor{
        public Integer minVal;
        public NumberFilter(String field, String name, Integer minVal) {
            super(field, FilterType.number, name, false);
            this.minVal = minVal;
        }
    }
    public static class BoolFilter extends FilterDescriptor{
        public BoolFilter(String field, String name) {
            super(field, FilterType.bool, name, false);
        }
    }
    public static class TextFilter extends FilterDescriptor{
        public String placeholder;
        public TextFilter(String field, String name, String placeholder) {
            super(field, FilterType.text, name, false);
            this.placeholder = placeholder;
        }
        public TextFilter(String field, String name, String placeholder, Boolean multiset) {
            super(field, FilterType.text, name, multiset);
            this.placeholder = placeholder;
        }
    }
    public static class ListFilter extends FilterDescriptor{
        public Map<Long, String> values;
        public ListFilter(String field, String name, Map<Long, String> values) {
            super(field, FilterType.list, name, false);
            this.values = values;
        }
        public ListFilter(String field, String name, Map<Long, String> values, Boolean multiset) {
            super(field, FilterType.list, name, multiset);
            this.values = values;
        }
        public ListFilter(String field, String name, String[] values, Boolean multiset) {
            super(field, FilterType.list, name, multiset);
            this.values = new HashMap<Long, String>();
            for( int i=0; i<values.length ; i++)
                this.values.put(Long.valueOf(i), values[i]);
        }
    }
    public static class EnumFilter extends FilterDescriptor{
        public Object[] values;
        public EnumFilter(String field, String name, Object[] minVal) {
            super(field, FilterType.enumeration, name, false);
            this.values = minVal;
        }
        public EnumFilter(String field, String name, Object[] minVal, Boolean multiset) {
            super(field, FilterType.enumeration, name, multiset);
            this.values = minVal;
        }
    }

    public static final FilterDescriptor[] FILTER_DESC = {
        new BoolFilter("billable", "Billable"),
        new NumberFilter("age", "Age", 16),
        new NumberFilter("workingHours", "Working hours", 0),
        new ListFilter("english", "English", new String[]{
                "Begginer",
                "Elementary",
                "Pre-Intermediate",
                "Intermediate",
                "Upper-Intermediate",
                "Advanced"}
                , false)
    };

}
