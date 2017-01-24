package com.thinkgem.jeesite.common.Exception;

/**
 * Created by jjh on 16/2/1.
 */
public class AuthRoleException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 7808397008917807245L;

    /**
     * Constructor for MsgMalFormException.
     *
     * @param msg
     *            the detail message
     */
    public AuthRoleException(String msg) {
        super(msg);
    }

    /**
     * Constructor for MsgMalFormException.
     *
     * @param msg
     *            the detail message
     * @param cause
     *            the root cause
     */
    public AuthRoleException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructor for MsgMalFormException.
     *
     * @param cause
     *            the root cause
     */
    public AuthRoleException(Throwable cause) {
        super(cause);
    }
}
