<option value="USA">United States</option>
<option value="CAN">Canada</option>

<#assign SHOW_INT_SHIP_METHODS = Static["com.envelopes.util.EnvConstantsUtil"].SHOW_INT_SHIP_METHODS />
<#if webSiteId == "folders">
    <#assign SHOW_INT_SHIP_METHODS = false />
</#if>

<#if SHOW_INT_SHIP_METHODS?c == "true">
    <option value="AUS">Australia</option>
    <option value="BEL">Belgium</option>
    <option value="DNK">Denmark</option>
    <option value="DEU">Germany</option>
    <option value="FRA">France</option>
    <option value="IND">India</option>
    <option value="IRL">Ireland</option>
    <option value="ITA">Italy</option>
    <option value="FIN">Finland</option>
    <option value="MEX">Mexico</option>
    <option value="NLD">Netherlands</option>
    <option value="NZL">New Zealand</option>
    <option value="POL">Poland</option>
    <option value="PRT">Portugal</option>
    <option value="ESP">Spain</option>
    <option value="SWE">Sweden</option>
    <option value="CHE">Switzerland</option>
    <option value="GBR">United Kingdom</option>
</#if>