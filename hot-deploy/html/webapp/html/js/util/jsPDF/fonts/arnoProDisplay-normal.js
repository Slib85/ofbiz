(function (jsPDFAPI) {
var callAddFont = function () {
this.addFileToVFS('arnoProDisplay-normal.ttf', font);
this.addFont('arnoProDisplay-normal.ttf', 'arnoProDisplay', 'normal');
};
jsPDFAPI.events.push(['addFonts', callAddFont])
 })(jsPDF.API);