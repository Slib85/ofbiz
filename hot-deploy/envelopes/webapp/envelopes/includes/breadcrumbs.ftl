<#if breadcrumbs?has_content>
<#assign breadcrumb_count = 0 />
<div class="content-breadcrumbs" itemscope itemtype="http://data-vocabulary.org/Breadcrumb">
	<a href="<@ofbizUrl>/main</@ofbizUrl>" itemprop="url">
		<span itemprop="title">Home</span>
	</a>
    <#list breadcrumbs as breadcrumb>
        <#if breadcrumb.name?has_content>
            <#assign breadcrumb_count = breadcrumb_count + 1 />
            >
            <div itemprop="child" itemscope itemtype="http://data-vocabulary.org/Breadcrumb">
            <#if breadcrumb.link?has_content>
        		<!-- BREADCRUMB-LINK: ${breadcrumb.link} is put into <@ofbizUrl>${breadcrumb.link}</@ofbizUrl> -->
				<a href="<@ofbizUrl>${breadcrumb.link?replace("&#x2f;", "/")?replace("&#x7e;", "~")?replace("&#x3d;", "=")}</@ofbizUrl>" itemprop="url">
			<#else>
				<a href="#" onclick="return false;" class="fake_link" itemprop="url">
			</#if>
				<span itemprop="title">${breadcrumb.name}</span>
			</a>
        </#if>
    </#list>
	<#list 0..breadcrumb_count as i>
	</div>
	</#list>
</#if>