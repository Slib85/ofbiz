
/**
 * LoginObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.bronto.api.v4;
            

            /**
            *  LoginObject bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class LoginObject
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = loginObject
                Namespace URI = http://api.bronto.com/v4
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for Username
                        */

                        
                                    protected java.lang.String localUsername ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUsernameTracker = false ;

                           public boolean isUsernameSpecified(){
                               return localUsernameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUsername(){
                               return localUsername;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Username
                               */
                               public void setUsername(java.lang.String param){
                            localUsernameTracker = param != null;
                                   
                                            this.localUsername=param;
                                    

                               }
                            

                        /**
                        * field for Password
                        */

                        
                                    protected java.lang.String localPassword ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPasswordTracker = false ;

                           public boolean isPasswordSpecified(){
                               return localPasswordTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPassword(){
                               return localPassword;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Password
                               */
                               public void setPassword(java.lang.String param){
                            localPasswordTracker = param != null;
                                   
                                            this.localPassword=param;
                                    

                               }
                            

                        /**
                        * field for ContactInformation
                        */

                        
                                    protected com.bronto.api.v4.ContactInformation localContactInformation ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localContactInformationTracker = false ;

                           public boolean isContactInformationSpecified(){
                               return localContactInformationTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.bronto.api.v4.ContactInformation
                           */
                           public  com.bronto.api.v4.ContactInformation getContactInformation(){
                               return localContactInformation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ContactInformation
                               */
                               public void setContactInformation(com.bronto.api.v4.ContactInformation param){
                            localContactInformationTracker = param != null;
                                   
                                            this.localContactInformation=param;
                                    

                               }
                            

                        /**
                        * field for PermissionAgencyAdmin
                        */

                        
                                    protected boolean localPermissionAgencyAdmin ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionAgencyAdminTracker = false ;

                           public boolean isPermissionAgencyAdminSpecified(){
                               return localPermissionAgencyAdminTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionAgencyAdmin(){
                               return localPermissionAgencyAdmin;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionAgencyAdmin
                               */
                               public void setPermissionAgencyAdmin(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionAgencyAdminTracker =
                                       true;
                                   
                                            this.localPermissionAgencyAdmin=param;
                                    

                               }
                            

                        /**
                        * field for PermissionAdmin
                        */

                        
                                    protected boolean localPermissionAdmin ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionAdminTracker = false ;

                           public boolean isPermissionAdminSpecified(){
                               return localPermissionAdminTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionAdmin(){
                               return localPermissionAdmin;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionAdmin
                               */
                               public void setPermissionAdmin(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionAdminTracker =
                                       true;
                                   
                                            this.localPermissionAdmin=param;
                                    

                               }
                            

                        /**
                        * field for PermissionApi
                        */

                        
                                    protected boolean localPermissionApi ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionApiTracker = false ;

                           public boolean isPermissionApiSpecified(){
                               return localPermissionApiTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionApi(){
                               return localPermissionApi;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionApi
                               */
                               public void setPermissionApi(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionApiTracker =
                                       true;
                                   
                                            this.localPermissionApi=param;
                                    

                               }
                            

                        /**
                        * field for PermissionUpgrade
                        */

                        
                                    protected boolean localPermissionUpgrade ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionUpgradeTracker = false ;

                           public boolean isPermissionUpgradeSpecified(){
                               return localPermissionUpgradeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionUpgrade(){
                               return localPermissionUpgrade;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionUpgrade
                               */
                               public void setPermissionUpgrade(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionUpgradeTracker =
                                       true;
                                   
                                            this.localPermissionUpgrade=param;
                                    

                               }
                            

                        /**
                        * field for PermissionFatigueOverride
                        */

                        
                                    protected boolean localPermissionFatigueOverride ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionFatigueOverrideTracker = false ;

                           public boolean isPermissionFatigueOverrideSpecified(){
                               return localPermissionFatigueOverrideTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionFatigueOverride(){
                               return localPermissionFatigueOverride;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionFatigueOverride
                               */
                               public void setPermissionFatigueOverride(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionFatigueOverrideTracker =
                                       true;
                                   
                                            this.localPermissionFatigueOverride=param;
                                    

                               }
                            

                        /**
                        * field for PermissionMessageCompose
                        */

                        
                                    protected boolean localPermissionMessageCompose ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionMessageComposeTracker = false ;

                           public boolean isPermissionMessageComposeSpecified(){
                               return localPermissionMessageComposeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionMessageCompose(){
                               return localPermissionMessageCompose;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionMessageCompose
                               */
                               public void setPermissionMessageCompose(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionMessageComposeTracker =
                                       true;
                                   
                                            this.localPermissionMessageCompose=param;
                                    

                               }
                            

                        /**
                        * field for PermissionMessageApprove
                        */

                        
                                    protected boolean localPermissionMessageApprove ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionMessageApproveTracker = false ;

                           public boolean isPermissionMessageApproveSpecified(){
                               return localPermissionMessageApproveTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionMessageApprove(){
                               return localPermissionMessageApprove;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionMessageApprove
                               */
                               public void setPermissionMessageApprove(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionMessageApproveTracker =
                                       true;
                                   
                                            this.localPermissionMessageApprove=param;
                                    

                               }
                            

                        /**
                        * field for PermissionMessageDelete
                        */

                        
                                    protected boolean localPermissionMessageDelete ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionMessageDeleteTracker = false ;

                           public boolean isPermissionMessageDeleteSpecified(){
                               return localPermissionMessageDeleteTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionMessageDelete(){
                               return localPermissionMessageDelete;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionMessageDelete
                               */
                               public void setPermissionMessageDelete(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionMessageDeleteTracker =
                                       true;
                                   
                                            this.localPermissionMessageDelete=param;
                                    

                               }
                            

                        /**
                        * field for PermissionAutomatorCompose
                        */

                        
                                    protected boolean localPermissionAutomatorCompose ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionAutomatorComposeTracker = false ;

                           public boolean isPermissionAutomatorComposeSpecified(){
                               return localPermissionAutomatorComposeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionAutomatorCompose(){
                               return localPermissionAutomatorCompose;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionAutomatorCompose
                               */
                               public void setPermissionAutomatorCompose(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionAutomatorComposeTracker =
                                       true;
                                   
                                            this.localPermissionAutomatorCompose=param;
                                    

                               }
                            

                        /**
                        * field for PermissionListCreateSend
                        */

                        
                                    protected boolean localPermissionListCreateSend ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionListCreateSendTracker = false ;

                           public boolean isPermissionListCreateSendSpecified(){
                               return localPermissionListCreateSendTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionListCreateSend(){
                               return localPermissionListCreateSend;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionListCreateSend
                               */
                               public void setPermissionListCreateSend(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionListCreateSendTracker =
                                       true;
                                   
                                            this.localPermissionListCreateSend=param;
                                    

                               }
                            

                        /**
                        * field for PermissionListCreate
                        */

                        
                                    protected boolean localPermissionListCreate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionListCreateTracker = false ;

                           public boolean isPermissionListCreateSpecified(){
                               return localPermissionListCreateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionListCreate(){
                               return localPermissionListCreate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionListCreate
                               */
                               public void setPermissionListCreate(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionListCreateTracker =
                                       true;
                                   
                                            this.localPermissionListCreate=param;
                                    

                               }
                            

                        /**
                        * field for PermissionSegmentCreate
                        */

                        
                                    protected boolean localPermissionSegmentCreate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionSegmentCreateTracker = false ;

                           public boolean isPermissionSegmentCreateSpecified(){
                               return localPermissionSegmentCreateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionSegmentCreate(){
                               return localPermissionSegmentCreate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionSegmentCreate
                               */
                               public void setPermissionSegmentCreate(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionSegmentCreateTracker =
                                       true;
                                   
                                            this.localPermissionSegmentCreate=param;
                                    

                               }
                            

                        /**
                        * field for PermissionFieldCreate
                        */

                        
                                    protected boolean localPermissionFieldCreate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionFieldCreateTracker = false ;

                           public boolean isPermissionFieldCreateSpecified(){
                               return localPermissionFieldCreateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionFieldCreate(){
                               return localPermissionFieldCreate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionFieldCreate
                               */
                               public void setPermissionFieldCreate(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionFieldCreateTracker =
                                       true;
                                   
                                            this.localPermissionFieldCreate=param;
                                    

                               }
                            

                        /**
                        * field for PermissionFieldReorder
                        */

                        
                                    protected boolean localPermissionFieldReorder ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionFieldReorderTracker = false ;

                           public boolean isPermissionFieldReorderSpecified(){
                               return localPermissionFieldReorderTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionFieldReorder(){
                               return localPermissionFieldReorder;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionFieldReorder
                               */
                               public void setPermissionFieldReorder(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionFieldReorderTracker =
                                       true;
                                   
                                            this.localPermissionFieldReorder=param;
                                    

                               }
                            

                        /**
                        * field for PermissionSubscriberCreate
                        */

                        
                                    protected boolean localPermissionSubscriberCreate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionSubscriberCreateTracker = false ;

                           public boolean isPermissionSubscriberCreateSpecified(){
                               return localPermissionSubscriberCreateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionSubscriberCreate(){
                               return localPermissionSubscriberCreate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionSubscriberCreate
                               */
                               public void setPermissionSubscriberCreate(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionSubscriberCreateTracker =
                                       true;
                                   
                                            this.localPermissionSubscriberCreate=param;
                                    

                               }
                            

                        /**
                        * field for PermissionSubscriberView
                        */

                        
                                    protected boolean localPermissionSubscriberView ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionSubscriberViewTracker = false ;

                           public boolean isPermissionSubscriberViewSpecified(){
                               return localPermissionSubscriberViewTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPermissionSubscriberView(){
                               return localPermissionSubscriberView;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PermissionSubscriberView
                               */
                               public void setPermissionSubscriberView(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPermissionSubscriberViewTracker =
                                       true;
                                   
                                            this.localPermissionSubscriberView=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://api.bronto.com/v4");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":loginObject",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "loginObject",
                           xmlWriter);
                   }

               
                   }
                if (localUsernameTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "username", xmlWriter);
                             

                                          if (localUsername==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("username cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUsername);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPasswordTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "password", xmlWriter);
                             

                                          if (localPassword==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("password cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPassword);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localContactInformationTracker){
                                            if (localContactInformation==null){
                                                 throw new org.apache.axis2.databinding.ADBException("contactInformation cannot be null!!");
                                            }
                                           localContactInformation.serialize(new javax.xml.namespace.QName("","contactInformation"),
                                               xmlWriter);
                                        } if (localPermissionAgencyAdminTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionAgencyAdmin", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionAgencyAdmin cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionAgencyAdmin));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPermissionAdminTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionAdmin", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionAdmin cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionAdmin));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPermissionApiTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionApi", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionApi cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionApi));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPermissionUpgradeTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionUpgrade", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionUpgrade cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionUpgrade));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPermissionFatigueOverrideTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionFatigueOverride", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionFatigueOverride cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionFatigueOverride));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPermissionMessageComposeTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionMessageCompose", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionMessageCompose cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionMessageCompose));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPermissionMessageApproveTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionMessageApprove", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionMessageApprove cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionMessageApprove));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPermissionMessageDeleteTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionMessageDelete", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionMessageDelete cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionMessageDelete));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPermissionAutomatorComposeTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionAutomatorCompose", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionAutomatorCompose cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionAutomatorCompose));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPermissionListCreateSendTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionListCreateSend", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionListCreateSend cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionListCreateSend));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPermissionListCreateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionListCreate", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionListCreate cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionListCreate));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPermissionSegmentCreateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionSegmentCreate", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionSegmentCreate cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionSegmentCreate));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPermissionFieldCreateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionFieldCreate", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionFieldCreate cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionFieldCreate));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPermissionFieldReorderTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionFieldReorder", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionFieldReorder cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionFieldReorder));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPermissionSubscriberCreateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionSubscriberCreate", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionSubscriberCreate cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionSubscriberCreate));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPermissionSubscriberViewTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "permissionSubscriberView", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("permissionSubscriberView cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionSubscriberView));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://api.bronto.com/v4")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localUsernameTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "username"));
                                 
                                        if (localUsername != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUsername));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("username cannot be null!!");
                                        }
                                    } if (localPasswordTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "password"));
                                 
                                        if (localPassword != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassword));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("password cannot be null!!");
                                        }
                                    } if (localContactInformationTracker){
                            elementList.add(new javax.xml.namespace.QName("",
                                                                      "contactInformation"));
                            
                            
                                    if (localContactInformation==null){
                                         throw new org.apache.axis2.databinding.ADBException("contactInformation cannot be null!!");
                                    }
                                    elementList.add(localContactInformation);
                                } if (localPermissionAgencyAdminTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionAgencyAdmin"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionAgencyAdmin));
                            } if (localPermissionAdminTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionAdmin"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionAdmin));
                            } if (localPermissionApiTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionApi"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionApi));
                            } if (localPermissionUpgradeTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionUpgrade"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionUpgrade));
                            } if (localPermissionFatigueOverrideTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionFatigueOverride"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionFatigueOverride));
                            } if (localPermissionMessageComposeTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionMessageCompose"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionMessageCompose));
                            } if (localPermissionMessageApproveTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionMessageApprove"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionMessageApprove));
                            } if (localPermissionMessageDeleteTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionMessageDelete"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionMessageDelete));
                            } if (localPermissionAutomatorComposeTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionAutomatorCompose"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionAutomatorCompose));
                            } if (localPermissionListCreateSendTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionListCreateSend"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionListCreateSend));
                            } if (localPermissionListCreateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionListCreate"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionListCreate));
                            } if (localPermissionSegmentCreateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionSegmentCreate"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionSegmentCreate));
                            } if (localPermissionFieldCreateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionFieldCreate"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionFieldCreate));
                            } if (localPermissionFieldReorderTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionFieldReorder"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionFieldReorder));
                            } if (localPermissionSubscriberCreateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionSubscriberCreate"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionSubscriberCreate));
                            } if (localPermissionSubscriberViewTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "permissionSubscriberView"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPermissionSubscriberView));
                            }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static LoginObject parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            LoginObject object =
                new LoginObject();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"loginObject".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (LoginObject)com.bronto.api.v4.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","username").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"username" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUsername(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","password").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"password" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPassword(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","contactInformation").equals(reader.getName())){
                                
                                                object.setContactInformation(com.bronto.api.v4.ContactInformation.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionAgencyAdmin").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionAgencyAdmin" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionAgencyAdmin(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionAdmin").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionAdmin" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionAdmin(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionApi").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionApi" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionApi(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionUpgrade").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionUpgrade" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionUpgrade(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionFatigueOverride").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionFatigueOverride" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionFatigueOverride(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionMessageCompose").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionMessageCompose" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionMessageCompose(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionMessageApprove").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionMessageApprove" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionMessageApprove(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionMessageDelete").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionMessageDelete" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionMessageDelete(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionAutomatorCompose").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionAutomatorCompose" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionAutomatorCompose(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionListCreateSend").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionListCreateSend" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionListCreateSend(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionListCreate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionListCreate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionListCreate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionSegmentCreate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionSegmentCreate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionSegmentCreate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionFieldCreate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionFieldCreate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionFieldCreate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionFieldReorder").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionFieldReorder" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionFieldReorder(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionSubscriberCreate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionSubscriberCreate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionSubscriberCreate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","permissionSubscriberView").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"permissionSubscriberView" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPermissionSubscriberView(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    