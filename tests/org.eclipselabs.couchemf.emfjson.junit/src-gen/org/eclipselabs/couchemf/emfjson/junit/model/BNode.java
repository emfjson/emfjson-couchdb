/**
 */
package org.eclipselabs.couchemf.emfjson.junit.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>BNode</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipselabs.couchemf.emfjson.junit.model.BNode#isBooleanValue <em>Boolean Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipselabs.couchemf.emfjson.junit.model.ModelPackage#getBNode()
 * @model
 * @generated
 */
public interface BNode extends ANode {
	/**
	 * Returns the value of the '<em><b>Boolean Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Boolean Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Boolean Value</em>' attribute.
	 * @see #setBooleanValue(boolean)
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.ModelPackage#getBNode_BooleanValue()
	 * @model
	 * @generated
	 */
	boolean isBooleanValue();

	/**
	 * Sets the value of the '{@link org.eclipselabs.couchemf.emfjson.junit.model.BNode#isBooleanValue <em>Boolean Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Boolean Value</em>' attribute.
	 * @see #isBooleanValue()
	 * @generated
	 */
	void setBooleanValue(boolean value);

} // BNode
