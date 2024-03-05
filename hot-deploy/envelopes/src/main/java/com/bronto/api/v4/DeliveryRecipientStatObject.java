
/**
 * DeliveryRecipientStatObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.bronto.api.v4;
            

            /**
            *  DeliveryRecipientStatObject bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class DeliveryRecipientStatObject
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = deliveryRecipientStatObject
                Namespace URI = http://api.bronto.com/v4
                Namespace Prefix = ns1
                */
            

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

                        
                                    protected double localRevenue ;
                                
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
                           * @return double
                           */
                           public  double getRevenue(){
                               return localRevenue;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Revenue
                               */
                               public void setRevenue(double param){
                            
                                       // setting primitive attribute tracker to true
                                       localRevenueTracker =
                                       !java.lang.Double.isNaN(param);
                                   
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
                           namespacePrefix+":deliveryRecipientStatObject",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "deliveryRecipientStatObject",
                           xmlWriter);
                   }

               
                   }
                if (localDeliveryIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "deliveryId", xmlWriter);
                             

                                          if (localDeliveryId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("deliveryId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDeliveryId);
                                            
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
                             
                                               if (java.lang.Double.isNaN(localRevenue)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("revenue cannot be null!!");
                                                      
                                               } else {
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

                 if (localDeliveryIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "deliveryId"));
                                 
                                        if (localDeliveryId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDeliveryId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("deliveryId cannot be null!!");
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
                                    } if (localContactIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "contactId"));
                                 
                                        if (localContactId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localContactId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("contactId cannot be null!!");
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
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRevenue));
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
        public static DeliveryRecipientStatObject parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            DeliveryRecipientStatObject object =
                new DeliveryRecipientStatObject();

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
                    
                            if (!"deliveryRecipientStatObject".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (DeliveryRecipientStatObject)com.bronto.api.v4.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
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
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setRevenue(java.lang.Double.NaN);
                                           
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
           
    