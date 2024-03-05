<#assign pageInclude = "component://folders/widget/AccountScreens.xml#" + requestParameters.dest?default('login-layer')/>
${screens.render(pageInclude)}