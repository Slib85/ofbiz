
/**
 * Asset.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.scene7.www.ipsapi.xsd;
            

            /**
            *  Asset bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class Asset
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = Asset
                Namespace URI = http://www.scene7.com/IpsApi/xsd
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for AssetHandle
                        */

                        
                                    protected java.lang.String localAssetHandle ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAssetHandleTracker = false ;

                           public boolean isAssetHandleSpecified(){
                               return localAssetHandleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAssetHandle(){
                               return localAssetHandle;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AssetHandle
                               */
                               public void setAssetHandle(java.lang.String param){
                            localAssetHandleTracker = param != null;
                                   
                                            this.localAssetHandle=param;
                                    

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
                        * field for Name
                        */

                        
                                    protected java.lang.String localName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNameTracker = false ;

                           public boolean isNameSpecified(){
                               return localNameTracker;
                           }

                           

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
                            localNameTracker = param != null;
                                   
                                            this.localName=param;
                                    

                               }
                            

                        /**
                        * field for Folder
                        */

                        
                                    protected java.lang.String localFolder ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFolderTracker = false ;

                           public boolean isFolderSpecified(){
                               return localFolderTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFolder(){
                               return localFolder;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Folder
                               */
                               public void setFolder(java.lang.String param){
                            localFolderTracker = param != null;
                                   
                                            this.localFolder=param;
                                    

                               }
                            

                        /**
                        * field for FolderHandle
                        */

                        
                                    protected java.lang.String localFolderHandle ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFolderHandleTracker = false ;

                           public boolean isFolderHandleSpecified(){
                               return localFolderHandleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFolderHandle(){
                               return localFolderHandle;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FolderHandle
                               */
                               public void setFolderHandle(java.lang.String param){
                            localFolderHandleTracker = param != null;
                                   
                                            this.localFolderHandle=param;
                                    

                               }
                            

                        /**
                        * field for ReadyForPublish
                        */

                        
                                    protected boolean localReadyForPublish ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReadyForPublishTracker = false ;

                           public boolean isReadyForPublishSpecified(){
                               return localReadyForPublishTracker;
                           }

                           

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
                            
                                       // setting primitive attribute tracker to true
                                       localReadyForPublishTracker =
                                       true;
                                   
                                            this.localReadyForPublish=param;
                                    

                               }
                            

                        /**
                        * field for Projects
                        */

                        
                                    protected java.lang.String localProjects ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProjectsTracker = false ;

                           public boolean isProjectsSpecified(){
                               return localProjectsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProjects(){
                               return localProjects;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Projects
                               */
                               public void setProjects(java.lang.String param){
                            localProjectsTracker = param != null;
                                   
                                            this.localProjects=param;
                                    

                               }
                            

                        /**
                        * field for IpsImageUrl
                        */

                        
                                    protected java.lang.String localIpsImageUrl ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIpsImageUrlTracker = false ;

                           public boolean isIpsImageUrlSpecified(){
                               return localIpsImageUrlTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getIpsImageUrl(){
                               return localIpsImageUrl;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IpsImageUrl
                               */
                               public void setIpsImageUrl(java.lang.String param){
                            localIpsImageUrlTracker = param != null;
                                   
                                            this.localIpsImageUrl=param;
                                    

                               }
                            

                        /**
                        * field for Created
                        */

                        
                                    protected java.util.Calendar localCreated ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreatedTracker = false ;

                           public boolean isCreatedSpecified(){
                               return localCreatedTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getCreated(){
                               return localCreated;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Created
                               */
                               public void setCreated(java.util.Calendar param){
                            localCreatedTracker = param != null;
                                   
                                            this.localCreated=param;
                                    

                               }
                            

                        /**
                        * field for CreateUser
                        */

                        
                                    protected java.lang.String localCreateUser ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreateUserTracker = false ;

                           public boolean isCreateUserSpecified(){
                               return localCreateUserTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCreateUser(){
                               return localCreateUser;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CreateUser
                               */
                               public void setCreateUser(java.lang.String param){
                            localCreateUserTracker = param != null;
                                   
                                            this.localCreateUser=param;
                                    

                               }
                            

                        /**
                        * field for LastModified
                        */

                        
                                    protected java.util.Calendar localLastModified ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLastModifiedTracker = false ;

                           public boolean isLastModifiedSpecified(){
                               return localLastModifiedTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getLastModified(){
                               return localLastModified;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LastModified
                               */
                               public void setLastModified(java.util.Calendar param){
                            localLastModifiedTracker = param != null;
                                   
                                            this.localLastModified=param;
                                    

                               }
                            

                        /**
                        * field for LastModifyUser
                        */

                        
                                    protected java.lang.String localLastModifyUser ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLastModifyUserTracker = false ;

                           public boolean isLastModifyUserSpecified(){
                               return localLastModifyUserTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLastModifyUser(){
                               return localLastModifyUser;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LastModifyUser
                               */
                               public void setLastModifyUser(java.lang.String param){
                            localLastModifyUserTracker = param != null;
                                   
                                            this.localLastModifyUser=param;
                                    

                               }
                            

                        /**
                        * field for MetadataArray
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.MetadataArray localMetadataArray ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMetadataArrayTracker = false ;

                           public boolean isMetadataArraySpecified(){
                               return localMetadataArrayTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.MetadataArray
                           */
                           public  com.scene7.www.ipsapi.xsd.MetadataArray getMetadataArray(){
                               return localMetadataArray;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MetadataArray
                               */
                               public void setMetadataArray(com.scene7.www.ipsapi.xsd.MetadataArray param){
                            localMetadataArrayTracker = param != null;
                                   
                                            this.localMetadataArray=param;
                                    

                               }
                            

                        /**
                        * field for ImageInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.ImageInfo localImageInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localImageInfoTracker = false ;

                           public boolean isImageInfoSpecified(){
                               return localImageInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.ImageInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.ImageInfo getImageInfo(){
                               return localImageInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ImageInfo
                               */
                               public void setImageInfo(com.scene7.www.ipsapi.xsd.ImageInfo param){
                            localImageInfoTracker = param != null;
                                   
                                            this.localImageInfo=param;
                                    

                               }
                            

                        /**
                        * field for TemplateInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.TemplateInfo localTemplateInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTemplateInfoTracker = false ;

                           public boolean isTemplateInfoSpecified(){
                               return localTemplateInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.TemplateInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.TemplateInfo getTemplateInfo(){
                               return localTemplateInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TemplateInfo
                               */
                               public void setTemplateInfo(com.scene7.www.ipsapi.xsd.TemplateInfo param){
                            localTemplateInfoTracker = param != null;
                                   
                                            this.localTemplateInfo=param;
                                    

                               }
                            

                        /**
                        * field for WatermarkInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.WatermarkInfo localWatermarkInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWatermarkInfoTracker = false ;

                           public boolean isWatermarkInfoSpecified(){
                               return localWatermarkInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.WatermarkInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.WatermarkInfo getWatermarkInfo(){
                               return localWatermarkInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WatermarkInfo
                               */
                               public void setWatermarkInfo(com.scene7.www.ipsapi.xsd.WatermarkInfo param){
                            localWatermarkInfoTracker = param != null;
                                   
                                            this.localWatermarkInfo=param;
                                    

                               }
                            

                        /**
                        * field for RenderSceneInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.RenderSceneInfo localRenderSceneInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRenderSceneInfoTracker = false ;

                           public boolean isRenderSceneInfoSpecified(){
                               return localRenderSceneInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.RenderSceneInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.RenderSceneInfo getRenderSceneInfo(){
                               return localRenderSceneInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RenderSceneInfo
                               */
                               public void setRenderSceneInfo(com.scene7.www.ipsapi.xsd.RenderSceneInfo param){
                            localRenderSceneInfoTracker = param != null;
                                   
                                            this.localRenderSceneInfo=param;
                                    

                               }
                            

                        /**
                        * field for VignetteInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.VignetteInfo localVignetteInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localVignetteInfoTracker = false ;

                           public boolean isVignetteInfoSpecified(){
                               return localVignetteInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.VignetteInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.VignetteInfo getVignetteInfo(){
                               return localVignetteInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param VignetteInfo
                               */
                               public void setVignetteInfo(com.scene7.www.ipsapi.xsd.VignetteInfo param){
                            localVignetteInfoTracker = param != null;
                                   
                                            this.localVignetteInfo=param;
                                    

                               }
                            

                        /**
                        * field for CabinetInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.CabinetInfo localCabinetInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCabinetInfoTracker = false ;

                           public boolean isCabinetInfoSpecified(){
                               return localCabinetInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.CabinetInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.CabinetInfo getCabinetInfo(){
                               return localCabinetInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CabinetInfo
                               */
                               public void setCabinetInfo(com.scene7.www.ipsapi.xsd.CabinetInfo param){
                            localCabinetInfoTracker = param != null;
                                   
                                            this.localCabinetInfo=param;
                                    

                               }
                            

                        /**
                        * field for WindowCoveringInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.WindowCoveringInfo localWindowCoveringInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWindowCoveringInfoTracker = false ;

                           public boolean isWindowCoveringInfoSpecified(){
                               return localWindowCoveringInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.WindowCoveringInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.WindowCoveringInfo getWindowCoveringInfo(){
                               return localWindowCoveringInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WindowCoveringInfo
                               */
                               public void setWindowCoveringInfo(com.scene7.www.ipsapi.xsd.WindowCoveringInfo param){
                            localWindowCoveringInfoTracker = param != null;
                                   
                                            this.localWindowCoveringInfo=param;
                                    

                               }
                            

                        /**
                        * field for IccProfileInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.IccProfileInfo localIccProfileInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIccProfileInfoTracker = false ;

                           public boolean isIccProfileInfoSpecified(){
                               return localIccProfileInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.IccProfileInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.IccProfileInfo getIccProfileInfo(){
                               return localIccProfileInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IccProfileInfo
                               */
                               public void setIccProfileInfo(com.scene7.www.ipsapi.xsd.IccProfileInfo param){
                            localIccProfileInfoTracker = param != null;
                                   
                                            this.localIccProfileInfo=param;
                                    

                               }
                            

                        /**
                        * field for FontInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.FontInfo localFontInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFontInfoTracker = false ;

                           public boolean isFontInfoSpecified(){
                               return localFontInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.FontInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.FontInfo getFontInfo(){
                               return localFontInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FontInfo
                               */
                               public void setFontInfo(com.scene7.www.ipsapi.xsd.FontInfo param){
                            localFontInfoTracker = param != null;
                                   
                                            this.localFontInfo=param;
                                    

                               }
                            

                        /**
                        * field for XslInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.XslInfo localXslInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localXslInfoTracker = false ;

                           public boolean isXslInfoSpecified(){
                               return localXslInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.XslInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.XslInfo getXslInfo(){
                               return localXslInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param XslInfo
                               */
                               public void setXslInfo(com.scene7.www.ipsapi.xsd.XslInfo param){
                            localXslInfoTracker = param != null;
                                   
                                            this.localXslInfo=param;
                                    

                               }
                            

                        /**
                        * field for ViewerSwfInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.ViewerSwfInfo localViewerSwfInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localViewerSwfInfoTracker = false ;

                           public boolean isViewerSwfInfoSpecified(){
                               return localViewerSwfInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.ViewerSwfInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.ViewerSwfInfo getViewerSwfInfo(){
                               return localViewerSwfInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ViewerSwfInfo
                               */
                               public void setViewerSwfInfo(com.scene7.www.ipsapi.xsd.ViewerSwfInfo param){
                            localViewerSwfInfoTracker = param != null;
                                   
                                            this.localViewerSwfInfo=param;
                                    

                               }
                            

                        /**
                        * field for XmlInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.XmlInfo localXmlInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localXmlInfoTracker = false ;

                           public boolean isXmlInfoSpecified(){
                               return localXmlInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.XmlInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.XmlInfo getXmlInfo(){
                               return localXmlInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param XmlInfo
                               */
                               public void setXmlInfo(com.scene7.www.ipsapi.xsd.XmlInfo param){
                            localXmlInfoTracker = param != null;
                                   
                                            this.localXmlInfo=param;
                                    

                               }
                            

                        /**
                        * field for SvgInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.SvgInfo localSvgInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSvgInfoTracker = false ;

                           public boolean isSvgInfoSpecified(){
                               return localSvgInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.SvgInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.SvgInfo getSvgInfo(){
                               return localSvgInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SvgInfo
                               */
                               public void setSvgInfo(com.scene7.www.ipsapi.xsd.SvgInfo param){
                            localSvgInfoTracker = param != null;
                                   
                                            this.localSvgInfo=param;
                                    

                               }
                            

                        /**
                        * field for ZipInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.ZipInfo localZipInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localZipInfoTracker = false ;

                           public boolean isZipInfoSpecified(){
                               return localZipInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.ZipInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.ZipInfo getZipInfo(){
                               return localZipInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ZipInfo
                               */
                               public void setZipInfo(com.scene7.www.ipsapi.xsd.ZipInfo param){
                            localZipInfoTracker = param != null;
                                   
                                            this.localZipInfo=param;
                                    

                               }
                            

                        /**
                        * field for VideoInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.VideoInfo localVideoInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localVideoInfoTracker = false ;

                           public boolean isVideoInfoSpecified(){
                               return localVideoInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.VideoInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.VideoInfo getVideoInfo(){
                               return localVideoInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param VideoInfo
                               */
                               public void setVideoInfo(com.scene7.www.ipsapi.xsd.VideoInfo param){
                            localVideoInfoTracker = param != null;
                                   
                                            this.localVideoInfo=param;
                                    

                               }
                            

                        /**
                        * field for AcoInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.AcoInfo localAcoInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAcoInfoTracker = false ;

                           public boolean isAcoInfoSpecified(){
                               return localAcoInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.AcoInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.AcoInfo getAcoInfo(){
                               return localAcoInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AcoInfo
                               */
                               public void setAcoInfo(com.scene7.www.ipsapi.xsd.AcoInfo param){
                            localAcoInfoTracker = param != null;
                                   
                                            this.localAcoInfo=param;
                                    

                               }
                            

                        /**
                        * field for PdfInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.PdfInfo localPdfInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPdfInfoTracker = false ;

                           public boolean isPdfInfoSpecified(){
                               return localPdfInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.PdfInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.PdfInfo getPdfInfo(){
                               return localPdfInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PdfInfo
                               */
                               public void setPdfInfo(com.scene7.www.ipsapi.xsd.PdfInfo param){
                            localPdfInfoTracker = param != null;
                                   
                                            this.localPdfInfo=param;
                                    

                               }
                            

                        /**
                        * field for PsdInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.PsdInfo localPsdInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPsdInfoTracker = false ;

                           public boolean isPsdInfoSpecified(){
                               return localPsdInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.PsdInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.PsdInfo getPsdInfo(){
                               return localPsdInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PsdInfo
                               */
                               public void setPsdInfo(com.scene7.www.ipsapi.xsd.PsdInfo param){
                            localPsdInfoTracker = param != null;
                                   
                                            this.localPsdInfo=param;
                                    

                               }
                            

                        /**
                        * field for FlashInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.FlashInfo localFlashInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFlashInfoTracker = false ;

                           public boolean isFlashInfoSpecified(){
                               return localFlashInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.FlashInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.FlashInfo getFlashInfo(){
                               return localFlashInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FlashInfo
                               */
                               public void setFlashInfo(com.scene7.www.ipsapi.xsd.FlashInfo param){
                            localFlashInfoTracker = param != null;
                                   
                                            this.localFlashInfo=param;
                                    

                               }
                            

                        /**
                        * field for InDesignInfo
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.InDesignInfo localInDesignInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInDesignInfoTracker = false ;

                           public boolean isInDesignInfoSpecified(){
                               return localInDesignInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.InDesignInfo
                           */
                           public  com.scene7.www.ipsapi.xsd.InDesignInfo getInDesignInfo(){
                               return localInDesignInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InDesignInfo
                               */
                               public void setInDesignInfo(com.scene7.www.ipsapi.xsd.InDesignInfo param){
                            localInDesignInfoTracker = param != null;
                                   
                                            this.localInDesignInfo=param;
                                    

                               }
                            

                        /**
                        * field for Permissions
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.PermissionArray localPermissions ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPermissionsTracker = false ;

                           public boolean isPermissionsSpecified(){
                               return localPermissionsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.PermissionArray
                           */
                           public  com.scene7.www.ipsapi.xsd.PermissionArray getPermissions(){
                               return localPermissions;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Permissions
                               */
                               public void setPermissions(com.scene7.www.ipsapi.xsd.PermissionArray param){
                            localPermissionsTracker = param != null;
                                   
                                            this.localPermissions=param;
                                    

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
                           namespacePrefix+":Asset",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "Asset",
                           xmlWriter);
                   }

               
                   }
                if (localAssetHandleTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "assetHandle", xmlWriter);
                             

                                          if (localAssetHandle==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("assetHandle cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAssetHandle);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTypeTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "type", xmlWriter);
                             

                                          if (localType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("type cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNameTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "name", xmlWriter);
                             

                                          if (localName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("name cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFolderTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "folder", xmlWriter);
                             

                                          if (localFolder==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("folder cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFolder);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFolderHandleTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "folderHandle", xmlWriter);
                             

                                          if (localFolderHandle==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("folderHandle cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFolderHandle);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localReadyForPublishTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "readyForPublish", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("readyForPublish cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReadyForPublish));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localProjectsTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "projects", xmlWriter);
                             

                                          if (localProjects==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("projects cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProjects);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localIpsImageUrlTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "ipsImageUrl", xmlWriter);
                             

                                          if (localIpsImageUrl==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("ipsImageUrl cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localIpsImageUrl);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCreatedTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "created", xmlWriter);
                             

                                          if (localCreated==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("created cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreated));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCreateUserTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "createUser", xmlWriter);
                             

                                          if (localCreateUser==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("createUser cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCreateUser);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLastModifiedTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "lastModified", xmlWriter);
                             

                                          if (localLastModified==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("lastModified cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastModified));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLastModifyUserTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "lastModifyUser", xmlWriter);
                             

                                          if (localLastModifyUser==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("lastModifyUser cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLastModifyUser);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMetadataArrayTracker){
                                            if (localMetadataArray==null){
                                                 throw new org.apache.axis2.databinding.ADBException("metadataArray cannot be null!!");
                                            }
                                           localMetadataArray.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","metadataArray"),
                                               xmlWriter);
                                        } if (localImageInfoTracker){
                                            if (localImageInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("imageInfo cannot be null!!");
                                            }
                                           localImageInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","imageInfo"),
                                               xmlWriter);
                                        } if (localTemplateInfoTracker){
                                            if (localTemplateInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("templateInfo cannot be null!!");
                                            }
                                           localTemplateInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","templateInfo"),
                                               xmlWriter);
                                        } if (localWatermarkInfoTracker){
                                            if (localWatermarkInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("watermarkInfo cannot be null!!");
                                            }
                                           localWatermarkInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","watermarkInfo"),
                                               xmlWriter);
                                        } if (localRenderSceneInfoTracker){
                                            if (localRenderSceneInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("renderSceneInfo cannot be null!!");
                                            }
                                           localRenderSceneInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","renderSceneInfo"),
                                               xmlWriter);
                                        } if (localVignetteInfoTracker){
                                            if (localVignetteInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("vignetteInfo cannot be null!!");
                                            }
                                           localVignetteInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","vignetteInfo"),
                                               xmlWriter);
                                        } if (localCabinetInfoTracker){
                                            if (localCabinetInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("cabinetInfo cannot be null!!");
                                            }
                                           localCabinetInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","cabinetInfo"),
                                               xmlWriter);
                                        } if (localWindowCoveringInfoTracker){
                                            if (localWindowCoveringInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("windowCoveringInfo cannot be null!!");
                                            }
                                           localWindowCoveringInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","windowCoveringInfo"),
                                               xmlWriter);
                                        } if (localIccProfileInfoTracker){
                                            if (localIccProfileInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("iccProfileInfo cannot be null!!");
                                            }
                                           localIccProfileInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","iccProfileInfo"),
                                               xmlWriter);
                                        } if (localFontInfoTracker){
                                            if (localFontInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("fontInfo cannot be null!!");
                                            }
                                           localFontInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","fontInfo"),
                                               xmlWriter);
                                        } if (localXslInfoTracker){
                                            if (localXslInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("xslInfo cannot be null!!");
                                            }
                                           localXslInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","xslInfo"),
                                               xmlWriter);
                                        } if (localViewerSwfInfoTracker){
                                            if (localViewerSwfInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("viewerSwfInfo cannot be null!!");
                                            }
                                           localViewerSwfInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","viewerSwfInfo"),
                                               xmlWriter);
                                        } if (localXmlInfoTracker){
                                            if (localXmlInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("xmlInfo cannot be null!!");
                                            }
                                           localXmlInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","xmlInfo"),
                                               xmlWriter);
                                        } if (localSvgInfoTracker){
                                            if (localSvgInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("svgInfo cannot be null!!");
                                            }
                                           localSvgInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","svgInfo"),
                                               xmlWriter);
                                        } if (localZipInfoTracker){
                                            if (localZipInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("zipInfo cannot be null!!");
                                            }
                                           localZipInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","zipInfo"),
                                               xmlWriter);
                                        } if (localVideoInfoTracker){
                                            if (localVideoInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("videoInfo cannot be null!!");
                                            }
                                           localVideoInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","videoInfo"),
                                               xmlWriter);
                                        } if (localAcoInfoTracker){
                                            if (localAcoInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("acoInfo cannot be null!!");
                                            }
                                           localAcoInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","acoInfo"),
                                               xmlWriter);
                                        } if (localPdfInfoTracker){
                                            if (localPdfInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("pdfInfo cannot be null!!");
                                            }
                                           localPdfInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","pdfInfo"),
                                               xmlWriter);
                                        } if (localPsdInfoTracker){
                                            if (localPsdInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("psdInfo cannot be null!!");
                                            }
                                           localPsdInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","psdInfo"),
                                               xmlWriter);
                                        } if (localFlashInfoTracker){
                                            if (localFlashInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("flashInfo cannot be null!!");
                                            }
                                           localFlashInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","flashInfo"),
                                               xmlWriter);
                                        } if (localInDesignInfoTracker){
                                            if (localInDesignInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("inDesignInfo cannot be null!!");
                                            }
                                           localInDesignInfo.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","inDesignInfo"),
                                               xmlWriter);
                                        } if (localPermissionsTracker){
                                            if (localPermissions==null){
                                                 throw new org.apache.axis2.databinding.ADBException("permissions cannot be null!!");
                                            }
                                           localPermissions.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","permissions"),
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

                 if (localAssetHandleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "assetHandle"));
                                 
                                        if (localAssetHandle != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAssetHandle));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("assetHandle cannot be null!!");
                                        }
                                    } if (localTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "type"));
                                 
                                        if (localType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("type cannot be null!!");
                                        }
                                    } if (localNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "name"));
                                 
                                        if (localName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("name cannot be null!!");
                                        }
                                    } if (localFolderTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "folder"));
                                 
                                        if (localFolder != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFolder));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("folder cannot be null!!");
                                        }
                                    } if (localFolderHandleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "folderHandle"));
                                 
                                        if (localFolderHandle != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFolderHandle));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("folderHandle cannot be null!!");
                                        }
                                    } if (localReadyForPublishTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "readyForPublish"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReadyForPublish));
                            } if (localProjectsTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "projects"));
                                 
                                        if (localProjects != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProjects));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("projects cannot be null!!");
                                        }
                                    } if (localIpsImageUrlTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "ipsImageUrl"));
                                 
                                        if (localIpsImageUrl != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIpsImageUrl));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("ipsImageUrl cannot be null!!");
                                        }
                                    } if (localCreatedTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "created"));
                                 
                                        if (localCreated != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreated));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("created cannot be null!!");
                                        }
                                    } if (localCreateUserTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "createUser"));
                                 
                                        if (localCreateUser != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreateUser));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("createUser cannot be null!!");
                                        }
                                    } if (localLastModifiedTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "lastModified"));
                                 
                                        if (localLastModified != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastModified));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("lastModified cannot be null!!");
                                        }
                                    } if (localLastModifyUserTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "lastModifyUser"));
                                 
                                        if (localLastModifyUser != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastModifyUser));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("lastModifyUser cannot be null!!");
                                        }
                                    } if (localMetadataArrayTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "metadataArray"));
                            
                            
                                    if (localMetadataArray==null){
                                         throw new org.apache.axis2.databinding.ADBException("metadataArray cannot be null!!");
                                    }
                                    elementList.add(localMetadataArray);
                                } if (localImageInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "imageInfo"));
                            
                            
                                    if (localImageInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("imageInfo cannot be null!!");
                                    }
                                    elementList.add(localImageInfo);
                                } if (localTemplateInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "templateInfo"));
                            
                            
                                    if (localTemplateInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("templateInfo cannot be null!!");
                                    }
                                    elementList.add(localTemplateInfo);
                                } if (localWatermarkInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "watermarkInfo"));
                            
                            
                                    if (localWatermarkInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("watermarkInfo cannot be null!!");
                                    }
                                    elementList.add(localWatermarkInfo);
                                } if (localRenderSceneInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "renderSceneInfo"));
                            
                            
                                    if (localRenderSceneInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("renderSceneInfo cannot be null!!");
                                    }
                                    elementList.add(localRenderSceneInfo);
                                } if (localVignetteInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "vignetteInfo"));
                            
                            
                                    if (localVignetteInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("vignetteInfo cannot be null!!");
                                    }
                                    elementList.add(localVignetteInfo);
                                } if (localCabinetInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "cabinetInfo"));
                            
                            
                                    if (localCabinetInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("cabinetInfo cannot be null!!");
                                    }
                                    elementList.add(localCabinetInfo);
                                } if (localWindowCoveringInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "windowCoveringInfo"));
                            
                            
                                    if (localWindowCoveringInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("windowCoveringInfo cannot be null!!");
                                    }
                                    elementList.add(localWindowCoveringInfo);
                                } if (localIccProfileInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "iccProfileInfo"));
                            
                            
                                    if (localIccProfileInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("iccProfileInfo cannot be null!!");
                                    }
                                    elementList.add(localIccProfileInfo);
                                } if (localFontInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "fontInfo"));
                            
                            
                                    if (localFontInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("fontInfo cannot be null!!");
                                    }
                                    elementList.add(localFontInfo);
                                } if (localXslInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "xslInfo"));
                            
                            
                                    if (localXslInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("xslInfo cannot be null!!");
                                    }
                                    elementList.add(localXslInfo);
                                } if (localViewerSwfInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "viewerSwfInfo"));
                            
                            
                                    if (localViewerSwfInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("viewerSwfInfo cannot be null!!");
                                    }
                                    elementList.add(localViewerSwfInfo);
                                } if (localXmlInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "xmlInfo"));
                            
                            
                                    if (localXmlInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("xmlInfo cannot be null!!");
                                    }
                                    elementList.add(localXmlInfo);
                                } if (localSvgInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "svgInfo"));
                            
                            
                                    if (localSvgInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("svgInfo cannot be null!!");
                                    }
                                    elementList.add(localSvgInfo);
                                } if (localZipInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "zipInfo"));
                            
                            
                                    if (localZipInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("zipInfo cannot be null!!");
                                    }
                                    elementList.add(localZipInfo);
                                } if (localVideoInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "videoInfo"));
                            
                            
                                    if (localVideoInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("videoInfo cannot be null!!");
                                    }
                                    elementList.add(localVideoInfo);
                                } if (localAcoInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "acoInfo"));
                            
                            
                                    if (localAcoInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("acoInfo cannot be null!!");
                                    }
                                    elementList.add(localAcoInfo);
                                } if (localPdfInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "pdfInfo"));
                            
                            
                                    if (localPdfInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("pdfInfo cannot be null!!");
                                    }
                                    elementList.add(localPdfInfo);
                                } if (localPsdInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "psdInfo"));
                            
                            
                                    if (localPsdInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("psdInfo cannot be null!!");
                                    }
                                    elementList.add(localPsdInfo);
                                } if (localFlashInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "flashInfo"));
                            
                            
                                    if (localFlashInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("flashInfo cannot be null!!");
                                    }
                                    elementList.add(localFlashInfo);
                                } if (localInDesignInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "inDesignInfo"));
                            
                            
                                    if (localInDesignInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("inDesignInfo cannot be null!!");
                                    }
                                    elementList.add(localInDesignInfo);
                                } if (localPermissionsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "permissions"));
                            
                            
                                    if (localPermissions==null){
                                         throw new org.apache.axis2.databinding.ADBException("permissions cannot be null!!");
                                    }
                                    elementList.add(localPermissions);
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
        public static Asset parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            Asset object =
                new Asset();

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
                    
                            if (!"Asset".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (Asset)com.scene7.www.ipsapi.xsd.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","assetHandle").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"assetHandle" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAssetHandle(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
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
                                
                                    else {
                                        
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
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","folder").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"folder" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFolder(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","folderHandle").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"folderHandle" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFolderHandle(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
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
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","projects").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"projects" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProjects(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","ipsImageUrl").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ipsImageUrl" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIpsImageUrl(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","created").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"created" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCreated(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","createUser").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"createUser" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCreateUser(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","lastModified").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"lastModified" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLastModified(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","lastModifyUser").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"lastModifyUser" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLastModifyUser(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","metadataArray").equals(reader.getName())){
                                
                                                object.setMetadataArray(com.scene7.www.ipsapi.xsd.MetadataArray.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","imageInfo").equals(reader.getName())){
                                
                                                object.setImageInfo(com.scene7.www.ipsapi.xsd.ImageInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","templateInfo").equals(reader.getName())){
                                
                                                object.setTemplateInfo(com.scene7.www.ipsapi.xsd.TemplateInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","watermarkInfo").equals(reader.getName())){
                                
                                                object.setWatermarkInfo(com.scene7.www.ipsapi.xsd.WatermarkInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","renderSceneInfo").equals(reader.getName())){
                                
                                                object.setRenderSceneInfo(com.scene7.www.ipsapi.xsd.RenderSceneInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","vignetteInfo").equals(reader.getName())){
                                
                                                object.setVignetteInfo(com.scene7.www.ipsapi.xsd.VignetteInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","cabinetInfo").equals(reader.getName())){
                                
                                                object.setCabinetInfo(com.scene7.www.ipsapi.xsd.CabinetInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","windowCoveringInfo").equals(reader.getName())){
                                
                                                object.setWindowCoveringInfo(com.scene7.www.ipsapi.xsd.WindowCoveringInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","iccProfileInfo").equals(reader.getName())){
                                
                                                object.setIccProfileInfo(com.scene7.www.ipsapi.xsd.IccProfileInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","fontInfo").equals(reader.getName())){
                                
                                                object.setFontInfo(com.scene7.www.ipsapi.xsd.FontInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","xslInfo").equals(reader.getName())){
                                
                                                object.setXslInfo(com.scene7.www.ipsapi.xsd.XslInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","viewerSwfInfo").equals(reader.getName())){
                                
                                                object.setViewerSwfInfo(com.scene7.www.ipsapi.xsd.ViewerSwfInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","xmlInfo").equals(reader.getName())){
                                
                                                object.setXmlInfo(com.scene7.www.ipsapi.xsd.XmlInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","svgInfo").equals(reader.getName())){
                                
                                                object.setSvgInfo(com.scene7.www.ipsapi.xsd.SvgInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","zipInfo").equals(reader.getName())){
                                
                                                object.setZipInfo(com.scene7.www.ipsapi.xsd.ZipInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","videoInfo").equals(reader.getName())){
                                
                                                object.setVideoInfo(com.scene7.www.ipsapi.xsd.VideoInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","acoInfo").equals(reader.getName())){
                                
                                                object.setAcoInfo(com.scene7.www.ipsapi.xsd.AcoInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","pdfInfo").equals(reader.getName())){
                                
                                                object.setPdfInfo(com.scene7.www.ipsapi.xsd.PdfInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","psdInfo").equals(reader.getName())){
                                
                                                object.setPsdInfo(com.scene7.www.ipsapi.xsd.PsdInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","flashInfo").equals(reader.getName())){
                                
                                                object.setFlashInfo(com.scene7.www.ipsapi.xsd.FlashInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","inDesignInfo").equals(reader.getName())){
                                
                                                object.setInDesignInfo(com.scene7.www.ipsapi.xsd.InDesignInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","permissions").equals(reader.getName())){
                                
                                                object.setPermissions(com.scene7.www.ipsapi.xsd.PermissionArray.Factory.parse(reader));
                                              
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
           
    