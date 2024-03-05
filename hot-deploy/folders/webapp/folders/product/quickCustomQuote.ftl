<link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/product/quickCustomQuote.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" type="text/css" />

<div class="foldersContainer foldersContainerLimiter quickCustomQuote">
	<div class="quickCustomQuoteHeader textCenter padding20">
		<h1>Get a Quick<br />Custom Quote</h1>
		<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/quickCustomQuoteHeader?fmt=png-alpha&amp;wid=333</@ofbizScene7Url>" alt="Custom Quote Header">
	</div>
    <form class="marginTop20" name="quoteRequestForm" action="#" method="POST">
        <div id="customQuote" class="customBackground customQuote marginTop20" data-bigNameValidateForm="quoteRequestSpecialtyProduct">
            <div class="marginTop10">
                <p class="textBold" data-bigNameValidateId="quoteDescribeProject">Describe Your Custom Project:</p>
                <textarea name="quoteDescribeProject" data-bigNameValidate="quoteDescribeProject" data-bigNameValidateType="required"></textarea>
            </div>
            <div class="marginTop10">
                <p class="textBold" data-bigNameValidateId="quoteQuantitySelection">Quantity</p>
                <label class="bigNameSelect" data-bigNameValidateParent="quoteQuantitySelection">
                    <select name="quoteQuantitySelection" data-bigNameValidate="quoteQuantitySelection" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Quantity">
                        <option value="">Select Quantity</option>
                        <option value="50">50</option>
                        <option value="100">100</option>
                        <option value="100">250</option>
                        <option value="100">500</option>
                        <option value="100">750</option>
                        <option value="100">1000</option>
                        <option value="custom">Custom</option>
                    </select>
                </label>
            </div>
            <div class="foldersRow marginTop10">
                <div class="foldersColumn small6 medium6 large6 noPaddingLeft">
                    <p class="textBold" data-bigNameValidateId="quoteFirstName">First Name:</p>
                    <input name="quoteFirstName" data-bigNameValidate="quoteFirstName" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="First Name">                            
                </div>
                <div class="foldersColumn small6 medium6 large6 noPaddingRight">
                    <p class="textBold" data-bigNameValidateId="quoteFirstName">Last Name:</p>
                    <input name="quoteLastName" data-bigNameValidate="quoteLastName" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Last Name">   
                </div>
            </div>
            <div class="marginTop10">
                <p class="textBold" data-bigNameValidateId="quoteEmailAddress">Email:</p>
                <input type="email" name="quoteEmailAddress" data-bigNameValidate="quoteEmailAddress" data-bigNameValidateType="required email" data-bigNameValidateErrorTitle="Email">
            </div>
            <div class="marginTop10">
                <p class="textBold" data-bigNameValidateId="quotePhoneNumber">Phone:</p>
                <input name="quotePhoneNumber" data-bigNameValidate="quotePhoneNumber" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Phone">
            </div>
            <div class="foldersButton buttonGreen marginTop20" data-bigNameValidateSubmit="quoteRequestSpecialtyProduct" data-bigNameValidateAction="quoteRequestSubmit">Submit</div>
        </div>
        <div bns-quotecompleted class="productSidebarSection hidden marginTop20 textCenter padding20 backgroundWhite">
            <h3 class="sidebarHeader">
                Thank You
            </h3>
            <div class="jqs-quoteThankYou quoteThankYou textCenter">
                <h5 class="ftc-blue padding10">Your quote request has been submitted! You will receive an email confirmation shortly.</h5>
                <h3 class="paddingRight10 paddingLeft10 paddingBottom10">Your Quote ID is:<br><span id="quoteId"></span></h3>
                <a href="tel:+18883273606" class="ftc-orange">Any Questions? <strong>Call Us 888-327-3606</strong></a>
            </div>
        </div>
    </form>
</div>

<script src="<@ofbizContentUrl>/html/js/folders/product/quickCustomQuote.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>