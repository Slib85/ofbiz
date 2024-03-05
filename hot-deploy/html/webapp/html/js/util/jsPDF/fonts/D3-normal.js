(function (jsPDFAPI) {
var font = 'AAEAAAAOAIAAAwBgT1MvMlsWo4oAAAGIAAAAVmNtYXCbaiqZAAAf8AAAAcpjdnQgD8AQAAAAGrgAAAIAZnBnbQFSnBgAABy4AAAAs2dhc3AAFwAGAAAaqAAAABBnbHlmy0hsbAAAKKgAACUkaGVhZGJ49bEAAADsAAAANmhoZWEFmAOyAAABJAAAACRobXR43xcBWQAAJTQAAAN0bG9jYQAZMVAAACG8AAADeG1heHAY9gB5AAABSAAAACBuYW1lsP00iwAAAeAAABjGcG9zdP+jADAAAAFoAAAAIHByZXAPJT6lAAAdbAAAAoIAAQAAAAKzMx35l1FfDzz1AAEEAAAAAAAAAAAAAAAAAAAAAAD/w/8pA6ACWAAAAAcAAQABAAAAAAABAAACWP8pAAADoP/DAAsDewABAAAAAAAAAAAAAAAAAAAA3QABAAAA3QBLAAMAAAAAAAEAAAAAABQAABgAACwAAQABAAMAAAAAAAD/oAAwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQJWAZAABQAEAgACAAAAAXACAAIAAAABuAA0AQgAAAILBgAAAAAAAAAAAAARAAAAAAAAAAAAAAAAVEFLRQBAACAhIgJY/ykAAAJYANdAAACf39cAAAAAAAAAEADGAAEAAAQJAAAANAAAAAEAAAQJAAEACgEAAAEAAAQJAAIABwIAAAEAAAQJAAMACgMAAAEAAAQJAAQACgQAAAEAAAQJAAUAAwUAAAEAAAQJAAYACgYAAAEAAAQJAAcACgcAAAMAAQQJAAAAaAgAAAMAAQQJAAEAFAoAAAMAAQQJAAIADgwAAAMAAQQJAAMAFA4AAAMAAQQJAAQAFBAAAAMAAQQJAAUABhIAAAMAAQQJAAYAFBQAAAMAAQQJAAcAFBYAKEMpMTk5OS0yMDAwIERpZ2l0YWxEcmVhbURlc2lnbi5BbGwgUmlnaHRzIFJlc2VydmVkLgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEQzIEFyY2hpc20AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABSZWd1bGFyAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARDMgQXJjaGlzbQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEQzIEFyY2hpc20AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAyLjEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARDMgQXJjaGlzbQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEQzIEFyY2hpc20AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKABDACkAMQA5ADkAOQAtADIAMAAwADAAIABEAGkAZwBpAHQAYQBsAEQAcgBlAGEAbQBEAGUAcwBpAGcAbgAuAEEAbABsACAAUgBpAGcAaAB0AHMAIABSAGUAcwBlAHIAdgBlAGQALgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABEADMAIABBAHIAYwBoAGkAcwBtAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFIAZQBnAHUAbABhAHIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARAAzACAAQQByAGMAaABpAHMAbQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABEADMAIABBAHIAYwBoAGkAcwBtAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADIALgAxAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARAAzACAAQQByAGMAaABpAHMAbQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABEADMAIABBAHIAYwBoAGkAcwBtAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACABgAAf//AAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAAgADAAQABQAGAAcACAAJAAoACwAMAA0ADgAPABAAEQASABMAFAAVABYAFwAYABkAGgAbABwAHQAeAB8AIAAhACIAIwAkACUAJgAnACgAKQAqACsALAAtAC4ALwAwADEAMgAzADQANQA2ADcAOAA5ADoAOwA8AD0APgA/AEAAQQBCAEMARABFAEYARwBIAEkASgBLAEwATQBOAE8AUABRAFIAUwBUAFUAVgBXAFgAWQBaAFsAXABdAF4AXwBgAGEAYgBjAGQAZQBmAGcAaABpAGoAawBsAG0AbgBvAHAAcQByAHMAdAB1AHYAdwB4AHkAegB7AHwAfQB+AH+wACwv/S2wASwv/TwtsAIsL/08PC2wAywv/Tw8PC2wBCwvPP0tsAUsLzz9PC2wBiwvPP08PC2wBywvPP08PDwtsAgsLzw8/S2wCSwvPDz9PC2wCiwvPDz9PDwtsAssLzw8/Tw8PC2wDCwvPDw8/S2wDSwvPDw8/TwtsA4sLzw8PP08PC2wDywvPDw8/Tw8PC2wECw8LbARLC+wECr9LbASLC/9sBAqLbATLC+wECr9sBAqLQC4Af+FsAGNsQGBRUSxAoJFRLEDg0VEsQSERUSxBYVFRLEGhkVEsQeHRUSxCIhFRLEJiUVEsQqKRUSxC4tFRLEMjEVEsQ2NRUSxDo5FRLEPj0VEsRCQRUSxEZFFRLESkkVEsROTRUSxFJRFRLEVlUVEsRaWRUSxF5dFRLEYmEVEsRmZRUSxGppFRLEbm0VEsRycRUSxHZ1FRLEenkVEsR+fRUSxIKBFRLEhoUVEsSKiRUSxI6NFRLEkpEVEsSWlRUSxJqZFRLEnp0VEsSioRUSxKalFRLEqqkVEsSurRUSxLKxFRLEtrUVEsS6uRUSxL69FRLEwsEVEsTGxRUSxMrJFRLEzs0VEsTS0RUSxNbVFRLE2tkVEsTe3RUSxOLhFRLE5uUVEsTq6RUSxO7tFRLE8vEVEsT29RUSxPr5FRLE/v0VEsUDARUSxQcFFRLFCwkVEsUPDRUSxRMRFRLFFxUVEsUbGRUSxR8dFRLFIyEVEsUnJRUSxSspFRLFLy0VEsUzMRUSxTc1FRLFOzkVEsU/PRUSxUNBFRLFR0UVEsVLSRUSxU9NFRLFU1EVEsVXVRUSxVtZFRLFX10VEsVjYRUSxWdlFRLFa2kVEsVvbRUSxXNxFRLFd3UVEsV7eRUSxX99FRLFg4EVEsWHhRUSxYuJFRLFj40VEsWTkRUSxZeVFRLFm5kVEsWfnRUSxaOhFRLFp6UVEsWrqRUSxa+tFRLFs7EVEsW3tRUSxbu5FRLFv70VEsXDwRUSxcfFFRLFy8kVEsXPzRUSxdPRFRLF19UVEsXb2RUSxd/dFRLF4+EVEsXn5RUSxevpFRLF7+0VEsXz8RUSxff1FRLF+/kVEsX//RUQAAAAAAAIAAQAAAAAAFAADAAEAAAEaAAABBgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDEyMzQ1Njc4OTo7PD0+P0BBQkNERUZHSElKS0xNTk9QUVJTVFVWV1hZWltcXV5fYABhYmNkZWZnaGlqa2xtbm9wcXJzdHV2d3h5ent8fX5/gIGCg4SFhoeIiYqLjI2Oj5CRkpOUlZaXmJmam5ydnp+goaKjpKWmp6ipqqusra6vsLGys7S1tre4ubq7vL2+v8DBwsPExcbHyMnKy8zNzs/Q0dLT1NXW19jZ2tvc3d7f4AAEALAAAAAoACAABAAIAH4A/wFTAWEBeAF+AZICxgLcIBQgGiAeICIgJiAwIDogrCEiIhn//wAAACAAoAFSAWABeAF9AZICxgLcIBMgGCAcICAgJiAwIDkgrCEiIhn////i/8H/b/9j/03/Sf82/gP97uC44LXgtOCz4LDgp+Cf4C7fud7DAAEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGAAAABgAAAAYAAAAYAAAAIwAAAEIAAABuAAAAmgAAANcAAADfAAAA8wAAAQcAAAEZAAABJgAAATMAAAE8AAABSAAAAVEAAAFyAAABfwAAAZoAAAHOAAAB5AAAAgoAAAI6AAACRwAAAnwAAAKsAAACvwAAAtMAAALcAAAC6gAAAvMAAAMWAAADVQAAA3AAAAOeAAADyAAAA+YAAAQRAAAELAAABFIAAARfAAAEZgAABIEAAASYAAAEsQAABNUAAATsAAAFDQAABTQAAAVWAAAFeQAABaMAAAW0AAAFxgAABdYAAAX6AAAGEwAABiQAAAZOAAAGTgAABk4AAAZOAAAGTgAABk4AAAZOAAAGeQAABpwAAAa9AAAG4AAABwEAAAcXAAAHRgAAB10AAAduAAAHhQAAB5cAAAekAAAHyAAAB94AAAf/AAAIIgAACEUAAAhTAAAIegAACJIAAAioAAAIuQAACN0AAAkAAAAJIgAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQAACUkAAAlJAAAJSQCAADOAAAAAAJYAAABFAAAAfgAIwLYAAICdAADA0QAAwL8AAgA2AAVAZgABAGgAAsCeAAAAsQAAQEYAAQB6wABARgABAKMAAUCjAAEAYgAAQJoAAACcAAGArAAEAJgAAICgAADAoQABgKQAAUCgAAEARgAAAEcAAABogABAqwABAGkAAACGAAFAxT//QMYAAADCAAAAwD//wMEAAAC/P//Avj//wMoAAMDDAADAOAAAQL8AAMDGAAAAvj//wOgAAADIAAAAxgAAwMQ//8DJAADAyT//wMEAAIC/AACAwAAAAMAAAADlAACAwQAAAMEAAADCAAFAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAoAAAAJ8AAACVAAAAoT//wJoAAQCNAABAoAAAAJ8AAABDAAEAVj/wwJ0AAEBFAAAA5AAAgKAAAACgAAEAoAAAAKA//8B0AAAAmAAAwG8AAECgAAAAoAAAAOMAAICgAAAAoAAAAJcAAMCAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAABAM4BkAEyAfQAAwAAEzUzFc5kAZBkZAACAAD/5ADwAbwAAwAPAAsAtQ0MeAcGBSswMRMDIwsBNDYzMhYVFAYjIibeF6MXDUYyMUdHMTJGAbz+xAE8/mQYJCQYGSMjAAACACMBbwHWAiAAAwAHAAMAMDEBByMnIwcjJwHWHnEeWR5xHgIgsbGxsQAAAAIAAgAAAqwBvAAbAB8AHQBAFQwIBEceAhwQAEgaFhIKDAgESA4CBisrKzAxASMXMxUjFyM1IxUjNyM1MycjNTM3MxczJzMVMwUjFTMCrG4BbW8CtGS/AWdpAWdlA7oBaAGzbP7iamoBC1lIampqakhZSGlpaWlIWgAAAAADAAP/9AJeAYkAJAArADIADrcgTyUAL1IMACsrADAxJRQGDwEuASc1Mx4BFzUnLgEnLgE1NDY/AR4BFxUjLgEnFRceASU1DgEVFBYXNCYnFT4BAl6Odlc4kykWLng7MS5NHBwfiHhXLnwjFiNeNihxa/6tITQi0DUbIS92MkkFAgEPDFgPEgNSBgcRDg0pHixABgEBDghWDBACTAUNNk9FAhAQDhCPERECSgMRAAAAAAMAA//xAyQBwQAZACUAMQAfAEAWCQhALy4FEEAjIgQdHEAWFQUpKEADASsrKyswMQEzBzcyFhUUBiMiJicHIzcHIiY1NDYzMhYPATQmIyIGFRQWMzI2BTQmIyIGFRQWMzI2AfzOnC1YcWteWXEDYs6UI1lzdVdYcQKRHRoiFRUiGh0BjB0aIhUVIhodAbyqA0hJS0hKLWieAUpISkhIKx8wIiIwMCIifDAiIjAwIiIAAwAI//EC3QHCACsAPQBKABMAQAwDAk1IRwUyMUEYFwUrKzAxJQ4BIyImNTQ2Nz4BNy4BJy4BNTQ2Nz4BMzIWFx4BFRQGBw4BBxc2JzMVFAYlNCYnLgEjIgYHDgEVFBYXPgEXJw4BFRQWFx4BMzI2AkxheEOKnhkVEzMXFSYODQ8eHh9oSD1fIB8gDRIUQDeZGQmqQP7dEAwMGggIFg0KEDAmHB1L1QoaGhMUMRciNSgpDkw9GiYPDhUICRQMDBwSFSQPDxQPDQ0kFA4hDw8bC1EnOiEjYfsPEgUFAgIFBBENEiERDSDvcgYfExQdCgkKCAAAAAEAFQFvAMICIAADAAMAMDETByMnwh5xHgIgsbEAAAAAAQAE/zIBfQHQABUAAwAwMQUjLgE1NDY3MxUOAQcOARUUFhceARcBfbxgXV1gvChKHx4mJR8eSyjOQ499fI9ECRY3JiNXWVxTJSM6FQAAAAABAAv/MgGEAdAAFQADADAxJRQGByM1PgE3PgE1NCYnLgEnNTMeAQGEXWC8KEwdHyUmHh9KKLxgXYF9j0MJFTkkJVNcWVcjJjcWCUSPAAAAAAEAAACLAlgBvQARAAMAMDElBycXIzcHJzcnNxcnMwc3FwcCWDigBrcEnTaxsTadBLYEnzez5TJCamc/Mj8/MkBoaD8xPwAAAAEAAQAAAp8BjQALAAsAtQQAUAoGBSswMSUjFSM1IzUzNTMVMwKf58nu7snnnp6eUJ+fAAABAAT/uAD8AHQADAADADAxNzQ2MzIWFRQGBzciJgRJMzJKLHgoM0k0GScnGRs9JDwlAAAAAQABAPcBxgFNAAMACQCzAFYCACswMSUhNSEBxv47AcX3VgAAAQAE//QA/AB0AAsAAwAwMTc0NjMyFhUUBiMiJgRJMzJKSjIzSTQZJycZGyUlAAAAAQAFAAACTwG8AAMAAwAwMQkBIwECT/6c5gFjAbz+RAG8AAAAAgAE//ECbAHBABcAIwATAEAMBgVSISAFGxpSEhEFKyswMSUUBgcOASMiJicuATU0Njc+ATMyFhceAQc0JiMiBhUUFjMyNgJsIiQldFVUdiQlISIlJXZSVXQlJSHCNT0+NTY9PDbZOVgdHR0dHR5XOTpWHR4dHh4dVzhRRUVST0ZGAAAAAQABAAABXAHCAAsAAwAwMTM1Jgc1PgIXFgcRnASXGzBEI6kE+nAvShkUEAEHeP6+AAAAAAEAAAAAAkkBxwAcABEAQAoAVRsACwpXEhEFKyswMSkBNT4BNz4BNTQmIyIGBzU+ATMyFhUUBgcOAQchAkn9t0OHKD0zQj0uZjsji0WMk0ZJLVwUAU9JHUIYIjUZHyEXEWMJE0dAK0woGSsIAAEABv/xAlEBwgA+ABoAQBIrKlgyMQUcG1AfHgUJCFcQDwUrKyswMSUeARUUBgcOASMiJic1HgEzMjY3PgE1NCYnLgErATUzMjY3PgE1NCYnLgEjIgYHNT4BMzIWFx4BFRQGBxUeAQIcGB0pKypyUVyFKT54MR1EGBIWHhgZRR0pKic8FRYWFxEUNRMvbkYpjUlGaiMpKE0/GTnLDSEcIDYUExMQC2MQFwYJCBUUFBUEBQFQAwYFExIOEAUFBBMTYQoTDwwPKh0lOggFAQ0AAAACABD//gKMAbgADwAYAAMAMDElJxUjNQciJic+ATcyHwIFNSYHDgIWMwKMZri4eCoEBNCQmBgCZv7iBDAmKiwESGQBZ2MBHiY0zBRQsQEBZTwKECQuNgAAAQAC//ECRQG8ACkAFwBADxkYUCEBH1UdAAYFVw0MBSsrKzAxJRQGBw4BIyImJzUeATMyNjc+ATU0JicuASMiBgcRIRUhFTI2MzIWFx4BAkUsKS54TFp7J0JpNCBJFhERFxMbTB8tUjUCEf6jEC8SPmAjLjORIzwVFxUQCmMQFgkLChMUDxcHCgYJBQEAVU0BDg0QNgAAAAACAAP/8QJkAcAAJAA5ABMAQAwGBUs3NgUZGFESEQUrKzAxJRQGBw4BIyImJy4BNTQ2Nz4BMzIWFxUuASMiBgc+ATMyFhceAQc+ATU0JicuASMiDgEUFhceATMyNgJkKiYpakhDbigtMC0zMZxsJVcNIUApYGsLJlYxLEogKjLkEBIVEhIxGhYmHxgVDiYWECiRIjwVFxYVFhpSOTtbIyElBgFYBAo5Mg4RDA0RO3AKHBgWHAgJBgodJC8NCQkJAAABAAYAAAJ0AbwACwADADAxISM3NiYHJTUhMhYHAST+1moSRP72AYaYUKLCXkoCAVOIkgAAAwAF//ECegHDABwALgBAABMAQAwDAkw7OgUgH0sTEgUrKzAxJRQGIyImJy4BNTQ2NzUuATU0NjMyFhUUBgcVHgEnNCYjIgYHDgEVFBYXHgEXPgEXNCYnLgEnDgEVFBYzMjY3PgECeqiTU3YmJiVCUEE9pISKnEJETkvOOzASJg8PEyImDjAjFxQPJDgQPxYVI0s8ECsPEhZ4PEsUEhEwGyI0EwISNyQ1Qz0zIDIRAhI8phcbBgUFEQoSFAkECwYQGsEWFg4FDQUMIRUfJQYGBxMAAAACAAT/8gJlAcEAJAA5ABMAQAwqKUsfHgUGBVENDAUrKzAxJRQGBw4BIyImJzUeATMyNjc+ATcOASMiJicuATU0NjMyFhceAQcmJy4BIyIGBw4BFRQWFx4BMzI+AQJlMDE1nWklVg0jNzAnSxseJgYrSzcqTR8qMaaHRmsnLi7AByUOJhUUIxEPFBUTETAcFiwU8jhhISMjBQJXBAkMDA0pHA8PDA0ROytIWhUXGlADKxEJCQkJCh8WFRwJCAYLJAACAAAALAD4AYAACwAXAAMAMDERNDYzMhYVFAYjIiYVNDYzMhYVFAYjIiZJMzJKSjIzSUkzMkpKMjNJAUAZJycZGyUluRknJxkbJSUAAgAA//AA+AGAAAsAGAADADAxETQ2MzIWFRQGIyImFTQ2MzIWFRQGBzciJkkzMkpKMjNJSTMySix4KDNJAUAZJycZGyUluRknJxkbPSQ8JQABAAEAAAGOAbwABQADADAxAQcXIyc3AYqrr+anogG83t7i2gACAAQAcgKDAWAAAwAHAA4AtwRQBgAAUQIAKyswMQEhNSEVITUhAoP9gQJ//YECfwEPUe5QAAAAAAEAAAAAAY0BvAAFAAMAMDETMxcHIzcE56Kn5q8BvNri3gAAAAIABf/kAfsBxwAZACUAEwBADCMidB0cBRAPVBcWBSsrMDEBFAYHDgEXIyY2Nz4BNTQmIyIGBzU+ATMyFgE0NjMyFhUUBiMiJgH7HRsbgAWwAUY3HiFAOyRaOiCFRXuR/mVFMTBGRjAxRQFRHi8RER5MMD4WDB8VHhkTDlsJEUH+mBcjIxcYIiIAAAAAAv/9AAAC+AGwAD0ASAAsQAwSEUwiIQUpKEwLCgUrKwBAFA4NJyYlBR4dJRUUBTQzLi0rBgUHKysrMDEBBwYVFBYzMjc+ATU0JiMiDgEVFBYzMjY3MwYHDgEjIi4BNTQ+ATMyFhUUBgcGIyImJw4BIyImNTQ2NzIXFgUUFj4BNTQmBw4BAigjAgoKFxwrN5eAXppUto9GgjtnKSU3kFNzuGhssnqdxk1KOjIeJgkiNyBDYHxbPEZD/u0mOScoGiEnAQBtBwIFBAkQOSdCTS1VNFJcFxcYDhQZNmE8P2U5Zk4wThcRDg4ODjY1PWMECRqNHxEQUSEZGAwRSAAAAgAAAAADAwHCAAwAGwARAEAKA1QbABQTVQoJBSsrMDElByM1IRUjNzQ2MzIWBzc0JicuASMiBgcOARUHAwMDyP6QyAPMtLPNywMfGRlDJCRBGxkfA9nZUFDZa35+oDUoOBISEBASEDsnNQAAAwAAAAAC7AG+ABcAJgA1ABMAQAwGBVEwLwUtLE0hIAUrKzAxJRQGBw4BIyEnNgU3MhYXHgEVFAYHFR4BJzQmJy4BIyIVMzI2Nz4BFzQmJy4BKwEVMzI2Nz4BAuwsJixpUf5PAwMBQUxaUykrKTk0SVb2ExcVPzSCHplAExoQLxwhFk8rlg69Rh0eF4ggMxEUEOjWAgIHDA0sHCAxDgMJNosLFgUFAV4BBgYXqBUYBQQBcAEIBxkAAAH////yAtcBwQAvABMAQAwvAFUkIwUYF1YMCwUrKzAxBSImJy4BNTQ2Nz4BMzIWFx4BFxUmJy4BIyIGBw4BFRQWFx4BMzI2NzY3FQ4BBw4BAXtTjDMyODUzMJBVg0sgIjYVTxsbQCV8SR8eJCceH0t4I0UdGEwcMxwlQA4cHh1XOTVWHx4gBgUGDgZsGwkKDQ8SETgoKjkQEQ4NCwgbawcNBQYGAAAAAAIAAAAAAu4BvgAPAB8ACwC1BgVTGhkFKzAxJRQGBw4BIyEnNiEyFhceAQc0JicuASMGHwEzMjY3PgEC7l9IN4JZ/s4DAwE6W4QtTVjGNjodLhqUAQNcRUAeNTHePmMaFA/o1hIRHGBALD8SCQYEf5UICRI9AAAAAAH////yAtcBwQAwABMAQAwwAFUlJAUYF1YMCwUrKzAxBSImJy4BNTQ2Nz4BMzIWFx4BFxUmJy4BIyIGBwYHBRUlFhceATMyNjc2NxUOAQcOAQF7U4wzMjg1MzCQVYNLICI2FU8bG0AlfEkfHiQCDf3zJx4fS3gjRR0YTBwzHCVADhweHVc5NVYfHiAGBQYOBmwbCQoNDxIROAFUAzkQEQ4NCwgbawcNBQYGAAAB//8AAALXAcEAHQALALUUE1YIBwUrMDExJjU0Njc+ATMyFhceARcVJicuASMiBgcGBwUVJQcBNTMwkFWDSyAiNhVPGxtAJXxJHx4kAg398wOgOTVWHx4gBgUGDgZsGwkKDQ8SETgBVAOvAAABAAP/8QMIAcIALQALALUPDlMGBQUrMDEFIiY1NDYzMhYXFSYnLgEjIgYHDgEVFBY3Fjc+ATcuAS8BNTMWFx4BFw4BBw4BAYiu19nCdoVRWRkdTitfUyEgJIM6PT8cIAgEICiKtixMNDAEBDAsTFwOe21ogBAYaR4ICg4SExI4JEpPAgICCBAYGBgEA1kEDBA0KCA4GBgQAAEAAwAAAu4BvgALAAkAswJXCAArMDEhIzUhFSMRMxUhNTMC7sD+lcDAAWvAxMQBvqOjAAABAAEAAADBAb4AAwADADAxMxEzEQHAAb7+QgAAAQAD//cC2wG+AB0ACwC1CAdWFhUFKzAxARYVFAYHDgEjIiYnLgEnNTMeARceATMyNjc2NzUnAtoBNTMwkFWDSyAiNhUWDisbG0AlfEkfHiQBAb6mOTVWHx4gBgUGDgZsBxQJCg0PEhE4UrUAAQAAAAAC9wG+ABoAAwAwMTERMxcWNjc+ATUnMxcUBxYXByM3NCYnLgEHFbwBwkMZGR8DyAOHnAcDyAMfGRlD3wG+tAEQEhI4KCEha1AyjyEhKDgSEhABtAAAAAAB////9wLXAb4AGwALALUUE1YIBwUrMDERMxcVFhceATMyNjc2NxUOAQcOASMiJicuATU0xAMkHh9JfCVAGxtPFTYiIEuDVZAwMzUBvrVSOBESDw0KCRtsBg4GBQYgHh9WNTkAAQAAAAADewHCACkAEABACiMiBwZVFxYTEg8rMDEhNzQmJy4BIyIGBw4BFQcjNzQ2MzIXNjMyFhUHIzc0JicuASMiBgcOAQMBWAMHCRETFBQRDw0HA8gDnHR9NkVgi4UDyAMHCRETFBQRDw0HA9koOBISEBASEDsn2dlrfj4+fmvZ2Sg4EhIQEBIQO/8AAAAAAQAAAAADAwHCABcACwC1CQhVFRQFKzAxJQcjNzQmJy4BIyIGBw4BFQcjNzQ2MzIWAwMDyAMfGRlDJCRBGxkfA8gDzLSzzdnZ2Sg4EhIQEBIQOyfZ2Wt+fgAAAAACAAP/8QMDAcIACwAjABMAQAwDAlQhIAUVFFUJCAUrKzAxJRQGIyImNTQ2MzIWBz4BNTQmJy4BIyIGBw4BFRQWFx4BMzI2AwPMtLTMzLSzzf8cGx8ZGUMkJEEbGR8eGRlDJSVD2Wt9fWtrfn7dFTglKDgSEhAQEhA7Jyg4EhIQEAAAAAAC//8AAAL5AcEAFwAqABEAQAoGBVQZGAUlVhAAKyswMQEUBgcOASMhByMmNTQ2Nz4BMxcyFhceAQUzMjY3PgE1NCYnLgEHIgYHDgEC+SMgLW1S/v0DxAE1MzCQVVNHYSYtL/3WuzE/FhIRIhceOSYMSR8eGAEzHjkUGhuToDk1Vh8eIAEODxI3cwcLCRsUEhwFBwICDxIRNAAAAAACAAP/8QMIAcIADQAlABMAQAwFBFQjIgUXFlULCgUrKzAxJRQHFwYjIiY1NDYzMhYHPgE1NCYnLgEjIgYHDgEVFBYXHgEzMjYDA0dM0bS0zMy0s83/HBsfGRlDJCRBGxkfHhkZQyUlQ9lrIiwvfWtrfn7dFTglKDgSEhAQEhA7Jyg4EhIQEAAAAv//AAADAwG/ABgAKgADADAxARQGDwEWHQEjNTQmDwEjJjU2JRcyFhceAQUyNjc+ATU0JicuAQciBgcOAQL5IyASX8GG9wHEAQEBfFNHYSYtL/3W7D8WEhEiFx45JgxJHx4YATEeORQKMFM5MjFBCZueOeEHAQ4PEjdzBwsJGxQSHAUHAgIPEhE0AAABAAL/8wLrAcEAMQATAEAMIyJSHBsFAwJSCgkFKyswMSUUBiMiJic1HgEzMjY3PgE1NCYnLgEnLgE1NDYzMhYXFS4BIyIGBw4BFRQWFx4BFx4BAuu7oMiKPFGTsBI5EhYcKSiWXileUrr/SZA0QYNEhC8WFBwtP5VLLFVSh0JSEw9rHR4DBQUQEA8VBgYKCBI/Lz5PEQ1nFh0EBgQSDBISCAYJCRA6AAABAAIAAALiAcUAEAADADAxIREOAQc1PgE3HgEXFS4BBxEBDDp/UTyKrKiKPFGDPgFsARodaw8TBAgTD2sdHgP+mAAAAAEAAP/xAucBvgARAAsAtQMCVAwLBSswMSUUBiMiJjURMxEUFjMyNjURMwLnqMzLqMFBcXBDwZtTV1dTASP+5C8uLDEBHAAAAAEAAP/9AucBvgANAAkAswFUCQArMDENASImNREzERQWMzcRMwLn/ozLqMFBcbPBAQJLUwEj/vAvLgEBbAAAAAABAAL/8gN7Ab4AKQAQAEAKFxYTElUjIgcGDyswMQEzEhYXHgEzMjY3PgE1JzMXFAYjIicGIyImNSczFxQWFx4BMzI2Nz4BNQFayAEHDQ8RFBQTEQkHAcgBhYtgRTZ9dJwByAEHDQ8RFBQTEQkHAb7+9jsQEhAQEhI4KOPja34+Pn5r4+MnOxASEBASEjgoAAABAAAAAALnAb4AHwADADAxARQHFh0BIzU0JicOAR0BIzU0NyY9ATMVHgEXPgE3NTMC51VVwVNgW1fBUlLBCDlsdTsIwQFjUywsU2VWMSwGAjYrVmVTLCxTW1QvJgYEJjFUAAAAAQAAAAAC5wG+ABQAAwAwMQEUBicVIzUGJj0BMxUUFjMyNj0BMwLnqGvIZKjBQXFwQ8EBY1NXB8C8A1dTW1QvLiwxVAABAAX/8wLuAcEAMQATAEAMLy5SKCcFDw5SFhUFKyswMTc0Njc+ATc+ATU0JicuASMiBgc1PgEzMhYVFAYHDgEHDgEVFBYXHgEzMjY3FQ4BIyImBVJVLEuVPy0cFBYvhESDQTSQSf+6Ul4pXpYoKRwWEjkSsJNRPIrIoLuHLjoQCQkGCBISDBIEBgQdFmcNEU8+Lz8SCAoGBhUPEBAFBQMaIWsPE1IAAAACAAD/8QJrAVIAGQAxABkAQBESEUcvLgUjIkkXAQMCSgoJBSsrKzAxJS4BIyIGBzU+ATMyFhcVNQ4BIyImNTQ2MxYHPgE1NCYnLgEjIgYHDgEVNBYXHgEzMjYBvgRMSlllJhyJZJp5DwSikpKhopFvGRIREhAQLRoaKBMQFBERECwdGC3PIxYTBVEFDDg9cQE7QUE7OkMCoQkZAxQICQgIBwgJCBUBHQgICAcAAgAA//ECZwG+AA4AJgASAEALCAdLJCMFGBdLAgErKzAxEzY3MhYVFAYjIiY1NxEzEz4BNTQmJy4BIyIGBw4BFRQWFx4BMzI2tDRLk6GikpKhAbTUEhESEBAtGhooExAUEREQLB0YLQFEEAJgU1NfX1MCARn+kw0kIB4oDAwLCQwMKR8bKQwMCgkAAAABAAD/8QI0AVQAIwATAEAMIwBMGxoFFRRODAsFKyswMQUiJicuATU0Njc+ATMyFhcVJicuASMiBhUUFjMyNjc2NxUOAQFdWXotLDE0Ly53UFFhKkEWFDE5WE1PWTo3EhE+Kl0PFRUWQywwRBYWFA8MXRQHBwg2MDAzCgYGFF0MDQAAAAAC////8QJmAb4ADgAmABIAQAsHBksSEQUeHUsMASsrMDEBJzMRFxQGIyImNTQ2MxYDHgEzMjY3PgE1NCYnLgEjIgYHDgEVFBYBsgG0AaGSkqKhk0uhDy0YHSwQEREUEBMoGhotEBASEQFEev7nAlNfX1NTYAL+/QwJCgwMKRsfKQwMCQsMDCgeICQAAAIABP/xAlYBVQAXACMAEwBADBsaRBIRBQwLSwUEBSsrMDElJiceATMyNjcVDgEjIiY1NDYzMhYVDgEnLgEjIgYVBhYXMjYBQkZCBFxaOWszPWxBqbSqlYmKAoQtAjU4NEMBQDg0OHIGFigqFRJXDg1bVFNiRzIwNGUaGiAYFQwEEAAAAAEAAQAAAikBwQAWABMAQAwCAUsTEgUMCEkOBgUrKzAxASYjIgYdATMVIxUjNSM1MzU0NjMyFhcCKUAWNWbLxbSJiW6vKEEZAW8HFiEDSfPzSQo+PQMDAAACAAD/cwJrAVYAHgA2ABkAQBEUSzQzBCgnSxoZBQYFTg0MBSsrKzAxJQ4BBw4BIyImJzUeATMyNjc+ATcHIiY1NDYzMhYXFQc+ATU0JicuASMiBgcOARUUFhceATMyNgJrBC01KHNIYm4pN1tNMj8QEBAHfZKhopGToQTiEhESEBAtGhooExAUEREQLB0YLRgvQRMSEBAGVA4OCgsJDg8LX1NUX2BTAVENJCAeKAwMCwkMDCkfGykMDAoJAAEAAAAAAmcBvgAYAAoAtBAPSwQBKzAxEzMHNjcyFhUHIzU0JicuASMiBgcOARUXIwG4ASBbk6EBuhIQEC0aGigTEBQCvAG+ghYCYFOhoB4oDAwLCQwMKR+gAAAAAgAEAAAA+AG0AAMADwALALUNDGgHBgUrMDEzIxEzJzQ2MzIWFRQGIyIm2bS01UczMkhIMjNHASBgFR8fFRYeHgAAAAAC/8P/TQFAAbQADQAZAAsAtRcWaBEQBSswMQUUBiMiJzUWMzI2NREXJzQ2MzIWFRQGIyImASh1XDFjSg00JrTcRzMySEgyM0c7OT8HTgclKgE1A2QVHx8VFh4eAAEAAQAAAlUBvAASAAMAMDEhIy4BBxUjETMRFjYnMw4BBx4BAlXSB3xLtLRLfAXPDnxISJQ8MAFrAbz+/wNUQGBMBARAAAAAAAEAAP/2APQBvAAKAAMAMDEXBicmNRMzAwYWF/R+PjgBtAEEGCwIAhYsUAE0/vwwOAQAAAABAAIAAAN7AVIAKQAQAEAKIyIHBlUXFhMSDyswMSE3NCYnLgEjIgYHDgEVByM3NDYzMhc2MzIWFQcjNzQmJy4BIyIGBw4BBwFaAQcJERMUFBEPDQcByAGcdH02RWCLhQHIAQcJERMUFBEPDQcBaSg4EhIQEBIQOydpaWt+Pj5+a2lpKDgSEhAQEhA7kAAAAAABAAAAAAJnAVQAFwALALUJCEsVFAUrMDElByM1NCYnLgEjIgYHDgEVByM1NDYzMhYCZwO4EhAQLRoaKBMQFAK4opGToaGhoB4oDAwLCQwMKR+goVRfYAAAAgAE//ECawFWAAsAIwATAEAMAwJLISAFFRRLCQgFKyswMSUUBiMiJjU0NjMyFgc+ATU0JicuASMiBgcOARUUFhceATMyNgJropKSoaKRk6HeEhESEBAtGhooExAUEREQLB0YLaNTX19TVF9gpQ0kIB4oDAwLCQwMKR8bKQwMCgkAAAAAAgAA/4gCZwFVAA4AJgASAEALDEseHQQSEUsHBgUrKzAxNxcjESc0NjMyFhUUBiMmEy4BIyIGBw4BFRQWFx4BMzI2Nz4BNTQmtAG0AaGSkqKhk0uhDy0YHSwQEREUEBMoGhotEBASEQJ6ARkCU19fU1NgAgEDDAkKDAwpGx8pDAwJCwwMKB4gJAAAAAAC////iAJmAVUADgAmABIAQAsCSxgXBCQjSwgHBSsrMDElBgciJjU0NjMyFhUHESMDDgEVFBYXHgEzMjY3PgE1NCYnLgEjIgYBsjRLk6GikpKhAbTUEhESEBAtGhooExAUEREQLB0YLQIQAmBTU19fUwL+5wFtDSQgHigMDAsJDAwpHxspDAwKCQAAAAEAAAAAAbsBXAANAAMAMDEBIyIGBw4BFQcjNTQ2BQG5ci4oExAUArigARsBCQkMDCkfoKFUZwgAAQAD//ECSQFUAC0AEwBADCIhSBsaBQMCRwwLBSsrMDElFAYjIiYnNRYXHgEzMjY1NCYnLgEnLgE1NDYzMhYXFS4BIyIGFRQWFx4BFx4BAkmciIN6JUIgHEdhNTMeKhR9HExKmb0/dCA3ZzRkOxtqG0AgR0RkND8QClkTCAcKDg4MDAQDBgUMMyUxQRAIVRAVDw0MDAcDBwQLMAAAAQAB//UBqAGsABkAEwBADAMCSRcWBQ8HSQ0JBSsrMDEFDgEjIiY9ASM1MzUzFTMVIxUUFhceATMyNwGoHUEvaWdKSrSpqQEIByMiDkYCBAUzPZ5JYGBJeBEbCgsMCgAAAQAA//ICZwFMABcACwC1AwJLDw4FKzAxJRQGIyImPQEzFxQWFx4BMzI2Nz4BPQEzAmehk5GiuAIUEBMoGhotEBASuKVTYF9Up6YfKQwMCQsMDCgepgAAAAEAAAAAAmQBTAAQAAoAtAEASwwBKzAxKQEiJj0BMxcUFhceATMXETMCZP7PkaK4AhQQEygaebhfVJmYHykMDAkDAQQAAQAC//gDewFKACkAEABAChcWExJVIyIHBg8rMDEBMx4BFx4BMzI2Nz4BNSczFxQGIyInBiMiJjUnMxcUFhceATMyNjc+ATUBWsgBBw0PERQUExEJBwHIAYWLYEU2fXScAcgBBw0PERQUExEJBwFKkDsQEhAQEhI4KGlpa34+Pn5raWknOxASEBASEjgoAAAAAQAAAAACZwFMACsACwC1DQxOIyIFKzAxARQHFhUHIzU0JicuASMiBgcOARUHIzU0NyY9ATMXFBYXHgEzMjY3PgE9ATMCZ2NjA7gSEBAtGhooExAUArhkZLgCFBATKBoaLRAQErgBO1ksMGElJB4oDAwLCQwMKR8kJWEsL1oRCB8pDAwJCwwMKB4IAAABAAD/dQJrAUwAJQASAEALE0seHQQGBU4NDAUrKzAxJQ4BBw4BIyImJzUeATMyPgE3BgciJj0BMwYWFx4BMzI2Nz4BJzMCawQtNShzSGJuKTdbTTI/JhQlW5GivAIUEBMoGhotEBASAsAOI0ETEhAQBlQMEAoSKRAEX1SlwykMDAkLDAwowgAAAQAD//ECRQFUAC0AEwBADCsqRyIhBQwLSBMSBSsrMDE3NDY3PgE3PgE1NCYjIgYHNT4BMzIWFRQGBw4BBw4BFRQWMzI2NzY3FQ4BIyImA0RHIHQbMhs7LGhnNyB0c4WZSkwcRRReHjNpKUccIEIleku8nGQkMAsEBwMHDAwNDxUQVQgQQTElMwwFBgMEDAwODgoHCBNZChA/AAA=';
var callAddFont = function () {
this.addFileToVFS('D3-normal.ttf', font);
this.addFont('D3-normal.ttf', 'D3Archism', 'normal');
};
jsPDFAPI.events.push(['addFonts', callAddFont])
 })(jsPDF.API);