
/**
 * AuthorizationFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.scene7.www.ipsapi;

public class AuthorizationFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1395003775278L;
    
    private com.scene7.www.ipsapi.xsd.AuthorizationFault faultMessage;

    
        public AuthorizationFaultException() {
            super("AuthorizationFaultException");
        }

        public AuthorizationFaultException(java.lang.String s) {
           super(s);
        }

        public AuthorizationFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public AuthorizationFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.scene7.www.ipsapi.xsd.AuthorizationFault msg){
       faultMessage = msg;
    }
    
    public com.scene7.www.ipsapi.xsd.AuthorizationFault getFaultMessage(){
       return faultMessage;
    }
}
    