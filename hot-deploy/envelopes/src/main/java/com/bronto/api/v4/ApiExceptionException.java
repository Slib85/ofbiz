
/**
 * ApiExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.bronto.api.v4;

public class ApiExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1394937587870L;
    
    private com.bronto.api.v4.ApiExceptionE faultMessage;

    
        public ApiExceptionException() {
            super("ApiExceptionException");
        }

        public ApiExceptionException(java.lang.String s) {
           super(s);
        }

        public ApiExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public ApiExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.bronto.api.v4.ApiExceptionE msg){
       faultMessage = msg;
    }
    
    public com.bronto.api.v4.ApiExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    