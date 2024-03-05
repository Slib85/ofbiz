$.widget("envelopes.VariableDataGrid", $.envelopes.DataGridProxy, {
    options: {
        dataGroupAttributeSet:'address'
    },

    /**
     * Overriding the _getColumnTitles() method in DataGrid widget, so that the attributes corresponding to the current dataGroupAttributeSet
     * can be used as the columnTitles for the grid.
     *
     * @returns {*}
     * @private
     */
    //@Override
    _getColumnTitles: function() {
        return this._getDataGroupAttributes();
    },


    _getDataGroup: function() {
        return this._super();
    }/*,

    _call$UploadGridData: function(arg1, arg2) {
        $('#env-variabledata-grid').VariableDataGrid('uploadGridData', arg1, arg2);
    }*/
});