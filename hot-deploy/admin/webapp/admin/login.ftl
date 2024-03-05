<link rel="stylesheet" href="/html/themes/base/assets/examples/css/pages/login-v3.css">
<style>
    .page-login-v3 form a {
        margin-left: 0px;
        color: #fff !important;
    }
</style>

<div class="panel">
    <div class="panel-body">
        <div class="brand">
            <img class="brand-img" src="/html/img/logo/bigNameBlack.png">
        </div>
        <form method="post" name="loginform" action="<@ofbizUrl>/login</@ofbizUrl>" role="form" id="form_login">
            <input type="hidden" id="token" name="token" value="" />
            <input type="hidden" name="JavaScriptEnabled" value="Y">
            <#if globalContext.webSiteId?exists && globalContext.webSiteId == "vendor">
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-addon">
                        <i class="entypo-user"></i>
                    </div>
                    <input type="text" class="form-control" name="USERNAME" id="username" placeholder="Username" autocomplete="off" />
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-addon">
                        <i class="entypo-key"></i>
                    </div>
                    <input type="password" class="form-control" name="PASSWORD" id="password" placeholder="Password" autocomplete="off" />
                </div>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block btn-login">
                    <i class="entypo-login"></i>
                    Login
                </button>
            </div>
            <#else>
            <a class="btn btn-primary btn-block btn-lg m-t-40 google-button janrainEngage">Login to BOS</a>
            </#if>
        </form>
    </div>
</div>

