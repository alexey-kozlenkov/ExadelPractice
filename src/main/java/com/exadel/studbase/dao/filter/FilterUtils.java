package com.exadel.studbase.dao.filter;

import com.exadel.studbase.dao.filter.impl.EqualsFilter;
import com.exadel.studbase.dao.filter.impl.GreaterEqualsFilter;
import com.exadel.studbase.dao.filter.impl.IsNotNullFilter;
import com.exadel.studbase.dao.filter.impl.NumbersBetweenFilter;
import com.exadel.studbase.domain.impl.StudentView;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.Map;

/**
 * Created by Алексей on 05.08.2014.
 */
public class FilterUtils {
    public static <T> Criterion buildFilterCriterion(Map<String, Filter<T>> filters) {
        if (filters == null || filters.isEmpty()) {
            return null;
        }

        Criterion root = null;
        for (Map.Entry<String, Filter<T>> e : filters.entrySet()) {
            String propertyName = e.getKey();
            Filter filterType = e.getValue();
            Object value = filterType.getValue();
            Criterion current;

            if (value == null) {
                current = Restrictions.isNull(propertyName);
            } else if (filterType instanceof EqualsFilter) {
                current = Restrictions.eq(propertyName, value);
            } else if (filterType instanceof GreaterEqualsFilter) {
                current = Restrictions.ge(propertyName, value);
            } else if (filterType instanceof NumbersBetweenFilter) {
                NumbersBetweenFilter bf = (NumbersBetweenFilter) filterType;
                current = Restrictions.between(propertyName, bf.getFromValue(), bf.getToValue());
            } else if (filterType instanceof IsNotNullFilter) {
                current = Restrictions.isNotNull(propertyName);
            } else {
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

    public static void buildFilterToSpecification(Map<String, Filter<StudentView>> filters, Map<String, String[]> params) {
        for (Map.Entry<String, String[]> parameter : params.entrySet()) {
            String paramName = parameter.getKey();
            String[] paramValues = parameter.getValue();

            if (paramName.equalsIgnoreCase("university")) {
                Filter filter = new EqualsFilter(paramValues[0]);
                filters.put("university", filter);
            } else if (paramName.equalsIgnoreCase("faculty")) {
                Filter filter = new EqualsFilter(paramValues[0]);
                filters.put("faculty", filter);
            } else if (paramName.equalsIgnoreCase("course")) {
                Filter filter = new EqualsFilter(paramValues[0]);
                filters.put("course", filter);
            } else if (paramName.equalsIgnoreCase("graduation year")) {
                Filter filter = new EqualsFilter(Integer.valueOf(paramValues[0]));
                filters.put("graduationDate", filter);
            } else if (paramName.equalsIgnoreCase("working hours")) {
                Filter filter = new EqualsFilter(Integer.valueOf(paramValues[0]));
                filters.put("workingHours", filter);
            } else if (paramName.equalsIgnoreCase("billable")) {
                Filter filter = new IsNotNullFilter(paramValues[0]);
                filters.put("billable", filter);
            }
        }
    }
}
