
/**
 * GeneralSettings.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.bronto.api.v4;
            

            /**
            *  GeneralSettings bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class GeneralSettings
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = generalSettings
                Namespace URI = http://api.bronto.com/v4
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for EmergencyEmail
                        */

                        
                                    protected java.lang.String localEmergencyEmail ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEmergencyEmailTracker = false ;

                           public boolean isEmergencyEmailSpecified(){
                               return localEmergencyEmailTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEmergencyEmail(){
                               return localEmergencyEmail;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EmergencyEmail
                               */
                               public void setEmergencyEmail(java.lang.String param){
                            localEmergencyEmailTracker = param != null;
                                   
                                            this.localEmergencyEmail=param;
                                    

                               }
                            

                        /**
                        * field for DailyFrequencyCap
                        */

                        
                                    protected long localDailyFrequencyCap ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDailyFrequencyCapTracker = false ;

                           public boolean isDailyFrequencyCapSpecified(){
                               return localDailyFrequencyCapTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getDailyFrequencyCap(){
                               return localDailyFrequencyCap;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DailyFrequencyCap
                               */
                               public void setDailyFrequencyCap(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localDailyFrequencyCapTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localDailyFrequencyCap=param;
                                    

                               }
                            

                        /**
                        * field for WeeklyFrequencyCap
                        */

                        
                                    protected long localWeeklyFrequencyCap ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWeeklyFrequencyCapTracker = false ;

                           public boolean isWeeklyFrequencyCapSpecified(){
                               return localWeeklyFrequencyCapTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getWeeklyFrequencyCap(){
                               return localWeeklyFrequencyCap;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WeeklyFrequencyCap
                               */
                               public void setWeeklyFrequencyCap(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localWeeklyFrequencyCapTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localWeeklyFrequencyCap=param;
                                    

                               }
                            

                        /**
                        * field for MonthlyFrequencyCap
                        */

                        
                                    protected long localMonthlyFrequencyCap ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMonthlyFrequencyCapTracker = false ;

                           public boolean isMonthlyFrequencyCapSpecified(){
                               return localMonthlyFrequencyCapTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getMonthlyFrequencyCap(){
                               return localMonthlyFrequencyCap;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MonthlyFrequencyCap
                               */
                               public void setMonthlyFrequencyCap(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localMonthlyFrequencyCapTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localMonthlyFrequencyCap=param;
                                    

                               }
                            

                        /**
                        * field for TextDelivery
                        */

                        
                                    protected boolean localTextDelivery ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTextDeliveryTracker = false ;

                           public boolean isTextDeliverySpecified(){
                               return localTextDeliveryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getTextDelivery(){
                               return localTextDelivery;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TextDelivery
                               */
                               public void setTextDelivery(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localTextDeliveryTracker =
                                       true;
                                   
                                            this.localTextDelivery=param;
                                    

                               }
                            

                        /**
                        * field for TextPreference
                        */

                        
                                    protected boolean localTextPreference ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTextPreferenceTracker = false ;

                           public boolean isTextPreferenceSpecified(){
                               return localTextPreferenceTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getTextPreference(){
                               return localTextPreference;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TextPreference
                               */
                               public void setTextPreference(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localTextPreferenceTracker =
                                       true;
                                   
                                            this.localTextPreference=param;
                                    

                               }
                            

                        /**
                        * field for UseSSL
                        */

                        
                                    protected boolean localUseSSL ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUseSSLTracker = false ;

                           public boolean isUseSSLSpecified(){
                               return localUseSSLTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getUseSSL(){
                               return localUseSSL;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param UseSSL
                               */
                               public void setUseSSL(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localUseSSLTracker =
                                       true;
                                   
                                            this.localUseSSL=param;
                                    

                               }
                            

                        /**
                        * field for SendUsageAlerts
                        */

                        
                                    protected boolean localSendUsageAlerts ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSendUsageAlertsTracker = false ;

                           public boolean isSendUsageAlertsSpecified(){
                               return localSendUsageAlertsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getSendUsageAlerts(){
                               return localSendUsageAlerts;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SendUsageAlerts
                               */
                               public void setSendUsageAlerts(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localSendUsageAlertsTracker =
                                       true;
                                   
                                            this.localSendUsageAlerts=param;
                                    

                               }
                            

                        /**
                        * field for UsageAlertEmail
                        */

                        
                                    protected java.lang.String localUsageAlertEmail ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUsageAlertEmailTracker = false ;

                           public boolean isUsageAlertEmailSpecified(){
                               return localUsageAlertEmailTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUsageAlertEmail(){
                               return localUsageAlertEmail;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param UsageAlertEmail
                               */
                               public void setUsageAlertEmail(java.lang.String param){
                            localUsageAlertEmailTracker = param != null;
                                   
                                            this.localUsageAlertEmail=param;
                                    

                               }
                            

                        /**
                        * field for CurrentContacts
                        */

                        
                                    protected long localCurrentContacts ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCurrentContactsTracker = false ;

                           public boolean isCurrentContactsSpecified(){
                               return localCurrentContactsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getCurrentContacts(){
                               return localCurrentContacts;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CurrentContacts
                               */
                               public void setCurrentContacts(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localCurrentContactsTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localCurrentContacts=param;
                                    

                               }
                            

                        /**
                        * field for MaxContacts
                        */

                        
                                    protected long localMaxContacts ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMaxContactsTracker = false ;

                           public boolean isMaxContactsSpecified(){
                               return localMaxContactsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getMaxContacts(){
                               return localMaxContacts;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MaxContacts
                               */
                               public void setMaxContacts(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localMaxContactsTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localMaxContacts=param;
                                    

                               }
                            

                        /**
                        * field for CurrentMonthlyEmails
                        */

                        
                                    protected long localCurrentMonthlyEmails ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCurrentMonthlyEmailsTracker = false ;

                           public boolean isCurrentMonthlyEmailsSpecified(){
                               return localCurrentMonthlyEmailsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getCurrentMonthlyEmails(){
                               return localCurrentMonthlyEmails;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CurrentMonthlyEmails
                               */
                               public void setCurrentMonthlyEmails(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localCurrentMonthlyEmailsTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localCurrentMonthlyEmails=param;
                                    

                               }
                            

                        /**
                        * field for CurrentHostingSize
                        */

                        
                                    protected long localCurrentHostingSize ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCurrentHostingSizeTracker = false ;

                           public boolean isCurrentHostingSizeSpecified(){
                               return localCurrentHostingSizeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getCurrentHostingSize(){
                               return localCurrentHostingSize;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CurrentHostingSize
                               */
                               public void setCurrentHostingSize(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localCurrentHostingSizeTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localCurrentHostingSize=param;
                                    

                               }
                            

                        /**
                        * field for MaxHostingSize
                        */

                        
                                    protected long localMaxHostingSize ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMaxHostingSizeTracker = false ;

                           public boolean isMaxHostingSizeSpecified(){
                               return localMaxHostingSizeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getMaxHostingSize(){
                               return localMaxHostingSize;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MaxHostingSize
                               */
                               public void setMaxHostingSize(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localMaxHostingSizeTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localMaxHostingSize=param;
                                    

                               }
                            

                        /**
                        * field for AgencyTemplateuploadPerm
                        */

                        
                                    protected boolean localAgencyTemplateuploadPerm ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAgencyTemplateuploadPermTracker = false ;

                           public boolean isAgencyTemplateuploadPermSpecified(){
                               return localAgencyTemplateuploadPermTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getAgencyTemplateuploadPerm(){
                               return localAgencyTemplateuploadPerm;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AgencyTemplateuploadPerm
                               */
                               public void setAgencyTemplateuploadPerm(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localAgencyTemplateuploadPermTracker =
                                       true;
                                   
                                            this.localAgencyTemplateuploadPerm=param;
                                    

                               }
                            

                        /**
                        * field for DefaultTemplates
                        */

                        
                                    protected boolean localDefaultTemplates ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDefaultTemplatesTracker = false ;

                           public boolean isDefaultTemplatesSpecified(){
                               return localDefaultTemplatesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getDefaultTemplates(){
                               return localDefaultTemplates;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DefaultTemplates
                               */
                               public void setDefaultTemplates(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localDefaultTemplatesTracker =
                                       true;
                                   
                                            this.localDefaultTemplates=param;
                                    

                               }
                            

                        /**
                        * field for EnableInboxPreviews
                        */

                        
                                    protected boolean localEnableInboxPreviews ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEnableInboxPreviewsTracker = false ;

                           public boolean isEnableInboxPreviewsSpecified(){
                               return localEnableInboxPreviewsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getEnableInboxPreviews(){
                               return localEnableInboxPreviews;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EnableInboxPreviews
                               */
                               public void setEnableInboxPreviews(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localEnableInboxPreviewsTracker =
                                       true;
                                   
                                            this.localEnableInboxPreviews=param;
                                    

                               }
                            

                        /**
                        * field for AllowCustomizedBranding
                        */

                        
                                    protected boolean localAllowCustomizedBranding ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAllowCustomizedBrandingTracker = false ;

                           public boolean isAllowCustomizedBrandingSpecified(){
                               return localAllowCustomizedBrandingTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getAllowCustomizedBranding(){
                               return localAllowCustomizedBranding;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AllowCustomizedBranding
                               */
                               public void setAllowCustomizedBranding(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localAllowCustomizedBrandingTracker =
                                       true;
                                   
                                            this.localAllowCustomizedBranding=param;
                                    

                               }
                            

                        /**
                        * field for BounceLimit
                        */

                        
                                    protected long localBounceLimit ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBounceLimitTracker = false ;

                           public boolean isBounceLimitSpecified(){
                               return localBounceLimitTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getBounceLimit(){
                               return localBounceLimit;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BounceLimit
                               */
                               public void setBounceLimit(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localBounceLimitTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localBounceLimit=param;
                                    

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
                           namespacePrefix+":generalSettings",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "generalSettings",
                           xmlWriter);
                   }

               
                   }
                if (localEmergencyEmailTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "emergencyEmail", xmlWriter);
                             

                                          if (localEmergencyEmail==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("emergencyEmail cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEmergencyEmail);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDailyFrequencyCapTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "dailyFrequencyCap", xmlWriter);
                             
                                               if (localDailyFrequencyCap==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("dailyFrequencyCap cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDailyFrequencyCap));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWeeklyFrequencyCapTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "weeklyFrequencyCap", xmlWriter);
                             
                                               if (localWeeklyFrequencyCap==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("weeklyFrequencyCap cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWeeklyFrequencyCap));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMonthlyFrequencyCapTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "monthlyFrequencyCap", xmlWriter);
                             
                                               if (localMonthlyFrequencyCap==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("monthlyFrequencyCap cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMonthlyFrequencyCap));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTextDeliveryTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "textDelivery", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("textDelivery cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTextDelivery));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTextPreferenceTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "textPreference", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("textPreference cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTextPreference));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUseSSLTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "useSSL", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("useSSL cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUseSSL));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSendUsageAlertsTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "sendUsageAlerts", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("sendUsageAlerts cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSendUsageAlerts));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUsageAlertEmailTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "usageAlertEmail", xmlWriter);
                             

                                          if (localUsageAlertEmail==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("usageAlertEmail cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUsageAlertEmail);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCurrentContactsTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "currentContacts", xmlWriter);
                             
                                               if (localCurrentContacts==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("currentContacts cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurrentContacts));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMaxContactsTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "maxContacts", xmlWriter);
                             
                                               if (localMaxContacts==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("maxContacts cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxContacts));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCurrentMonthlyEmailsTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "currentMonthlyEmails", xmlWriter);
                             
                                               if (localCurrentMonthlyEmails==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("currentMonthlyEmails cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurrentMonthlyEmails));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCurrentHostingSizeTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "currentHostingSize", xmlWriter);
                             
                                               if (localCurrentHostingSize==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("currentHostingSize cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurrentHostingSize));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMaxHostingSizeTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "maxHostingSize", xmlWriter);
                             
                                               if (localMaxHostingSize==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("maxHostingSize cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxHostingSize));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAgencyTemplateuploadPermTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "agencyTemplateuploadPerm", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("agencyTemplateuploadPerm cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAgencyTemplateuploadPerm));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDefaultTemplatesTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "defaultTemplates", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("defaultTemplates cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDefaultTemplates));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEnableInboxPreviewsTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "enableInboxPreviews", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("enableInboxPreviews cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEnableInboxPreviews));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAllowCustomizedBrandingTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "allowCustomizedBranding", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("allowCustomizedBranding cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowCustomizedBranding));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBounceLimitTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "bounceLimit", xmlWriter);
                             
                                               if (localBounceLimit==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("bounceLimit cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBounceLimit));
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

                 if (localEmergencyEmailTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "emergencyEmail"));
                                 
                                        if (localEmergencyEmail != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmergencyEmail));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("emergencyEmail cannot be null!!");
                                        }
                                    } if (localDailyFrequencyCapTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "dailyFrequencyCap"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDailyFrequencyCap));
                            } if (localWeeklyFrequencyCapTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "weeklyFrequencyCap"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWeeklyFrequencyCap));
                            } if (localMonthlyFrequencyCapTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "monthlyFrequencyCap"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMonthlyFrequencyCap));
                            } if (localTextDeliveryTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "textDelivery"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTextDelivery));
                            } if (localTextPreferenceTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "textPreference"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTextPreference));
                            } if (localUseSSLTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "useSSL"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUseSSL));
                            } if (localSendUsageAlertsTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "sendUsageAlerts"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSendUsageAlerts));
                            } if (localUsageAlertEmailTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "usageAlertEmail"));
                                 
                                        if (localUsageAlertEmail != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUsageAlertEmail));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("usageAlertEmail cannot be null!!");
                                        }
                                    } if (localCurrentContactsTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "currentContacts"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurrentContacts));
                            } if (localMaxContactsTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "maxContacts"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxContacts));
                            } if (localCurrentMonthlyEmailsTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "currentMonthlyEmails"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurrentMonthlyEmails));
                            } if (localCurrentHostingSizeTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "currentHostingSize"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurrentHostingSize));
                            } if (localMaxHostingSizeTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "maxHostingSize"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxHostingSize));
                            } if (localAgencyTemplateuploadPermTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "agencyTemplateuploadPerm"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAgencyTemplateuploadPerm));
                            } if (localDefaultTemplatesTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "defaultTemplates"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDefaultTemplates));
                            } if (localEnableInboxPreviewsTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "enableInboxPreviews"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEnableInboxPreviews));
                            } if (localAllowCustomizedBrandingTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "allowCustomizedBranding"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowCustomizedBranding));
                            } if (localBounceLimitTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "bounceLimit"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBounceLimit));
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
        public static GeneralSettings parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GeneralSettings object =
                new GeneralSettings();

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
                    
                            if (!"generalSettings".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GeneralSettings)com.bronto.api.v4.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","emergencyEmail").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"emergencyEmail" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEmergencyEmail(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","dailyFrequencyCap").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"dailyFrequencyCap" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDailyFrequencyCap(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setDailyFrequencyCap(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","weeklyFrequencyCap").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"weeklyFrequencyCap" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWeeklyFrequencyCap(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setWeeklyFrequencyCap(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","monthlyFrequencyCap").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"monthlyFrequencyCap" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMonthlyFrequencyCap(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setMonthlyFrequencyCap(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","textDelivery").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"textDelivery" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTextDelivery(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","textPreference").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"textPreference" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTextPreference(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","useSSL").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"useSSL" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUseSSL(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","sendUsageAlerts").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"sendUsageAlerts" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSendUsageAlerts(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","usageAlertEmail").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"usageAlertEmail" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUsageAlertEmail(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","currentContacts").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"currentContacts" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCurrentContacts(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setCurrentContacts(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","maxContacts").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"maxContacts" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMaxContacts(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setMaxContacts(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","currentMonthlyEmails").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"currentMonthlyEmails" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCurrentMonthlyEmails(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setCurrentMonthlyEmails(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","currentHostingSize").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"currentHostingSize" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCurrentHostingSize(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setCurrentHostingSize(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","maxHostingSize").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"maxHostingSize" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMaxHostingSize(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setMaxHostingSize(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","agencyTemplateuploadPerm").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"agencyTemplateuploadPerm" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAgencyTemplateuploadPerm(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","defaultTemplates").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"defaultTemplates" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDefaultTemplates(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","enableInboxPreviews").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"enableInboxPreviews" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEnableInboxPreviews(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","allowCustomizedBranding").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"allowCustomizedBranding" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAllowCustomizedBranding(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","bounceLimit").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"bounceLimit" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBounceLimit(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setBounceLimit(java.lang.Long.MIN_VALUE);
                                           
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
           
    