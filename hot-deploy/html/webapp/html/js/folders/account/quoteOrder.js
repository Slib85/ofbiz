$('.jqs-addItem').on('click', function() {
    var dataSrc = $(this).data();
    var comments = $(this).closest('.jqs-wrapper').find('.jqs-comments').val();
    var attr = {
        'add_product_id': 'CUSTOM-P',
        'quoteId': dataSrc['quoteid'],
        'quoteItemSeqId': dataSrc['quoteitemseqid'],
        'quantity': dataSrc['quantity'],
        'colorsFront': dataSrc['colorsfront'],
        'colorsBack': dataSrc['colorsback']
    };

    attr.itemComment = comments;
    attr.artworkSource = 'ART_UPLOADED_LATER';
    attr.isProduct = true;
    attr.fileName = [];
    attr.filePath = [];
    attr.fileOrder = [];
    attr.fileOrderItem = [];

    $(this).closest('.jqs-wrapper').find('.jqs-designFiles .design-file').each(function() {
        attr.artworkSource = 'ART_UPLOADED';
        attr.fileName.push($(this).attr('data-filename'));
        attr.filePath.push($(this).attr('data-filepath'));
        attr.fileOrder.push('');
        attr.fileOrderItem.push('');
    });

    $.ajax({
        type: 'POST',
        url: '/folders/control/addToCart',
        timeout: 5000,
        async: false,
        data: attr,
        cache: false
    }).done(function(data) {
        //relocate to cart once all products have been added
        var crossSell = $('<form/>').attr('id', 'crossSell').attr('action', '/folders/control/cart').attr('method', 'POST').append('<input type="hidden" name="lastVisitedURL" value="' + document.referrer + '" />').append('<input type="hidden" name="fromAddToCart" value="true" />');
        $('body').append(crossSell);
        $('#crossSell').submit();
    }).fail(function(jqXHR) {
        //
    });
});

$(function() {
    var fileList;
    $('.upload-file-button').on('click', function(e) {
        $('.jqs-upload').each(function(){
            myForm = $(this).find('[id^=files]');
            $(this).find('input[type="file"]').trigger('click');
        });
    });

    // Initialize the jQuery File Upload plugin
    $('.jqs-upload').fileupload({
        url: '/' + websiteId + '/control/uploadScene7Files',
        dataType: 'json',
        dropZone: $(this).find('[id^=drop]'),
        sequentialUploads: true,
        add: function(e, data) {
            fileList = $(this).find('[id^=files]');
            fileList.find('div.placeHolder').remove();
            // Add the HTML to the File list element
            var fileDiv = $('<div/>').addClass('relative design-file inprogress').append(
                    $('<div/>').addClass('absolute progress')
            ).append(
                    $('<span/>').addClass('relative').append(
                            $('<i/>').addClass('fa fa-file-photo-o')
                    )
            ).append(
                    $('<span/>').addClass('relative').css({ 'margin': ' 0 5px' }).html(data.files[0].name)
            ).attr('data-filename', data.files[0].name);

            data.context = fileDiv.appendTo(fileList);
            var jqXHR = data.submit();
        },
        progress: function(e, data) {
            // Calculate the completion percentage of the upload
            var progress = parseInt(data.loaded / data.total * 100, 10);
            if(progress == 100) {
                fileList.find('[data-filename="' + data.files[0].name + '"]').first().removeClass('inprogress').children('div.progress').fadeOut();
            } else {
                fileList.find('[data-filename="' + data.files[0].name + '"]').first().children('div.progress').width(progress + '%');
            }
        },
        done: function(e, data) {
            if(data.result.success) {
                //add the path to the dom data for that line
                $.each(data.result.files, function(idx) {
                    fileList.find('[data-filename="' + data.result.files[idx].name + '"]').attr('data-filepath', data.result.files[idx].path);
                });

                //loop through the dom and create data for the slide
                var completedFileArray = [];
                fileList.find('.design-file').each(function(idx, el) {
                    completedFileArray.push( { 'name' : $(this).attr('data-filename'), 'path' : $(this).attr('data-filepath') } );
                });
            }
        },
        fail: function (e, data) {
            $.each(data.files, function(idx, el) {
                fileList.find('[data-filename="' + data.files[idx].name + '"]').first().children('div.progress').first().find('i.fa').removeClass('fa-trash-o').addClass('fa-warning');
            });
        }
    });
});