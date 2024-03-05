(function (jsPDFAPI) {
var font = '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AAAAAAADABQABwABAAAAAAAEAA0AGwABAAAAAAAFABcAKAABAAAAAAAGAAcABwABAAAAAAAKAEAAPwADAAEEAwACAAwK+QADAAEEBQACABAAfwADAAEEBgACAAwAjwADAAEEBwACABAAmwADAAEECAACABAAqwADAAEECQAAAH4AuwADAAEECQABAA4FowADAAEECQACAA4BOQADAAEECQADACgBRwADAAEECQAEABoBbwADAAEECQAFAC4BiQADAAEECQAGAA4FowADAAEECQAIAGIBtwADAAEECQAJAFICGQADAAEECQAKA2gCawADAAEECQALACwCPwADAAEECQAMACwCPwADAAEECQANBGIF0wADAAEECQATAJQKNQADAAEECgACAAwK+QADAAEECwACABAKyQADAAEEDAACAAwK+QADAAEEDgACAAwLFwADAAEEEAACAA4K2QADAAEEEwACABIK5wADAAEEFAACAAwK+QADAAEEFQACABAK+QADAAEEFgACAAwK+QADAAEEGQACAA4LCQADAAEEGwACABALFwADAAEEHQACAAwK+QADAAEEHwACAAwK+QADAAEEJAACAA4LJwADAAEELQACAA4LNQADAAEICgACAAwK+QADAAEIFgACAAwK+QADAAEMCgACAAwK+QADAAEMDAACAAwK+VJlZ3VsYXJTdGVpbmVyOlZlcnNpb24gMS4wMFN0ZWluZXIgTGlnaHRWZXJzaW9uIDEuMDAgMTYvMDUvMjAwNgAAqSAyMDA2IEdhYnJpZWxlIE1hZ3Vybm8gLSBBbGwgUmlnaHRzIFJlc2VydmVkLgAAAAAAAAAAAAAAAAAAAAAAbwBiAHkBDQBlAGoAbgDpAG4AbwByAG0AYQBsAFMAdABhAG4AZABhAHIAZAOaA7EDvQO/A70DuQO6A6wAqQAgADIAMAAwADYAIABHAGEAYgByAGkAZQBsAGUAIABNAGEAZwB1AHIAbgBvACAALQAgAHcAdwB3AC4AbQBhAGcAdQByAG4AbwAuAGMAbwBtAC4AIABBAGwAbAAgAFIAaQBnAGgAdABzACAAUgBlAHMAZQByAHYAZQBkAC4AUgBlAGcAdQBsAGEAcgBTAHQAZQBpAG4AZQByADoAVgBlAHIAcwBpAG8AbgAgADEALgAwADAAUwB0AGUAaQBuAGUAcgAgAEwAaQBnAGgAdABWAGUAcgBzAGkAbwBuACAAMQAuADAAMAAgADEAOQAvADAANQAvADIAMAAwADYAVABoAGkAcwAgAGYAbwBuAHQAIABpAHMAIABmAHIAZQBlAHcAYQByAGUAOgAgAG4AbwAgAGYAdQAqAGsAaQBuAGcAIAB2AGUAbgBkAG8AcgBzACAAbgBlAGUAZABlAGQALgBHAGEAYgByAGkAZQBsAGUAIABNAGEAZwB1AHIAbgBvACAALQAgAGgAdAB0AHAAOgAvAC8AdwB3AHcALgBtAGEAZwB1AHIAbgBvAC4AYwBvAG0ASQAnAHYAZQAgAGIAZQBlAG4AIABhAGwAdwBhAHkAcwAgAGYAYQBzAGMAaQBuAGEAdABlAGQAIABiAHkAIAB0AHkAcABvAGcAcgBhAHAAaAB5AC4AIABUAGgAaQBzACAAaQBzACAAbQB5ACAAZgBpAHIAcwB0ACAAYQB0AHQAZQBtAHAAdAAgAHQAbwAgAGIAdQBpAGwAZAAgAGEAIABUAHIAdQBlAFQAeQBwAGUAKAB0AG0AKQAgAGYAbwBuAHQAOgAgAGEAZgB0AGUAcgAgAHQAdQByAG4AaQBuAGcAIABtAHkAIABQAGgAbwB0AG8AcwBoAG8AcAAgAEIAcgB1AHMAaABlAHMAIABpAG4AdABvACAAYQAgAEQAaQBuAGcAQgBhAHQAcwAgAGYAbwBuAHQAIABpAG4AIAAyADAAMAA0ACAAKAAiAHQAaABlACAAQgAuAE8ALgBNAC4AQgAuACIAKQAgAEkAJwB2AGUAIAB0AGgAbwB1AGcAaAB0ACAAdABvACAAdAByAHkAIABhAG4AZAAgAG0AYQBrAGUAIABhACAAIgBzAGUAcgBpAG8AdQBzACIAIAB0AHkAcABlAGYAYQBjAGUALgAgAEkAIABoAGEAZAAgAGkAbgAgAG0AaQBuAGQAIABhACAAbQBvAGQAZQByAG4ALAAgAHMAdAB5AGwAaQBzAGgAIABmAG8AbgB0ACwAIABpAGQAZQBhAGwAIABmAG8AcgAgAGYAYQBzAGgAaQBvAG4AIABsAG8AZwBvAHMAIABhAG4AZAAgAGMAbwByAHAAbwByAGEAdABlACAAZABlAHMAaQBnAG4AOgAgAGgAbwBwAGUAIABJACcAdgBlACAAYQBjAGgAaQBlAHYAZQBkACAAbQB5ACAAZwBvAGEAbAAuACAAQwBvAG4AYwBlAHIAbgBpAG4AZwAgAHQAaABlACAAZgBvAG4AdAAnAHMAIABuAGEAbQBlADoAIABJACAAdwBhAG4AdABlAGQAIAB0AG8AIABkAGUAZABpAGMAYQB0AGUAIABtAHkAIABlAGYAZgBvAHIAdABzACAAdABvACAAUgB1AGQAbwBsAGYAIABTAHQAZQBpAG4AZQByACcAcwAgAEEAbgB0AGgAcgBvAHAAbwBzAG8AcABoAHkALgBUAGgAaQBzACAAZgBvAG4AdAAgAGkAcwAgAGwAaQBjAGUAbgBzAGUAZAAgAGEAcwAgAEYAcgBlAGUAdwBhAHIAZQAuACAASQBuAHQAZQBsAGwAZQBjAHQAdQBhAGwAIAByAGkAZwBoAHQAcwAgAGEAbgBkACAAcAByAG8AcABlAHIAdAB5ACAAcgBlAG0AYQBpAG4AcwAgAHQAbwAgAHQAaABlACAAbABlAGcAaQB0ACAAbwB3AG4AZQByACAAYQBuAGQAIABhAHUAdABoAG8AcgAsACAARwBhAGIAcgBpAGUAbABlACAATQBhAGcAdQByAG4AbwAuACAASQBmACAAeQBvAHUAIABwAGwAYQBuACAAdABvACAAcgBlAGQAaQBzAHQAcgBpAGIAdQB0AGUAIAB0AGgAaQBzACAAZgBvAG4AdAAgAG8AbgAgAG0AYQBnAGEAegBpAG4AZQBzACwAIABDAEQALQBSAE8ATQBzACAAbwByACAAdwBlAGIAcwBpAHQAZQBzACwAIAB5AG8AdQAgAG0AdQBzAHQAIABsAGUAYQB2AGUAIAB0AGgAZQAgAHAAYQBjAGsAYQBnAGUAIABpAG4AdABhAGMAdAAgAHcAaQB0AGgAIAB0AGgAZQAgAHMAYQBtAGUAIABmAGkAbABlAHMAIAB5AG8AdQAnAHYAZQAgAGYAbwB1AG4AZAAgAG8AbgAgAHQAaABlACAAbwByAGkAZwBpAG4AYQBsACwAIABhAHYAYQBpAGwAYQBiAGwAZQAgAGEAdAAgAG0AeQAgAHMAaQB0AGUALgAgAFkAbwB1ACAAbQBhAHkAIABOAE8AVAAgAGEAcwBrAC8AZQBhAHIAbgAgAG0AbwBuAGUAeQAgAHcAaQB0AGgAIAB0AGgAaQBzACAAZgBvAG4AdAAgAGQAaQByAGUAYwB0AGwAeQAgACgAZQAuAGcALgAgAGIAeQAgAHMAZQBsAGwAaQBuAGcAIABpAHQAKQAgAGIAdQB0ACAAeQBvAHUAJwByAGUAIABmAHIAZQBlACAAdABvACAAdQBzAGUAIABpAHQAIABmAG8AcgAgAGMAbwBtAG0AZQByAGMAaQBhAGwAIABwAHIAbwBqAGUAYwB0AHMAIAAoAGwAbwBnAG8AcwAsACAAZgBsAHkAZQByAHMALAAgAHAAbwBzAHQAZQByAHMALAAgAGUAdABjAC4ALgAuACkAIABBACAAYwByAGUAZABpAHQAIABvAHIAIABhACAAbABpAG4AawAgAGIAYQBjAGsAIAB0AG8AIABtAHkAIABzAGkAdABlACAAKAB3AHcAdwAuAG0AYQBnAHUAcgBuAG8ALgBjAG8AbQApACAAYQByAGUAbgAnAHQAIAByAGUAcQB1AGkAcgBlAGQALAAgAGIAdQB0ACAAdABoAGUAeQAgAGEAcgBlACAAYQBsAHcAYQB5AHMAIABhAHAAcAByAGUAYwBpAGEAdABlAGQALgBMAGEAIABzAGMAZQBsAHQAYQAgAOgAIAB1AG4AJwBpAGwAbAB1AHMAaQBvAG4AZQAsACAAYwByAGUAYQB0AGEAIABlACAAcABvAHMAdABhACAAZgByAGEAIABjAGgAaQAgAGgAYQAgAHAAbwB0AGUAcgBlACAAZQAgAGMAaABpACAAbgBvAG4AIABuAGUAIABoAGEATgBvAHIAbQBhAGEAbABpAE4AbwByAG0AYQBsAGUAUwB0AGEAbgBkAGEAYQByAGQATgBvAHIAbQBhAGwAbgB5BB4EMQRLBEcEPQRLBDkATgBvAHIAbQDhAGwAbgBlAE4AYQB2AGEAZABuAG8AQQByAHIAdQBuAHQAYQAAAAACAAAAAAAA/ycAlgAAAAAAAAAAAAAAAAAAAAAAAAAAAFAAAAECAQMAAwAEAAkACgANAA4ADwAQABEAEgATABQAFQAWABcAGAAZABoAGwAcAB0AHgAiACQAJQAmACcAKAApACoAKwAsAC0ALgAvADAAMQAyADMANAA1ADYANwA4ADkAOgA7ADwAPQA/AEQARQBGAEcASABJAEoASwBMAE0ATgBPAFAAUQBSAFMAVABVAFYAVwBYAFkAWgBbAFwAXQEEBS5udWxsEG5vbm1hcmtpbmdyZXR1cm4ERXVybwAAAAAAAf//AAI=';
var callAddFont = function () {
this.addFileToVFS('Steinerlight-normal.ttf', font);
this.addFont('Steinerlight-normal.ttf', 'Steiner', 'normal');
};
jsPDFAPI.events.push(['addFonts', callAddFont])
 })(jsPDF.API);