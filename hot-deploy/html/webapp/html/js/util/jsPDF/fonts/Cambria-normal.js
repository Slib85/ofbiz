(function (jsPDFAPI) {
var callAddFont = function () {
this.addFileToVFS('Cambria-normal.ttf', font);
this.addFont('Cambria-normal.ttf', 'Cambria', 'normal');
};
jsPDFAPI.events.push(['addFonts', callAddFont])
 })(jsPDF.API);