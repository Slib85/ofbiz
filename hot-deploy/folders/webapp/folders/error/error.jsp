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
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="imagetoolbar" content="no" />
	<title>Folders.com - 404 Error</title>
	<!--<script>window.location = '/';</script>-->
	<style media="all" type="text/css">
		* { margin: 0; padding: 0; }
		img { display: block; border: 0; }
		a { outline: none; }
		body { width: 100%; font: normal 62.5% Verdana, Arial, Helvetica, sans-serif; }
		#maintenance { width: 520px; margin: 0 auto; padding: 100px 0; text-align: center; }
		#maintenance img { margin: 0 auto; }
		#maintenance h1 { padding: 60px 0 30px 0; font-size: 2.3em; line-height: 35px; color: #888; text-transform: uppercase; }
		#maintenance h2 { padding: 0 0 30px 0; font-size: 2.0em; color: #004276; }
		#maintenance h3 { padding: 0 0 20px 0; font-size: 1.5em; line-height: 22px; color: #888; font-weight: normal; }

		#wrap {
			margin: 0 auto;
			display: inline-block;
			position: relative;
			height: 60px;
			float: right;
			padding: 0;
			position: relative;
		}

		#error {
			clear: both;
			margin-top: 100px;
		}

		input[type="text"] {
			height: 60px;
			font-size: 40px;
			display: inline-block;
			font-weight: 100;
			border: none;
			outline: none;
			color: #555;
			padding: 3px;
			padding-right: 60px;
			position: absolute;
			top: 0;
			right: 0;
			background: none;
			border-bottom: 1px solid #BBB;
			width: 450px;
			z-index: 1;
			cursor: text;
		}

		input[type="submit"] {
			height: 67px;
			width: 63px;
			display: inline-block;
			color:red;
			float: right;
			background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAMAAABg3Am1AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAADNQTFRFU1NT9fX1lJSUXl5e1dXVfn5+c3Nz6urqv7+/tLS0iYmJqampn5+fysrK39/faWlp////Vi4ZywAAABF0Uk5T/////////////////////wAlrZliAAABLklEQVR42rSWWRbDIAhFHeOUtN3/ags1zaA4cHrKZ8JFRHwoXkwTvwGP1Qo0bYObAPwiLmbNAHBWFBZlD9j0JxflDViIObNHG/Do8PRHTJk0TezAhv7qloK0JJEBh+F8+U/hopIELOWfiZUCDOZD1RADOQKA75oq4cvVkcT+OdHnqqpQCITWAjnWVgGQUWz12lJuGwGoaWgBKzRVBcCypgUkOAoWgBX/L0CmxN40u6xwcIJ1cOzWYDffp3axsQOyvdkXiH9FKRFwPRHYZUaXMgPLeiW7QhbDRciyLXJaKheCuLbiVoqx1DVRyH26yb0hsuoOFEPsoz+BVE0MRlZNjGZcRQyHYkmMp2hBTIzdkzCTc/pLqOnBrk7/yZdAOq/q5NPBH1f7x7fGP4C3AAMAQrhzX9zhcGsAAAAASUVORK5CYII=) center center no-repeat;
			text-indent: -10000px;
			border: none;
			position: absolute;
			top: 0;
			right: 0;
			z-index: 2;
			cursor: pointer;
			opacity: 0.4;
			cursor: pointer;
			transition: opacity .4s ease;
		}

		input[type="submit"]:hover {
			opacity: 0.8;
		}
	</style>
</head>
<body>
<% String errorMsg = (String) request.getAttribute("_ERROR_MESSAGE_"); %>
<div id="maintenance">
	<a href="https://www.folders.com" title="Go back to Folders.com"><img src="/html/img/logo/foldersNavy.png" alt="Folders.com logo" /></a>
	<h1>Sorry, we could not find the page you  were looking for.</h1>
	<div id="wrap">
		<form action="/folders/control/search" autocomplete="on">
			<input name="w" type="text" placeholder="Start your search again"><input value="Search" type="submit">
		</form>
	</div>
	<div id="error">
		<span><%=UtilFormatOut.replaceString(errorMsg, "\n", "<br/>")%></span>
	</div>
</div>
</body>
</html>