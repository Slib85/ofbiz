<div class="container checkout-footer padding-sm">
	<div class="row">
		<div class="chat">
			Call <a href="tel:1-877-683-5673">1-877-683-5673</a> or <a href="http://support.envelopes.com/customer/widget/chats/new" target="_blank" onclick="javascript:window.open('//support.envelopes.com/customer/widget/chats/new','_blank','width=475,height=450,resizable=yes');return false;">Click to Chat</a>
		</div>
		<div class="info-links">
			<#--<div>
				<a href="<@ofbizUrl>/terms</@ofbizUrl>">Terms &amp; Conditions</a>
			</div>-->
			<div>
				<a href="<@ofbizUrl>/privacy</@ofbizUrl>">Privacy Policy</a>
			</div>
			<div>
				<span>Folders.com is 100% Safe &amp; Secure</span>
			</div>
		</div>
	</div>
	<div class="margin-top-xs about-envelopes">
		<span>
			Envelopes.com is the leading supplier of plain and printed envelopes in all sizes, styles and colors, to businesses, organizations, and individuals.
		</span>
		<span>
			&copy; ${nowTimestamp?string("yyyy")}. All rights reserved.
		</span>
	</div>
    <div id="secure-layer" class="reveal-modal small no-padding reveal-modal-limiter" data-reveal>
        <div id="jqs-login-layer" class="login-layer">
            <iframe id="secure-iframe" class="env-login" src="/${globalContext.webSiteId?default("envelopes")}/control/secureWrapper?dest=forgot-password"></iframe>
            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
        </div>
    </div>
</div>
<script>
    function closeSecureLayer() {
        $('#secure-layer').foundation('reveal', 'close');
    }
</script>
<style>
	/* BEGIN CHECKOUT FOOTER */
	.checkout-footer {
		background-color: #8d8b8b;
		color: #ffffff;
		font-size: 14px;
	}

	.checkout-footer a {
		color: inherit;
	}
		.checkout-footer .chat {
			float: left;
		}

		.checkout-footer .info-links {
			float: right;
			display: table;
		}
			.checkout-footer .info-links > div {
				padding-left: 20px;
				display: table-cell;
			}

			.checkout-footer .info-links span {
				font-weight: bold;
			}

		.checkout-footer .about-envelopes {
			width: 100%;
			max-width: 450px;
		}
			.checkout-footer .about-envelopes span {
				display: block;
			}

	@media only screen and (max-width: 1023px) {
		.checkout-footer {
			text-align: center;
		}

		.checkout-footer .chat {
			float: none;
		}

		.checkout-footer .info-links {
			float: none;
			display: block;
		}
			.checkout-footer .info-links > div {
				margin-top: 10px;
				padding-left: 0px;
				display: block;
			}

			.checkout-footer .about-envelopes {
				margin: auto;
			}

	}
	/* END CHECKOUT FOOTER */
</style>
