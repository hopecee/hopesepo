package com.hopecee.proshopnew.jpa.model;

//import static org.jboss.seam.security.annotations.management.EntityType.IDENTITY_ROLE_NAME;

import java.io.Serializable;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.jboss.seam.security.annotations.management.EntityType;

import org.jboss.seam.security.annotations.management.IdentityEntity;

/**
 * This is a simple lookup table containing role names
 *
 * @author Shane Bryzak
 */
@IdentityEntity(EntityType.IDENTITY_ROLE_NAME)
@Entity
public class IdentityRoleName implements Serializable {
    private static final long serialVersionUID = 8775236263787825703L;

    @Id
    @GeneratedValue
    private Long id;
     @Column(unique = true, nullable = false)
    private String name;

        public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
