<style>
    .table {
        font-size: 12px;
    }
    .table td {
        white-space: nowrap;
    }
    .table a {
        text-decoration: none;
    }
</style>

<#if externalLogins?has_content>
<#list externalLogins as customer>
    <#if customer.photo?has_content>
        <#assign photo = customer.photo />
    </#if>
</#list>
</#if>

<#if customer?has_content>
<div class="row">
    <div class="col-xl-12 col-lg-12 col-xs-12">
        <div class="card card-inverse card-shadow bg-cyan-500 white">
            <div class="card-block p-20 h-full">
                <a class="avatar avatar-lg img-bordered bg-white pull-xs-left m-r-20" href="javascript:void(0)">
                    <img src="${photo?default("/html/themes/global/photos/placeholder.png")}" alt="">
                </a>
                <div>
                    <div class="pull-xs-left">
                        <div class="font-size-18">${(person.firstName)?if_exists} ${(person.lastName)?if_exists} <i class="wb-pencil" data-toggle="modal" data-target="#updatePerson" aria-hidden="true"></i></div>
                        <div class="white-200 font-size-14 m-b-10">${customer.userLoginId}</div>
                    </div>
                    <#if lastBillingAddress?has_content>
                        <div class="pull-xs-left" style="position: relative; top: 3px; left: 25px;">
                            <#if companyName?has_content>${lastBillingAddress.companyName?if_exists}<br /></#if>
                        ${lastBillingAddress.address1?if_exists}<br />
                            <#if companyName?has_content>${lastBillingAddress.address2?if_exists}<br /></#if>
                        ${lastBillingAddress.city?if_exists}, ${lastBillingAddress.stateProvinceGeoId?if_exists} ${lastBillingAddress.postalCode?if_exists}
                        </div>
                    </#if>
                    <div class="pull-xs-right white-200">
                        Party ID: ${customer.partyId} (Netsuite ID: ${party.externalId?default("Not Available")})<br />
                        <span data-role="wholesaler" class="partyRole tag tag-<#if isWholesaler?c == "true">success<#else>default</#if>">Wholesaler</span>
                        <span data-role="wholesaler_algra" class="partyRole tag tag-<#if isTradeAllegra?c == "true">success<#else>default</#if>">Wholesaler Allegra</span>
                        <span data-role="wholesaler_pn" class="partyRole tag tag-<#if isTradePostNet?c == "true">success<#else>default</#if>">Wholesaler PostNet</span>
                        <span data-role="non_profit" class="partyRole tag tag-<#if isNonProfit?c == "true">success<#else>default</#if>">Non Profit</span>
                        <span data-role="non_taxable" class="partyRole tag tag-<#if isNonTaxable?c == "true">success<#else>default</#if>">Non Taxable</span>
                        <span data-role="net_30" class="partyRole tag tag-<#if isNet30?c == "true">success<#else>default</#if>">Net 30</span>
                    </div>
                </div>
            </div>
            <div class="card-footer bg-white p-x-30 p-y-20 h-100">
                <div class="row no-space p-y-20 p-x-30 text-xs-center">
                    <div class="col-xs-4">
                        <div class="counter">
                            <div class="counter-label cyan-600">Orders</div>
                            <span class="counter-number">${orders?size}</span>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="counter">
                            <div class="counter-label cyan-600">Member Since</div>
                            <span class="counter-number">${customer.createdStamp?string("MM/yyyy")}</span>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="counter">
                            <div class="counter-label cyan-600">Sales Rep</div>
                            <div class="form-group">
                                <select name="assignedTo" class="jqs-assignedTo form-control form-control-sm">
                                    <option value="none">None</option>
                                    <#list foldersAssignedToUsers.keySet() as key>
                                        <option value="${key}" <#if party.salesRep?exists && party.salesRep == key>selected="selected"</#if>>${key}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="panel panel-success panel-line">
    <div class="panel-heading">
        <div class="panel-title"></div>
    </div>
    <div class="panel-body">
        <div class="row row-lg">
            <div class="col-xl-6 col-xs-12">
                <h4 class="example-title">User Login IDs <span class="tag tag-warning addUser" data-toggle="modal" data-target="#addUser">ADD USER</span></h4>
                <table class="table table-hover table-striped">
                    <thead>
                    <tr>
                        <th>LOGIN ID</th>
                        <th>ENABLED</th>
                        <th>DISABLED DATE</th>
                        <th>CART ID</th>
                        <th></th>
                    </tr>
                    </thead>
                    <#list userLogins as customer>
                        <tr>
                            <td class="userLoginId" data-userloginid="${customer.userLoginId}">${customer.userLoginId}</td>
                            <td>${customer.enabled?default("Y")}</td>
                            <td><#if customer.disabledDateTime?has_content>${customer.disabledDateTime?string.medium}</#if></td>
                            <td>${customer.cartId?if_exists}</td>
                            <td><span class="tag tag-warning editUser" data-toggle="modal" data-target="#editUser">EDIT</span></td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <div class="modal fade" id="editUser" role="dialog">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h4 class="modal-title" id="myModalLabel">Edit User</h4>
                            </div>
                            <div class="modal-body row">
                                <div class="col-xs-12">
                                    <div class="form-group has-feedback">
                                        <input type="hidden" id="userLoginId" value="" />
                                        <div class="col-xs-4">
                                            <label class="control-label" for="newPassword">New Password:</label>
                                            <input type="text" class="form-control form-control-sm" id="newPassword" />
                                        </div>
                                        <div class="col-xs-4">
                                            <label class="control-label" for="verifyPassword">Verify Password:</label>
                                            <input type="text" class="form-control form-control-sm" id="verifyPassword" />
                                        </div>
                                        <div class="col-xs-4">
                                            <label class="control-label" for="disableAccount">Enabled:</label>
                                            <select class="form-control form-control-sm" id="disableAccount">
                                                <option value="Y">Y</option>
                                                <option value="N">N</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                <button type="button" class="btn btn-primary j-btn-add">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-6 col-xs-12">
                <h4 class="example-title">Social Logins</h4>
                <table class="table table-hover table-striped">
                    <thead>
                    <tr>
                        <th>LOGIN ID</th>
                        <th>PROVIDER</th>
                        <th>ENABLED</th>
                        <th>GENDER</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list externalLogins as customer>
                        <tr>
                            <td>${customer.id}</td>
                            <td>${customer.provider?if_exists}</td>
                            <td>${customer.enabled?if_exists}</td>
                            <td>${customer.gender?if_exists}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="panel panel-success panel-line">
    <div class="panel-heading">
        <div class="panel-title">Orders</div>
    </div>
    <div class="panel-body">
        <table class="table table-hover table-striped" id="orderList">
            <thead>
            <tr>
                <th>ORDER ID</th>
                <th>EXTERNAL ID</th>
                <th>CREATED BY</th>
                <th>TOTAL</th>
                <th>DATE</th>
            </tr>
            </thead>
            <tfoot>
            <tr class="replace-inputs">
                <th>ORDER ID</th>
                <th>EXTERNAL ID</th>
                <th>CREATED BY</th>
                <th>TOTAL</th>
                <th>DATE</th>
            </tr>
            </tfoot>
            <tbody>
            <#list orders as order>
                <tr>
                    <td><a href="viewOrder?orderId=${order.orderId}">${order.orderId}</a></td>
                    <td>${order.externalId?if_exists}</td>
                    <td>${order.createdBy?if_exists}</td>
                    <td>${order.grandTotal}</td>
                    <td>${order.orderDate?string.medium}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
