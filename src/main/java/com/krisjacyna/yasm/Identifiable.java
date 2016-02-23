package com.krisjacyna.yasm;

/**
 * An object which has an identity.
 * 
 * @author Kris Jacyna
 */
public interface Identifiable {

	/**
	 * Returns the ID for this object.
	 * The default ID is the simple class name.
	 * 
	 * @return the ID for this object
	 */
	default String getId() {
		return getClass().getSimpleName();
	}
}
