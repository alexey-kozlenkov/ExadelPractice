package com.exadel.studbase.domain.impl;

import com.exadel.studbase.domain.IEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Алексей on 24.07.14.
 */
@Entity
@Table(name = "\"SKILL_TYPE\"")
public class SkillType implements IEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "skillType", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SkillSet> skillSets;


    public SkillType() {
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

    public Set<SkillSet> getSkillSets() {
        return skillSets;


    }

    public void setSkillSets(Set<SkillSet> skillSets) {
        this.skillSets = skillSets;
    }
}
