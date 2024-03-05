(function (jsPDFAPI) {
var font = 'T1RUTwAMAIAAAwBAQkFTRT9iT8EAAFx4AAAANENGRiDXQLVHAAALpAAATLxEU0lHxz6HFQAAXKwAABZoR1NVQvmH/HcAAFpsAAACDE9TLzJ7miN6AAABMAAAAGBjbWFwUtWphAAABswAAAS4aGVhZOGwGjUAAADMAAAANmhoZWEGpP8kAAABBAAAACRobXR4QwRCHwAAWGAAAAIMbWF4cAEFUAAAAAEoAAAABm5hbWUuDi0WAAABkAAABTpwb3N0/9EAZQAAC4QAAAAgAAEAAAACAUcYwJ6tXw889QADA+gAAAAAv+Pq4wAAAAC/4+rj/TD/EQWgAzcAAAADAAIAAAAAAAAAAQAAAwr/IgDIAtD9MP0wBaAAAQAAAAAAAAAAAAAAAAAAAAEAAFAAAQUAAAACAtABkAAFAAgCigJYAAAASwKKAlgAAAFeAGQBQwAAAg8GCQABBAYDB4AAAK9QACJKAAAAAAAAAABBREJFAEAAIPsCAwr/IgDIAzcA7wAAAAEAAAAAAhsDCgAgACAABAAAABYBDgABAAAAAAAAAEMAAAABAAAAAAABAAkAQwABAAAAAAACAAcATAABAAAAAAADABIAUwABAAAAAAAEAAkAQwABAAAAAAAFADkAZQABAAAAAAAGAAcAngABAAAAAAAHAFEApQABAAAAAAAJADEA9gABAAAAAAALABkBJwABAAAAAAAOACQBQAADAAEECQAAAIYBZAADAAEECQABABIB6gADAAEECQACAA4B/AADAAEECQADACQCCgADAAEECQAEAA4CLgADAAEECQAFAHICPAADAAEECQAGAA4CLgADAAEECQAHAKICrgADAAEECQAJAGIDUAADAAEECQALADIDsgADAAEECQAOAEgD5KkgMTk4OCwgMjAwMiwgMjAwNiBBZG9iZSBTeXN0ZW1zIEluY29ycG9yYXRlZC4gQWxsIHJpZ2h0cyByZXNlcnZlZC5PQ1IgQSBTdGRSZWd1bGFyMi4wMDU7QURCRTtPQ1JBU3RkVmVyc2lvbiAyLjAwNTtQUyAwMDIuMDAwO0NvcmUgMS4wLjM4O21ha2VvdGYubGliMS43LjEyNzQ2T0NSQVN0ZFBsZWFzZSByZWZlciB0byB0aGUgQ29weXJpZ2h0IHNlY3Rpb24gZm9yIHRoZSBmb250IHRyYWRlbWFyayBhdHRyaWJ1dGlvbiBub3RpY2VzLkFtZXJpY2FuIFR5cGUgRm91bmRlcnMgc3RhZmYgYW5kIEFkb2JlIFR5cGUgc3RhZmZodHRwOi8vd3d3LmFkb2JlLmNvbS90eXBlaHR0cDovL3d3dy5hZG9iZS5jb20vdHlwZS9sZWdhbC5odG1sAKkAIAAxADkAOAA4ACwAIAAyADAAMAAyACwAIAAyADAAMAA2ACAAQQBkAG8AYgBlACAAUwB5AHMAdABlAG0AcwAgAEkAbgBjAG8AcgBwAG8AcgBhAHQAZQBkAC4AIABBAGwAbAAgAHIAaQBnAGgAdABzACAAcgBlAHMAZQByAHYAZQBkAC4ATwBDAFIAIABBACAAUwB0AGQAUgBlAGcAdQBsAGEAcgAyAC4AMAAwADUAOwBBAEQAQgBFADsATwBDAFIAQQBTAHQAZABPAEMAUgBBAFMAdABkAFYAZQByAHMAaQBvAG4AIAAyAC4AMAAwADUAOwBQAFMAIAAwADAAMgAuADAAMAAwADsAQwBvAHIAZQAgADEALgAwAC4AMwA4ADsAbQBhAGsAZQBvAHQAZgAuAGwAaQBiADEALgA3AC4AMQAyADcANAA2AFAAbABlAGEAcwBlACAAcgBlAGYAZQByACAAdABvACAAdABoAGUAIABDAG8AcAB5AHIAaQBnAGgAdAAgAHMAZQBjAHQAaQBvAG4AIABmAG8AcgAgAHQAaABlACAAZgBvAG4AdAAgAHQAcgBhAGQAZQBtAGEAcgBrACAAYQB0AHQAcgBpAGIAdQB0AGkAbwBuACAAbgBvAHQAaQBjAGUAcwAuAEEAbQBlAHIAaQBjAGEAbgAgAFQAeQBwAGUAIABGAG8AdQBuAGQAZQByAHMAIABzAHQAYQBmAGYAIABhAG4AZAAgAEEAZABvAGIAZQAgAFQAeQBwAGUAIABzAHQAYQBmAGYAaAB0AHQAcAA6AC8ALwB3AHcAdwAuAGEAZABvAGIAZQAuAGMAbwBtAC8AdAB5AHAAZQBoAHQAdABwADoALwAvAHcAdwB3AC4AYQBkAG8AYgBlAC4AYwBvAG0ALwB0AHkAcABlAC8AbABlAGcAYQBsAC4AaAB0AG0AbAAAAAAAAwAAAAMAAAIUAAEAAAAAABwAAwABAAACFAAGAfgAAAAJAPcAAQAAAAAAAAABAAAAAAAAAAAAAAAA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';
var callAddFont = function () {
this.addFileToVFS('OCRAStdRegular-normal.ttf', font);
this.addFont('OCRAStdRegular-normal.ttf', 'OCRAStdRegular', 'normal');
};
jsPDFAPI.events.push(['addFonts', callAddFont])
 })(jsPDF.API);