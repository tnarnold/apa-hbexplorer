/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.radaction.main;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author tiago
 */
public class HeartBleedTest {
    private HeartBleed hb;
    
    public HeartBleedTest() {
    }
    
    @Before
    public void setUp() {
        hb=new HeartBleed();
    }
    
    @After
    public void tearDown() {
        
    }
    @Ignore
    @Test
    public void testGetMemoryMessage() throws IOException {
        System.out.println("getMemoryMessage");
        hb.connect("192.168.200.14", 443);
        hb.hello();
        hb.heartBeat("Test");
        String rt=hb.getMemoryMessage();
        String ex="kdjdfkkdjdkf";
        assertEquals(ex, rt);
    }
    
}
