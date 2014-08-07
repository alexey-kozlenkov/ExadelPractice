package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IStudentViewDAO;
import com.exadel.studbase.domain.impl.StudentView;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by ala'n on 29.07.2014.
 */
@Repository
public class StudentViewDAO extends GenericDAOImpl<StudentView, StudentView, Long> implements IStudentViewDAO {
    @Override
    public Collection<StudentView> getViewByStudentName(String desiredName) {
        Query query = getSession()
                .createSQLQuery("SELECT a.* FROM find_student_by_name('" + desiredName + "' ) as a")
                .addEntity("a", StudentView.class);
        return query.list();
    }

    @Override
    public Collection<StudentView> filterBySkillTypeId(Long[] ids) {
        String idsString = "";
        for (int i = 0; i < ids.length - 1; i++) {
            idsString += ids[i] + ",";
        }
        idsString += ids[ids.length - 1];
        Query query = getSession().createSQLQuery(
                "SELECT * FROM \"STUDENT_VIEW\" AS STV INNER JOIN " +
                        "(SELECT user_id FROM \"SKILL_SET\" WHERE skill_type_id IN (" + idsString + ") GROUP BY user_id " +
                        "HAVING COUNT(*)=" + ids.length + ") AS skillFilter ON STV.id = skillFilter.user_id")
                .addEntity("STV", StudentView.class);
        return query.list();
    }
}
