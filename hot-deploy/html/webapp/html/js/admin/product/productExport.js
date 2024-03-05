$('[bns-generate_and_download]').on('click', function() {
    let generateAndDownloadElement = $(this);
    generateAndDownloadElement.spinner(true, false, 50, null, null, '', null, 600000);

    $.ajax({
        type: "GET",
        url: '/' + websiteId + '/control/generateProductExport',
        cache: false,
        dataType: 'json',
        data: {
            args: ($("[name=\"allow_discontinued\"]").is(":checked") ? "ALLOW_DISCONTINUED" : "")
        }
    }).done(function(data) {
        generateAndDownloadElement.spinner(false);
        
        if (data.success) {
            window.location = window.location.origin + "/" + websiteId + "/control/serveFileForStream?filePath=exports/product_export2.tsv&fileName=productExport.tsv&downLoad=Y"
        } else {
            alert("There was an issue trying to generate and download a new file.");
        }
    });
});