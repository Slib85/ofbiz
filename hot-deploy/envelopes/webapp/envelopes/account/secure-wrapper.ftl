<#assign pageInclude = "component://envelopes/widget/AccountScreens.xml#" + requestParameters.dest?default('login-layer')/>
${screens.render(pageInclude)}
