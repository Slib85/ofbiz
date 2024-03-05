<#assign presses = Static["com.envelopes.printing.PressManHelper"].getPresses(delegator) />
<#assign productsAttributes = Static["com.envelopes.printing.PressManHelper"].getProductPrintAttributes(delegator) />

<div class="row">

    <div class="col-md-7">
        <div class="panel panel-dark">

            <!-- panel head -->
            <div class="panel-heading">
                <div class="panel-title">Open Print Jobs</div>

                <div class="panel-options">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a data-toggle="tab" href="#tab1"><i class="entypo-target"></i></a>
                        </li>

                        <li class="">
                            <a data-toggle="tab" href="#tab2"><i class="entypo-mail"></i></a>
                        </li>
                    </ul>
                </div>
            </div>

            <!-- panel body -->
            <div class="panel-body-with-table">
                <div style="height:340px; overflow-y: auto">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Job ID</th>
                        <th>Rush Item</th>
                        <th>Rush Job</th>
                        <th>Item Due Date</th>
                        <th>Job Due Date</th>
                        <th>Qty</th>
                        <th>Press</th>
                        <th># Colors</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>ENV34981032-00001-FRONT</td>
                        <td>Y</td>
                        <td>Y</td>
                        <td>09.14</td>
                        <td>09.14</td>
                        <td>2000</td>
                        <td>SMJ2C</td>
                        <td>2</td>
                        <td>Good to Go</td>
                        <td>
                            <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                        </td>
                    </tr>

                    <tr>
                        <td>2</td>
                        <td>ENV34981032-00001-FRONT</td>
                        <td>Y</td>
                        <td>Y</td>
                        <td>09.14</td>
                        <td>09.14</td>
                        <td>500</td>
                        <td>SMJ2C</td>
                        <td>2</td>
                        <td>Assigned to Plate</td>
                        <td>
                            <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                        </td>
                    </tr>


                    <tr>
                        <td>3</td>
                        <td>ENV34981032-00001-FRONT</td>
                        <td>Y</td>
                        <td>Y</td>
                        <td>09.14</td>
                        <td>09.14</td>
                        <td>90</td>
                        <td>SMJ2C</td>
                        <td>2</td>
                        <td>Send for Plating</td>
                        <td>
                            <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                        </td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>ENV34981032-00001-FRONT</td>
                        <td>Y</td>
                        <td>Y</td>
                        <td>09.14</td>
                        <td>09.14</td>
                        <td>90</td>
                        <td>SMJ2C</td>
                        <td>2</td>
                        <td>Plating in Progress</td>
                        <td>
                            <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                        </td>
                    </tr>
                    <tr>
                        <td>5</td>
                        <td>ENV34981032-00001-FRONT</td>
                        <td>Y</td>
                        <td>Y</td>
                        <td>09.14</td>
                        <td>09.14</td>
                        <td>90</td>
                        <td>SMJ2C</td>
                        <td>2</td>
                        <td>Ready for Printing</td>
                        <td>
                            <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                        </td>
                    </tr>
                    <tr>
                        <td>6</td>
                        <td>ENV34981032-00001-FRONT</td>
                        <td>Y</td>
                        <td>Y</td>
                        <td>09.14</td>
                        <td>09.14</td>
                        <td>90</td>
                        <td>SMJ2C</td>
                        <td>2</td>
                        <td>Printing in Progress</td>
                        <td>
                            <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                        </td>
                    </tr>
                    <tr>
                        <td>7</td>
                        <td>ENV34981032-00001-FRONT</td>
                        <td>Y</td>
                        <td>Y</td>
                        <td>09.14</td>
                        <td>09.14</td>
                        <td>90</td>
                        <td>SMJ2C</td>
                        <td>2</td>
                        <td>Finished Printing</td>
                        <td>
                            <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                        </td>
                    </tr>
                    <tr>
                        <td>8</td>
                        <td>ENV34981032-00001-FRONT</td>
                        <td>Y</td>
                        <td>Y</td>
                        <td>09.14</td>
                        <td>09.14</td>
                        <td>90</td>
                        <td>SMJ2C</td>
                        <td>2</td>
                        <td>Good to Go</td>
                        <td>
                            <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                        </td>
                    </tr>
                    </tbody>
                </table>
                </div>
            </div>

        </div>
    </div>

    <div class="col-md-5">
        <div class="panel panel-dark">

            <!-- panel head -->
            <div class="panel-heading">
                <div class="panel-title">Plates</div>

                <div class="panel-options">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a data-toggle="tab" href="#tab1"><i class="entypo-target"></i></a>
                        </li>

                        <li class="">
                            <a data-toggle="tab" href="#tab2"><i class="entypo-mail"></i></a>
                        </li>
                    </ul>
                </div>
            </div>

            <!-- panel body -->
            <div class="panel-body-with-table">
                <div style="height:340px; overflow-y: auto">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Plate #</th>
                            <th>Status</th>
                            <th>Jobs on Plate</th>
                            <th>Impressions</th>
                            <th>Press Code</th>
                            <th>Actions</th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <td>1</td>
                            <td>2C-09142016-001</td>
                            <td>Assigned to Plate</td>
                            <td>4</td>
                            <td>2000</td>
                            <td>SMJ2C</td>
                            <td>
                                <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                            </td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>2C-09142016-001</td>
                            <td>Assigned to Plate</td>
                            <td>4</td>
                            <td>2000</td>
                            <td>SMJ2C</td>
                            <td>
                                <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                            </td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>2C-09142016-001</td>
                            <td>Assigned to Plate</td>
                            <td>4</td>
                            <td>2000</td>
                            <td>SMJ2C</td>
                            <td>
                                <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                            </td>
                        </tr>
                        <tr>
                            <td>4</td>
                            <td>2C-09142016-001</td>
                            <td>Assigned to Plate</td>
                            <td>4</td>
                            <td>2000</td>
                            <td>SMJ2C</td>
                            <td>
                                <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                            </td>
                        </tr>
                        <tr>
                            <td>5</td>
                            <td>2C-09142016-001</td>
                            <td>Assigned to Plate</td>
                            <td>4</td>
                            <td>2000</td>
                            <td>SMJ2C</td>
                            <td>
                                <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                            </td>
                        </tr>
                        <tr>
                            <td>6</td>
                            <td>2C-09142016-001</td>
                            <td>Assigned to Plate</td>
                            <td>4</td>
                            <td>2000</td>
                            <td>SMJ2C</td>
                            <td>
                                <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                            </td>
                        </tr>
                        <tr>
                            <td>7</td>
                            <td>2C-09142016-001</td>
                            <td>Assigned to Plate</td>
                            <td>4</td>
                            <td>2000</td>
                            <td>SMJ2C</td>
                            <td>
                                <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                            </td>
                        </tr>
                        <tr>
                            <td>8</td>
                            <td>2C-09142016-001</td>
                            <td>Assigned to Plate</td>
                            <td>4</td>
                            <td>2000</td>
                            <td>SMJ2C</td>
                            <td>
                                <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">

    <div class="col-md-7">
        <div class="panel panel-dark">

            <!-- panel head -->
            <div class="panel-heading">
                <div class="panel-title">Product Print Attributes</div>

                <div class="panel-options">
                    <a class="bg" data-target="#sample-modal-dialog-2" data-toggle="modal" href="#sample-modal"><i class="entypo-cog"></i></a>
                    <a class="bg" data-rel="reload" href="#"><i class="entypo-arrows-ccw"></i></a>
                </div>
            </div>

            <!-- panel body -->
            <div class="panel-body-with-table">
                <div style="height:340px; overflow-y: auto">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Height</th>
                        <th>Width</th>
                        <th>Offset Press</th>
                        <th>Digital Press</th>
                        <th>Non Perfecting</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>

                    <tbody>
                    <#list productsAttributes as productAttributes>
                        <tr>
                            <td>${productAttributes.parentProductId}</td>
                            <td>${productAttributes.productName!'-'}</td>
                            <td>${productAttributes.height!'-'}</td>
                            <td>${productAttributes.width!'-'}</td>
                            <td>${productAttributes.offsetPressId!'-'}</td>
                            <td>${productAttributes.digitalPressId!'-'}</td>
                            <td>${productAttributes.nonPerfectingFlag!'-'}</td>
                            <td>${productAttributes.isActive!'-'}</td>
                            <td>
                                <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                            </td>
                        </tr>
                    </#list>
                </tbody>

                </table>
            </div>
            </div>

        </div>
    </div>

    <div class="col-md-5">
        <div class="panel panel-dark">

            <!-- panel head -->
            <div class="panel-heading">
                <div class="panel-title">Presses</div>

                <div class="panel-options">
                    <a class="bg" data-target="#sample-modal-dialog-2" data-toggle="modal" href="#sample-modal"><i class="entypo-cog"></i></a>
                    <a class="bg" data-rel="reload" href="#"><i class="entypo-arrows-ccw"></i></a>
                </div>
            </div>

            <!-- panel body -->
            <div class="panel-body-with-table">
                <div style="height:340px; overflow-y: auto">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Press Code</th>
                        <th>Press Name</th>
                        <th>Status</th>
                        <th>Started Time</th>
                        <th>Time to Finish</th>
                        <th>Pressman</th>
                        <th>Job Id</th>
                        <th>Active</th>
                        <th>Actions</th>
                    </tr>
                    </thead>

                    <tbody>
                    <#list presses as press>
                    <tr>
                        <td>${press.pressCode}</td>
                        <td>${press.pressName!''}</td>
                        <td>Idle</td>
                        <td>-<#--09-14:15:25--></td>
                        <td>-<#--02:30--></td>
                        <td>-<#--Shaun--></td>
                        <td>-<#--ENV34981032-00001-FRONT--></td>
                        <td>${press.isActive!'-'}</td>
                        <td>
                            <a class="btn btn-default btn-sm btn-icon icon-left" href="#"><i class="entypo-pencil"></i>Edit</a>
                        </td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
                </div>
            </div>
        </div>
    </div>
</div>
