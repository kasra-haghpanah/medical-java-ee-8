package com.kasra.javaee.jta.exception;

import javax.ejb.ApplicationException;

/**
 * @author Ahmad kasra.haghpnah
 *         Date: 9/21/16
 *         Time: 10:02 AM
 */
@ApplicationException(rollback = true)
public class DaoException extends Exception {

    // is not runtime exception , it's ApplicationException
    // it's runtime exception when extends RnutimeException
    // rollback = true says ...
}