<div class="panel panel-success panel-line">
    <div class="panel-heading">
        <div class="panel-title">Quotes</div>
    </div>
    <div class="panel-body">
            <table class="table table-hover table-striped" id="quoteList">
            <thead>
            <tr>
                <th>QUOTE ID</th>
                <th>SALES REP</th>
                <th>WEB SITE ID</th>
                <th>CREATED BY</th>
                <th>DATE</th>
            </tr>
            </thead>
            <tfoot>
            <tr class="replace-inputs">
                <th>QUOTE ID</th>
                <th>SALES REP</th>
                <th>WEB SITE ID</th>
                <th>CREATED BY</th>
                <th>DATE</th>
            </tr>
            </tfoot>
            <tbody>
                <#list quotes as quote>
                <tr>
                    <td><a href="<@ofbizUrl><#if quote.webSiteId?has_content && quote.webSiteId == "folders">/foldersQuoteView<#else>/envelopesQuoteView</#if></@ofbizUrl>?quoteId=${quote.quoteId}">${quote.quoteId}</a></td>
                    <td>${quote.assignedTo?if_exists}</td>
                    <td>${quote.webSiteId?if_exists}</td>
                    <td>${quote.userEmail?if_exists}</td>
                    <td>${quote.createdStamp?string.medium}</td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>
