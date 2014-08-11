package com.exadel.studbase.domain.impl;

import com.exadel.studbase.domain.IEntity;

import javax.persistence.*;

@Entity
@Table(name = "\"SKILL_SET\"")
public class SkillSet implements IEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "skill_type_id")
    private Long skillTypeId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "level")
    private Integer level;

    @Column(name = "info")
    private String info;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkillTypeId() {
        return skillTypeId;
    }

    public void setSkillType(Long skillTypeid) {
        this.skillTypeId = skillTypeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUser(Long userId) {
        this.userId = userId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
