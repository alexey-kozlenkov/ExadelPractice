package com.exadel.studbase.service.filter;

import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.service.filter.impl.EqualsFilter;
import com.exadel.studbase.service.filter.impl.GreaterEqualsFilter;
import com.exadel.studbase.service.filter.impl.IsNotNullFilter;
import com.exadel.studbase.service.filter.impl.IsNullFilter;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.Map;

public class FilterUtils {
    public static <T> Criterion buildFilterCriterion(Map<String, Filter<T>> filters) {
        if (filters == null || filters.isEmpty()) {
            return null;
        }

        Criterion root = null;
        for (Map.Entry<String, Filter<T>> e : filters.entrySet()) {
            String propertyName = e.getKey();
            Filter<T> filterType = e.getValue();
            T value = filterType.getValue();
            Criterion current;

            if (value == null) {
                current = Restrictions.isNull(propertyName);
            } else if (filterType instanceof EqualsFilter) {
                current = Restrictions.eq(propertyName, value);
            } else if (filterType instanceof GreaterEqualsFilter) {
                current = Restrictions.ge(propertyName, value);
            } else if (filterType instanceof IsNotNullFilter) {
                current = Restrictions.isNotNull(propertyName);
            } else if (filterType instanceof IsNullFilter) {
                current = Restrictions.isNull(propertyName);
            }
            else {
                throw new RuntimeException("Unexpected filter class: " + filterType.getClass().getName());
            }

            if (root == null) {
                root = current;
            } else {
                root = Restrictions.and(root, current);
            }
        }
        return root;
    }

    public static void buildFilterToSpecification(Map<String, Filter<StudentView>> filters, Map<String, Object> params) {
        for (Map.Entry<String, Object> parameter : params.entrySet()) {
            String paramName = parameter.getKey();
            Object paramValue = parameter.getValue();

            if (paramName.equalsIgnoreCase("university")) {
                Filter filter = new EqualsFilter(paramValue);
                filters.put("university", filter);
            } else if (paramName.equalsIgnoreCase("faculty")) {
                Filter filter = new EqualsFilter(paramValue);
                filters.put("faculty", filter);
            } else if (paramName.equalsIgnoreCase("course")) {
                Filter filter = new EqualsFilter(Integer.valueOf((String) paramValue));
                filters.put("course", filter);
            } else if (paramName.equalsIgnoreCase("graduationYear")) {
                Filter filter = new EqualsFilter(Integer.valueOf((String) paramValue));
                filters.put("graduationDate", filter);
            } else if (paramName.equalsIgnoreCase("workingHours")) {
                Filter filter = new GreaterEqualsFilter(Integer.valueOf((String) paramValue));
                filters.put("workingHours", filter);
            } else if (paramName.equalsIgnoreCase("billable")) {
                Boolean isBillable = Boolean.valueOf((String) paramValue);
                Filter filter = isBillable ?  new IsNotNullFilter(paramValue) : new IsNullFilter(paramValue);
                filters.put("billable", filter);
            } else if (paramName.equalsIgnoreCase("englishLevel")) {
                Filter filter = new GreaterEqualsFilter(Integer.valueOf((String)paramValue));
                filters.put("englishLevel", filter);
            }
        }
    }
}