<div class="panel panel-success panel-line">
    <div class="panel-heading">
        <div class="panel-title">Loyalty Points</div>
        <div class="panel-actions panel-actions-keep">
            <span class="tag tag-primary tag-lg" data-toggle="modal" data-target="#addPoints">Add Points</span>
        </div>
    </div>
    <div class="panel-body">
        <table class="table table-hover table-striped" id="pointsList">
            <thead>
            <tr>
                <th>POINTS</th>
                <th>ADDED BY</th>
                <th>DATE</th>
                <th>VALID</th>
            </tr>
            </thead>
            <#list loyaltyPoints as points>
                <tr>
                    <td>${points.points}</td>
                    <td>${points.createdByUserLogin?if_exists}</td>
                    <td>${points.createdStamp?string.medium}</td>
                    <td>Y</td>
                </tr>
            </#list>
            <#list allPoints.keySet() as points>
                <tr>
                    <td>${allPoints.get(points).totalSubRemainingAmount}</td>
                    <td>${points}</td>
                    <td></td>
                    <td>Y</td>
                </tr>
            </#list>
            </tbody>
        </table>
        <div class="modal fade" id="addPoints" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">Add Loyalty Points</h4>
                    </div>
                    <div class="modal-body row">
                        <div class="col-xs-12">
                            <div class="form-group has-feedback">
                                <input type="hidden" id="partyId" value="${customer.partyId}" />
                                <label class="control-label" for="pointsToAdd">Points:</label>
                                <input type="text" class="form-control form-control-sm" id="pointsToAdd" />
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-primary j-btn-add">Save</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="addUser" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">Add New User</h4>
                    </div>
                    <div class="modal-body row">
                        <div class="col-xs-12">
                            <div class="form-group has-feedback">
                                <label class="control-label" for="email">Email:</label>
                                <input type="text" class="form-control form-control-sm" id="email" />
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-primary j-btn-add">Save</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="updatePerson" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">Update Person</h4>
                    </div>
                    <div class="modal-body row">
                        <div class="col-xs-12">
                            <div class="form-group has-feedback">
                                <label class="control-label" for="firstName">First Name:</label>
                                <input type="text" class="form-control form-control-sm" id="firstName" />
                            </div>
                            <div class="form-group has-feedback">
                                <label class="control-label" for="lastName">Last Name:</label>
                                <input type="text" class="form-control form-control-sm" id="lastName" />
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-primary j-btn-add">Save</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#elseif customers?has_content>
<div class="panel panel-success panel-line">
    <div class="panel-heading">
        <div class="panel-title">New Customers</div>
        <div class="panel-actions">
            <form class="" name="customers" action="<@ofbizUrl>/customers</@ofbizUrl>" method="GET">
                <div class="input-group-sm pull-lg-right">
                    <button type="submit" class="input-search-btn">
                        <i class="icon wb-search" aria-hidden="true"></i>
                    </button>
                    <input type="text" class="form-control" name="id" placeholder="Search for Customers" value="">
                </div>
                <style>
                    .searchAddresses {
                        margin: 0px 20px 0px 0px;
                        line-height: 20px;
                        position: relative;
                        top: 5px;
                    }
                </style>
                <div class="checkbox-custom checkbox-default pull-lg-left searchAddresses">
                    <input type="checkbox" id="searchAddress" name="searchAddress" value="true">
                    <label for="searchAddress">Search Addresses</label>
                </div>
            </form>
        </div>
    </div>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="customerList">
                <thead>
                <tr>
                    <th>LOGIN ID</th>
                    <th>ENABLED</th>
                    <th>CART ID</th>
                    <th>PARTY ID</th>
                    <th>COMPANY NAME</th>
                    <th>NAME</th>
                    <th>SALES REP</th>
                    <th>TRADE</th>
                    <th>NON-PROFIT</th>
                    <th>NON-TAXABLE</th>
                    <th>NET-30</th>
                </tr>
                </thead>
            <#list customers as customer>
                <#if isPostalAddress?c == "false">
                <tr>
                    <td><a href="<@ofbizUrl>/customers</@ofbizUrl>?id=ID:${customer.partyId}">${customer.userLoginId}</a></td>
                    <td>${customer.enabled?default("Y")}</td>
                    <td>${customer.cartId?if_exists}</td>
                    <td>${customer.partyId}</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <#else>
                <tr>
                    <td><a href="<@ofbizUrl>/customers</@ofbizUrl>?id=ID:${customer.partyId}">${customer.partyId}</a></td>
                    <td></td>
                    <td></td>
                    <td>${customer.partyId}</td>
                    <td>${customer.companyName?if_exists}</td>
                    <td>${customer.toName?if_exists}</td>
                    <td>${customer.salesRep?if_exists}</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                </#if>
            </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</#if>

