package com.exadel.studbase.service.filter;

import java.util.*;

/**
 * Created by ala'n on 07.08.2014.
 */

public class FilterDescription {
    public static List<FilterDescriptor> createFilterDescription(boolean isCurator,
                                                                 Map<Long, String> curators,
                                                                 Map<Long, String> skills) {
        List<FilterDescriptor> filterDescriptor = new ArrayList();
        filterDescriptor.add(new TextFilter("university", "University", "..."));
        filterDescriptor.add(new TextFilter("faculty", "Faculty", "..."));
        filterDescriptor.add(new EnumFilter("course", "Course", new Integer[]{1, 2, 3, 4, 5}));
        filterDescriptor.add(new NumberFilter("graduationYear", "Grad. year", 2000));
        filterDescriptor.add(new NumberFilter("workingHours", "Working hours", 0));
        filterDescriptor.add(new BoolFilter("billable", "Billable"));
        filterDescriptor.add(new ListFilter("skills", "Skill", skills, true));
        filterDescriptor.add(new ListFilter("englishLevel", "English level",
                new String[]{
                        "Beginner",
                        "Elementary",
                        "Pre-Intermediate",
                        "Intermediate",
                        "Upper-Intermediate",
                        "Advanced"}, false));
        if (!isCurator) {
            filterDescriptor.add(new ListFilter("curator", "Curator", curators, false));
        }
        return filterDescriptor;
    }

    public abstract static class FilterDescriptor {
        enum FilterType {
            number,
            text,
            bool,
            list,
            enumeration
        }

        public final String field;
        public final FilterType type;
        public String name;

        protected FilterDescriptor(String field, FilterType type, String name) {
            this.field = field;
            this.type = type;
            this.name = name;
        }
    }

    public static class NumberFilter extends FilterDescriptor {
        public Integer minVal;

        public NumberFilter(String field, String name, Integer minVal) {
            super(field, FilterType.number, name);
            this.minVal = minVal;
        }
    }

    public static class BoolFilter extends FilterDescriptor {
        public BoolFilter(String field, String name) {
            super(field, FilterType.bool, name);
        }
    }

    public static class TextFilter extends FilterDescriptor {
        public String placeholder;
        public Boolean multiset;

        public TextFilter(String field, String name, String placeholder) {
            this(field, name, placeholder, false);
        }
        public TextFilter(String field, String name, String placeholder, Boolean multiset) {
            super(field, FilterType.text, name);
            this.placeholder = placeholder;
            this.multiset = multiset;
        }
    }

    public static class ListFilter extends FilterDescriptor {
        public Boolean multiset;
        public Map<Long, String> values;

        public ListFilter(String field, String name, Map<Long, String> values, Boolean multiset) {
            super(field, FilterType.list, name);
            this.values = values;
            this.multiset = multiset;
        }

        public ListFilter(String field, String name, String[] values, Boolean multiset) {
            super(field, FilterType.list, name);
            this.multiset = multiset;
            this.values = new HashMap<Long, String>();
            for (int i = 0; i < values.length; i++) {
                this.values.put(Long.valueOf(i), values[i]);
            }
        }
    }

    public static class EnumFilter extends FilterDescriptor {
        public Boolean multiset;
        public Object[] values;

        public EnumFilter(String field, String name, Object[] minVal) {
           this(field, name, minVal, false);
        }

        public EnumFilter(String field, String name, Object[] minVal, Boolean multiset) {
            super(field, FilterType.enumeration, name);
            this.values = minVal;
            this.multiset = multiset;
        }

    }

}
