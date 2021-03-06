package com.hopecee.proshopnew.jpa.model;

import java.io.Serializable;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jboss.seam.security.annotations.management.IdentityProperty;
import org.jboss.seam.security.annotations.management.PropertyType;

/**
 * Lookup table containing credential types
 *
 * @author Shane Bryzak
 */
@Entity
public class IdentityObjectCredentialType implements Serializable {
    private static final long serialVersionUID = 282711089697868242L;

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

    @IdentityProperty(PropertyType.NAME)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
