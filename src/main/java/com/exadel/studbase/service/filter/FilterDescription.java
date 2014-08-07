package com.exadel.studbase.service.filter;

import java.util.*;

/**
 * Created by ala'n on 07.08.2014.
 */

public class FilterDescription {
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
        public Boolean multiset;
        public String name;

        protected FilterDescriptor(String field, FilterType type, String name, Boolean multiset) {
            this.field = field;
            this.type = type;
            this.name = name;
            this.multiset = multiset;
        }
    }

    public static class NumberFilter extends FilterDescriptor {
        public Integer minVal;

        public NumberFilter(String field, String name, Integer minVal) {
            super(field, FilterType.number, name, false);
            this.minVal = minVal;
        }
    }

    public static class BoolFilter extends FilterDescriptor {
        public BoolFilter(String field, String name) {
            super(field, FilterType.bool, name, false);
        }
    }

    public static class TextFilter extends FilterDescriptor {
        public String placeholder;

        public TextFilter(String field, String name, String placeholder) {
            super(field, FilterType.text, name, false);
            this.placeholder = placeholder;
        }
    }

    public static class ListFilter extends FilterDescriptor {
        public Map<Long, String> values;

        public ListFilter(String field, String name, Map<Long, String> values) {
            super(field, FilterType.list, name, false);
            this.values = values;
        }

        public ListFilter(String field, String name, Map<Long, String> values, Boolean multiset) {
            super(field, FilterType.list, name, multiset);
            this.values = values;
        }

        public ListFilter(String field, String name, Collection<String> values, Boolean multiset) {
            super(field, FilterType.list, name, multiset);
            this.values = new HashMap<Long, String>();
            String[] value = (String[]) values.toArray();
            for (int i = 0; i < values.size(); i++) {
                this.values.put(Long.valueOf(i), value[i]);
            }
        }

        public static class EnumFilter extends FilterDescriptor {
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

        public List<FilterDescriptor> createFilterDescription(String role, Map<Long, String> curators, Map<Long, String> skills) {
            List<FilterDescriptor> filterDescriptor = new ArrayList();
            filterDescriptor.add(new TextFilter("university", "University", "..."));
            filterDescriptor.add(new TextFilter("faculty", "Faculty", "..."));
            filterDescriptor.add(new NumberFilter("cource", "Cource", 1));
            filterDescriptor.add(new NumberFilter("graduationYear", "Grad. year", 2000));
            filterDescriptor.add(new NumberFilter("workingHours", "Working hours", 0));
            filterDescriptor.add(new BoolFilter("billable", "Billable"));
            filterDescriptor.add(new ListFilter("skills", "Skill", skills, true));
            filterDescriptor.add(new EnumFilter("englishLevel", "English level",
                    new String[]{
                    "Begginer",
                    "Elementary",
                    "Pre-Intermediate",
                    "Intermediate",
                    "Upper-Intermediate",
                    "Advanced"}, true));
            if(!role.equals("ROLE_CURATOR")) {
                filterDescriptor.add(new ListFilter("curators", "Curator", curators, true));
            }
            return filterDescriptor;
        }
    }
}
