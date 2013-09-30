/**
 */
package org.eclipselabs.couchemf.emfjson.junit.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipselabs.couchemf.emfjson.junit.model.ANode;
import org.eclipselabs.couchemf.emfjson.junit.model.BNode;
import org.eclipselabs.couchemf.emfjson.junit.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ANode</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipselabs.couchemf.emfjson.junit.model.impl.ANodeImpl#getBNode <em>BNode</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ANodeImpl extends NodeImpl implements ANode {
	/**
	 * The cached value of the '{@link #getBNode() <em>BNode</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBNode()
	 * @generated
	 * @ordered
	 */
	protected BNode bNode;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ANodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.ANODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BNode getBNode() {
		if (bNode != null && bNode.eIsProxy()) {
			InternalEObject oldBNode = (InternalEObject)bNode;
			bNode = (BNode)eResolveProxy(oldBNode);
			if (bNode != oldBNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.ANODE__BNODE, oldBNode, bNode));
			}
		}
		return bNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BNode basicGetBNode() {
		return bNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBNode(BNode newBNode) {
		BNode oldBNode = bNode;
		bNode = newBNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ANODE__BNODE, oldBNode, bNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.ANODE__BNODE:
				if (resolve) return getBNode();
				return basicGetBNode();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.ANODE__BNODE:
				setBNode((BNode)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelPackage.ANODE__BNODE:
				setBNode((BNode)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelPackage.ANODE__BNODE:
				return bNode != null;
		}
		return super.eIsSet(featureID);
	}

} //ANodeImpl
