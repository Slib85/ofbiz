function createFileList(fileList) {
    $('.jqs-uploadedfiles').removeClass('hidden').empty();
    var completedFileArray = [];
    var myList = $('<ul />').addClass('text-left no-margin margin-left-xs');

    $.each(fileList, function() {
        var fileIndex = completedFileArray.push( { 'name' : $(this).attr('data-filename'), 'path' : $(this).attr('data-filepath') } );

        myList.append(
            $('<li />').append(
                'Added ' + $(this).attr('data-filename')
            ).append(
                $('<i/>').addClass('fa fa-trash-o').on('click', function() {
                    self.returnActiveProduct(true).files.splice($(this).parent().index(), 1);
                    $(this).parent().remove();
                    if(self.returnActiveProduct(true).files.length == 0) {
                        $('.' + self.uploadedFiles).removeClass('hidden').addClass('hidden');
                    }
                })
            )
        );
    });

    // myList.append(
    //     $('<div />').css({
    //         'margin-top': '10px',
    //         'color': '#fc7e22'
    //     }).html('After checkout, you\'ll receive an emailed proof within 24 hours.  Once you approve your proof, your order will enter print production.')
    // );

    $('.jqs-uploadedfiles').append(myList);

    // self.returnActiveProduct(true).files = completedFileArray;
}

$('.dropzone').on('click', function() {
    $('.jqs-fileupload').trigger('click');
});
if($.fn.fileupload) {
    $('#uploadScene7Files').fileupload({
        url: '/' + websiteId + '/control/uploadScene7Files',
        dataType: 'json',
        dropZone: $('.' + self.fileGrid).find('.dropzone'),
        sequentialUploads: true,
        add: function (e, data) {
            if(data.originalFiles.length > 1) {
                alert("Only one file can be uploaded");
                return false;
            } else {
                $('[data-filename]').remove();
                //remove the placeholder div
                $('.dropzone').removeClass('placeholder');

                //create the file progress
                var fileDiv = $('<div/>').addClass('text-size-md relative design-file text-left inprogress').append(
                    $('<div/>').addClass('absolute progress')
                ).append(
                    $('<span/>').addClass('paddingLeft10 relative').append(
                        $('<i/>').addClass('fa fa-file-photo-o')
                    )
                ).append(
                    $('<span/>').addClass('relative filename').html(data.files[0].name)
                ).append(
                    $('<span/>').addClass('absolute remove action').append(
                        $('<i/>').addClass('fa fa-trash-o')
                    )
                ).attr('data-filename', data.files[0].name);

                //create the delete action
                fileDiv.find('span.action .fa-trash-o').on('click', function (e) {
                    e.stopPropagation();
                    $(this).parents('[data-filename]').remove();
                    productPage.getActiveProductAttributes().fileUpload.pop();
                });

                data.context = fileDiv.appendTo($('.jqs-filecontainer').find('.dropzone'));
                var jqXHR = data.submit();
            }
        },
        progress: function (e, data) {
            // Calculate the completion percentage of the upload
            var progress = parseInt(data.loaded / data.total * 100, 10);
            if (progress == 100) {
                $('.jqs-filecontainer').find('.dropzone').find('[data-filename="' + data.files[0].name + '"]').first().removeClass('inprogress').children('div.progress').fadeOut();
            } else {
                $('.jqs-filecontainer').find('.dropzone').find('[data-filename="' + data.files[0].name + '"]').first().children('div.progress').width(progress + '%');
            }
        },
        done: function (e, data) {
            $('[bns-hiddenuploadbutton]').removeClass('hidden');
            if (data.result.success) {
                var fileList = [];

                $.each(data.result.files, function (idx) {
                    var fileInfo = {};
                    fileInfo['name'] = data.result.files[idx].name;
                    fileInfo['path'] = data.result.files[idx].path;
                    fileList.push(fileInfo);
                    return false;
                });
                createFileList($('.dropzone > div'));
            }
        },
        fail: function (e, data) {
            $.each(data.files, function (idx, el) {
                $('.' + self.fileGrid).find('.dropzone').find('[data-filename="' + data.files[idx].name + '"]').first().children('div.progress').first().find('i.fa').removeClass('fa-trash-o').addClass('fa-warning');
            });
        }
    });
}

$('[data-reveal-id="startUpload"]').on('click', function(){
    $(window).scrollTop(0);
});

$('.jqs-closemodal').on('click', function() {
    $(this).closest('.reveal-modal').foundation('reveal', 'close');
});

if (typeof BigNameValidate != 'undefined') {
    window['commerceConciergeForm'] = new BigNameValidate($('[data-bigNameValidateForm="commerceConciergeForm"]'), 'commerceConciergeForm');
}

function submitConciergeForm() {
    // var quantities = [];

    // for (var i = 1; i <= 4; i++) {
    //     // var quantity = productPage.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData['quoteQuantity' + i];
    //     var quantity = $('select[name="quoteQuantity' + i + 'Selection"]').val();

    //     if (typeof quantity != 'undefined') {
    //         quantities.push(quantity);
    //     }
    // }

    var formData = {
        name: $('input[name="conciergeName"]').val(),
        company: $('input[name="conciergeCompany"]').val(),
        avgSpent: $('input[name="conciergeExpense"]').val(),
        email: $('input[name="conciergeEmail"]').val(),
        webSiteId: 'envelopes'
    };

    $.ajax({
        type: "POST",
        url: "/envelopes/control/commerceConciergeSubmission",
        data: formData,
        dataType:'json',
        cache: false
    }).done(function(data) {
        if (data.success) {
            GoogleAnalytics.trackEvent('Concierge Page', 'Finish', 'Success');

            // Figure out what they want to happen on successful submission
            $('[data-bigNameValidateForm="commerceConciergeForm"]').removeClass('hidden').addClass('hidden');
            $('[bns-applicationcompleted]').removeClass('hidden');
        }
        else {
            GoogleAnalytics.trackEvent('Commerce Concierge', 'Finish', 'Error');
        }
    }).fail(function(XMLHttpRequest, error, errorThrown){
        //console.log(errorThrown)
    });

    //clicks on this will trigger tracking of quote start
    $('[name="quoteRequestForm"] select,[name="quoteRequestForm"] textarea').on('change', function(e) {
        GoogleAnalytics.trackEvent('Commerce Concierge', 'CUSTOM', 'Start');
    });
}