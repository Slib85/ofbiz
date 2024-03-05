
/**
 * RecentActivityObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.bronto.api.v4;
            

            /**
            *  RecentActivityObject bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class RecentActivityObject
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = recentActivityObject
                Namespace URI = http://api.bronto.com/v4
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for CreatedDate
                        */

                        
                                    protected java.util.Calendar localCreatedDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreatedDateTracker = false ;

                           public boolean isCreatedDateSpecified(){
                               return localCreatedDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getCreatedDate(){
                               return localCreatedDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CreatedDate
                               */
                               public void setCreatedDate(java.util.Calendar param){
                            localCreatedDateTracker = param != null;
                                   
                                            this.localCreatedDate=param;
                                    

                               }
                            

                        /**
                        * field for ContactId
                        */

                        
                                    protected java.lang.String localContactId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localContactIdTracker = false ;

                           public boolean isContactIdSpecified(){
                               return localContactIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getContactId(){
                               return localContactId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ContactId
                               */
                               public void setContactId(java.lang.String param){
                            localContactIdTracker = param != null;
                                   
                                            this.localContactId=param;
                                    

                               }
                            

                        /**
                        * field for ListId
                        */

                        
                                    protected java.lang.String localListId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localListIdTracker = false ;

                           public boolean isListIdSpecified(){
                               return localListIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getListId(){
                               return localListId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ListId
                               */
                               public void setListId(java.lang.String param){
                            localListIdTracker = param != null;
                                   
                                            this.localListId=param;
                                    

                               }
                            

                        /**
                        * field for SegmentId
                        */

                        
                                    protected java.lang.String localSegmentId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSegmentIdTracker = false ;

                           public boolean isSegmentIdSpecified(){
                               return localSegmentIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSegmentId(){
                               return localSegmentId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SegmentId
                               */
                               public void setSegmentId(java.lang.String param){
                            localSegmentIdTracker = param != null;
                                   
                                            this.localSegmentId=param;
                                    

                               }
                            

                        /**
                        * field for KeywordId
                        */

                        
                                    protected java.lang.String localKeywordId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localKeywordIdTracker = false ;

                           public boolean isKeywordIdSpecified(){
                               return localKeywordIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getKeywordId(){
                               return localKeywordId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param KeywordId
                               */
                               public void setKeywordId(java.lang.String param){
                            localKeywordIdTracker = param != null;
                                   
                                            this.localKeywordId=param;
                                    

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
                        * field for DeliveryId
                        */

                        
                                    protected java.lang.String localDeliveryId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDeliveryIdTracker = false ;

                           public boolean isDeliveryIdSpecified(){
                               return localDeliveryIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDeliveryId(){
                               return localDeliveryId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DeliveryId
                               */
                               public void setDeliveryId(java.lang.String param){
                            localDeliveryIdTracker = param != null;
                                   
                                            this.localDeliveryId=param;
                                    

                               }
                            

                        /**
                        * field for WorkflowId
                        */

                        
                                    protected java.lang.String localWorkflowId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkflowIdTracker = false ;

                           public boolean isWorkflowIdSpecified(){
                               return localWorkflowIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkflowId(){
                               return localWorkflowId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkflowId
                               */
                               public void setWorkflowId(java.lang.String param){
                            localWorkflowIdTracker = param != null;
                                   
                                            this.localWorkflowId=param;
                                    

                               }
                            

                        /**
                        * field for ActivityType
                        */

                        
                                    protected java.lang.String localActivityType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localActivityTypeTracker = false ;

                           public boolean isActivityTypeSpecified(){
                               return localActivityTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getActivityType(){
                               return localActivityType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ActivityType
                               */
                               public void setActivityType(java.lang.String param){
                            localActivityTypeTracker = param != null;
                                   
                                            this.localActivityType=param;
                                    

                               }
                            

                        /**
                        * field for EmailAddress
                        */

                        
                                    protected java.lang.String localEmailAddress ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEmailAddressTracker = false ;

                           public boolean isEmailAddressSpecified(){
                               return localEmailAddressTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEmailAddress(){
                               return localEmailAddress;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EmailAddress
                               */
                               public void setEmailAddress(java.lang.String param){
                            localEmailAddressTracker = param != null;
                                   
                                            this.localEmailAddress=param;
                                    

                               }
                            

                        /**
                        * field for MobileNumber
                        */

                        
                                    protected java.lang.String localMobileNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMobileNumberTracker = false ;

                           public boolean isMobileNumberSpecified(){
                               return localMobileNumberTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMobileNumber(){
                               return localMobileNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MobileNumber
                               */
                               public void setMobileNumber(java.lang.String param){
                            localMobileNumberTracker = param != null;
                                   
                                            this.localMobileNumber=param;
                                    

                               }
                            

                        /**
                        * field for ContactStatus
                        */

                        
                                    protected java.lang.String localContactStatus ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localContactStatusTracker = false ;

                           public boolean isContactStatusSpecified(){
                               return localContactStatusTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getContactStatus(){
                               return localContactStatus;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ContactStatus
                               */
                               public void setContactStatus(java.lang.String param){
                            localContactStatusTracker = param != null;
                                   
                                            this.localContactStatus=param;
                                    

                               }
                            

                        /**
                        * field for MessageName
                        */

                        
                                    protected java.lang.String localMessageName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMessageNameTracker = false ;

                           public boolean isMessageNameSpecified(){
                               return localMessageNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMessageName(){
                               return localMessageName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MessageName
                               */
                               public void setMessageName(java.lang.String param){
                            localMessageNameTracker = param != null;
                                   
                                            this.localMessageName=param;
                                    

                               }
                            

                        /**
                        * field for DeliveryType
                        */

                        
                                    protected java.lang.String localDeliveryType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDeliveryTypeTracker = false ;

                           public boolean isDeliveryTypeSpecified(){
                               return localDeliveryTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDeliveryType(){
                               return localDeliveryType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DeliveryType
                               */
                               public void setDeliveryType(java.lang.String param){
                            localDeliveryTypeTracker = param != null;
                                   
                                            this.localDeliveryType=param;
                                    

                               }
                            

                        /**
                        * field for DeliveryStart
                        */

                        
                                    protected java.util.Calendar localDeliveryStart ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDeliveryStartTracker = false ;

                           public boolean isDeliveryStartSpecified(){
                               return localDeliveryStartTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getDeliveryStart(){
                               return localDeliveryStart;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DeliveryStart
                               */
                               public void setDeliveryStart(java.util.Calendar param){
                            localDeliveryStartTracker = param != null;
                                   
                                            this.localDeliveryStart=param;
                                    

                               }
                            

                        /**
                        * field for WorkflowName
                        */

                        
                                    protected java.lang.String localWorkflowName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkflowNameTracker = false ;

                           public boolean isWorkflowNameSpecified(){
                               return localWorkflowNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkflowName(){
                               return localWorkflowName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkflowName
                               */
                               public void setWorkflowName(java.lang.String param){
                            localWorkflowNameTracker = param != null;
                                   
                                            this.localWorkflowName=param;
                                    

                               }
                            

                        /**
                        * field for SegmentName
                        */

                        
                                    protected java.lang.String localSegmentName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSegmentNameTracker = false ;

                           public boolean isSegmentNameSpecified(){
                               return localSegmentNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSegmentName(){
                               return localSegmentName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SegmentName
                               */
                               public void setSegmentName(java.lang.String param){
                            localSegmentNameTracker = param != null;
                                   
                                            this.localSegmentName=param;
                                    

                               }
                            

                        /**
                        * field for ListName
                        */

                        
                                    protected java.lang.String localListName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localListNameTracker = false ;

                           public boolean isListNameSpecified(){
                               return localListNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getListName(){
                               return localListName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ListName
                               */
                               public void setListName(java.lang.String param){
                            localListNameTracker = param != null;
                                   
                                            this.localListName=param;
                                    

                               }
                            

                        /**
                        * field for ListLabel
                        */

                        
                                    protected java.lang.String localListLabel ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localListLabelTracker = false ;

                           public boolean isListLabelSpecified(){
                               return localListLabelTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getListLabel(){
                               return localListLabel;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ListLabel
                               */
                               public void setListLabel(java.lang.String param){
                            localListLabelTracker = param != null;
                                   
                                            this.localListLabel=param;
                                    

                               }
                            

                        /**
                        * field for AutomatorName
                        */

                        
                                    protected java.lang.String localAutomatorName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAutomatorNameTracker = false ;

                           public boolean isAutomatorNameSpecified(){
                               return localAutomatorNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAutomatorName(){
                               return localAutomatorName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AutomatorName
                               */
                               public void setAutomatorName(java.lang.String param){
                            localAutomatorNameTracker = param != null;
                                   
                                            this.localAutomatorName=param;
                                    

                               }
                            

                        /**
                        * field for SmsKeywordName
                        */

                        
                                    protected java.lang.String localSmsKeywordName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSmsKeywordNameTracker = false ;

                           public boolean isSmsKeywordNameSpecified(){
                               return localSmsKeywordNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSmsKeywordName(){
                               return localSmsKeywordName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SmsKeywordName
                               */
                               public void setSmsKeywordName(java.lang.String param){
                            localSmsKeywordNameTracker = param != null;
                                   
                                            this.localSmsKeywordName=param;
                                    

                               }
                            

                        /**
                        * field for BounceType
                        */

                        
                                    protected java.lang.String localBounceType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBounceTypeTracker = false ;

                           public boolean isBounceTypeSpecified(){
                               return localBounceTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBounceType(){
                               return localBounceType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BounceType
                               */
                               public void setBounceType(java.lang.String param){
                            localBounceTypeTracker = param != null;
                                   
                                            this.localBounceType=param;
                                    

                               }
                            

                        /**
                        * field for BounceReason
                        */

                        
                                    protected java.lang.String localBounceReason ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBounceReasonTracker = false ;

                           public boolean isBounceReasonSpecified(){
                               return localBounceReasonTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBounceReason(){
                               return localBounceReason;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BounceReason
                               */
                               public void setBounceReason(java.lang.String param){
                            localBounceReasonTracker = param != null;
                                   
                                            this.localBounceReason=param;
                                    

                               }
                            

                        /**
                        * field for SkipReason
                        */

                        
                                    protected java.lang.String localSkipReason ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSkipReasonTracker = false ;

                           public boolean isSkipReasonSpecified(){
                               return localSkipReasonTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSkipReason(){
                               return localSkipReason;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SkipReason
                               */
                               public void setSkipReason(java.lang.String param){
                            localSkipReasonTracker = param != null;
                                   
                                            this.localSkipReason=param;
                                    

                               }
                            

                        /**
                        * field for LinkName
                        */

                        
                                    protected java.lang.String localLinkName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLinkNameTracker = false ;

                           public boolean isLinkNameSpecified(){
                               return localLinkNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLinkName(){
                               return localLinkName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LinkName
                               */
                               public void setLinkName(java.lang.String param){
                            localLinkNameTracker = param != null;
                                   
                                            this.localLinkName=param;
                                    

                               }
                            

                        /**
                        * field for LinkUrl
                        */

                        
                                    protected java.lang.String localLinkUrl ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLinkUrlTracker = false ;

                           public boolean isLinkUrlSpecified(){
                               return localLinkUrlTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLinkUrl(){
                               return localLinkUrl;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LinkUrl
                               */
                               public void setLinkUrl(java.lang.String param){
                            localLinkUrlTracker = param != null;
                                   
                                            this.localLinkUrl=param;
                                    

                               }
                            

                        /**
                        * field for OrderId
                        */

                        
                                    protected java.lang.String localOrderId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOrderIdTracker = false ;

                           public boolean isOrderIdSpecified(){
                               return localOrderIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOrderId(){
                               return localOrderId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OrderId
                               */
                               public void setOrderId(java.lang.String param){
                            localOrderIdTracker = param != null;
                                   
                                            this.localOrderId=param;
                                    

                               }
                            

                        /**
                        * field for UnsubscribeMethod
                        */

                        
                                    protected java.lang.String localUnsubscribeMethod ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUnsubscribeMethodTracker = false ;

                           public boolean isUnsubscribeMethodSpecified(){
                               return localUnsubscribeMethodTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUnsubscribeMethod(){
                               return localUnsubscribeMethod;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param UnsubscribeMethod
                               */
                               public void setUnsubscribeMethod(java.lang.String param){
                            localUnsubscribeMethodTracker = param != null;
                                   
                                            this.localUnsubscribeMethod=param;
                                    

                               }
                            

                        /**
                        * field for FtafEmails
                        */

                        
                                    protected java.lang.String localFtafEmails ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFtafEmailsTracker = false ;

                           public boolean isFtafEmailsSpecified(){
                               return localFtafEmailsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFtafEmails(){
                               return localFtafEmails;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FtafEmails
                               */
                               public void setFtafEmails(java.lang.String param){
                            localFtafEmailsTracker = param != null;
                                   
                                            this.localFtafEmails=param;
                                    

                               }
                            

                        /**
                        * field for SocialNetwork
                        */

                        
                                    protected java.lang.String localSocialNetwork ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSocialNetworkTracker = false ;

                           public boolean isSocialNetworkSpecified(){
                               return localSocialNetworkTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSocialNetwork(){
                               return localSocialNetwork;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SocialNetwork
                               */
                               public void setSocialNetwork(java.lang.String param){
                            localSocialNetworkTracker = param != null;
                                   
                                            this.localSocialNetwork=param;
                                    

                               }
                            

                        /**
                        * field for SocialActivity
                        */

                        
                                    protected java.lang.String localSocialActivity ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSocialActivityTracker = false ;

                           public boolean isSocialActivitySpecified(){
                               return localSocialActivityTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSocialActivity(){
                               return localSocialActivity;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SocialActivity
                               */
                               public void setSocialActivity(java.lang.String param){
                            localSocialActivityTracker = param != null;
                                   
                                            this.localSocialActivity=param;
                                    

                               }
                            

                        /**
                        * field for WebformType
                        */

                        
                                    protected java.lang.String localWebformType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWebformTypeTracker = false ;

                           public boolean isWebformTypeSpecified(){
                               return localWebformTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWebformType(){
                               return localWebformType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WebformType
                               */
                               public void setWebformType(java.lang.String param){
                            localWebformTypeTracker = param != null;
                                   
                                            this.localWebformType=param;
                                    

                               }
                            

                        /**
                        * field for WebformAction
                        */

                        
                                    protected java.lang.String localWebformAction ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWebformActionTracker = false ;

                           public boolean isWebformActionSpecified(){
                               return localWebformActionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWebformAction(){
                               return localWebformAction;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WebformAction
                               */
                               public void setWebformAction(java.lang.String param){
                            localWebformActionTracker = param != null;
                                   
                                            this.localWebformAction=param;
                                    

                               }
                            

                        /**
                        * field for WebformName
                        */

                        
                                    protected java.lang.String localWebformName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWebformNameTracker = false ;

                           public boolean isWebformNameSpecified(){
                               return localWebformNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWebformName(){
                               return localWebformName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WebformName
                               */
                               public void setWebformName(java.lang.String param){
                            localWebformNameTracker = param != null;
                                   
                                            this.localWebformName=param;
                                    

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
                           namespacePrefix+":recentActivityObject",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "recentActivityObject",
                           xmlWriter);
                   }

               
                   }
                if (localCreatedDateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "createdDate", xmlWriter);
                             

                                          if (localCreatedDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("createdDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreatedDate));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localContactIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "contactId", xmlWriter);
                             

                                          if (localContactId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("contactId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localContactId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localListIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "listId", xmlWriter);
                             

                                          if (localListId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("listId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localListId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSegmentIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "segmentId", xmlWriter);
                             

                                          if (localSegmentId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("segmentId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSegmentId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localKeywordIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "keywordId", xmlWriter);
                             

                                          if (localKeywordId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("keywordId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localKeywordId);
                                            
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
                             } if (localDeliveryIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "deliveryId", xmlWriter);
                             

                                          if (localDeliveryId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("deliveryId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDeliveryId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkflowIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "workflowId", xmlWriter);
                             

                                          if (localWorkflowId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workflowId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkflowId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localActivityTypeTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "activityType", xmlWriter);
                             

                                          if (localActivityType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("activityType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localActivityType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEmailAddressTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "emailAddress", xmlWriter);
                             

                                          if (localEmailAddress==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("emailAddress cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEmailAddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMobileNumberTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "mobileNumber", xmlWriter);
                             

                                          if (localMobileNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("mobileNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMobileNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localContactStatusTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "contactStatus", xmlWriter);
                             

                                          if (localContactStatus==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("contactStatus cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localContactStatus);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMessageNameTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "messageName", xmlWriter);
                             

                                          if (localMessageName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("messageName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMessageName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDeliveryTypeTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "deliveryType", xmlWriter);
                             

                                          if (localDeliveryType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("deliveryType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDeliveryType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDeliveryStartTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "deliveryStart", xmlWriter);
                             

                                          if (localDeliveryStart==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("deliveryStart cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDeliveryStart));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkflowNameTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "workflowName", xmlWriter);
                             

                                          if (localWorkflowName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workflowName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkflowName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSegmentNameTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "segmentName", xmlWriter);
                             

                                          if (localSegmentName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("segmentName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSegmentName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localListNameTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "listName", xmlWriter);
                             

                                          if (localListName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("listName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localListName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localListLabelTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "listLabel", xmlWriter);
                             

                                          if (localListLabel==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("listLabel cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localListLabel);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAutomatorNameTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "automatorName", xmlWriter);
                             

                                          if (localAutomatorName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("automatorName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAutomatorName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSmsKeywordNameTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "smsKeywordName", xmlWriter);
                             

                                          if (localSmsKeywordName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("smsKeywordName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSmsKeywordName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBounceTypeTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "bounceType", xmlWriter);
                             

                                          if (localBounceType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("bounceType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBounceType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBounceReasonTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "bounceReason", xmlWriter);
                             

                                          if (localBounceReason==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("bounceReason cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBounceReason);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSkipReasonTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "skipReason", xmlWriter);
                             

                                          if (localSkipReason==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("skipReason cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSkipReason);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLinkNameTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "linkName", xmlWriter);
                             

                                          if (localLinkName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("linkName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLinkName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLinkUrlTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "linkUrl", xmlWriter);
                             

                                          if (localLinkUrl==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("linkUrl cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLinkUrl);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOrderIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "orderId", xmlWriter);
                             

                                          if (localOrderId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("orderId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOrderId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUnsubscribeMethodTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "unsubscribeMethod", xmlWriter);
                             

                                          if (localUnsubscribeMethod==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("unsubscribeMethod cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUnsubscribeMethod);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFtafEmailsTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "ftafEmails", xmlWriter);
                             

                                          if (localFtafEmails==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("ftafEmails cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFtafEmails);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSocialNetworkTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "socialNetwork", xmlWriter);
                             

                                          if (localSocialNetwork==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("socialNetwork cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSocialNetwork);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSocialActivityTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "socialActivity", xmlWriter);
                             

                                          if (localSocialActivity==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("socialActivity cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSocialActivity);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWebformTypeTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "webformType", xmlWriter);
                             

                                          if (localWebformType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("webformType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWebformType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWebformActionTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "webformAction", xmlWriter);
                             

                                          if (localWebformAction==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("webformAction cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWebformAction);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWebformNameTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "webformName", xmlWriter);
                             

                                          if (localWebformName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("webformName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWebformName);
                                            
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

                 if (localCreatedDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "createdDate"));
                                 
                                        if (localCreatedDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreatedDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("createdDate cannot be null!!");
                                        }
                                    } if (localContactIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "contactId"));
                                 
                                        if (localContactId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localContactId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("contactId cannot be null!!");
                                        }
                                    } if (localListIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "listId"));
                                 
                                        if (localListId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localListId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("listId cannot be null!!");
                                        }
                                    } if (localSegmentIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "segmentId"));
                                 
                                        if (localSegmentId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSegmentId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("segmentId cannot be null!!");
                                        }
                                    } if (localKeywordIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "keywordId"));
                                 
                                        if (localKeywordId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localKeywordId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("keywordId cannot be null!!");
                                        }
                                    } if (localMessageIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "messageId"));
                                 
                                        if (localMessageId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMessageId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("messageId cannot be null!!");
                                        }
                                    } if (localDeliveryIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "deliveryId"));
                                 
                                        if (localDeliveryId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDeliveryId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("deliveryId cannot be null!!");
                                        }
                                    } if (localWorkflowIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "workflowId"));
                                 
                                        if (localWorkflowId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkflowId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workflowId cannot be null!!");
                                        }
                                    } if (localActivityTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "activityType"));
                                 
                                        if (localActivityType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localActivityType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("activityType cannot be null!!");
                                        }
                                    } if (localEmailAddressTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "emailAddress"));
                                 
                                        if (localEmailAddress != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmailAddress));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("emailAddress cannot be null!!");
                                        }
                                    } if (localMobileNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "mobileNumber"));
                                 
                                        if (localMobileNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMobileNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("mobileNumber cannot be null!!");
                                        }
                                    } if (localContactStatusTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "contactStatus"));
                                 
                                        if (localContactStatus != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localContactStatus));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("contactStatus cannot be null!!");
                                        }
                                    } if (localMessageNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "messageName"));
                                 
                                        if (localMessageName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMessageName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("messageName cannot be null!!");
                                        }
                                    } if (localDeliveryTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "deliveryType"));
                                 
                                        if (localDeliveryType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDeliveryType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("deliveryType cannot be null!!");
                                        }
                                    } if (localDeliveryStartTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "deliveryStart"));
                                 
                                        if (localDeliveryStart != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDeliveryStart));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("deliveryStart cannot be null!!");
                                        }
                                    } if (localWorkflowNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "workflowName"));
                                 
                                        if (localWorkflowName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkflowName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workflowName cannot be null!!");
                                        }
                                    } if (localSegmentNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "segmentName"));
                                 
                                        if (localSegmentName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSegmentName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("segmentName cannot be null!!");
                                        }
                                    } if (localListNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "listName"));
                                 
                                        if (localListName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localListName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("listName cannot be null!!");
                                        }
                                    } if (localListLabelTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "listLabel"));
                                 
                                        if (localListLabel != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localListLabel));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("listLabel cannot be null!!");
                                        }
                                    } if (localAutomatorNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "automatorName"));
                                 
                                        if (localAutomatorName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAutomatorName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("automatorName cannot be null!!");
                                        }
                                    } if (localSmsKeywordNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "smsKeywordName"));
                                 
                                        if (localSmsKeywordName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSmsKeywordName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("smsKeywordName cannot be null!!");
                                        }
                                    } if (localBounceTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "bounceType"));
                                 
                                        if (localBounceType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBounceType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("bounceType cannot be null!!");
                                        }
                                    } if (localBounceReasonTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "bounceReason"));
                                 
                                        if (localBounceReason != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBounceReason));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("bounceReason cannot be null!!");
                                        }
                                    } if (localSkipReasonTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "skipReason"));
                                 
                                        if (localSkipReason != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSkipReason));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("skipReason cannot be null!!");
                                        }
                                    } if (localLinkNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "linkName"));
                                 
                                        if (localLinkName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLinkName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("linkName cannot be null!!");
                                        }
                                    } if (localLinkUrlTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "linkUrl"));
                                 
                                        if (localLinkUrl != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLinkUrl));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("linkUrl cannot be null!!");
                                        }
                                    } if (localOrderIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "orderId"));
                                 
                                        if (localOrderId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOrderId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("orderId cannot be null!!");
                                        }
                                    } if (localUnsubscribeMethodTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "unsubscribeMethod"));
                                 
                                        if (localUnsubscribeMethod != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUnsubscribeMethod));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("unsubscribeMethod cannot be null!!");
                                        }
                                    } if (localFtafEmailsTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "ftafEmails"));
                                 
                                        if (localFtafEmails != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFtafEmails));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("ftafEmails cannot be null!!");
                                        }
                                    } if (localSocialNetworkTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "socialNetwork"));
                                 
                                        if (localSocialNetwork != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSocialNetwork));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("socialNetwork cannot be null!!");
                                        }
                                    } if (localSocialActivityTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "socialActivity"));
                                 
                                        if (localSocialActivity != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSocialActivity));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("socialActivity cannot be null!!");
                                        }
                                    } if (localWebformTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "webformType"));
                                 
                                        if (localWebformType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWebformType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("webformType cannot be null!!");
                                        }
                                    } if (localWebformActionTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "webformAction"));
                                 
                                        if (localWebformAction != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWebformAction));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("webformAction cannot be null!!");
                                        }
                                    } if (localWebformNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "webformName"));
                                 
                                        if (localWebformName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWebformName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("webformName cannot be null!!");
                                        }
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
        public static RecentActivityObject parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            RecentActivityObject object =
                new RecentActivityObject();

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
                    
                            if (!"recentActivityObject".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (RecentActivityObject)com.bronto.api.v4.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","createdDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"createdDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCreatedDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","contactId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"contactId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setContactId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","listId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"listId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setListId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","segmentId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"segmentId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSegmentId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","keywordId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"keywordId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setKeywordId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","deliveryId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"deliveryId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDeliveryId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","workflowId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workflowId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkflowId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","activityType").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"activityType" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setActivityType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","emailAddress").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"emailAddress" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEmailAddress(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","mobileNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"mobileNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMobileNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","contactStatus").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"contactStatus" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setContactStatus(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","messageName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"messageName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMessageName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","deliveryType").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"deliveryType" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDeliveryType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","deliveryStart").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"deliveryStart" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDeliveryStart(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","workflowName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workflowName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkflowName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","segmentName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"segmentName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSegmentName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","listName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"listName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setListName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","listLabel").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"listLabel" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setListLabel(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","automatorName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"automatorName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAutomatorName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","smsKeywordName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"smsKeywordName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSmsKeywordName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","bounceType").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"bounceType" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBounceType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","bounceReason").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"bounceReason" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBounceReason(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","skipReason").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"skipReason" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSkipReason(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","linkName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"linkName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLinkName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","linkUrl").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"linkUrl" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLinkUrl(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","orderId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"orderId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOrderId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","unsubscribeMethod").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"unsubscribeMethod" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUnsubscribeMethod(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ftafEmails").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ftafEmails" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFtafEmails(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","socialNetwork").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"socialNetwork" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSocialNetwork(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","socialActivity").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"socialActivity" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSocialActivity(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","webformType").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"webformType" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWebformType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","webformAction").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"webformAction" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWebformAction(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","webformName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"webformName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWebformName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
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
           
    