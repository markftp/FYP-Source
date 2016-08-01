package module;

import module.descriptor.*;

class NodeEventHandler {

	NodeEventHandler() {
		// default implementation: do nothing
	}

	void preRemoveChildEvent(Node node, Node child, StructuralPropertyDescriptor property) {
		// do nothing
		// System.out.println("DEL1 " + property);
	}

	void postRemoveChildEvent(Node node, Node child, StructuralPropertyDescriptor property) {
		// do nothing
		// System.out.println("DEL2 " + property);
	}

	void preReplaceChildEvent(Node node, Node child, Node newChild, StructuralPropertyDescriptor property) {
		// do nothing
		// System.out.println("REP1 " + property);
	}

	void postReplaceChildEvent(Node node, Node child, Node newChild, StructuralPropertyDescriptor property) {
		// do nothing
		// System.out.println("REP2 " + property);
	}

	void preAddChildEvent(Node node, Node child, StructuralPropertyDescriptor property) {
		// do nothing
		// System.out.println("ADD1 " + property);
	}

	void postAddChildEvent(Node node, Node child, StructuralPropertyDescriptor property) {
		// do nothing
		// System.out.println("ADD2 " + property);
	}

	void preValueChangeEvent(Node node, SimplePropertyDescriptor property) {
		// do nothing
		// System.out.println("MOD1 " + property);
	}

	void postValueChangeEvent(Node node, SimplePropertyDescriptor property) {
		// do nothing
		// System.out.println("MOD2 " + property);
	}

	void preCloneNodeEvent(Node node) {
		// do nothing
		// System.out.println("CLONE1");
	}

	void postCloneNodeEvent(Node node, Node clone) {
		// do nothing
		// System.out.println("CLONE2");
	}

}
