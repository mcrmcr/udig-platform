/*
 *    Parkinfo
 *    http://qpws/parkinfo
 *
 *    (C) 2011, Department of Environment Resource Management
 *
 *    This code is provided for department use.
 */
package net.refractions.udig.project.listener;

import net.refractions.udig.project.EditFeature.AttributeStatus;

/**
 * Used to hold onto event data when an attributes status change
 * 
 * @author leviputna
 * 
 */
public class EditFeatureStateChangeEvent {

    /**
     * The type of State change that accrued.
     * 
     * @author leviputna
     * 
     */
    public enum Type {
        /**
         * The dirty state of an attribute has changes.
         * <p>
         * if <code>attributeStatus.getDirty()</code> is true an un-applied attribute value change
         * has been added to the EditFeature and are not reflected in the data model, false if
         * changes have been
         * </p>
         */
        DIRTY,
        /**
         * The visibility of an attribute as changes.
         */
        VISIBLE, 
        
        /**
         * the attribute enablement has changed.
         */
        ENABLED, 
        /**
         * the attribute editability has changed 
         */
        EDITABLE
    };

    private Type state;

    private AttributeStatus attributeStatus;

    public EditFeatureStateChangeEvent(Type stateChange, AttributeStatus attributeStatus) {

    }

    /**
     * Return the status type that changes as part of this event.
     * 
     * @return the state type that changes
     */
    public Type getEventType() {
        return state;
    }

    /**
     * Return the AttributeStatus for the attribute thats status changed.
     * 
     * @return the attributeStatus
     */
    public AttributeStatus getAttributeStatus() {
        return attributeStatus;
    }
}
