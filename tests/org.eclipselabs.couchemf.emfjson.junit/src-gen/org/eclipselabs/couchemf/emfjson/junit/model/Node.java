/**
 */
package org.eclipselabs.couchemf.emfjson.junit.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipselabs.couchemf.emfjson.junit.model.Node#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipselabs.couchemf.emfjson.junit.model.Node#getNodes <em>Nodes</em>}</li>
 *   <li>{@link org.eclipselabs.couchemf.emfjson.junit.model.Node#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipselabs.couchemf.emfjson.junit.model.ModelPackage#getNode()
 * @model
 * @generated
 */
public interface Node extends EObject {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.ModelPackage#getNode_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.eclipselabs.couchemf.emfjson.junit.model.Node#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipselabs.couchemf.emfjson.junit.model.Node}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nodes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' reference list.
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.ModelPackage#getNode_Nodes()
	 * @model
	 * @generated
	 */
	EList<Node> getNodes();

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.ModelPackage#getNode_Value()
	 * @model dataType="org.eclipselabs.couchemf.emfjson.junit.model.SomeType"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipselabs.couchemf.emfjson.junit.model.Node#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // Node
