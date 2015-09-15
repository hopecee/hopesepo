/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.seam;

import java.io.Serializable;

/**
 *
 * @author hope
 */
public class SeamBean2 implements Serializable {

    private static final long serialVersionUID = 3723552335163650583L;
    

//@Transactional
    public String getWelcome() {
        return "Seam2 injected into Seam " ;
    }
}

