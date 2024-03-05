
/**
 * AuthenticationFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.scene7.www.ipsapi;

public class AuthenticationFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1395003775272L;
    
    private com.scene7.www.ipsapi.xsd.AuthenticationFault faultMessage;

    
        public AuthenticationFaultException() {
            super("AuthenticationFaultException");
        }

        public AuthenticationFaultException(java.lang.String s) {
           super(s);
        }

        public AuthenticationFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public AuthenticationFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.scene7.www.ipsapi.xsd.AuthenticationFault msg){
       faultMessage = msg;
    }
    
    public com.scene7.www.ipsapi.xsd.AuthenticationFault getFaultMessage(){
       return faultMessage;
    }
}
    