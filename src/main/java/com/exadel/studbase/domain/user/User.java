package com.exadel.studbase.domain.user;

import com.exadel.studbase.domain.IEntity;
import com.exadel.studbase.domain.employee.Employee;
import com.exadel.studbase.domain.skills.SkillSet;
import com.exadel.studbase.domain.student.Student;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Алексей on 18.07.14.
 */

@Entity
@Table(name="\"USER\"")
public class User implements IEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="login")
    private String login;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role;

    @OneToOne
    @JoinColumn(name="id")
    private Student studentInfo;

    @OneToOne
    @JoinColumn(name="id")
    private Employee employeeInfo;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SkillSet> skills;

    public User() {
        password="pass";
        skills = new HashSet<SkillSet>();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Student getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(Student studentInfo) {
        this.studentInfo = studentInfo;
    }

    public Employee getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(Employee employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public Set<SkillSet> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillSet> skills) {
        this.skills = skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!login.equals(user.login)) return false;
        if (!password.equals(user.password)) return false;
        if (!role.equals(user.role)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id!=null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
