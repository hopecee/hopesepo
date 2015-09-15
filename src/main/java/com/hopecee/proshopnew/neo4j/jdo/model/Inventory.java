/**********************************************************************
Copyright (c) 2011 Andy Jefferson and others. All rights reserved.
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
import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedAttributeNode;
import javax.persistence.OneToMany;

/**
 * Definition of an Inventory of products.
 */
//@Entity
//@NamedEntityGraph(name="allProps", 
   // attributeNodes={@NamedAttributeNode("name"), @NamedAttributeNode("products")})
@PersistenceCapable
public class Inventory //implements Serializable
{
    //private static final long serialVersionUID = -7047363433708052121L;
   
    
    //private static final long serialVersionUID = -7047363433708052121L;
    //@Id
    @PrimaryKey
     String name=null;

    //@OneToMany(cascade={CascadeType.PERSIST//, CascadeType.MERGE, CascadeType.DETACH
   // })
     Set<Product> products = new HashSet();

    public Inventory() {
    }

    

    public Inventory(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public Set<Product> getProducts()
    {
        return products;
    }

    @Override
    public String toString()
    {
        return "Inventory : " + name;
    }
}