<script>
    var toastrOpts = {
        "closeButton": true,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "toast-top-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };

    jQuery(document).ready(function($) {
        var offsetTop = 0;

        if ($('.site-navbar').length > 0) {
            offsetTop = $('.site-navbar').eq(0).innerHeight();
        }

        // initialize datatable
        $('#customerList').DataTable({
            iDisplayLength: 50,
            responsive: true,
            fixedHeader: {
                header: true,
                headerOffset: offsetTop
            },
            searching: false,
            bPaginate: true,
            aaSorting: [],
            initComplete: function() {
                $('#customerList .replace-inputs > th').each(function(index) {
                    if (!$(this).hasClass('jqs-inputIgnore')) {
                        var title = $(this).text();
                        $(this).html('<input class="form-control w-full" type="text" placeholder="' + title + '" />');
                    }
                });

                $("#customerList .replace-inputs input").on('keyup change', function () {
                    table
                            .column($(this).parent().index() + ':visible' )
                            .search(this.value)
                            .draw();
                });
            }
        });

        // initialize datatable
        $('#orderList').DataTable({
            iDisplayLength: 10,
            responsive: true,
            fixedHeader: {
                header: true,
                headerOffset: offsetTop
            },
            bPaginate: true,
            aaSorting: []
        });

        $('#quoteList').DataTable({
            iDisplayLength: 10,
            responsive: true,
            fixedHeader: {
                header: true,
                headerOffset: offsetTop
            },
            bPaginate: true,
            aaSorting: [],
            searching: false
        });

        $('#pointsList').DataTable({
            iDisplayLength: 10,
            responsive: true,
            fixedHeader: {
                header: true,
                headerOffset: offsetTop
            },
            bPaginate: true,
            aaSorting: [],
            searching: false
        });

        $('.partyRole').on('click', function(e) {
            e.preventDefault();
            var tType = $(this).attr('data-role');
            var remove = $(this).hasClass('tag-success');

            $.ajax({
                type: 'POST',
                url: '/admin/control/addOrRemovePartyRole',
                dataType: 'json',
                data: { 'partyId' : $('#addPoints #partyId').val(), 'roleTypeId' : tType.toUpperCase(), 'remove' : (remove ? 'Y' : 'N') }
            }).done(function( response ) {
                if (!response.success){
                    toastr.error('There was an error processing your request. Please try again', 'Error', toastrOpts);
                } else {
                    window.location.reload();
                }
            }).error(function( response ) {
                toastr.error('There was an error processing your request. Please try again', 'Error', toastrOpts);
            });
        });

        $('#addUser .j-btn-add').on('click', function(e) {
            e.preventDefault();
            $.ajax({
                type: 'POST',
                url: '/admin/control/addUser',
                dataType: 'json',
                data: { 'email' : $('#addUser #email').val(), 'partyId' : $('#addPoints #partyId').val() }
            }).done(function( response ) {
                if (!response.success){
                    if(response.errorMessage != '') {
                        alert(errorMessage);
                    } else {
                        toastr.error('There was an error processing your request. Please try again', 'Error', toastrOpts);
                    }
                } else {
                    window.location.reload();
                }
            }).error(function( response ) {
                toastr.error('There was an error processing your request. Please try again', 'Error', toastrOpts);
            });
        });

        $('.editUser').on('click', function(e) {
            var userLogin = $(this).parent().siblings('.userLoginId').attr('data-userloginid');
            $('#userLoginId').val(userLogin);
        });

        $('#editUser .j-btn-add').on('click', function(e) {
            e.preventDefault();
            $.ajax({
                type: 'POST',
                url: '/admin/control/updateUser',
                dataType: 'json',
                data: { 'email' : $('#editUser #userLoginId').val(), 'newPassword' : $('#editUser #newPassword').val(), 'verifyPassword' : $('#editUser #verifyPassword').val(), 'active' : $('#editUser #disableAccount').val() }
            }).done(function( response ) {
                if (!response.success){
                    toastr.error('There was an error processing your request. Please try again', 'Error', toastrOpts);
                } else {
                    window.location.reload();
                }
            }).error(function( response ) {
                toastr.error('There was an error processing your request. Please try again', 'Error', toastrOpts);
            });
        });

        $('#addPoints .j-btn-add').on('click', function(e) {
            e.preventDefault();
            if($('#addPoints #pointsToAdd').val() == '') {
                alert('Please enter the amount of points to add.')
            } else {
                $.ajax({
                    type: 'POST',
                    url: '/admin/control/addLoyaltyPoints',
                    dataType: 'json',
                    data: { 'partyId': $('#addPoints #partyId').val(), 'points': $('#addPoints #pointsToAdd').val() }
                }).done(function( response ) {
                    if (!response.success){
                        toastr.error('There was an error processing your request. Please try again', 'Error', toastrOpts);
                    } else {
                        window.location.reload();
                    }
                }).error(function( response ) {
                    toastr.error('There was an error processing your request. Please try again', 'Error', toastrOpts);
                });
            }
        });

        $('#updatePerson .j-btn-add').on('click', function(e) {
            e.preventDefault();
            if($('#updatePerson #firstName').val() == '' || $('#updatePerson #lastName').val() == '') {
                alert('Please enter the amount of points to add.')
            } else {
                $.ajax({
                    type: 'POST',
                    url: '/admin/control/updatePerson',
                    dataType: 'json',
                    data: { 'firstName': $('#updatePerson #firstName').val(), 'lastName': $('#updatePerson #lastName').val(), 'partyId': $('#addPoints #partyId').val() }
                }).done(function( response ) {
                    if (!response.success){
                        toastr.error('There was an error processing your request. Please try again', 'Error', toastrOpts);
                    } else {
                        window.location.reload();
                    }
                }).error(function( response ) {
                    toastr.error('There was an error processing your request. Please try again', 'Error', toastrOpts);
                });
            }
        });

        var previousAssignedTo = $('.jqs-assignedTo').val();
        $('.jqs-assignedTo').on('change', function() {
            var selfElement = $(this);

            $.ajax({
                type: "POST",
                url: "/admin/control/updateSalesRep",
                data: {
                    partyId: $('#addPoints #partyId').val(),
                    salesRep: selfElement.val()
                },
                dataType:'json',
                cache: false
            }).done(function(data) {
                if (data.success) {
                    location.reload();
                } else {
                    selfElement.val(previousAssignedTo);
                    toastr.error('There was an issue while updating the sales rep. Please try again.', 'Error', toastrOpts);
                }
            });
        });
    });
</script>