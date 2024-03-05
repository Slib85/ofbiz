
/**
 * UploadDirectoryJob.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.scene7.www.ipsapi.xsd;
            

            /**
            *  UploadDirectoryJob bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class UploadDirectoryJob
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = UploadDirectoryJob
                Namespace URI = http://www.scene7.com/IpsApi/xsd
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for DestFolder
                        */

                        
                                    protected java.lang.String localDestFolder ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDestFolderTracker = false ;

                           public boolean isDestFolderSpecified(){
                               return localDestFolderTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDestFolder(){
                               return localDestFolder;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DestFolder
                               */
                               public void setDestFolder(java.lang.String param){
                            localDestFolderTracker = param != null;
                                   
                                            this.localDestFolder=param;
                                    

                               }
                            

                        /**
                        * field for ServerDir
                        */

                        
                                    protected java.lang.String localServerDir ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getServerDir(){
                               return localServerDir;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ServerDir
                               */
                               public void setServerDir(java.lang.String param){
                            
                                            this.localServerDir=param;
                                    

                               }
                            

                        /**
                        * field for Overwrite
                        */

                        
                                    protected boolean localOverwrite ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getOverwrite(){
                               return localOverwrite;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Overwrite
                               */
                               public void setOverwrite(boolean param){
                            
                                            this.localOverwrite=param;
                                    

                               }
                            

                        /**
                        * field for ReadyForPublish
                        */

                        
                                    protected boolean localReadyForPublish ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getReadyForPublish(){
                               return localReadyForPublish;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReadyForPublish
                               */
                               public void setReadyForPublish(boolean param){
                            
                                            this.localReadyForPublish=param;
                                    

                               }
                            

                        /**
                        * field for IncludeSubfolders
                        */

                        
                                    protected boolean localIncludeSubfolders ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getIncludeSubfolders(){
                               return localIncludeSubfolders;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IncludeSubfolders
                               */
                               public void setIncludeSubfolders(boolean param){
                            
                                            this.localIncludeSubfolders=param;
                                    

                               }
                            

                        /**
                        * field for CreateMask
                        */

                        
                                    protected boolean localCreateMask ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getCreateMask(){
                               return localCreateMask;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CreateMask
                               */
                               public void setCreateMask(boolean param){
                            
                                            this.localCreateMask=param;
                                    

                               }
                            

                        /**
                        * field for ProcessMetadataFiles
                        */

                        
                                    protected boolean localProcessMetadataFiles ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getProcessMetadataFiles(){
                               return localProcessMetadataFiles;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ProcessMetadataFiles
                               */
                               public void setProcessMetadataFiles(boolean param){
                            
                                            this.localProcessMetadataFiles=param;
                                    

                               }
                            

                        /**
                        * field for ManualCropOptions
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.ManualCropOptions localManualCropOptions ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localManualCropOptionsTracker = false ;

                           public boolean isManualCropOptionsSpecified(){
                               return localManualCropOptionsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.ManualCropOptions
                           */
                           public  com.scene7.www.ipsapi.xsd.ManualCropOptions getManualCropOptions(){
                               return localManualCropOptions;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ManualCropOptions
                               */
                               public void setManualCropOptions(com.scene7.www.ipsapi.xsd.ManualCropOptions param){
                            localManualCropOptionsTracker = param != null;
                                   
                                            this.localManualCropOptions=param;
                                    

                               }
                            

                        /**
                        * field for AutoColorCropOptions
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.AutoColorCropOptions localAutoColorCropOptions ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAutoColorCropOptionsTracker = false ;

                           public boolean isAutoColorCropOptionsSpecified(){
                               return localAutoColorCropOptionsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.AutoColorCropOptions
                           */
                           public  com.scene7.www.ipsapi.xsd.AutoColorCropOptions getAutoColorCropOptions(){
                               return localAutoColorCropOptions;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AutoColorCropOptions
                               */
                               public void setAutoColorCropOptions(com.scene7.www.ipsapi.xsd.AutoColorCropOptions param){
                            localAutoColorCropOptionsTracker = param != null;
                                   
                                            this.localAutoColorCropOptions=param;
                                    

                               }
                            

                        /**
                        * field for AutoTransparentCropOptions
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.AutoTransparentCropOptions localAutoTransparentCropOptions ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAutoTransparentCropOptionsTracker = false ;

                           public boolean isAutoTransparentCropOptionsSpecified(){
                               return localAutoTransparentCropOptionsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.AutoTransparentCropOptions
                           */
                           public  com.scene7.www.ipsapi.xsd.AutoTransparentCropOptions getAutoTransparentCropOptions(){
                               return localAutoTransparentCropOptions;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AutoTransparentCropOptions
                               */
                               public void setAutoTransparentCropOptions(com.scene7.www.ipsapi.xsd.AutoTransparentCropOptions param){
                            localAutoTransparentCropOptionsTracker = param != null;
                                   
                                            this.localAutoTransparentCropOptions=param;
                                    

                               }
                            

                        /**
                        * field for PhotoshopOptions
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.PhotoshopOptions localPhotoshopOptions ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPhotoshopOptionsTracker = false ;

                           public boolean isPhotoshopOptionsSpecified(){
                               return localPhotoshopOptionsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.PhotoshopOptions
                           */
                           public  com.scene7.www.ipsapi.xsd.PhotoshopOptions getPhotoshopOptions(){
                               return localPhotoshopOptions;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PhotoshopOptions
                               */
                               public void setPhotoshopOptions(com.scene7.www.ipsapi.xsd.PhotoshopOptions param){
                            localPhotoshopOptionsTracker = param != null;
                                   
                                            this.localPhotoshopOptions=param;
                                    

                               }
                            

                        /**
                        * field for PostScriptOptions
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.PostScriptOptions localPostScriptOptions ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPostScriptOptionsTracker = false ;

                           public boolean isPostScriptOptionsSpecified(){
                               return localPostScriptOptionsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.PostScriptOptions
                           */
                           public  com.scene7.www.ipsapi.xsd.PostScriptOptions getPostScriptOptions(){
                               return localPostScriptOptions;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PostScriptOptions
                               */
                               public void setPostScriptOptions(com.scene7.www.ipsapi.xsd.PostScriptOptions param){
                            localPostScriptOptionsTracker = param != null;
                                   
                                            this.localPostScriptOptions=param;
                                    

                               }
                            

                        /**
                        * field for ColorManagementOptions
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.ColorManagementOptions localColorManagementOptions ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localColorManagementOptionsTracker = false ;

                           public boolean isColorManagementOptionsSpecified(){
                               return localColorManagementOptionsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.ColorManagementOptions
                           */
                           public  com.scene7.www.ipsapi.xsd.ColorManagementOptions getColorManagementOptions(){
                               return localColorManagementOptions;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ColorManagementOptions
                               */
                               public void setColorManagementOptions(com.scene7.www.ipsapi.xsd.ColorManagementOptions param){
                            localColorManagementOptionsTracker = param != null;
                                   
                                            this.localColorManagementOptions=param;
                                    

                               }
                            

                        /**
                        * field for ProjectHandleArray
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.HandleArray localProjectHandleArray ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProjectHandleArrayTracker = false ;

                           public boolean isProjectHandleArraySpecified(){
                               return localProjectHandleArrayTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.HandleArray
                           */
                           public  com.scene7.www.ipsapi.xsd.HandleArray getProjectHandleArray(){
                               return localProjectHandleArray;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ProjectHandleArray
                               */
                               public void setProjectHandleArray(com.scene7.www.ipsapi.xsd.HandleArray param){
                            localProjectHandleArrayTracker = param != null;
                                   
                                            this.localProjectHandleArray=param;
                                    

                               }
                            

                        /**
                        * field for EmailSetting
                        */

                        
                                    protected java.lang.String localEmailSetting ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEmailSetting(){
                               return localEmailSetting;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EmailSetting
                               */
                               public void setEmailSetting(java.lang.String param){
                            
                                            this.localEmailSetting=param;
                                    

                               }
                            

                        /**
                        * field for PostJobOnlyIfFiles
                        */

                        
                                    protected boolean localPostJobOnlyIfFiles ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPostJobOnlyIfFilesTracker = false ;

                           public boolean isPostJobOnlyIfFilesSpecified(){
                               return localPostJobOnlyIfFilesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPostJobOnlyIfFiles(){
                               return localPostJobOnlyIfFiles;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PostJobOnlyIfFiles
                               */
                               public void setPostJobOnlyIfFiles(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPostJobOnlyIfFilesTracker =
                                       true;
                                   
                                            this.localPostJobOnlyIfFiles=param;
                                    

                               }
                            

                        /**
                        * field for PostHttpUrl
                        */

                        
                                    protected java.lang.String localPostHttpUrl ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPostHttpUrlTracker = false ;

                           public boolean isPostHttpUrlSpecified(){
                               return localPostHttpUrlTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPostHttpUrl(){
                               return localPostHttpUrl;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PostHttpUrl
                               */
                               public void setPostHttpUrl(java.lang.String param){
                            localPostHttpUrlTracker = param != null;
                                   
                                            this.localPostHttpUrl=param;
                                    

                               }
                            

                        /**
                        * field for PostImageServingPublishJob
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.ImageServingPublishJob localPostImageServingPublishJob ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPostImageServingPublishJobTracker = false ;

                           public boolean isPostImageServingPublishJobSpecified(){
                               return localPostImageServingPublishJobTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.ImageServingPublishJob
                           */
                           public  com.scene7.www.ipsapi.xsd.ImageServingPublishJob getPostImageServingPublishJob(){
                               return localPostImageServingPublishJob;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PostImageServingPublishJob
                               */
                               public void setPostImageServingPublishJob(com.scene7.www.ipsapi.xsd.ImageServingPublishJob param){
                            localPostImageServingPublishJobTracker = param != null;
                                   
                                            this.localPostImageServingPublishJob=param;
                                    

                               }
                            

                        /**
                        * field for PostImageRenderingPublishJob
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.ImageRenderingPublishJob localPostImageRenderingPublishJob ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPostImageRenderingPublishJobTracker = false ;

                           public boolean isPostImageRenderingPublishJobSpecified(){
                               return localPostImageRenderingPublishJobTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.ImageRenderingPublishJob
                           */
                           public  com.scene7.www.ipsapi.xsd.ImageRenderingPublishJob getPostImageRenderingPublishJob(){
                               return localPostImageRenderingPublishJob;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PostImageRenderingPublishJob
                               */
                               public void setPostImageRenderingPublishJob(com.scene7.www.ipsapi.xsd.ImageRenderingPublishJob param){
                            localPostImageRenderingPublishJobTracker = param != null;
                                   
                                            this.localPostImageRenderingPublishJob=param;
                                    

                               }
                            

                        /**
                        * field for PostVideoPublishJob
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.VideoPublishJob localPostVideoPublishJob ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPostVideoPublishJobTracker = false ;

                           public boolean isPostVideoPublishJobSpecified(){
                               return localPostVideoPublishJobTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.VideoPublishJob
                           */
                           public  com.scene7.www.ipsapi.xsd.VideoPublishJob getPostVideoPublishJob(){
                               return localPostVideoPublishJob;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PostVideoPublishJob
                               */
                               public void setPostVideoPublishJob(com.scene7.www.ipsapi.xsd.VideoPublishJob param){
                            localPostVideoPublishJobTracker = param != null;
                                   
                                            this.localPostVideoPublishJob=param;
                                    

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
                           namespacePrefix+":UploadDirectoryJob",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "UploadDirectoryJob",
                           xmlWriter);
                   }

               
                   }
                if (localDestFolderTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "destFolder", xmlWriter);
                             

                                          if (localDestFolder==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("destFolder cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDestFolder);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "serverDir", xmlWriter);
                             

                                          if (localServerDir==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("serverDir cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localServerDir);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "overwrite", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("overwrite cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOverwrite));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "readyForPublish", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("readyForPublish cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReadyForPublish));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "includeSubfolders", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("includeSubfolders cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIncludeSubfolders));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "createMask", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("createMask cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreateMask));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "processMetadataFiles", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("processMetadataFiles cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProcessMetadataFiles));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                              if (localManualCropOptionsTracker){
                                            if (localManualCropOptions==null){
                                                 throw new org.apache.axis2.databinding.ADBException("manualCropOptions cannot be null!!");
                                            }
                                           localManualCropOptions.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","manualCropOptions"),
                                               xmlWriter);
                                        } if (localAutoColorCropOptionsTracker){
                                            if (localAutoColorCropOptions==null){
                                                 throw new org.apache.axis2.databinding.ADBException("autoColorCropOptions cannot be null!!");
                                            }
                                           localAutoColorCropOptions.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","autoColorCropOptions"),
                                               xmlWriter);
                                        } if (localAutoTransparentCropOptionsTracker){
                                            if (localAutoTransparentCropOptions==null){
                                                 throw new org.apache.axis2.databinding.ADBException("autoTransparentCropOptions cannot be null!!");
                                            }
                                           localAutoTransparentCropOptions.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","autoTransparentCropOptions"),
                                               xmlWriter);
                                        } if (localPhotoshopOptionsTracker){
                                            if (localPhotoshopOptions==null){
                                                 throw new org.apache.axis2.databinding.ADBException("photoshopOptions cannot be null!!");
                                            }
                                           localPhotoshopOptions.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","photoshopOptions"),
                                               xmlWriter);
                                        } if (localPostScriptOptionsTracker){
                                            if (localPostScriptOptions==null){
                                                 throw new org.apache.axis2.databinding.ADBException("postScriptOptions cannot be null!!");
                                            }
                                           localPostScriptOptions.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","postScriptOptions"),
                                               xmlWriter);
                                        } if (localColorManagementOptionsTracker){
                                            if (localColorManagementOptions==null){
                                                 throw new org.apache.axis2.databinding.ADBException("colorManagementOptions cannot be null!!");
                                            }
                                           localColorManagementOptions.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","colorManagementOptions"),
                                               xmlWriter);
                                        } if (localProjectHandleArrayTracker){
                                            if (localProjectHandleArray==null){
                                                 throw new org.apache.axis2.databinding.ADBException("projectHandleArray cannot be null!!");
                                            }
                                           localProjectHandleArray.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","projectHandleArray"),
                                               xmlWriter);
                                        }
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "emailSetting", xmlWriter);
                             

                                          if (localEmailSetting==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("emailSetting cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEmailSetting);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localPostJobOnlyIfFilesTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "postJobOnlyIfFiles", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("postJobOnlyIfFiles cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPostJobOnlyIfFiles));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPostHttpUrlTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "postHttpUrl", xmlWriter);
                             

                                          if (localPostHttpUrl==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("postHttpUrl cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPostHttpUrl);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPostImageServingPublishJobTracker){
                                            if (localPostImageServingPublishJob==null){
                                                 throw new org.apache.axis2.databinding.ADBException("postImageServingPublishJob cannot be null!!");
                                            }
                                           localPostImageServingPublishJob.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","postImageServingPublishJob"),
                                               xmlWriter);
                                        } if (localPostImageRenderingPublishJobTracker){
                                            if (localPostImageRenderingPublishJob==null){
                                                 throw new org.apache.axis2.databinding.ADBException("postImageRenderingPublishJob cannot be null!!");
                                            }
                                           localPostImageRenderingPublishJob.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","postImageRenderingPublishJob"),
                                               xmlWriter);
                                        } if (localPostVideoPublishJobTracker){
                                            if (localPostVideoPublishJob==null){
                                                 throw new org.apache.axis2.databinding.ADBException("postVideoPublishJob cannot be null!!");
                                            }
                                           localPostVideoPublishJob.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","postVideoPublishJob"),
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

                 if (localDestFolderTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "destFolder"));
                                 
                                        if (localDestFolder != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDestFolder));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("destFolder cannot be null!!");
                                        }
                                    }
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "serverDir"));
                                 
                                        if (localServerDir != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localServerDir));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("serverDir cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "overwrite"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOverwrite));
                            
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "readyForPublish"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReadyForPublish));
                            
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "includeSubfolders"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIncludeSubfolders));
                            
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "createMask"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreateMask));
                            
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "processMetadataFiles"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProcessMetadataFiles));
                             if (localManualCropOptionsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "manualCropOptions"));
                            
                            
                                    if (localManualCropOptions==null){
                                         throw new org.apache.axis2.databinding.ADBException("manualCropOptions cannot be null!!");
                                    }
                                    elementList.add(localManualCropOptions);
                                } if (localAutoColorCropOptionsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "autoColorCropOptions"));
                            
                            
                                    if (localAutoColorCropOptions==null){
                                         throw new org.apache.axis2.databinding.ADBException("autoColorCropOptions cannot be null!!");
                                    }
                                    elementList.add(localAutoColorCropOptions);
                                } if (localAutoTransparentCropOptionsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "autoTransparentCropOptions"));
                            
                            
                                    if (localAutoTransparentCropOptions==null){
                                         throw new org.apache.axis2.databinding.ADBException("autoTransparentCropOptions cannot be null!!");
                                    }
                                    elementList.add(localAutoTransparentCropOptions);
                                } if (localPhotoshopOptionsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "photoshopOptions"));
                            
                            
                                    if (localPhotoshopOptions==null){
                                         throw new org.apache.axis2.databinding.ADBException("photoshopOptions cannot be null!!");
                                    }
                                    elementList.add(localPhotoshopOptions);
                                } if (localPostScriptOptionsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "postScriptOptions"));
                            
                            
                                    if (localPostScriptOptions==null){
                                         throw new org.apache.axis2.databinding.ADBException("postScriptOptions cannot be null!!");
                                    }
                                    elementList.add(localPostScriptOptions);
                                } if (localColorManagementOptionsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "colorManagementOptions"));
                            
                            
                                    if (localColorManagementOptions==null){
                                         throw new org.apache.axis2.databinding.ADBException("colorManagementOptions cannot be null!!");
                                    }
                                    elementList.add(localColorManagementOptions);
                                } if (localProjectHandleArrayTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "projectHandleArray"));
                            
                            
                                    if (localProjectHandleArray==null){
                                         throw new org.apache.axis2.databinding.ADBException("projectHandleArray cannot be null!!");
                                    }
                                    elementList.add(localProjectHandleArray);
                                }
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "emailSetting"));
                                 
                                        if (localEmailSetting != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmailSetting));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("emailSetting cannot be null!!");
                                        }
                                     if (localPostJobOnlyIfFilesTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "postJobOnlyIfFiles"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPostJobOnlyIfFiles));
                            } if (localPostHttpUrlTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "postHttpUrl"));
                                 
                                        if (localPostHttpUrl != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPostHttpUrl));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("postHttpUrl cannot be null!!");
                                        }
                                    } if (localPostImageServingPublishJobTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "postImageServingPublishJob"));
                            
                            
                                    if (localPostImageServingPublishJob==null){
                                         throw new org.apache.axis2.databinding.ADBException("postImageServingPublishJob cannot be null!!");
                                    }
                                    elementList.add(localPostImageServingPublishJob);
                                } if (localPostImageRenderingPublishJobTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "postImageRenderingPublishJob"));
                            
                            
                                    if (localPostImageRenderingPublishJob==null){
                                         throw new org.apache.axis2.databinding.ADBException("postImageRenderingPublishJob cannot be null!!");
                                    }
                                    elementList.add(localPostImageRenderingPublishJob);
                                } if (localPostVideoPublishJobTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "postVideoPublishJob"));
                            
                            
                                    if (localPostVideoPublishJob==null){
                                         throw new org.apache.axis2.databinding.ADBException("postVideoPublishJob cannot be null!!");
                                    }
                                    elementList.add(localPostVideoPublishJob);
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
        public static UploadDirectoryJob parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            UploadDirectoryJob object =
                new UploadDirectoryJob();

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
                    
                            if (!"UploadDirectoryJob".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (UploadDirectoryJob)com.scene7.www.ipsapi.xsd.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","destFolder").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"destFolder" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDestFolder(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","serverDir").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"serverDir" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setServerDir(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","overwrite").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"overwrite" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOverwrite(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","readyForPublish").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"readyForPublish" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReadyForPublish(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","includeSubfolders").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"includeSubfolders" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIncludeSubfolders(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","createMask").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"createMask" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCreateMask(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","processMetadataFiles").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"processMetadataFiles" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProcessMetadataFiles(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","manualCropOptions").equals(reader.getName())){
                                
                                                object.setManualCropOptions(com.scene7.www.ipsapi.xsd.ManualCropOptions.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","autoColorCropOptions").equals(reader.getName())){
                                
                                                object.setAutoColorCropOptions(com.scene7.www.ipsapi.xsd.AutoColorCropOptions.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","autoTransparentCropOptions").equals(reader.getName())){
                                
                                                object.setAutoTransparentCropOptions(com.scene7.www.ipsapi.xsd.AutoTransparentCropOptions.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","photoshopOptions").equals(reader.getName())){
                                
                                                object.setPhotoshopOptions(com.scene7.www.ipsapi.xsd.PhotoshopOptions.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","postScriptOptions").equals(reader.getName())){
                                
                                                object.setPostScriptOptions(com.scene7.www.ipsapi.xsd.PostScriptOptions.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","colorManagementOptions").equals(reader.getName())){
                                
                                                object.setColorManagementOptions(com.scene7.www.ipsapi.xsd.ColorManagementOptions.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","projectHandleArray").equals(reader.getName())){
                                
                                                object.setProjectHandleArray(com.scene7.www.ipsapi.xsd.HandleArray.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","emailSetting").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"emailSetting" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEmailSetting(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","postJobOnlyIfFiles").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"postJobOnlyIfFiles" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPostJobOnlyIfFiles(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","postHttpUrl").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"postHttpUrl" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPostHttpUrl(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","postImageServingPublishJob").equals(reader.getName())){
                                
                                                object.setPostImageServingPublishJob(com.scene7.www.ipsapi.xsd.ImageServingPublishJob.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","postImageRenderingPublishJob").equals(reader.getName())){
                                
                                                object.setPostImageRenderingPublishJob(com.scene7.www.ipsapi.xsd.ImageRenderingPublishJob.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","postVideoPublishJob").equals(reader.getName())){
                                
                                                object.setPostVideoPublishJob(com.scene7.www.ipsapi.xsd.VideoPublishJob.Factory.parse(reader));
                                              
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
           
    