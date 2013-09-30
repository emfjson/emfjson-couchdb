/**
 */
package org.eclipselabs.couchemf.emfjson.junit.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipselabs.couchemf.emfjson.junit.model.ModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://eclipselabs.org/couchemf/junit/model";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelPackage eINSTANCE = org.eclipselabs.couchemf.emfjson.junit.model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipselabs.couchemf.emfjson.junit.model.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.impl.NodeImpl
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.impl.ModelPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__LABEL = 0;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__NODES = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__VALUE = 2;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipselabs.couchemf.emfjson.junit.model.impl.ANodeImpl <em>ANode</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.impl.ANodeImpl
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.impl.ModelPackageImpl#getANode()
	 * @generated
	 */
	int ANODE = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANODE__LABEL = NODE__LABEL;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANODE__NODES = NODE__NODES;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANODE__VALUE = NODE__VALUE;

	/**
	 * The feature id for the '<em><b>BNode</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANODE__BNODE = NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>ANode</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANODE_FEATURE_COUNT = NODE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>ANode</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANODE_OPERATION_COUNT = NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipselabs.couchemf.emfjson.junit.model.impl.BNodeImpl <em>BNode</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.impl.BNodeImpl
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.impl.ModelPackageImpl#getBNode()
	 * @generated
	 */
	int BNODE = 2;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BNODE__LABEL = ANODE__LABEL;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BNODE__NODES = ANODE__NODES;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BNODE__VALUE = ANODE__VALUE;

	/**
	 * The feature id for the '<em><b>BNode</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BNODE__BNODE = ANODE__BNODE;

	/**
	 * The feature id for the '<em><b>Boolean Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BNODE__BOOLEAN_VALUE = ANODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>BNode</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BNODE_FEATURE_COUNT = ANODE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>BNode</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BNODE_OPERATION_COUNT = ANODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '<em>Some Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.impl.ModelPackageImpl#getSomeType()
	 * @generated
	 */
	int SOME_TYPE = 3;


	/**
	 * Returns the meta object for class '{@link org.eclipselabs.couchemf.emfjson.junit.model.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipselabs.couchemf.emfjson.junit.model.Node#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.Node#getLabel()
	 * @see #getNode()
	 * @generated
	 */
	EAttribute getNode_Label();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipselabs.couchemf.emfjson.junit.model.Node#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Nodes</em>'.
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.Node#getNodes()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_Nodes();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipselabs.couchemf.emfjson.junit.model.Node#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.Node#getValue()
	 * @see #getNode()
	 * @generated
	 */
	EAttribute getNode_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipselabs.couchemf.emfjson.junit.model.ANode <em>ANode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ANode</em>'.
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.ANode
	 * @generated
	 */
	EClass getANode();

	/**
	 * Returns the meta object for the reference '{@link org.eclipselabs.couchemf.emfjson.junit.model.ANode#getBNode <em>BNode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>BNode</em>'.
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.ANode#getBNode()
	 * @see #getANode()
	 * @generated
	 */
	EReference getANode_BNode();

	/**
	 * Returns the meta object for class '{@link org.eclipselabs.couchemf.emfjson.junit.model.BNode <em>BNode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>BNode</em>'.
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.BNode
	 * @generated
	 */
	EClass getBNode();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipselabs.couchemf.emfjson.junit.model.BNode#isBooleanValue <em>Boolean Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Boolean Value</em>'.
	 * @see org.eclipselabs.couchemf.emfjson.junit.model.BNode#isBooleanValue()
	 * @see #getBNode()
	 * @generated
	 */
	EAttribute getBNode_BooleanValue();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Some Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Some Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 * @generated
	 */
	EDataType getSomeType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelFactory getModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipselabs.couchemf.emfjson.junit.model.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipselabs.couchemf.emfjson.junit.model.impl.NodeImpl
		 * @see org.eclipselabs.couchemf.emfjson.junit.model.impl.ModelPackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE__LABEL = eINSTANCE.getNode_Label();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__NODES = eINSTANCE.getNode_Nodes();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE__VALUE = eINSTANCE.getNode_Value();

		/**
		 * The meta object literal for the '{@link org.eclipselabs.couchemf.emfjson.junit.model.impl.ANodeImpl <em>ANode</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipselabs.couchemf.emfjson.junit.model.impl.ANodeImpl
		 * @see org.eclipselabs.couchemf.emfjson.junit.model.impl.ModelPackageImpl#getANode()
		 * @generated
		 */
		EClass ANODE = eINSTANCE.getANode();

		/**
		 * The meta object literal for the '<em><b>BNode</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANODE__BNODE = eINSTANCE.getANode_BNode();

		/**
		 * The meta object literal for the '{@link org.eclipselabs.couchemf.emfjson.junit.model.impl.BNodeImpl <em>BNode</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipselabs.couchemf.emfjson.junit.model.impl.BNodeImpl
		 * @see org.eclipselabs.couchemf.emfjson.junit.model.impl.ModelPackageImpl#getBNode()
		 * @generated
		 */
		EClass BNODE = eINSTANCE.getBNode();

		/**
		 * The meta object literal for the '<em><b>Boolean Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BNODE__BOOLEAN_VALUE = eINSTANCE.getBNode_BooleanValue();

		/**
		 * The meta object literal for the '<em>Some Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipselabs.couchemf.emfjson.junit.model.impl.ModelPackageImpl#getSomeType()
		 * @generated
		 */
		EDataType SOME_TYPE = eINSTANCE.getSomeType();

	}

} //ModelPackage
