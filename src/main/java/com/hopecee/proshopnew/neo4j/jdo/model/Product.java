/**********************************************************************
Copyright (c) 2006 Andy Jefferson and others. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Contributors:
    ...
**********************************************************************/
package com.hopecee.proshopnew.neo4j.jdo.model;

import java.io.Serializable;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Definition of a Product
 * Represents a product, and contains the key aspects of the item.
 *
 * @version $Revision: 1.3 $  
 **/
//@Entity
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@PersistenceCapable
public class Product //implements Serializable 
{
    //private static final long serialVersionUID = -641184101548336645L;
    
     //private static final long serialVersionUID = 3123552335163652313L;

    /** Id for the product. */
    //@Id
    @PrimaryKey
    @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
     long id;

    /** Name of the Product. */
    //@Basic
     String name=null;

    /** Description of the Product. */
    //@Basic
    String description=null;

    /** Price of the Product. */
    //@Basic
    //@Column (name="THE_PRICE")
     double price=0.0;

    /**
     * Default constructor. 
     */
     //Product()
    //{
    //}

    /**
     * Constructor.
     * @param name name of product
     * @param description description of product
     * @param price Price
     **/
    public Product(String name, String description, double price)
    {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /** 
     * Accessor for the name of the product.
     * @return Name of the product.
     */
    public String getName()
    {
        return name;
    }

    /** 
     * Accessor for the description of the product.
     * @return Description of the product.
     */
    public String getDescription()
    {
        return description;
    }

    /** 
     * Accessor for the price of the product.
     * @return Price of the product.
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * Accessor for the id
     * @return The identity
     */
    public long getId()
    {
        return id;
    }

    /** 
     * Mutator for the name of the product.
     * @param name Name of the product.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Mutator for the description of the product.
     * @param description Description of the product.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Mutator for the price of the product.
     * @param price price of the product.
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return "Product : " + name + " [" + description + "]";
    }
}
