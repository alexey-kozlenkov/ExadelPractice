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

    @ManyToOne
    @JoinColumn(name = "skill_type_id")
    private SkillType skillTypeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "level")
    private Integer level;

    @Column(name = "info")
    private String info;

    public SkillSet() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public SkillType getSkillTypeId() {
        return skillTypeId;
    }

    public void setSkillTypeId(SkillType skillTypeId) {
        this.skillTypeId = skillTypeId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public void setLevel(Integer level) {
        this.level = level;
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
