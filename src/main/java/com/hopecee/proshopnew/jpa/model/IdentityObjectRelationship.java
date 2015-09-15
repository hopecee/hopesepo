package com.hopecee.proshopnew.jpa.model;

//import static org.jboss.seam.security.annotations.management.EntityType.IDENTITY_RELATIONSHIP;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.jboss.seam.security.annotations.management.EntityType;

import org.jboss.seam.security.annotations.management.IdentityEntity;
import org.jboss.seam.security.annotations.management.IdentityProperty;
import org.jboss.seam.security.annotations.management.PropertyType;

/**
 * Contains relationships between identities
 *
 * @author Shane Bryzak
 */
@IdentityEntity(EntityType.IDENTITY_RELATIONSHIP)
@Entity
public class IdentityObjectRelationship implements Serializable {
    private static final long serialVersionUID = -5254503795105571898L;
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "RELATIONSHIP_TYPE_ID")
    private IdentityObjectRelationshipType relationshipType;
    @ManyToOne
    @JoinColumn(name = "FROM_IDENTITY_ID")
    private IdentityObject from;
    @ManyToOne
    @JoinColumn(name = "TO_IDENTITY_ID")
    private IdentityObject to;

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

    @IdentityProperty(PropertyType.TYPE)
    public IdentityObjectRelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(IdentityObjectRelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    @IdentityProperty(PropertyType.RELATIONSHIP_FROM)
    public IdentityObject getFrom() {
        return from;
    }

    public void setFrom(IdentityObject from) {
        this.from = from;
    }

    @IdentityProperty(PropertyType.RELATIONSHIP_TO)
    public IdentityObject getTo() {
        return to;
    }

    public void setTo(IdentityObject to) {
        this.to = to;
    }
}
