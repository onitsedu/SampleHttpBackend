package com.suay.king.exception.business;

public class LevelNotFoundException extends BusinessException {

    /**
     * Serial for this class version
     */
    private static final long serialVersionUID = -9079586059430224755L;

    private static final Integer LVL_NOT_FOUND_ERROR_CODE = -2;
    private static final String LVL_NOT_FOUND_MESSAGE = "session expired";

    public LevelNotFoundException() {
	super(LVL_NOT_FOUND_MESSAGE, LVL_NOT_FOUND_ERROR_CODE);
    }
}
