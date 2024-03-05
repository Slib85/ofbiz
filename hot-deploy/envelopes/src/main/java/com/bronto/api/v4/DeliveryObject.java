
/**
 * DeliveryObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.bronto.api.v4;
            

            /**
            *  DeliveryObject bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class DeliveryObject
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = deliveryObject
                Namespace URI = http://api.bronto.com/v4
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for Id
                        */

                        
                                    protected java.lang.String localId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIdTracker = false ;

                           public boolean isIdSpecified(){
                               return localIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getId(){
                               return localId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Id
                               */
                               public void setId(java.lang.String param){
                            localIdTracker = param != null;
                                   
                                            this.localId=param;
                                    

                               }
                            

                        /**
                        * field for Start
                        */

                        
                                    protected java.util.Calendar localStart ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localStartTracker = false ;

                           public boolean isStartSpecified(){
                               return localStartTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getStart(){
                               return localStart;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Start
                               */
                               public void setStart(java.util.Calendar param){
                            localStartTracker = param != null;
                                   
                                            this.localStart=param;
                                    

                               }
                            

                        /**
                        * field for MessageId
                        */

                        
                                    protected java.lang.String localMessageId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMessageIdTracker = false ;

                           public boolean isMessageIdSpecified(){
                               return localMessageIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMessageId(){
                               return localMessageId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MessageId
                               */
                               public void setMessageId(java.lang.String param){
                            localMessageIdTracker = param != null;
                                   
                                            this.localMessageId=param;
                                    

                               }
                            

                        /**
                        * field for Status
                        */

                        
                                    protected java.lang.String localStatus ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localStatusTracker = false ;

                           public boolean isStatusSpecified(){
                               return localStatusTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getStatus(){
                               return localStatus;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Status
                               */
                               public void setStatus(java.lang.String param){
                            localStatusTracker = param != null;
                                   
                                            this.localStatus=param;
                                    

                               }
                            

                        /**
                        * field for Type
                        */

                        
                                    protected java.lang.String localType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTypeTracker = false ;

                           public boolean isTypeSpecified(){
                               return localTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getType(){
                               return localType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Type
                               */
                               public void setType(java.lang.String param){
                            localTypeTracker = param != null;
                                   
                                            this.localType=param;
                                    

                               }
                            

                        /**
                        * field for FromEmail
                        */

                        
                                    protected java.lang.String localFromEmail ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFromEmailTracker = false ;

                           public boolean isFromEmailSpecified(){
                               return localFromEmailTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFromEmail(){
                               return localFromEmail;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FromEmail
                               */
                               public void setFromEmail(java.lang.String param){
                            localFromEmailTracker = param != null;
                                   
                                            this.localFromEmail=param;
                                    

                               }
                            

                        /**
                        * field for FromName
                        */

                        
                                    protected java.lang.String localFromName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFromNameTracker = false ;

                           public boolean isFromNameSpecified(){
                               return localFromNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFromName(){
                               return localFromName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FromName
                               */
                               public void setFromName(java.lang.String param){
                            localFromNameTracker = param != null;
                                   
                                            this.localFromName=param;
                                    

                               }
                            

                        /**
                        * field for ReplyEmail
                        */

                        
                                    protected java.lang.String localReplyEmail ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReplyEmailTracker = false ;

                           public boolean isReplyEmailSpecified(){
                               return localReplyEmailTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getReplyEmail(){
                               return localReplyEmail;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReplyEmail
                               */
                               public void setReplyEmail(java.lang.String param){
                            localReplyEmailTracker = param != null;
                                   
                                            this.localReplyEmail=param;
                                    

                               }
                            

                        /**
                        * field for Authentication
                        */

                        
                                    protected boolean localAuthentication ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAuthenticationTracker = false ;

                           public boolean isAuthenticationSpecified(){
                               return localAuthenticationTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getAuthentication(){
                               return localAuthentication;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Authentication
                               */
                               public void setAuthentication(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localAuthenticationTracker =
                                       true;
                                   
                                            this.localAuthentication=param;
                                    

                               }
                            

                        /**
                        * field for ReplyTracking
                        */

                        
                                    protected boolean localReplyTracking ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReplyTrackingTracker = false ;

                           public boolean isReplyTrackingSpecified(){
                               return localReplyTrackingTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getReplyTracking(){
                               return localReplyTracking;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReplyTracking
                               */
                               public void setReplyTracking(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localReplyTrackingTracker =
                                       true;
                                   
                                            this.localReplyTracking=param;
                                    

                               }
                            

                        /**
                        * field for MessageRuleId
                        */

                        
                                    protected java.lang.String localMessageRuleId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMessageRuleIdTracker = false ;

                           public boolean isMessageRuleIdSpecified(){
                               return localMessageRuleIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMessageRuleId(){
                               return localMessageRuleId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MessageRuleId
                               */
                               public void setMessageRuleId(java.lang.String param){
                            localMessageRuleIdTracker = param != null;
                                   
                                            this.localMessageRuleId=param;
                                    

                               }
                            

                        /**
                        * field for Optin
                        */

                        
                                    protected boolean localOptin ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOptinTracker = false ;

                           public boolean isOptinSpecified(){
                               return localOptinTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getOptin(){
                               return localOptin;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Optin
                               */
                               public void setOptin(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localOptinTracker =
                                       true;
                                   
                                            this.localOptin=param;
                                    

                               }
                            

                        /**
                        * field for Throttle
                        */

                        
                                    protected long localThrottle ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localThrottleTracker = false ;

                           public boolean isThrottleSpecified(){
                               return localThrottleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getThrottle(){
                               return localThrottle;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Throttle
                               */
                               public void setThrottle(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localThrottleTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localThrottle=param;
                                    

                               }
                            

                        /**
                        * field for FatigueOverride
                        */

                        
                                    protected boolean localFatigueOverride ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFatigueOverrideTracker = false ;

                           public boolean isFatigueOverrideSpecified(){
                               return localFatigueOverrideTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getFatigueOverride(){
                               return localFatigueOverride;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FatigueOverride
                               */
                               public void setFatigueOverride(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localFatigueOverrideTracker =
                                       true;
                                   
                                            this.localFatigueOverride=param;
                                    

                               }
                            

                        /**
                        * field for Content
                        * This was an Array!
                        */

                        
                                    protected com.bronto.api.v4.MessageContentObject[] localContent ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localContentTracker = false ;

                           public boolean isContentSpecified(){
                               return localContentTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.bronto.api.v4.MessageContentObject[]
                           */
                           public  com.bronto.api.v4.MessageContentObject[] getContent(){
                               return localContent;
                           }

                           
                        


                               
                              /**
                               * validate the array for Content
                               */
                              protected void validateContent(com.bronto.api.v4.MessageContentObject[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Content
                              */
                              public void setContent(com.bronto.api.v4.MessageContentObject[] param){
                              
                                   validateContent(param);

                               localContentTracker = true;
                                      
                                      this.localContent=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param com.bronto.api.v4.MessageContentObject
                             */
                             public void addContent(com.bronto.api.v4.MessageContentObject param){
                                   if (localContent == null){
                                   localContent = new com.bronto.api.v4.MessageContentObject[]{};
                                   }

                            
                                 //update the setting tracker
                                localContentTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localContent);
                               list.add(param);
                               this.localContent =
                             (com.bronto.api.v4.MessageContentObject[])list.toArray(
                            new com.bronto.api.v4.MessageContentObject[list.size()]);

                             }
                             

                        /**
                        * field for Recipients
                        * This was an Array!
                        */

                        
                                    protected com.bronto.api.v4.DeliveryRecipientObject[] localRecipients ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRecipientsTracker = false ;

                           public boolean isRecipientsSpecified(){
                               return localRecipientsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.bronto.api.v4.DeliveryRecipientObject[]
                           */
                           public  com.bronto.api.v4.DeliveryRecipientObject[] getRecipients(){
                               return localRecipients;
                           }

                           
                        


                               
                              /**
                               * validate the array for Recipients
                               */
                              protected void validateRecipients(com.bronto.api.v4.DeliveryRecipientObject[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Recipients
                              */
                              public void setRecipients(com.bronto.api.v4.DeliveryRecipientObject[] param){
                              
                                   validateRecipients(param);

                               localRecipientsTracker = true;
                                      
                                      this.localRecipients=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param com.bronto.api.v4.DeliveryRecipientObject
                             */
                             public void addRecipients(com.bronto.api.v4.DeliveryRecipientObject param){
                                   if (localRecipients == null){
                                   localRecipients = new com.bronto.api.v4.DeliveryRecipientObject[]{};
                                   }

                            
                                 //update the setting tracker
                                localRecipientsTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localRecipients);
                               list.add(param);
                               this.localRecipients =
                             (com.bronto.api.v4.DeliveryRecipientObject[])list.toArray(
                            new com.bronto.api.v4.DeliveryRecipientObject[list.size()]);

                             }
                             

                        /**
                        * field for Fields
                        * This was an Array!
                        */

                        
                                    protected com.bronto.api.v4.MessageFieldObject[] localFields ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFieldsTracker = false ;

                           public boolean isFieldsSpecified(){
                               return localFieldsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.bronto.api.v4.MessageFieldObject[]
                           */
                           public  com.bronto.api.v4.MessageFieldObject[] getFields(){
                               return localFields;
                           }

                           
                        


                               
                              /**
                               * validate the array for Fields
                               */
                              protected void validateFields(com.bronto.api.v4.MessageFieldObject[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Fields
                              */
                              public void setFields(com.bronto.api.v4.MessageFieldObject[] param){
                              
                                   validateFields(param);

                               localFieldsTracker = true;
                                      
                                      this.localFields=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param com.bronto.api.v4.MessageFieldObject
                             */
                             public void addFields(com.bronto.api.v4.MessageFieldObject param){
                                   if (localFields == null){
                                   localFields = new com.bronto.api.v4.MessageFieldObject[]{};
                                   }

                            
                                 //update the setting tracker
                                localFieldsTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localFields);
                               list.add(param);
                               this.localFields =
                             (com.bronto.api.v4.MessageFieldObject[])list.toArray(
                            new com.bronto.api.v4.MessageFieldObject[list.size()]);

                             }
                             

                        /**
                        * field for NumSends
                        */

                        
                                    protected long localNumSends ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumSendsTracker = false ;

                           public boolean isNumSendsSpecified(){
                               return localNumSendsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumSends(){
                               return localNumSends;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumSends
                               */
                               public void setNumSends(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumSendsTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumSends=param;
                                    

                               }
                            

                        /**
                        * field for NumDeliveries
                        */

                        
                                    protected long localNumDeliveries ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumDeliveriesTracker = false ;

                           public boolean isNumDeliveriesSpecified(){
                               return localNumDeliveriesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumDeliveries(){
                               return localNumDeliveries;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumDeliveries
                               */
                               public void setNumDeliveries(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumDeliveriesTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumDeliveries=param;
                                    

                               }
                            

                        /**
                        * field for NumHardBadEmail
                        */

                        
                                    protected long localNumHardBadEmail ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumHardBadEmailTracker = false ;

                           public boolean isNumHardBadEmailSpecified(){
                               return localNumHardBadEmailTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumHardBadEmail(){
                               return localNumHardBadEmail;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumHardBadEmail
                               */
                               public void setNumHardBadEmail(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumHardBadEmailTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumHardBadEmail=param;
                                    

                               }
                            

                        /**
                        * field for NumHardDestUnreach
                        */

                        
                                    protected long localNumHardDestUnreach ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumHardDestUnreachTracker = false ;

                           public boolean isNumHardDestUnreachSpecified(){
                               return localNumHardDestUnreachTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumHardDestUnreach(){
                               return localNumHardDestUnreach;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumHardDestUnreach
                               */
                               public void setNumHardDestUnreach(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumHardDestUnreachTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumHardDestUnreach=param;
                                    

                               }
                            

                        /**
                        * field for NumHardMessageContent
                        */

                        
                                    protected long localNumHardMessageContent ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumHardMessageContentTracker = false ;

                           public boolean isNumHardMessageContentSpecified(){
                               return localNumHardMessageContentTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumHardMessageContent(){
                               return localNumHardMessageContent;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumHardMessageContent
                               */
                               public void setNumHardMessageContent(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumHardMessageContentTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumHardMessageContent=param;
                                    

                               }
                            

                        /**
                        * field for NumHardBounces
                        */

                        
                                    protected long localNumHardBounces ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumHardBouncesTracker = false ;

                           public boolean isNumHardBouncesSpecified(){
                               return localNumHardBouncesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumHardBounces(){
                               return localNumHardBounces;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumHardBounces
                               */
                               public void setNumHardBounces(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumHardBouncesTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumHardBounces=param;
                                    

                               }
                            

                        /**
                        * field for NumSoftBadEmail
                        */

                        
                                    protected long localNumSoftBadEmail ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumSoftBadEmailTracker = false ;

                           public boolean isNumSoftBadEmailSpecified(){
                               return localNumSoftBadEmailTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumSoftBadEmail(){
                               return localNumSoftBadEmail;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumSoftBadEmail
                               */
                               public void setNumSoftBadEmail(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumSoftBadEmailTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumSoftBadEmail=param;
                                    

                               }
                            

                        /**
                        * field for NumSoftDestUnreach
                        */

                        
                                    protected long localNumSoftDestUnreach ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumSoftDestUnreachTracker = false ;

                           public boolean isNumSoftDestUnreachSpecified(){
                               return localNumSoftDestUnreachTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumSoftDestUnreach(){
                               return localNumSoftDestUnreach;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumSoftDestUnreach
                               */
                               public void setNumSoftDestUnreach(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumSoftDestUnreachTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumSoftDestUnreach=param;
                                    

                               }
                            

                        /**
                        * field for NumSoftMessageContent
                        */

                        
                                    protected long localNumSoftMessageContent ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumSoftMessageContentTracker = false ;

                           public boolean isNumSoftMessageContentSpecified(){
                               return localNumSoftMessageContentTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumSoftMessageContent(){
                               return localNumSoftMessageContent;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumSoftMessageContent
                               */
                               public void setNumSoftMessageContent(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumSoftMessageContentTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumSoftMessageContent=param;
                                    

                               }
                            

                        /**
                        * field for NumSoftBounces
                        */

                        
                                    protected long localNumSoftBounces ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumSoftBouncesTracker = false ;

                           public boolean isNumSoftBouncesSpecified(){
                               return localNumSoftBouncesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumSoftBounces(){
                               return localNumSoftBounces;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumSoftBounces
                               */
                               public void setNumSoftBounces(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumSoftBouncesTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumSoftBounces=param;
                                    

                               }
                            

                        /**
                        * field for NumOtherBounces
                        */

                        
                                    protected long localNumOtherBounces ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumOtherBouncesTracker = false ;

                           public boolean isNumOtherBouncesSpecified(){
                               return localNumOtherBouncesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumOtherBounces(){
                               return localNumOtherBounces;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumOtherBounces
                               */
                               public void setNumOtherBounces(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumOtherBouncesTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumOtherBounces=param;
                                    

                               }
                            

                        /**
                        * field for NumBounces
                        */

                        
                                    protected long localNumBounces ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumBouncesTracker = false ;

                           public boolean isNumBouncesSpecified(){
                               return localNumBouncesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumBounces(){
                               return localNumBounces;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumBounces
                               */
                               public void setNumBounces(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumBouncesTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumBounces=param;
                                    

                               }
                            

                        /**
                        * field for UniqOpens
                        */

                        
                                    protected long localUniqOpens ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUniqOpensTracker = false ;

                           public boolean isUniqOpensSpecified(){
                               return localUniqOpensTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getUniqOpens(){
                               return localUniqOpens;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param UniqOpens
                               */
                               public void setUniqOpens(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localUniqOpensTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localUniqOpens=param;
                                    

                               }
                            

                        /**
                        * field for NumOpens
                        */

                        
                                    protected long localNumOpens ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumOpensTracker = false ;

                           public boolean isNumOpensSpecified(){
                               return localNumOpensTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumOpens(){
                               return localNumOpens;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumOpens
                               */
                               public void setNumOpens(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumOpensTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumOpens=param;
                                    

                               }
                            

                        /**
                        * field for AvgOpens
                        */

                        
                                    protected double localAvgOpens ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAvgOpensTracker = false ;

                           public boolean isAvgOpensSpecified(){
                               return localAvgOpensTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getAvgOpens(){
                               return localAvgOpens;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AvgOpens
                               */
                               public void setAvgOpens(double param){
                            
                                       // setting primitive attribute tracker to true
                                       localAvgOpensTracker =
                                       !java.lang.Double.isNaN(param);
                                   
                                            this.localAvgOpens=param;
                                    

                               }
                            

                        /**
                        * field for UniqClicks
                        */

                        
                                    protected long localUniqClicks ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUniqClicksTracker = false ;

                           public boolean isUniqClicksSpecified(){
                               return localUniqClicksTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getUniqClicks(){
                               return localUniqClicks;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param UniqClicks
                               */
                               public void setUniqClicks(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localUniqClicksTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localUniqClicks=param;
                                    

                               }
                            

                        /**
                        * field for NumClicks
                        */

                        
                                    protected long localNumClicks ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumClicksTracker = false ;

                           public boolean isNumClicksSpecified(){
                               return localNumClicksTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumClicks(){
                               return localNumClicks;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumClicks
                               */
                               public void setNumClicks(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumClicksTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumClicks=param;
                                    

                               }
                            

                        /**
                        * field for AvgClicks
                        */

                        
                                    protected double localAvgClicks ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAvgClicksTracker = false ;

                           public boolean isAvgClicksSpecified(){
                               return localAvgClicksTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getAvgClicks(){
                               return localAvgClicks;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AvgClicks
                               */
                               public void setAvgClicks(double param){
                            
                                       // setting primitive attribute tracker to true
                                       localAvgClicksTracker =
                                       !java.lang.Double.isNaN(param);
                                   
                                            this.localAvgClicks=param;
                                    

                               }
                            

                        /**
                        * field for UniqConversions
                        */

                        
                                    protected long localUniqConversions ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUniqConversionsTracker = false ;

                           public boolean isUniqConversionsSpecified(){
                               return localUniqConversionsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getUniqConversions(){
                               return localUniqConversions;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param UniqConversions
                               */
                               public void setUniqConversions(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localUniqConversionsTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localUniqConversions=param;
                                    

                               }
                            

                        /**
                        * field for NumConversions
                        */

                        
                                    protected long localNumConversions ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumConversionsTracker = false ;

                           public boolean isNumConversionsSpecified(){
                               return localNumConversionsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumConversions(){
                               return localNumConversions;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumConversions
                               */
                               public void setNumConversions(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumConversionsTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumConversions=param;
                                    

                               }
                            

                        /**
                        * field for AvgConversions
                        */

                        
                                    protected double localAvgConversions ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAvgConversionsTracker = false ;

                           public boolean isAvgConversionsSpecified(){
                               return localAvgConversionsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getAvgConversions(){
                               return localAvgConversions;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AvgConversions
                               */
                               public void setAvgConversions(double param){
                            
                                       // setting primitive attribute tracker to true
                                       localAvgConversionsTracker =
                                       !java.lang.Double.isNaN(param);
                                   
                                            this.localAvgConversions=param;
                                    

                               }
                            

                        /**
                        * field for Revenue
                        */

                        
                                    protected java.math.BigDecimal localRevenue ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRevenueTracker = false ;

                           public boolean isRevenueSpecified(){
                               return localRevenueTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.math.BigDecimal
                           */
                           public  java.math.BigDecimal getRevenue(){
                               return localRevenue;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Revenue
                               */
                               public void setRevenue(java.math.BigDecimal param){
                            localRevenueTracker = param != null;
                                   
                                            this.localRevenue=param;
                                    

                               }
                            

                        /**
                        * field for NumSurveyResponses
                        */

                        
                                    protected long localNumSurveyResponses ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumSurveyResponsesTracker = false ;

                           public boolean isNumSurveyResponsesSpecified(){
                               return localNumSurveyResponsesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumSurveyResponses(){
                               return localNumSurveyResponses;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumSurveyResponses
                               */
                               public void setNumSurveyResponses(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumSurveyResponsesTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumSurveyResponses=param;
                                    

                               }
                            

                        /**
                        * field for NumFriendForwards
                        */

                        
                                    protected long localNumFriendForwards ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumFriendForwardsTracker = false ;

                           public boolean isNumFriendForwardsSpecified(){
                               return localNumFriendForwardsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumFriendForwards(){
                               return localNumFriendForwards;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumFriendForwards
                               */
                               public void setNumFriendForwards(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumFriendForwardsTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumFriendForwards=param;
                                    

                               }
                            

                        /**
                        * field for NumContactUpdates
                        */

                        
                                    protected long localNumContactUpdates ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumContactUpdatesTracker = false ;

                           public boolean isNumContactUpdatesSpecified(){
                               return localNumContactUpdatesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumContactUpdates(){
                               return localNumContactUpdates;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumContactUpdates
                               */
                               public void setNumContactUpdates(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumContactUpdatesTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumContactUpdates=param;
                                    

                               }
                            

                        /**
                        * field for NumUnsubscribesByPrefs
                        */

                        
                                    protected long localNumUnsubscribesByPrefs ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumUnsubscribesByPrefsTracker = false ;

                           public boolean isNumUnsubscribesByPrefsSpecified(){
                               return localNumUnsubscribesByPrefsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumUnsubscribesByPrefs(){
                               return localNumUnsubscribesByPrefs;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumUnsubscribesByPrefs
                               */
                               public void setNumUnsubscribesByPrefs(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumUnsubscribesByPrefsTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumUnsubscribesByPrefs=param;
                                    

                               }
                            

                        /**
                        * field for NumUnsubscribesByComplaint
                        */

                        
                                    protected long localNumUnsubscribesByComplaint ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumUnsubscribesByComplaintTracker = false ;

                           public boolean isNumUnsubscribesByComplaintSpecified(){
                               return localNumUnsubscribesByComplaintTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumUnsubscribesByComplaint(){
                               return localNumUnsubscribesByComplaint;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumUnsubscribesByComplaint
                               */
                               public void setNumUnsubscribesByComplaint(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumUnsubscribesByComplaintTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumUnsubscribesByComplaint=param;
                                    

                               }
                            

                        /**
                        * field for NumContactLoss
                        */

                        
                                    protected long localNumContactLoss ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumContactLossTracker = false ;

                           public boolean isNumContactLossSpecified(){
                               return localNumContactLossTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumContactLoss(){
                               return localNumContactLoss;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumContactLoss
                               */
                               public void setNumContactLoss(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumContactLossTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumContactLoss=param;
                                    

                               }
                            

                        /**
                        * field for NumContactLossBounces
                        */

                        
                                    protected long localNumContactLossBounces ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumContactLossBouncesTracker = false ;

                           public boolean isNumContactLossBouncesSpecified(){
                               return localNumContactLossBouncesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumContactLossBounces(){
                               return localNumContactLossBounces;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumContactLossBounces
                               */
                               public void setNumContactLossBounces(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumContactLossBouncesTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumContactLossBounces=param;
                                    

                               }
                            

                        /**
                        * field for DeliveryRate
                        */

                        
                                    protected double localDeliveryRate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDeliveryRateTracker = false ;

                           public boolean isDeliveryRateSpecified(){
                               return localDeliveryRateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getDeliveryRate(){
                               return localDeliveryRate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DeliveryRate
                               */
                               public void setDeliveryRate(double param){
                            
                                       // setting primitive attribute tracker to true
                                       localDeliveryRateTracker =
                                       !java.lang.Double.isNaN(param);
                                   
                                            this.localDeliveryRate=param;
                                    

                               }
                            

                        /**
                        * field for OpenRate
                        */

                        
                                    protected double localOpenRate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOpenRateTracker = false ;

                           public boolean isOpenRateSpecified(){
                               return localOpenRateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getOpenRate(){
                               return localOpenRate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OpenRate
                               */
                               public void setOpenRate(double param){
                            
                                       // setting primitive attribute tracker to true
                                       localOpenRateTracker =
                                       !java.lang.Double.isNaN(param);
                                   
                                            this.localOpenRate=param;
                                    

                               }
                            

                        /**
                        * field for ClickRate
                        */

                        
                                    protected double localClickRate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localClickRateTracker = false ;

                           public boolean isClickRateSpecified(){
                               return localClickRateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getClickRate(){
                               return localClickRate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ClickRate
                               */
                               public void setClickRate(double param){
                            
                                       // setting primitive attribute tracker to true
                                       localClickRateTracker =
                                       !java.lang.Double.isNaN(param);
                                   
                                            this.localClickRate=param;
                                    

                               }
                            

                        /**
                        * field for ClickThroughRate
                        */

                        
                                    protected double localClickThroughRate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localClickThroughRateTracker = false ;

                           public boolean isClickThroughRateSpecified(){
                               return localClickThroughRateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getClickThroughRate(){
                               return localClickThroughRate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ClickThroughRate
                               */
                               public void setClickThroughRate(double param){
                            
                                       // setting primitive attribute tracker to true
                                       localClickThroughRateTracker =
                                       !java.lang.Double.isNaN(param);
                                   
                                            this.localClickThroughRate=param;
                                    

                               }
                            

                        /**
                        * field for ConversionRate
                        */

                        
                                    protected double localConversionRate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localConversionRateTracker = false ;

                           public boolean isConversionRateSpecified(){
                               return localConversionRateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getConversionRate(){
                               return localConversionRate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ConversionRate
                               */
                               public void setConversionRate(double param){
                            
                                       // setting primitive attribute tracker to true
                                       localConversionRateTracker =
                                       !java.lang.Double.isNaN(param);
                                   
                                            this.localConversionRate=param;
                                    

                               }
                            

                        /**
                        * field for BounceRate
                        */

                        
                                    protected double localBounceRate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBounceRateTracker = false ;

                           public boolean isBounceRateSpecified(){
                               return localBounceRateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getBounceRate(){
                               return localBounceRate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BounceRate
                               */
                               public void setBounceRate(double param){
                            
                                       // setting primitive attribute tracker to true
                                       localBounceRateTracker =
                                       !java.lang.Double.isNaN(param);
                                   
                                            this.localBounceRate=param;
                                    

                               }
                            

                        /**
                        * field for ComplaintRate
                        */

                        
                                    protected double localComplaintRate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localComplaintRateTracker = false ;

                           public boolean isComplaintRateSpecified(){
                               return localComplaintRateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getComplaintRate(){
                               return localComplaintRate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ComplaintRate
                               */
                               public void setComplaintRate(double param){
                            
                                       // setting primitive attribute tracker to true
                                       localComplaintRateTracker =
                                       !java.lang.Double.isNaN(param);
                                   
                                            this.localComplaintRate=param;
                                    

                               }
                            

                        /**
                        * field for ContactLossRate
                        */

                        
                                    protected double localContactLossRate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localContactLossRateTracker = false ;

                           public boolean isContactLossRateSpecified(){
                               return localContactLossRateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getContactLossRate(){
                               return localContactLossRate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ContactLossRate
                               */
                               public void setContactLossRate(double param){
                            
                                       // setting primitive attribute tracker to true
                                       localContactLossRateTracker =
                                       !java.lang.Double.isNaN(param);
                                   
                                            this.localContactLossRate=param;
                                    

                               }
                            

                        /**
                        * field for NumSocialShares
                        */

                        
                                    protected long localNumSocialShares ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumSocialSharesTracker = false ;

                           public boolean isNumSocialSharesSpecified(){
                               return localNumSocialSharesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumSocialShares(){
                               return localNumSocialShares;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumSocialShares
                               */
                               public void setNumSocialShares(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumSocialSharesTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumSocialShares=param;
                                    

                               }
                            

                        /**
                        * field for NumSharesFacebook
                        */

                        
                                    protected long localNumSharesFacebook ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumSharesFacebookTracker = false ;

                           public boolean isNumSharesFacebookSpecified(){
                               return localNumSharesFacebookTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumSharesFacebook(){
                               return localNumSharesFacebook;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumSharesFacebook
                               */
                               public void setNumSharesFacebook(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumSharesFacebookTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumSharesFacebook=param;
                                    

                               }
                            

                        /**
                        * field for NumSharesTwitter
                        */

                        
                                    protected long localNumSharesTwitter ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumSharesTwitterTracker = false ;

                           public boolean isNumSharesTwitterSpecified(){
                               return localNumSharesTwitterTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumSharesTwitter(){
                               return localNumSharesTwitter;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumSharesTwitter
                               */
                               public void setNumSharesTwitter(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumSharesTwitterTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumSharesTwitter=param;
                                    

                               }
                            

                        /**
                        * field for NumSharesLinkedIn
                        */

                        
                                    protected long localNumSharesLinkedIn ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumSharesLinkedInTracker = false ;

                           public boolean isNumSharesLinkedInSpecified(){
                               return localNumSharesLinkedInTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumSharesLinkedIn(){
                               return localNumSharesLinkedIn;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumSharesLinkedIn
                               */
                               public void setNumSharesLinkedIn(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumSharesLinkedInTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumSharesLinkedIn=param;
                                    

                               }
                            

                        /**
                        * field for NumSharesDigg
                        */

                        
                                    protected long localNumSharesDigg ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumSharesDiggTracker = false ;

                           public boolean isNumSharesDiggSpecified(){
                               return localNumSharesDiggTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumSharesDigg(){
                               return localNumSharesDigg;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumSharesDigg
                               */
                               public void setNumSharesDigg(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumSharesDiggTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumSharesDigg=param;
                                    

                               }
                            

                        /**
                        * field for NumSharesMySpace
                        */

                        
                                    protected long localNumSharesMySpace ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumSharesMySpaceTracker = false ;

                           public boolean isNumSharesMySpaceSpecified(){
                               return localNumSharesMySpaceTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumSharesMySpace(){
                               return localNumSharesMySpace;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumSharesMySpace
                               */
                               public void setNumSharesMySpace(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumSharesMySpaceTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumSharesMySpace=param;
                                    

                               }
                            

                        /**
                        * field for NumViewsFacebook
                        */

                        
                                    protected long localNumViewsFacebook ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumViewsFacebookTracker = false ;

                           public boolean isNumViewsFacebookSpecified(){
                               return localNumViewsFacebookTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumViewsFacebook(){
                               return localNumViewsFacebook;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumViewsFacebook
                               */
                               public void setNumViewsFacebook(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumViewsFacebookTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumViewsFacebook=param;
                                    

                               }
                            

                        /**
                        * field for NumViewsTwitter
                        */

                        
                                    protected long localNumViewsTwitter ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumViewsTwitterTracker = false ;

                           public boolean isNumViewsTwitterSpecified(){
                               return localNumViewsTwitterTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumViewsTwitter(){
                               return localNumViewsTwitter;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumViewsTwitter
                               */
                               public void setNumViewsTwitter(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumViewsTwitterTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumViewsTwitter=param;
                                    

                               }
                            

                        /**
                        * field for NumViewsLinkedIn
                        */

                        
                                    protected long localNumViewsLinkedIn ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumViewsLinkedInTracker = false ;

                           public boolean isNumViewsLinkedInSpecified(){
                               return localNumViewsLinkedInTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumViewsLinkedIn(){
                               return localNumViewsLinkedIn;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumViewsLinkedIn
                               */
                               public void setNumViewsLinkedIn(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumViewsLinkedInTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumViewsLinkedIn=param;
                                    

                               }
                            

                        /**
                        * field for NumViewsDigg
                        */

                        
                                    protected long localNumViewsDigg ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumViewsDiggTracker = false ;

                           public boolean isNumViewsDiggSpecified(){
                               return localNumViewsDiggTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumViewsDigg(){
                               return localNumViewsDigg;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumViewsDigg
                               */
                               public void setNumViewsDigg(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumViewsDiggTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumViewsDigg=param;
                                    

                               }
                            

                        /**
                        * field for NumViewsMySpace
                        */

                        
                                    protected long localNumViewsMySpace ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumViewsMySpaceTracker = false ;

                           public boolean isNumViewsMySpaceSpecified(){
                               return localNumViewsMySpaceTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumViewsMySpace(){
                               return localNumViewsMySpace;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumViewsMySpace
                               */
                               public void setNumViewsMySpace(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumViewsMySpaceTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumViewsMySpace=param;
                                    

                               }
                            

                        /**
                        * field for NumSocialViews
                        */

                        
                                    protected long localNumSocialViews ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumSocialViewsTracker = false ;

                           public boolean isNumSocialViewsSpecified(){
                               return localNumSocialViewsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getNumSocialViews(){
                               return localNumSocialViews;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumSocialViews
                               */
                               public void setNumSocialViews(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localNumSocialViewsTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localNumSocialViews=param;
                                    

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
                           namespacePrefix+":deliveryObject",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "deliveryObject",
                           xmlWriter);
                   }

               
                   }
                if (localIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "id", xmlWriter);
                             

                                          if (localId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("id cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localStartTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "start", xmlWriter);
                             

                                          if (localStart==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("start cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStart));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMessageIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "messageId", xmlWriter);
                             

                                          if (localMessageId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("messageId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMessageId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localStatusTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "status", xmlWriter);
                             

                                          if (localStatus==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localStatus);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTypeTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "type", xmlWriter);
                             

                                          if (localType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("type cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFromEmailTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "fromEmail", xmlWriter);
                             

                                          if (localFromEmail==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("fromEmail cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFromEmail);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFromNameTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "fromName", xmlWriter);
                             

                                          if (localFromName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("fromName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFromName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localReplyEmailTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "replyEmail", xmlWriter);
                             

                                          if (localReplyEmail==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("replyEmail cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localReplyEmail);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAuthenticationTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "authentication", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("authentication cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthentication));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localReplyTrackingTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "replyTracking", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("replyTracking cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReplyTracking));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMessageRuleIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "messageRuleId", xmlWriter);
                             

                                          if (localMessageRuleId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("messageRuleId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMessageRuleId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOptinTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "optin", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("optin cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOptin));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localThrottleTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "throttle", xmlWriter);
                             
                                               if (localThrottle==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("throttle cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localThrottle));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFatigueOverrideTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "fatigueOverride", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("fatigueOverride cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFatigueOverride));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localContentTracker){
                                       if (localContent!=null){
                                            for (int i = 0;i < localContent.length;i++){
                                                if (localContent[i] != null){
                                                 localContent[i].serialize(new javax.xml.namespace.QName("","content"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                            writeStartElement(null, "", "content", xmlWriter);

                                                           // write the nil attribute
                                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                           xmlWriter.writeEndElement();
                                                    
                                                }

                                            }
                                     } else {
                                        
                                                writeStartElement(null, "", "content", xmlWriter);

                                               // write the nil attribute
                                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                               xmlWriter.writeEndElement();
                                        
                                    }
                                 } if (localRecipientsTracker){
                                       if (localRecipients!=null){
                                            for (int i = 0;i < localRecipients.length;i++){
                                                if (localRecipients[i] != null){
                                                 localRecipients[i].serialize(new javax.xml.namespace.QName("","recipients"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                            writeStartElement(null, "", "recipients", xmlWriter);

                                                           // write the nil attribute
                                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                           xmlWriter.writeEndElement();
                                                    
                                                }

                                            }
                                     } else {
                                        
                                                writeStartElement(null, "", "recipients", xmlWriter);

                                               // write the nil attribute
                                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                               xmlWriter.writeEndElement();
                                        
                                    }
                                 } if (localFieldsTracker){
                                       if (localFields!=null){
                                            for (int i = 0;i < localFields.length;i++){
                                                if (localFields[i] != null){
                                                 localFields[i].serialize(new javax.xml.namespace.QName("","fields"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                            writeStartElement(null, "", "fields", xmlWriter);

                                                           // write the nil attribute
                                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                           xmlWriter.writeEndElement();
                                                    
                                                }

                                            }
                                     } else {
                                        
                                                writeStartElement(null, "", "fields", xmlWriter);

                                               // write the nil attribute
                                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                               xmlWriter.writeEndElement();
                                        
                                    }
                                 } if (localNumSendsTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numSends", xmlWriter);
                             
                                               if (localNumSends==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numSends cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSends));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumDeliveriesTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numDeliveries", xmlWriter);
                             
                                               if (localNumDeliveries==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numDeliveries cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumDeliveries));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumHardBadEmailTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numHardBadEmail", xmlWriter);
                             
                                               if (localNumHardBadEmail==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numHardBadEmail cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumHardBadEmail));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumHardDestUnreachTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numHardDestUnreach", xmlWriter);
                             
                                               if (localNumHardDestUnreach==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numHardDestUnreach cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumHardDestUnreach));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumHardMessageContentTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numHardMessageContent", xmlWriter);
                             
                                               if (localNumHardMessageContent==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numHardMessageContent cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumHardMessageContent));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumHardBouncesTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numHardBounces", xmlWriter);
                             
                                               if (localNumHardBounces==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numHardBounces cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumHardBounces));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumSoftBadEmailTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numSoftBadEmail", xmlWriter);
                             
                                               if (localNumSoftBadEmail==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numSoftBadEmail cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSoftBadEmail));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumSoftDestUnreachTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numSoftDestUnreach", xmlWriter);
                             
                                               if (localNumSoftDestUnreach==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numSoftDestUnreach cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSoftDestUnreach));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumSoftMessageContentTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numSoftMessageContent", xmlWriter);
                             
                                               if (localNumSoftMessageContent==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numSoftMessageContent cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSoftMessageContent));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumSoftBouncesTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numSoftBounces", xmlWriter);
                             
                                               if (localNumSoftBounces==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numSoftBounces cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSoftBounces));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumOtherBouncesTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numOtherBounces", xmlWriter);
                             
                                               if (localNumOtherBounces==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numOtherBounces cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumOtherBounces));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumBouncesTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numBounces", xmlWriter);
                             
                                               if (localNumBounces==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numBounces cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumBounces));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUniqOpensTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "uniqOpens", xmlWriter);
                             
                                               if (localUniqOpens==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("uniqOpens cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUniqOpens));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumOpensTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numOpens", xmlWriter);
                             
                                               if (localNumOpens==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numOpens cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumOpens));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAvgOpensTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "avgOpens", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localAvgOpens)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("avgOpens cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAvgOpens));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUniqClicksTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "uniqClicks", xmlWriter);
                             
                                               if (localUniqClicks==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("uniqClicks cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUniqClicks));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumClicksTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numClicks", xmlWriter);
                             
                                               if (localNumClicks==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numClicks cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumClicks));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAvgClicksTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "avgClicks", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localAvgClicks)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("avgClicks cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAvgClicks));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUniqConversionsTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "uniqConversions", xmlWriter);
                             
                                               if (localUniqConversions==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("uniqConversions cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUniqConversions));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumConversionsTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numConversions", xmlWriter);
                             
                                               if (localNumConversions==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numConversions cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumConversions));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAvgConversionsTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "avgConversions", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localAvgConversions)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("avgConversions cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAvgConversions));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRevenueTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "revenue", xmlWriter);
                             

                                          if (localRevenue==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("revenue cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRevenue));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumSurveyResponsesTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numSurveyResponses", xmlWriter);
                             
                                               if (localNumSurveyResponses==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numSurveyResponses cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSurveyResponses));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumFriendForwardsTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numFriendForwards", xmlWriter);
                             
                                               if (localNumFriendForwards==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numFriendForwards cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumFriendForwards));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumContactUpdatesTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numContactUpdates", xmlWriter);
                             
                                               if (localNumContactUpdates==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numContactUpdates cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumContactUpdates));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumUnsubscribesByPrefsTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numUnsubscribesByPrefs", xmlWriter);
                             
                                               if (localNumUnsubscribesByPrefs==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numUnsubscribesByPrefs cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumUnsubscribesByPrefs));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumUnsubscribesByComplaintTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numUnsubscribesByComplaint", xmlWriter);
                             
                                               if (localNumUnsubscribesByComplaint==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numUnsubscribesByComplaint cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumUnsubscribesByComplaint));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumContactLossTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numContactLoss", xmlWriter);
                             
                                               if (localNumContactLoss==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numContactLoss cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumContactLoss));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumContactLossBouncesTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numContactLossBounces", xmlWriter);
                             
                                               if (localNumContactLossBounces==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numContactLossBounces cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumContactLossBounces));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDeliveryRateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "deliveryRate", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localDeliveryRate)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("deliveryRate cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDeliveryRate));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOpenRateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "openRate", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localOpenRate)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("openRate cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOpenRate));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localClickRateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "clickRate", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localClickRate)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("clickRate cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localClickRate));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localClickThroughRateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "clickThroughRate", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localClickThroughRate)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("clickThroughRate cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localClickThroughRate));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localConversionRateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "conversionRate", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localConversionRate)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("conversionRate cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localConversionRate));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBounceRateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "bounceRate", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localBounceRate)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("bounceRate cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBounceRate));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localComplaintRateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "complaintRate", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localComplaintRate)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("complaintRate cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localComplaintRate));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localContactLossRateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "contactLossRate", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localContactLossRate)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("contactLossRate cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localContactLossRate));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumSocialSharesTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numSocialShares", xmlWriter);
                             
                                               if (localNumSocialShares==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numSocialShares cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSocialShares));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumSharesFacebookTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numSharesFacebook", xmlWriter);
                             
                                               if (localNumSharesFacebook==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numSharesFacebook cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSharesFacebook));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumSharesTwitterTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numSharesTwitter", xmlWriter);
                             
                                               if (localNumSharesTwitter==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numSharesTwitter cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSharesTwitter));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumSharesLinkedInTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numSharesLinkedIn", xmlWriter);
                             
                                               if (localNumSharesLinkedIn==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numSharesLinkedIn cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSharesLinkedIn));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumSharesDiggTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numSharesDigg", xmlWriter);
                             
                                               if (localNumSharesDigg==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numSharesDigg cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSharesDigg));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumSharesMySpaceTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numSharesMySpace", xmlWriter);
                             
                                               if (localNumSharesMySpace==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numSharesMySpace cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSharesMySpace));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumViewsFacebookTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numViewsFacebook", xmlWriter);
                             
                                               if (localNumViewsFacebook==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numViewsFacebook cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumViewsFacebook));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumViewsTwitterTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numViewsTwitter", xmlWriter);
                             
                                               if (localNumViewsTwitter==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numViewsTwitter cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumViewsTwitter));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumViewsLinkedInTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numViewsLinkedIn", xmlWriter);
                             
                                               if (localNumViewsLinkedIn==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numViewsLinkedIn cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumViewsLinkedIn));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumViewsDiggTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numViewsDigg", xmlWriter);
                             
                                               if (localNumViewsDigg==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numViewsDigg cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumViewsDigg));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumViewsMySpaceTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numViewsMySpace", xmlWriter);
                             
                                               if (localNumViewsMySpace==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numViewsMySpace cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumViewsMySpace));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumSocialViewsTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "numSocialViews", xmlWriter);
                             
                                               if (localNumSocialViews==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("numSocialViews cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSocialViews));
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

                 if (localIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "id"));
                                 
                                        if (localId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("id cannot be null!!");
                                        }
                                    } if (localStartTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "start"));
                                 
                                        if (localStart != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStart));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("start cannot be null!!");
                                        }
                                    } if (localMessageIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "messageId"));
                                 
                                        if (localMessageId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMessageId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("messageId cannot be null!!");
                                        }
                                    } if (localStatusTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "status"));
                                 
                                        if (localStatus != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatus));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
                                        }
                                    } if (localTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "type"));
                                 
                                        if (localType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("type cannot be null!!");
                                        }
                                    } if (localFromEmailTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "fromEmail"));
                                 
                                        if (localFromEmail != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFromEmail));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("fromEmail cannot be null!!");
                                        }
                                    } if (localFromNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "fromName"));
                                 
                                        if (localFromName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFromName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("fromName cannot be null!!");
                                        }
                                    } if (localReplyEmailTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "replyEmail"));
                                 
                                        if (localReplyEmail != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReplyEmail));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("replyEmail cannot be null!!");
                                        }
                                    } if (localAuthenticationTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "authentication"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthentication));
                            } if (localReplyTrackingTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "replyTracking"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReplyTracking));
                            } if (localMessageRuleIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "messageRuleId"));
                                 
                                        if (localMessageRuleId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMessageRuleId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("messageRuleId cannot be null!!");
                                        }
                                    } if (localOptinTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "optin"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOptin));
                            } if (localThrottleTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "throttle"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localThrottle));
                            } if (localFatigueOverrideTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "fatigueOverride"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFatigueOverride));
                            } if (localContentTracker){
                             if (localContent!=null) {
                                 for (int i = 0;i < localContent.length;i++){

                                    if (localContent[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("",
                                                                          "content"));
                                         elementList.add(localContent[i]);
                                    } else {
                                        
                                                elementList.add(new javax.xml.namespace.QName("",
                                                                          "content"));
                                                elementList.add(null);
                                            
                                    }

                                 }
                             } else {
                                 
                                        elementList.add(new javax.xml.namespace.QName("",
                                                                          "content"));
                                        elementList.add(localContent);
                                    
                             }

                        } if (localRecipientsTracker){
                             if (localRecipients!=null) {
                                 for (int i = 0;i < localRecipients.length;i++){

                                    if (localRecipients[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("",
                                                                          "recipients"));
                                         elementList.add(localRecipients[i]);
                                    } else {
                                        
                                                elementList.add(new javax.xml.namespace.QName("",
                                                                          "recipients"));
                                                elementList.add(null);
                                            
                                    }

                                 }
                             } else {
                                 
                                        elementList.add(new javax.xml.namespace.QName("",
                                                                          "recipients"));
                                        elementList.add(localRecipients);
                                    
                             }

                        } if (localFieldsTracker){
                             if (localFields!=null) {
                                 for (int i = 0;i < localFields.length;i++){

                                    if (localFields[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("",
                                                                          "fields"));
                                         elementList.add(localFields[i]);
                                    } else {
                                        
                                                elementList.add(new javax.xml.namespace.QName("",
                                                                          "fields"));
                                                elementList.add(null);
                                            
                                    }

                                 }
                             } else {
                                 
                                        elementList.add(new javax.xml.namespace.QName("",
                                                                          "fields"));
                                        elementList.add(localFields);
                                    
                             }

                        } if (localNumSendsTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numSends"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSends));
                            } if (localNumDeliveriesTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numDeliveries"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumDeliveries));
                            } if (localNumHardBadEmailTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numHardBadEmail"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumHardBadEmail));
                            } if (localNumHardDestUnreachTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numHardDestUnreach"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumHardDestUnreach));
                            } if (localNumHardMessageContentTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numHardMessageContent"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumHardMessageContent));
                            } if (localNumHardBouncesTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numHardBounces"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumHardBounces));
                            } if (localNumSoftBadEmailTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numSoftBadEmail"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSoftBadEmail));
                            } if (localNumSoftDestUnreachTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numSoftDestUnreach"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSoftDestUnreach));
                            } if (localNumSoftMessageContentTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numSoftMessageContent"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSoftMessageContent));
                            } if (localNumSoftBouncesTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numSoftBounces"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSoftBounces));
                            } if (localNumOtherBouncesTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numOtherBounces"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumOtherBounces));
                            } if (localNumBouncesTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numBounces"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumBounces));
                            } if (localUniqOpensTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "uniqOpens"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUniqOpens));
                            } if (localNumOpensTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numOpens"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumOpens));
                            } if (localAvgOpensTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "avgOpens"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAvgOpens));
                            } if (localUniqClicksTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "uniqClicks"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUniqClicks));
                            } if (localNumClicksTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numClicks"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumClicks));
                            } if (localAvgClicksTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "avgClicks"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAvgClicks));
                            } if (localUniqConversionsTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "uniqConversions"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUniqConversions));
                            } if (localNumConversionsTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numConversions"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumConversions));
                            } if (localAvgConversionsTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "avgConversions"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAvgConversions));
                            } if (localRevenueTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "revenue"));
                                 
                                        if (localRevenue != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRevenue));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("revenue cannot be null!!");
                                        }
                                    } if (localNumSurveyResponsesTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numSurveyResponses"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSurveyResponses));
                            } if (localNumFriendForwardsTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numFriendForwards"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumFriendForwards));
                            } if (localNumContactUpdatesTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numContactUpdates"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumContactUpdates));
                            } if (localNumUnsubscribesByPrefsTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numUnsubscribesByPrefs"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumUnsubscribesByPrefs));
                            } if (localNumUnsubscribesByComplaintTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numUnsubscribesByComplaint"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumUnsubscribesByComplaint));
                            } if (localNumContactLossTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numContactLoss"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumContactLoss));
                            } if (localNumContactLossBouncesTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numContactLossBounces"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumContactLossBounces));
                            } if (localDeliveryRateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "deliveryRate"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDeliveryRate));
                            } if (localOpenRateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "openRate"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOpenRate));
                            } if (localClickRateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "clickRate"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localClickRate));
                            } if (localClickThroughRateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "clickThroughRate"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localClickThroughRate));
                            } if (localConversionRateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "conversionRate"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localConversionRate));
                            } if (localBounceRateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "bounceRate"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBounceRate));
                            } if (localComplaintRateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "complaintRate"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localComplaintRate));
                            } if (localContactLossRateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "contactLossRate"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localContactLossRate));
                            } if (localNumSocialSharesTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numSocialShares"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSocialShares));
                            } if (localNumSharesFacebookTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numSharesFacebook"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSharesFacebook));
                            } if (localNumSharesTwitterTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numSharesTwitter"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSharesTwitter));
                            } if (localNumSharesLinkedInTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numSharesLinkedIn"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSharesLinkedIn));
                            } if (localNumSharesDiggTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numSharesDigg"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSharesDigg));
                            } if (localNumSharesMySpaceTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numSharesMySpace"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSharesMySpace));
                            } if (localNumViewsFacebookTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numViewsFacebook"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumViewsFacebook));
                            } if (localNumViewsTwitterTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numViewsTwitter"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumViewsTwitter));
                            } if (localNumViewsLinkedInTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numViewsLinkedIn"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumViewsLinkedIn));
                            } if (localNumViewsDiggTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numViewsDigg"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumViewsDigg));
                            } if (localNumViewsMySpaceTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numViewsMySpace"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumViewsMySpace));
                            } if (localNumSocialViewsTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "numSocialViews"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumSocialViews));
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
        public static DeliveryObject parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            DeliveryObject object =
                new DeliveryObject();

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
                    
                            if (!"deliveryObject".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (DeliveryObject)com.bronto.api.v4.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list15 = new java.util.ArrayList();
                    
                        java.util.ArrayList list16 = new java.util.ArrayList();
                    
                        java.util.ArrayList list17 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","id").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"id" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","start").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"start" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setStart(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","messageId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"messageId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMessageId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","status").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"status" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setStatus(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","type").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"type" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","fromEmail").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"fromEmail" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFromEmail(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","fromName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"fromName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFromName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","replyEmail").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"replyEmail" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReplyEmail(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","authentication").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"authentication" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuthentication(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","replyTracking").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"replyTracking" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReplyTracking(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","messageRuleId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"messageRuleId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMessageRuleId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","optin").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"optin" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOptin(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","throttle").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"throttle" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setThrottle(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setThrottle(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","fatigueOverride").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"fatigueOverride" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFatigueOverride(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","content").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list15.add(null);
                                                              reader.next();
                                                          } else {
                                                        list15.add(com.bronto.api.v4.MessageContentObject.Factory.parse(reader));
                                                                }
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone15 = false;
                                                        while(!loopDone15){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone15 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("","content").equals(reader.getName())){
                                                                    
                                                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                                          list15.add(null);
                                                                          reader.next();
                                                                      } else {
                                                                    list15.add(com.bronto.api.v4.MessageContentObject.Factory.parse(reader));
                                                                        }
                                                                }else{
                                                                    loopDone15 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setContent((com.bronto.api.v4.MessageContentObject[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                com.bronto.api.v4.MessageContentObject.class,
                                                                list15));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","recipients").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list16.add(null);
                                                              reader.next();
                                                          } else {
                                                        list16.add(com.bronto.api.v4.DeliveryRecipientObject.Factory.parse(reader));
                                                                }
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone16 = false;
                                                        while(!loopDone16){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone16 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("","recipients").equals(reader.getName())){
                                                                    
                                                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                                          list16.add(null);
                                                                          reader.next();
                                                                      } else {
                                                                    list16.add(com.bronto.api.v4.DeliveryRecipientObject.Factory.parse(reader));
                                                                        }
                                                                }else{
                                                                    loopDone16 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setRecipients((com.bronto.api.v4.DeliveryRecipientObject[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                com.bronto.api.v4.DeliveryRecipientObject.class,
                                                                list16));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","fields").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list17.add(null);
                                                              reader.next();
                                                          } else {
                                                        list17.add(com.bronto.api.v4.MessageFieldObject.Factory.parse(reader));
                                                                }
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone17 = false;
                                                        while(!loopDone17){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone17 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("","fields").equals(reader.getName())){
                                                                    
                                                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                                          list17.add(null);
                                                                          reader.next();
                                                                      } else {
                                                                    list17.add(com.bronto.api.v4.MessageFieldObject.Factory.parse(reader));
                                                                        }
                                                                }else{
                                                                    loopDone17 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setFields((com.bronto.api.v4.MessageFieldObject[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                com.bronto.api.v4.MessageFieldObject.class,
                                                                list17));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numSends").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numSends" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumSends(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumSends(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numDeliveries").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numDeliveries" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumDeliveries(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumDeliveries(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numHardBadEmail").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numHardBadEmail" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumHardBadEmail(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumHardBadEmail(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numHardDestUnreach").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numHardDestUnreach" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumHardDestUnreach(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumHardDestUnreach(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numHardMessageContent").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numHardMessageContent" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumHardMessageContent(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumHardMessageContent(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numHardBounces").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numHardBounces" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumHardBounces(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumHardBounces(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numSoftBadEmail").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numSoftBadEmail" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumSoftBadEmail(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumSoftBadEmail(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numSoftDestUnreach").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numSoftDestUnreach" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumSoftDestUnreach(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumSoftDestUnreach(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numSoftMessageContent").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numSoftMessageContent" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumSoftMessageContent(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumSoftMessageContent(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numSoftBounces").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numSoftBounces" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumSoftBounces(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumSoftBounces(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numOtherBounces").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numOtherBounces" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumOtherBounces(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumOtherBounces(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numBounces").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numBounces" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumBounces(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumBounces(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","uniqOpens").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"uniqOpens" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUniqOpens(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setUniqOpens(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numOpens").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numOpens" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumOpens(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumOpens(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","avgOpens").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"avgOpens" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAvgOpens(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setAvgOpens(java.lang.Double.NaN);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","uniqClicks").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"uniqClicks" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUniqClicks(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setUniqClicks(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numClicks").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numClicks" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumClicks(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumClicks(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","avgClicks").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"avgClicks" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAvgClicks(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setAvgClicks(java.lang.Double.NaN);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","uniqConversions").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"uniqConversions" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUniqConversions(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setUniqConversions(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numConversions").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numConversions" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumConversions(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumConversions(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","avgConversions").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"avgConversions" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAvgConversions(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setAvgConversions(java.lang.Double.NaN);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","revenue").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"revenue" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRevenue(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numSurveyResponses").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numSurveyResponses" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumSurveyResponses(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumSurveyResponses(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numFriendForwards").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numFriendForwards" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumFriendForwards(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumFriendForwards(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numContactUpdates").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numContactUpdates" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumContactUpdates(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumContactUpdates(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numUnsubscribesByPrefs").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numUnsubscribesByPrefs" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumUnsubscribesByPrefs(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumUnsubscribesByPrefs(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numUnsubscribesByComplaint").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numUnsubscribesByComplaint" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumUnsubscribesByComplaint(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumUnsubscribesByComplaint(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numContactLoss").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numContactLoss" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumContactLoss(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumContactLoss(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numContactLossBounces").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numContactLossBounces" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumContactLossBounces(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumContactLossBounces(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","deliveryRate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"deliveryRate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDeliveryRate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setDeliveryRate(java.lang.Double.NaN);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","openRate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"openRate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOpenRate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setOpenRate(java.lang.Double.NaN);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","clickRate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"clickRate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setClickRate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setClickRate(java.lang.Double.NaN);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","clickThroughRate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"clickThroughRate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setClickThroughRate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setClickThroughRate(java.lang.Double.NaN);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","conversionRate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"conversionRate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setConversionRate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setConversionRate(java.lang.Double.NaN);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","bounceRate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"bounceRate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBounceRate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setBounceRate(java.lang.Double.NaN);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","complaintRate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"complaintRate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setComplaintRate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setComplaintRate(java.lang.Double.NaN);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","contactLossRate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"contactLossRate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setContactLossRate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setContactLossRate(java.lang.Double.NaN);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numSocialShares").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numSocialShares" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumSocialShares(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumSocialShares(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numSharesFacebook").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numSharesFacebook" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumSharesFacebook(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumSharesFacebook(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numSharesTwitter").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numSharesTwitter" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumSharesTwitter(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumSharesTwitter(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numSharesLinkedIn").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numSharesLinkedIn" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumSharesLinkedIn(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumSharesLinkedIn(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numSharesDigg").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numSharesDigg" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumSharesDigg(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumSharesDigg(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numSharesMySpace").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numSharesMySpace" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumSharesMySpace(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumSharesMySpace(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numViewsFacebook").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numViewsFacebook" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumViewsFacebook(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumViewsFacebook(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numViewsTwitter").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numViewsTwitter" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumViewsTwitter(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumViewsTwitter(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numViewsLinkedIn").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numViewsLinkedIn" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumViewsLinkedIn(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumViewsLinkedIn(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numViewsDigg").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numViewsDigg" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumViewsDigg(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumViewsDigg(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numViewsMySpace").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numViewsMySpace" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumViewsMySpace(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumViewsMySpace(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","numSocialViews").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"numSocialViews" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumSocialViews(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNumSocialViews(java.lang.Long.MIN_VALUE);
                                           
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
           
    