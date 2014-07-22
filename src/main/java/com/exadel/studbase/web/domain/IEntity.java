package com.exadel.studbase.web.domain;

import java.io.Serializable;

/**
 * Created by Алексей on 18.07.14.
 */
public interface IEntity <ID extends Serializable> extends Serializable {
    ID getId();

    void setId(ID id);
}
