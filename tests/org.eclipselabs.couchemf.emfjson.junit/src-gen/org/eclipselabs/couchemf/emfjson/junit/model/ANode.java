/**
 */
package org.eclipselabs.couchemf.emfjson.junit.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ANode</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipselabs.couchemf.emfjson.junit.model.ANode#getBNode <em>BNode</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipselabs.couchemf.emfjson.junit.model.ModelPackage#getANode()
 * @model
 * @generated
 */
public interface ANode extends Node {
	/**
	 * Returns the value of the '<em><b>BNode</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>BNode</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>BNode</em>' reference.
	 * @see #setBNode(BNode)
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.ModelPackage#getANode_BNode()
	 * @model
	 * @generated
	 */
	BNode getBNode();

	/**
	 * Sets the value of the '{@link org.eclipselabs.couchemf.emfjson.junit.model.ANode#getBNode <em>BNode</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>BNode</em>' reference.
	 * @see #getBNode()
	 * @generated
	 */
	void setBNode(BNode value);

} // ANode
