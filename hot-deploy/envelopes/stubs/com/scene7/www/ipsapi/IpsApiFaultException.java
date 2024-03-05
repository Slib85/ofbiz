
/**
 * IpsApiFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.scene7.www.ipsapi;

public class IpsApiFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1395003775284L;
    
    private com.scene7.www.ipsapi.xsd.IpsApiFault faultMessage;

    
        public IpsApiFaultException() {
            super("IpsApiFaultException");
        }

        public IpsApiFaultException(java.lang.String s) {
           super(s);
        }

        public IpsApiFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public IpsApiFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.scene7.www.ipsapi.xsd.IpsApiFault msg){
       faultMessage = msg;
    }
    
    public com.scene7.www.ipsapi.xsd.IpsApiFault getFaultMessage(){
       return faultMessage;
    }
}
    