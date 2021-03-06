/*
 *    uDig - User Friendly Desktop Internet GIS client
 *    http://udig.refractions.net
 *    (C) 2012, Refractions Research Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * (http://www.eclipse.org/legal/epl-v10.html), and the Refractions BSD
 * License v1.0 (http://udig.refractions.net/files/bsd3-v10.html).
 */
package org.locationtech.udig.tools.edit.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.awt.geom.AffineTransform;

import org.locationtech.udig.TestViewportPane;
import org.locationtech.udig.tools.edit.support.EditBlackboard;
import org.locationtech.udig.tools.edit.support.EditUtils;
import org.locationtech.udig.tools.edit.support.Point;
import org.locationtech.udig.tools.edit.support.TestHandler;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.junit.Before;
import org.junit.Test;
import org.opengis.referencing.operation.MathTransform;

public class InsertVertexToShellCommandTest {
    AffineTransform transform=AffineTransform.getTranslateInstance(0,0);
    private MathTransform layerToWorld;

    java.awt.Point SCREEN=new java.awt.Point(500,500);

    @Before
    public void setUp() throws Exception {
        layerToWorld=CRS.findMathTransform(DefaultGeographicCRS.WGS84, DefaultGeographicCRS.WGS84);    
    }
    
    @Test
    public void testRunAndUndo() throws Exception {
        EditBlackboard map=new EditBlackboard(SCREEN.x, SCREEN.y, transform, layerToWorld);
        
        InsertVertexCommand command1=new InsertVertexCommand(new TestHandler(), map, new TestViewportPane(new Dimension(500,500)),
                new EditUtils.StaticShapeProvider(map.getGeoms().get(0).getShell()), Point.valueOf(10,10), 0, true );
        InsertVertexCommand command2=new InsertVertexCommand(new TestHandler(), map, new TestViewportPane(new Dimension(500,500)),
                new EditUtils.StaticShapeProvider(map.getGeoms().get(0).getShell()), Point.valueOf(10,15),0, true );

        assertEquals(0, map.getCoords(10,10).size());
        assertEquals(0, map.getCoords(10,15).size());        
        
        command1.run(new NullProgressMonitor());
        command2.run(new NullProgressMonitor());
        
        assertEquals(1, map.getCoords(10,10).size());
        assertEquals(1, map.getCoords(10,15).size());
        assertEquals( Point.valueOf(10,15), map.getGeoms().get(0).getShell().getPoint(0));
        assertEquals( Point.valueOf(10,10), map.getGeoms().get(0).getShell().getPoint(1));

        
        command2.rollback(new NullProgressMonitor());
        assertTrue( 0==map.getCoords(10,15).size());
        assertTrue( 0==map.getGeoms(10,15).size());
        assertEquals(Point.valueOf(10,10), map.getGeoms().get(0).getShell().getPoint(0));
        assertEquals(1, map.getGeoms().get(0).getShell().getNumPoints());
        assertEquals(1, map.getGeoms().get(0).getShell().getNumCoords());
        
        command1.rollback(new NullProgressMonitor());
        assertTrue(map.getCoords(10,10)==null || 0==map.getCoords(10,10).size());
        assertTrue(map.getGeoms(10,10)==null || 0==map.getGeoms(10,10).size());
        assertEquals(0, map.getGeoms().get(0).getShell().getNumPoints());
        assertEquals(0, map.getGeoms().get(0).getShell().getNumCoords());
        
        
    }
    
}
