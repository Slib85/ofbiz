<script>
    function isEmpty(obj) {
        for(var key in obj) {
            if(obj.hasOwnProperty(key))
                return false;
        }
        return true;
    }

    function getReport() {
        $('#channelReport').spinner(true, null, null, null, null, null, null, 10000000);
        $.ajax({
            type: 'GET',
            url: '/admin/control/getChannelSalesReport',
            dataType: 'json',
            data: {},
            cache: false
        }).done(function (response) {
            if (response.success) {
                $('#channelReport').spinner(false);
                var dateAlert = new Date().setMinutes(new Date().getMinutes() - 30);
                var dateWarning = new Date().setHours(new Date().getHours() - 1);

                var vendors = {};
                var dates = [];
                var data = {};
                <#list days?reverse as day>
                    data['${day?replace("&#x2f;","/")}'] = {};
                    dates.push('${day?replace("&#x2f;","/")}');
                </#list>

                for (var i = 0; i < response.data.length; i++) {
                    if(vendors.hasOwnProperty(response.data[i].name)) {
                        var existingDate = new Date(vendors[response.data[i].name]);
                        var newDate = new Date(response.data[i].datecreated);
                        if(newDate > existingDate) {
                            vendors[response.data[i].name] = response.data[i].datecreated;
                        }
                    } else {
                        vendors[response.data[i].name] = response.data[i].datecreated;
                    }

                    if (data.hasOwnProperty(response.data[i].date)) {
                        data[response.data[i].date][response.data[i].name] = (data[response.data[i].date].hasOwnProperty(response.data[i].name)) ? data[response.data[i].date][response.data[i].name] + 1 : 1;
                    }
                }

                if (!isEmpty(data)) {
                    $('.tablebody').remove();
                    var channelReportBody = $('<tbody>').addClass('tablebody');
                    $.each(vendors, function (vK, vV) {
                        var trTag = $('<tr>');
                        trTag.append($('<td>').html(vK));
                        $.each(dates, function (dI, dV) {
                            trTag.append(
                                $('<td>').html((data[dV].hasOwnProperty(vK)) ? data[dV][vK] : 0)
                            );
                        });

                        var icon = $('<i>').addClass('icon');
                        if(dateWarning > new Date(vV)) {
                            icon.addClass('wb-warning').css({'color':'#ff4343'});
                        } else if(dateAlert > new Date(vV)) {
                            icon.addClass('wb-alert-circle').css({'color':'#e8e629'});
                        } else {
                            icon.addClass('wb-check-circle').css({'color':'#00a727'});
                        }
                        trTag.append($('<td>').html('&nbsp;' + vV).prepend(icon));
                        channelReportBody.append(trTag);
                    });
                    $('#channelReport').append(channelReportBody);
                }
            }
        });
    }

    $(function() {
        $('.refreshChannelData').on('click', function() {
            getReport();
        });

        getReport();
    })
</script>

<div class="panel">
    <div class="panel-heading">
        <h3 class="panel-title">Channel Orders by Date</h3>
        <div class="panel-actions panel-actions-keep">
            <a class="panel-action icon wb-refresh refreshChannelData" data-toggle="panel-refresh" data-load-type="round-circle" data-load-callback="customRefreshCallback" aria-hidden="true"></a>
        </div>
    </div>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="channelReport">
                <thead>
                    <tr>
                        <th></th>
                        <#list days?reverse as day><th>${day}</th></#list>
                        <th>Last Order Date</th>
                    </tr>
                </thead>
                <tbody class="tablebody">
                    <tr>
                        <td></td>
                        <#list days?reverse as day><td></td></#list>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>