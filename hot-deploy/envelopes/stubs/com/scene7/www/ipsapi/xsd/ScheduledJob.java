
/**
 * ScheduledJob.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.scene7.www.ipsapi.xsd;
            

            /**
            *  ScheduledJob bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ScheduledJob
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = ScheduledJob
                Namespace URI = http://www.scene7.com/IpsApi/xsd
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for CompanyHandle
                        */

                        
                                    protected java.lang.String localCompanyHandle ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCompanyHandle(){
                               return localCompanyHandle;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CompanyHandle
                               */
                               public void setCompanyHandle(java.lang.String param){
                            
                                            this.localCompanyHandle=param;
                                    

                               }
                            

                        /**
                        * field for JobHandle
                        */

                        
                                    protected java.lang.String localJobHandle ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getJobHandle(){
                               return localJobHandle;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param JobHandle
                               */
                               public void setJobHandle(java.lang.String param){
                            
                                            this.localJobHandle=param;
                                    

                               }
                            

                        /**
                        * field for Name
                        */

                        
                                    protected java.lang.String localName ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getName(){
                               return localName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Name
                               */
                               public void setName(java.lang.String param){
                            
                                            this.localName=param;
                                    

                               }
                            

                        /**
                        * field for OriginalName
                        */

                        
                                    protected java.lang.String localOriginalName ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOriginalName(){
                               return localOriginalName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OriginalName
                               */
                               public void setOriginalName(java.lang.String param){
                            
                                            this.localOriginalName=param;
                                    

                               }
                            

                        /**
                        * field for Type
                        */

                        
                                    protected java.lang.String localType ;
                                

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
                            
                                            this.localType=param;
                                    

                               }
                            

                        /**
                        * field for SubmitUserEmail
                        */

                        
                                    protected java.lang.String localSubmitUserEmail ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSubmitUserEmail(){
                               return localSubmitUserEmail;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SubmitUserEmail
                               */
                               public void setSubmitUserEmail(java.lang.String param){
                            
                                            this.localSubmitUserEmail=param;
                                    

                               }
                            

                        /**
                        * field for ExecSchedule
                        */

                        
                                    protected java.lang.String localExecSchedule ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localExecScheduleTracker = false ;

                           public boolean isExecScheduleSpecified(){
                               return localExecScheduleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getExecSchedule(){
                               return localExecSchedule;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ExecSchedule
                               */
                               public void setExecSchedule(java.lang.String param){
                            localExecScheduleTracker = param != null;
                                   
                                            this.localExecSchedule=param;
                                    

                               }
                            

                        /**
                        * field for NextFireTime
                        */

                        
                                    protected java.util.Calendar localNextFireTime ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNextFireTimeTracker = false ;

                           public boolean isNextFireTimeSpecified(){
                               return localNextFireTimeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getNextFireTime(){
                               return localNextFireTime;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NextFireTime
                               */
                               public void setNextFireTime(java.util.Calendar param){
                            localNextFireTimeTracker = param != null;
                                   
                                            this.localNextFireTime=param;
                                    

                               }
                            

                        /**
                        * field for TimeZone
                        */

                        
                                    protected java.lang.String localTimeZone ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTimeZoneTracker = false ;

                           public boolean isTimeZoneSpecified(){
                               return localTimeZoneTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTimeZone(){
                               return localTimeZone;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TimeZone
                               */
                               public void setTimeZone(java.lang.String param){
                            localTimeZoneTracker = param != null;
                                   
                                            this.localTimeZone=param;
                                    

                               }
                            

                        /**
                        * field for TriggerState
                        */

                        
                                    protected java.lang.String localTriggerState ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTriggerStateTracker = false ;

                           public boolean isTriggerStateSpecified(){
                               return localTriggerStateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTriggerState(){
                               return localTriggerState;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TriggerState
                               */
                               public void setTriggerState(java.lang.String param){
                            localTriggerStateTracker = param != null;
                                   
                                            this.localTriggerState=param;
                                    

                               }
                            

                        /**
                        * field for ImageServingPublishJob
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.ImageServingPublishJob localImageServingPublishJob ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localImageServingPublishJobTracker = false ;

                           public boolean isImageServingPublishJobSpecified(){
                               return localImageServingPublishJobTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.ImageServingPublishJob
                           */
                           public  com.scene7.www.ipsapi.xsd.ImageServingPublishJob getImageServingPublishJob(){
                               return localImageServingPublishJob;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ImageServingPublishJob
                               */
                               public void setImageServingPublishJob(com.scene7.www.ipsapi.xsd.ImageServingPublishJob param){
                            localImageServingPublishJobTracker = param != null;
                                   
                                            this.localImageServingPublishJob=param;
                                    

                               }
                            

                        /**
                        * field for ImageRenderingPublishJob
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.ImageRenderingPublishJob localImageRenderingPublishJob ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localImageRenderingPublishJobTracker = false ;

                           public boolean isImageRenderingPublishJobSpecified(){
                               return localImageRenderingPublishJobTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.ImageRenderingPublishJob
                           */
                           public  com.scene7.www.ipsapi.xsd.ImageRenderingPublishJob getImageRenderingPublishJob(){
                               return localImageRenderingPublishJob;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ImageRenderingPublishJob
                               */
                               public void setImageRenderingPublishJob(com.scene7.www.ipsapi.xsd.ImageRenderingPublishJob param){
                            localImageRenderingPublishJobTracker = param != null;
                                   
                                            this.localImageRenderingPublishJob=param;
                                    

                               }
                            

                        /**
                        * field for VideoPublishJob
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.VideoPublishJob localVideoPublishJob ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localVideoPublishJobTracker = false ;

                           public boolean isVideoPublishJobSpecified(){
                               return localVideoPublishJobTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.VideoPublishJob
                           */
                           public  com.scene7.www.ipsapi.xsd.VideoPublishJob getVideoPublishJob(){
                               return localVideoPublishJob;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param VideoPublishJob
                               */
                               public void setVideoPublishJob(com.scene7.www.ipsapi.xsd.VideoPublishJob param){
                            localVideoPublishJobTracker = param != null;
                                   
                                            this.localVideoPublishJob=param;
                                    

                               }
                            

                        /**
                        * field for ServerDirectoryPublishJob
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.ServerDirectoryPublishJob localServerDirectoryPublishJob ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localServerDirectoryPublishJobTracker = false ;

                           public boolean isServerDirectoryPublishJobSpecified(){
                               return localServerDirectoryPublishJobTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.ServerDirectoryPublishJob
                           */
                           public  com.scene7.www.ipsapi.xsd.ServerDirectoryPublishJob getServerDirectoryPublishJob(){
                               return localServerDirectoryPublishJob;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ServerDirectoryPublishJob
                               */
                               public void setServerDirectoryPublishJob(com.scene7.www.ipsapi.xsd.ServerDirectoryPublishJob param){
                            localServerDirectoryPublishJobTracker = param != null;
                                   
                                            this.localServerDirectoryPublishJob=param;
                                    

                               }
                            

                        /**
                        * field for UploadDirectoryJob
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.UploadDirectoryJob localUploadDirectoryJob ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUploadDirectoryJobTracker = false ;

                           public boolean isUploadDirectoryJobSpecified(){
                               return localUploadDirectoryJobTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.UploadDirectoryJob
                           */
                           public  com.scene7.www.ipsapi.xsd.UploadDirectoryJob getUploadDirectoryJob(){
                               return localUploadDirectoryJob;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param UploadDirectoryJob
                               */
                               public void setUploadDirectoryJob(com.scene7.www.ipsapi.xsd.UploadDirectoryJob param){
                            localUploadDirectoryJobTracker = param != null;
                                   
                                            this.localUploadDirectoryJob=param;
                                    

                               }
                            

                        /**
                        * field for UploadUrlsJob
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.UploadUrlsJob localUploadUrlsJob ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUploadUrlsJobTracker = false ;

                           public boolean isUploadUrlsJobSpecified(){
                               return localUploadUrlsJobTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.UploadUrlsJob
                           */
                           public  com.scene7.www.ipsapi.xsd.UploadUrlsJob getUploadUrlsJob(){
                               return localUploadUrlsJob;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param UploadUrlsJob
                               */
                               public void setUploadUrlsJob(com.scene7.www.ipsapi.xsd.UploadUrlsJob param){
                            localUploadUrlsJobTracker = param != null;
                                   
                                            this.localUploadUrlsJob=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.scene7.com/IpsApi/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":ScheduledJob",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "ScheduledJob",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "companyHandle", xmlWriter);
                             

                                          if (localCompanyHandle==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("companyHandle cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCompanyHandle);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "jobHandle", xmlWriter);
                             

                                          if (localJobHandle==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("jobHandle cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localJobHandle);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "name", xmlWriter);
                             

                                          if (localName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("name cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "originalName", xmlWriter);
                             

                                          if (localOriginalName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("originalName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOriginalName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "type", xmlWriter);
                             

                                          if (localType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("type cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "submitUserEmail", xmlWriter);
                             

                                          if (localSubmitUserEmail==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("submitUserEmail cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSubmitUserEmail);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localExecScheduleTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "execSchedule", xmlWriter);
                             

                                          if (localExecSchedule==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("execSchedule cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localExecSchedule);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNextFireTimeTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "nextFireTime", xmlWriter);
                             

                                          if (localNextFireTime==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("nextFireTime cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNextFireTime));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTimeZoneTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "timeZone", xmlWriter);
                             

                                          if (localTimeZone==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("timeZone cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTimeZone);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTriggerStateTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "triggerState", xmlWriter);
                             

                                          if (localTriggerState==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("triggerState cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTriggerState);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localImageServingPublishJobTracker){
                                            if (localImageServingPublishJob==null){
                                                 throw new org.apache.axis2.databinding.ADBException("imageServingPublishJob cannot be null!!");
                                            }
                                           localImageServingPublishJob.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","imageServingPublishJob"),
                                               xmlWriter);
                                        } if (localImageRenderingPublishJobTracker){
                                            if (localImageRenderingPublishJob==null){
                                                 throw new org.apache.axis2.databinding.ADBException("imageRenderingPublishJob cannot be null!!");
                                            }
                                           localImageRenderingPublishJob.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","imageRenderingPublishJob"),
                                               xmlWriter);
                                        } if (localVideoPublishJobTracker){
                                            if (localVideoPublishJob==null){
                                                 throw new org.apache.axis2.databinding.ADBException("videoPublishJob cannot be null!!");
                                            }
                                           localVideoPublishJob.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","videoPublishJob"),
                                               xmlWriter);
                                        } if (localServerDirectoryPublishJobTracker){
                                            if (localServerDirectoryPublishJob==null){
                                                 throw new org.apache.axis2.databinding.ADBException("serverDirectoryPublishJob cannot be null!!");
                                            }
                                           localServerDirectoryPublishJob.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","serverDirectoryPublishJob"),
                                               xmlWriter);
                                        } if (localUploadDirectoryJobTracker){
                                            if (localUploadDirectoryJob==null){
                                                 throw new org.apache.axis2.databinding.ADBException("uploadDirectoryJob cannot be null!!");
                                            }
                                           localUploadDirectoryJob.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","uploadDirectoryJob"),
                                               xmlWriter);
                                        } if (localUploadUrlsJobTracker){
                                            if (localUploadUrlsJob==null){
                                                 throw new org.apache.axis2.databinding.ADBException("uploadUrlsJob cannot be null!!");
                                            }
                                           localUploadUrlsJob.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","uploadUrlsJob"),
                                               xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.scene7.com/IpsApi/xsd")){
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

                
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "companyHandle"));
                                 
                                        if (localCompanyHandle != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCompanyHandle));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("companyHandle cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "jobHandle"));
                                 
                                        if (localJobHandle != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localJobHandle));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("jobHandle cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "name"));
                                 
                                        if (localName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("name cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "originalName"));
                                 
                                        if (localOriginalName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOriginalName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("originalName cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "type"));
                                 
                                        if (localType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("type cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "submitUserEmail"));
                                 
                                        if (localSubmitUserEmail != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSubmitUserEmail));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("submitUserEmail cannot be null!!");
                                        }
                                     if (localExecScheduleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "execSchedule"));
                                 
                                        if (localExecSchedule != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExecSchedule));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("execSchedule cannot be null!!");
                                        }
                                    } if (localNextFireTimeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "nextFireTime"));
                                 
                                        if (localNextFireTime != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNextFireTime));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("nextFireTime cannot be null!!");
                                        }
                                    } if (localTimeZoneTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "timeZone"));
                                 
                                        if (localTimeZone != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTimeZone));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("timeZone cannot be null!!");
                                        }
                                    } if (localTriggerStateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "triggerState"));
                                 
                                        if (localTriggerState != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTriggerState));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("triggerState cannot be null!!");
                                        }
                                    } if (localImageServingPublishJobTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "imageServingPublishJob"));
                            
                            
                                    if (localImageServingPublishJob==null){
                                         throw new org.apache.axis2.databinding.ADBException("imageServingPublishJob cannot be null!!");
                                    }
                                    elementList.add(localImageServingPublishJob);
                                } if (localImageRenderingPublishJobTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "imageRenderingPublishJob"));
                            
                            
                                    if (localImageRenderingPublishJob==null){
                                         throw new org.apache.axis2.databinding.ADBException("imageRenderingPublishJob cannot be null!!");
                                    }
                                    elementList.add(localImageRenderingPublishJob);
                                } if (localVideoPublishJobTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "videoPublishJob"));
                            
                            
                                    if (localVideoPublishJob==null){
                                         throw new org.apache.axis2.databinding.ADBException("videoPublishJob cannot be null!!");
                                    }
                                    elementList.add(localVideoPublishJob);
                                } if (localServerDirectoryPublishJobTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "serverDirectoryPublishJob"));
                            
                            
                                    if (localServerDirectoryPublishJob==null){
                                         throw new org.apache.axis2.databinding.ADBException("serverDirectoryPublishJob cannot be null!!");
                                    }
                                    elementList.add(localServerDirectoryPublishJob);
                                } if (localUploadDirectoryJobTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "uploadDirectoryJob"));
                            
                            
                                    if (localUploadDirectoryJob==null){
                                         throw new org.apache.axis2.databinding.ADBException("uploadDirectoryJob cannot be null!!");
                                    }
                                    elementList.add(localUploadDirectoryJob);
                                } if (localUploadUrlsJobTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "uploadUrlsJob"));
                            
                            
                                    if (localUploadUrlsJob==null){
                                         throw new org.apache.axis2.databinding.ADBException("uploadUrlsJob cannot be null!!");
                                    }
                                    elementList.add(localUploadUrlsJob);
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
        public static ScheduledJob parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            ScheduledJob object =
                new ScheduledJob();

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
                    
                            if (!"ScheduledJob".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (ScheduledJob)com.scene7.www.ipsapi.xsd.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","companyHandle").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"companyHandle" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCompanyHandle(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","jobHandle").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"jobHandle" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setJobHandle(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","name").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"name" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","originalName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"originalName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOriginalName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","type").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"type" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","submitUserEmail").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"submitUserEmail" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSubmitUserEmail(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","execSchedule").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"execSchedule" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setExecSchedule(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","nextFireTime").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"nextFireTime" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNextFireTime(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","timeZone").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"timeZone" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTimeZone(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","triggerState").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"triggerState" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTriggerState(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","imageServingPublishJob").equals(reader.getName())){
                                
                                                object.setImageServingPublishJob(com.scene7.www.ipsapi.xsd.ImageServingPublishJob.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","imageRenderingPublishJob").equals(reader.getName())){
                                
                                                object.setImageRenderingPublishJob(com.scene7.www.ipsapi.xsd.ImageRenderingPublishJob.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","videoPublishJob").equals(reader.getName())){
                                
                                                object.setVideoPublishJob(com.scene7.www.ipsapi.xsd.VideoPublishJob.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","serverDirectoryPublishJob").equals(reader.getName())){
                                
                                                object.setServerDirectoryPublishJob(com.scene7.www.ipsapi.xsd.ServerDirectoryPublishJob.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","uploadDirectoryJob").equals(reader.getName())){
                                
                                                object.setUploadDirectoryJob(com.scene7.www.ipsapi.xsd.UploadDirectoryJob.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","uploadUrlsJob").equals(reader.getName())){
                                
                                                object.setUploadUrlsJob(com.scene7.www.ipsapi.xsd.UploadUrlsJob.Factory.parse(reader));
                                              
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
           
    