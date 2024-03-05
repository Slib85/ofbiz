<!-- ClickTale Bottom part (XHTML) -->
<script type='text/javascript'>
// The ClickTale Balkan Tracking Code may be programmatically customized using hooks:
//
//   function ClickTalePreRecordingHook() { /* place your customized code here */  }
//
// For details about ClickTale hooks, please consult the wiki page http://wiki.clicktale.com/Article/Customizing_code_version_2

// Add this to "OnDomLoad" event
(function(){
if (typeof (ClickTaleCreateDOMElement) != "function")
{
	ClickTaleCreateDOMElement = function(tagName)
	{
		if (document.createElementNS)
		{
			return document.createElementNS('http://www.w3.org/1999/xhtml', tagName);
		}
		return document.createElement(tagName);
	}
}

if (typeof (ClickTaleAppendInHead) != "function")
{
	ClickTaleAppendInHead = function(element)
	{
		var parent = document.getElementsByTagName('head').item(0) || document.documentElement;
		parent.appendChild(element);
	}
}

if (typeof (ClickTaleXHTMLCompliantScriptTagCreate) != "function")
{
	ClickTaleXHTMLCompliantScriptTagCreate = function(code)
	{
		var script = ClickTaleCreateDOMElement('script');
		script.setAttribute("type", "text/javascript");
		script.text = code;
		return script;
	}
}
var scriptElement = ClickTaleCreateDOMElement('script');
scriptElement.type = "text/javascript";
scriptElement.src = (document.location.protocol=='https:'?
'https://cdnssl.clicktale.net/www07/ptc/94401314-9715-4e6c-ac50-33cd42c134e5.js':
'http://cdn.clicktale.net/www07/ptc/94401314-9715-4e6c-ac50-33cd42c134e5.js');
document.body.appendChild(scriptElement);
})();
</script>
<!-- ClickTale end of Bottom part (XHTML) -->