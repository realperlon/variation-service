package org.spice3d.variation.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InternalAppException extends Exception {

	private static final long serialVersionUID = 1L;
	static final Logger logger = LoggerFactory.getLogger(InternalAppException.class);


	public InternalAppException(String message) {
		super(message);
	}


	
}