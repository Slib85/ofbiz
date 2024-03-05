<%--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
--%>

<%@ page import="org.apache.ofbiz.base.util.*" %>
<html>
	<head>
		<meta name="format-detection" content="telephone=no">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<title>Envelopes.com</title>
		<link href="<@ofbizContentUrl>/html/css/top.css?ts=2</@ofbizContentUrl>" rel="stylesheet" />
		<link href="<@ofbizContentUrl>/html/css/global.css</@ofbizContentUrl>?ts=2" rel="stylesheet" />
		<link href="/html/css/addons/font-awesome-4.3.0/css/font-awesome.min.css" rel="stylesheet">
		<link href="<@ofbizContentUrl>/html/img/icon/favicon-envelopes.png</@ofbizContentUrl>" rel="shortcut icon" type="image/x-icon"  />
		<script type="text/javascript">
			var websiteId = 'envelopes';
		</script>
		<script src="<@ofbizContentUrl>/html/js/addons/top.js?ts=2</@ofbizContentUrl>"></script>
		<!--[if lt IE 9]>
		<link rel="stylesheet" type="text/css" media="all" href="<@ofbizContentUrl>/html/css/ie.css</@ofbizContentUrl>" />
		<![endif]-->
		<script type="text/javascript" src="<@ofbizContentUrl>/html/js/global.js?ts=1</@ofbizContentUrl>"></script>
		<script type="text/javascript" src="<@ofbizContentUrl>/html/js/util/spinner.js</@ofbizContentUrl>"></script>
		<script type="text/javascript" src="<@ofbizContentUrl>/html/js/account/login.js</@ofbizContentUrl>"></script>
		<script type="text/javascript" src="<@ofbizContentUrl>/html/js/util/dropdown.js</@ofbizContentUrl>"></script>

		<script type="text/javascript">
			$(function() {
				if (navigator.appVersion.indexOf('MSIE 8.') == -1) {
					$(document).foundation({
						abide: {
							live_validate : false,
							focus_on_invalid : false
						}
					});
				}
			});
		</script>
		<!-- Google RichText Snippet for Organic Search -->
		<script type="application/ld+json">
			{
				"@context": "http://schema.org",
				"@type": "WebSite",
				"url": "http://www.envelopes.com/",
				"potentialAction": {
					"@type": "SearchAction",
					"target": "http://envelopes.envelopes.com/search?w={w}",
					"query-input": "required name=w"
				}
			}
		</script>
	</head>
	<body>
		<% String errorMsg = (String) request.getAttribute("_ERROR_MESSAGE_"); %>
		<div class="container content text-center padding-top-sm">
			<a href="http://www.envelopes.com"><img src="/html/img/logo/logo.png" /></a>
			<div class="content-body padding-top-sm">
				<div class="section no-padding no-margin">
					<h4 style="background-color: #CC6666; color: #ffffff; font-weight: bold;">:ERROR MESSAGE:</h4>
					<div class="padding-xs" style="word-wrap: break-word;"><%=UtilFormatOut.replaceString(errorMsg, "\n", "<br/>")%></div>
				</div>
			</div>
		</div>
	</body>
</html>
