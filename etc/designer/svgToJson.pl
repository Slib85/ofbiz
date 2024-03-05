#!/usr/bin/perl -w
use strict;
use warnings;
use LWP::UserAgent;
use POSIX;
use Data::Dumper qw(Dumper);

my @files = <filesToConvert/*>;

foreach my $file (@files) {
    open(my $fh, '<:encoding(UTF-8)', $file) or die "Could not open file '$file' $!";
    my $output = "";
    my $isBackground = 0;
    my $viewBoxWidth = 0;
    my $viewBoxHeight = 0;

    while (my $row = <$fh>) {
        chomp $row;
        my $outputToConcat = "";

        if ($row =~ /viewBox="0\s0\s(.*?)\s(.*?)"/) {
            $viewBoxWidth = $1 / 72;
            $viewBoxHeight = $2 / 72;
        }

        if ($row =~ /<g.*?id="background"/i || $row =~ /<rect.*?id="background"/i) {
            $isBackground = 1;
        } elsif ($isBackground && $row =~ /<\/g>/i) {
            $isBackground = 0;
        }

        if ($row =~ /<path\s/i) {
            my $coordinates;
            my $fill = "transparent";
            my $stroke = "transparent";
            my $strokeDashArray = "";
            my $strokeWidth = 1;
            my $left = 0;
            my $top = 0;

            if ($row =~ /\sd="(.*?)"/i) {
                $coordinates = $1;
            }

            if ($row =~ /transform=".*?translate\((.*?)\s(.*?)\)"/i) {
                $left = $1;
                $top = $2;
            }

            if ($row =~ /fill="(.*?)"/i) {
                $fill = ($1 eq "none" ? "transparent" : $1);
            }

            if ($row =~ /stroke="(.*?)"/i) {
                $stroke = $1;
            }

            if ($row =~ /stroke\-width="(.*?)"/i) {
                $strokeWidth = $1
            }

            if ($row =~ /stroke\-dasharray="(.*?)"/i) {
                $strokeDashArray = "[";

                for (split(" ", $1)) {
                    $strokeDashArray .= $_ . ","; 
                }

                $strokeDashArray =~ s/,$/]/;
            }

            $outputToConcat = qq|{"type": "keyline", "drawAs": "path", "fill": "| . ($isBackground ? "dynamic" : $fill) . qq|", "stroke": "$stroke", "strokeWidth": "$strokeWidth", "left": "$left", "top": "$top", "coordinates": "$coordinates"| . ($strokeDashArray ne "" ? qq|,"strokeDashArray": $strokeDashArray| : "") . qq|},|;
        } elsif ($row =~ /<line\s/i) {
            my $coordinates;
            my $fill = "transparent";
            my $stroke = "transparent";
            my $strokeDashArray = "";
            my $strokeWidth = 1;
            my $left = 0;
            my $top = 0;
            my $x1 = 0;
            my $y1 = 0;
            my $x2 = 0;
            my $y2 = 0;

            if ($row =~ /x1="(.*?)"/i) {
                $x1 = $1;
            }

            if ($row =~ /x2="(.*?)"/i) {
                $x2 = $1;
            }

            if ($row =~ /y1="(.*?)"/i) {
                $y1 = $1;
            }

            if ($row =~ /y2="(.*?)"/i) {
                $y2 = $1;
            }

            if ($row =~ /stroke\-width="(.*?)"/i) {
                $strokeWidth = $1
            }

            $left = ($x1 <= $x2 ? $x1 : $x2);
            $top = ($y1 <= $y2 ? $y1 : $y2);
            
            if ($row =~ /transform=".*?translate\((.*?)\s(.*?)\)"/i) {
                $left = $1;
                $top = $2;
            }

            if ($row =~ /fill="(.*?)"/i) {
                $fill = ($1 eq "none" ? "transparent" : $1);
            }

            if ($row =~ /stroke="(.*?)"/i) {
                $stroke = $1;
            }
            
            if ($row =~ /stroke\-dasharray="(.*?)"/i) {
                $strokeDashArray = "[";

                for (split(" ", $1)) {
                    $strokeDashArray .= $_ . ","; 
                }

                $strokeDashArray =~ s/,$/]/;
            }

            $outputToConcat = qq|{"type": "keyline", "drawAs": "line", "fill": "| . ($isBackground ? "dynamic" : $fill) . qq|", "stroke": "$stroke", "strokeWidth": "$strokeWidth", "left": "$left", "top": "$top", "coordinates": [$x1, $y1, $x2, $y2]| . ($strokeDashArray ne "" ? qq|,"strokeDashArray": $strokeDashArray| : "") . qq|},|;
        } elsif ($row =~ /<polygon\s/i) {
            my $coordinates;
            my $fill = "transparent";
            my $stroke = "transparent";
            my $strokeWidth = 1;
            my $left = 0;
            my $top = 0;

            if ($row =~ /points="(.*?)"/i) {
                $coordinates = $1;
            }

            if ($row =~ /stroke="(.*?)"/i) {
                $stroke = $1;
            }

            if ($row =~ /transform=".*?translate\((.*?)\s(.*?)\)"/i) {
                $left = $1;
                $top = $2;
            }

            if ($row =~ /fill="(.*?)"/i) {
                $fill = ($1 eq "none" ? "transparent" : $1);
            }

            if ($row =~ /stroke\-width="(.*?)"/i) {
                $strokeWidth = $1
            }

            $outputToConcat = qq|{"type": "keyline", "drawAs": "polygon", "fill": "| . ($isBackground ? "dynamic" : $fill) . qq|", "stroke": "$stroke", "strokeWidth": "$strokeWidth", "left": "$left", "top": "$top", "coordinates": "$coordinates"},|;
        } elsif ($row =~ /<polyline\s/i) {
            my $coordinates;
            my $fill = "transparent";
            my $stroke = "transparent";
            my $strokeWidth = 1;
            my $left = 0;
            my $top = 0;

            if ($row =~ /points="(.*?)"/i) {
                $coordinates = $1;
            }

            if ($row =~ /stroke="(.*?)"/i) {
                $stroke = $1;
            }

            if ($row =~ /transform=".*?translate\((.*?)\s(.*?)\)"/i) {
                $left = $1;
                $top = $2;
            }

            if ($row =~ /fill="(.*?)"/i) {
                $fill = ($1 eq "none" ? "transparent" : $1);
            }

            if ($row =~ /stroke\-width="(.*?)"/i) {
                $strokeWidth = $1
            }

            $outputToConcat = qq|{"type": "keyline", "drawAs": "polyline", "fill": "| . ($isBackground ? "dynamic" : $fill) . qq|", "stroke": "$stroke", "strokeWidth": "$strokeWidth", "left": "$left", "top": "$top", "coordinates": "$coordinates"},|;
        } elsif ($row =~ /<rect\s/i) {
            my $fill = "transparent";
            my $stroke = "transparent";
            my $strokeWidth = 1;
            my $strokeDashArray = "";
            my $left = 0;
            my $top = 0;
            my $width = 0;
            my $height = 0; 

            if ($row =~ /x="(.*?)"/i) {
                $left = $1;
            }

            if ($row =~ /y="(.*?)"/i) {
                $top = $1;
            }
            
            if ($row =~ /width="(.*?)"/i) {
                $width = $1;
            }

            if ($row =~ /height="(.*?)"/i) {
                $height = $1;
            }

            if ($row =~ /transform=".*?translate\((.*?)\s(.*?)\)"/i) {
                $left = $1;
                $top = $2;
            }
            
            if ($row =~ /stroke="(.*?)"/i) {
                $stroke = $1;
            }

            if ($row =~ /stroke\-width="(.*?)"/i) {
                $strokeWidth = $1
            }

            if ($row =~ /fill="(.*?)"/i) {
                $fill = ($1 eq "none" ? "transparent" : $1);
            }

            if ($row =~ /stroke\-dasharray="(.*?)"/i) {
                $strokeDashArray = "[";

                for (split(" ", $1)) {
                    $strokeDashArray .= $_ . ","; 
                }

                $strokeDashArray =~ s/,$/]/;
            }

            $outputToConcat = qq|{"type": "keyline", "drawAs": "rect", "fill": "| . ($isBackground ? "dynamic" : $fill) . qq|", "stroke": "$stroke", "strokeWidth": "$strokeWidth", "left": "$left", "top": "$top", "width": "$width", "height": "$height"| . ($strokeDashArray ne "" ? qq|,"strokeDashArray": $strokeDashArray| : "") . qq|},|;
        } elsif ($row =~ /<image\s/i) {
            my $scaleX = "1.0";
            my $scaleY = "1.0";
            my $left = 0;
            my $top = 0;
            my $width = 0;
            my $height = 0;
            my $src = "";

            if ($row =~ /x="(.*?)"/i) {
                $left = $1;
            }

            if ($row =~ /y="(.*?)"/i) {
                $top = $1;
            }
            
            if ($row =~ /width="(.*?)"/i) {
                $width = $1;
            }

            if ($row =~ /height="(.*?)"/i) {
                $height = $1;
            }

            if ($row =~ /transform=".*?translate\((.*?)\).*?"/i) {
                my @arr = split(/ /, $1);
                $left = $arr[0];

                if (defined $arr[1]) {
                    $top = $arr[1];
                } else {
                    $top = $scaleX;
                }
            }

            if ($row =~ /transform=".*?scale\((.*?)\).*?"/i) {
                my @arr = split(/ /, $1);
                $scaleX = $arr[0];

                if (defined $arr[1]) {
                    $scaleY = $arr[1];
                } else {
                    $scaleY = $scaleX;
                }
            }

            if ($row =~ /xlink\:href="(.*?)"/i) {
                $src = $1;
            }

            $outputToConcat = qq|{"type": "keyline", "drawAs": "image", "left": "$left", "top": "$top", "width": "$width", "height": "$height", "scaleX": "$scaleX", "scaleY": "$scaleY", "base64": "$src"},|;
        }
        
        if ($isBackground) {
            $output = $outputToConcat . $output;
        } else {
            $output .= $outputToConcat;
        }

        if ($row =~ /<rect.*?id="background"/i) {
            $isBackground = 0;
        }
    }

    $output = "{&quot;front&quot;:[" . $output;
    $output =~ s/,$/]/;
    #print "\n\n$output\n";

    $output =~ s/"/&quot;/g;
    open (FH2, '>', 'convertedFiles/converted.xml');
    print FH2 qq|\n\n<DesignTemplate location="front" name="Start From Scratch" width="$viewBoxWidth" height="$viewBoxHeight" jsonData="$output,&quot;back&quot;:[]}" active="Y" />\n|;
    close (FH2);
    close $fh;
}