(function (jsPDFAPI) {
var callAddFont = function () {
this.addFileToVFS('timesNewRoman-normal.ttf', font);
this.addFont('timesNewRoman-normal.ttf', 'TimesNewRomanPSMT', 'normal');
};
jsPDFAPI.events.push(['addFonts', callAddFont])
 })(jsPDF.API);