package com.usermanagement.feature.constants;

import javax.naming.OperationNotSupportedException;

/**
 * EndpointPath class is just a placeholder for routing constants.
 *
 * @author Timur Berezhnoi
 */
public final class EndpointPath {

    public static final String SIGN_IN = "/signIn";
    public static final String SIGN_OUT = "/signOut";
    public static final String USER = "/user";
    public static final String USERS = "/users";

    /**
     * The instance should not be created.
     *
     * @throws OperationNotSupportedException during creation an instance in the class.
     */
    private EndpointPath() throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Do not instantiate the " + getClass().getName() + " class, this is util class.");
    }
}
