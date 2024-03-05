if (typeof searchResults == 'undefined') {
    $(document).on('ready', function () {
        searchResults.initSearch();
    });
}
else {
    searchResults.initSearch();
}
