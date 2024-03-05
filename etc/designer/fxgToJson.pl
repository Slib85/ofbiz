#!/usr/bin/perl -w
use strict;
use warnings;
use LWP::UserAgent;
use POSIX;
use Data::Dumper qw(Dumper);

my $lwp = LWP::UserAgent->new(agent=>' Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0', cookie_jar=>{});

my $filename = 'C:/dev/perlScripts/fxgToJson/designs.txt';
open(my $fh, '<:encoding(UTF-8)', $filename) or die "Could not open file '$filename' $!";
open(my $fh2, '>', 'C:/dev/perlScripts/fxgToJson/insert.txt');

my @completedPlainFrontBackDesigns = ();

while (my $legacyDesignId = <$fh>) {
    chomp $legacyDesignId;
    print "\n\nWORKING ON ID $legacyDesignId\n";
    
    my $location = "front";
    if ($legacyDesignId =~ m/b$/i) {
        $location = "back";
    }
    my $jsonData = "";
    my $designWidth = 0;
    my $designHeight = 0;
    my @keylineCoordinatesList = ();
    my %nonKeylineObjectData = ();
    my $plainFrontJson = "";
    my $plainBackJson = "";
    

    my $url = "https://texel.envelopes.com/getFXG?id=$legacyDesignId";
    my $response = $lwp->get($url);
    
    die "Can't get $url -- ", $response->status_line unless $response->is_success;

    my $responseContent = $response->content;
    my @xmlRows = split(/(?:\n|\r)/, $responseContent);

    my $concatRow = 0;
    my $xmlRow;
    foreach my $row (@xmlRows) {
        chomp $row;

        if ($concatRow) {
            $xmlRow .= $row;
        } else {
            $xmlRow = $row;
        }
        
        if (!($xmlRow =~ m/(?:^\s+|^)\<.*?>(?:\s+$|$)/i)) {
            $concatRow = 1;
            next;
        } else {
            $concatRow = 0;
        }

        if ($xmlRow =~ m/\<group.*?(?:d\:pageheight.*?d\:pagewidth|d\:pagewidth.*?d\:pageheight)/i) {
            if ($xmlRow =~ m/^.*?d\:pagewidth=\"(.*?)\"/i) {
                $designWidth = $1;
            }

            if ($xmlRow =~ m/^.*?d\:pageheight=\"(.*?)\"/i) {
                $designHeight = $1;
            }
        } elsif ($xmlRow =~ m/(\<group.*?d\:type\=\"layer\".*?\>)/i) {
            my $groupContent = $1;

            if (!($groupContent =~ m/d:userlabel=\"page\"/i)) {
                my $capturedContent = $responseContent;
                $capturedContent =~ s/\n/magikMikeXXL/g;
                if ($capturedContent =~ m/($groupContent.*?<\/group\>)/i) {
                    $capturedContent = $1;
                    $capturedContent =~ s/magikMikeXXL/\n/g;
                    my @xmlRows = split(/(?:\n|\r)/, $capturedContent);

                    my $concatRow = 0;
                    my $xmlRow;
                    my $ignoreNextPath = 0;
                    foreach my $row (@xmlRows) {
                        if ($concatRow) {
                            $xmlRow .= $row;
                        } else {
                            $xmlRow = $row;
                        }
                        
                        if (!($xmlRow =~ m/(?:^\s+|^)\<.*?>(?:\s+$|$)/i)) {
                            $concatRow = 1;
                            next;
                        } else {
                            $concatRow = 0;
                        }
                        
                        if ($xmlRow =~ m/(\<group.*?userlabel="stamp.*?>)/i) {
                            $ignoreNextPath = 1;
                            next;
                        }
                        
                        if ($xmlRow =~ m/(\<path.*?\>)/i && $ignoreNextPath == 0) {
                            my $capturedContent = $1;

                            my $xCoordinate = 0;
                            my $yCoordinate = 0;
                            my $keylineCoordinates;
                            my $fillColor = "transparent";

                            $capturedContent =~ s/(?:\n|\r)//g;
                            
                            if ($capturedContent =~ m/color_bgcolor/i) {
                                $fillColor = "dynamic";
                            }

                            if ($xmlRow =~ m/^.*?\sx=\"(.*?)\"/i) {
                                $xCoordinate = $1;
                            }
                            
                            if ($xmlRow =~ m/^.*?\sy=\"(.*?)\"/i) {
                                $yCoordinate = $1;
                            }

                            if ($xmlRow =~ m/^.*?data=\"(.*?)\"/i) {
                                $keylineCoordinates = $1;
                                #$keylineCoordinates =~ s/[A-Za-z]/ /g;
                                #print $keylineCoordinates."\n\n";
                            }

                            $jsonData .= ($jsonData eq "" ? "" : ",") . qq|{\n\t"type": "keyline",\n\t"fill": "$fillColor",\n\t"left": $xCoordinate,\n\t"top": $yCoordinate,\n\t"coordinates": "$keylineCoordinates"\n}|;
                            if ($location eq "front") {
                                $plainFrontJson = $jsonData;
                            } else {
                                $plainBackJson = $jsonData;
                            }
                            
                            #if (defined $xCoordinate && defined $yCoordinate && defined $keylineCoordinates) {
                            #    push(@keylineCoordinatesList, { 
                            #        'xCoordinate' => $xCoordinate, 
                            #        'yCoordinate' => $yCoordinate, 
                            #        'keylineCoordinates' => $keylineCoordinates 
                            #    });
                            #}
                        }

                        if ($ignoreNextPath == 1) {
                            $ignoreNextPath = 0;
                        }
                    }
                }
            }
        } elsif ($xmlRow =~ m/(<richtext.*?s7\:elementid=\"(text_(?:(?:return|reply|flap).*?no_logo.*?|\d+)\"))/i) {
            my $labelContent = $1;
            my $key = "";

            if ($labelContent =~ m/(text_return)/i) {
                $key = "Return Address";
            } elsif ($labelContent =~ m/(text_reply)/i) {
                $key = "Self Addressed (Reply)";
            } elsif ($labelContent =~ m/(text_flap)/i) {
                $key = "Back Flap Address";
            }

            my $capturedContent = $responseContent;
            $capturedContent =~ s/\n//g;

            if ($capturedContent =~ m/($labelContent.*?<\/richtext\>)/i) {
                $capturedContent = $1;
                $nonKeylineObjectData{$key} = qq|{\n\t"type":"textBox"|;
                my $text = "";
                my @capturedContentRows = split(/(?:\n|\r)/, $capturedContent);
                my $lineHeight = 1;
                my $fontSize = 12;
                
                foreach my $capturedContentRow (@capturedContentRows) {
                    if ($capturedContentRow =~ m/<richtext.*?\sx=\"(.*?)\"/i) {
                        $nonKeylineObjectData{$key} .= qq|,\n\t"xPercent": | . sprintf("%.7f", ($1 / $designWidth) * 100);
                    } 
                    
                    if ($capturedContentRow =~ m/<richtext.*?\sy=\"(.*?)\"/i) {
                        $nonKeylineObjectData{$key} .= qq|,\n\t"yPercent": | . sprintf("%.7f", ($1 / $designHeight) * 100);
                    }

                    if ($capturedContentRow =~ m/<richtext.*?\sfontfamily=\"(.*?)\"/i) {
                        $nonKeylineObjectData{$key} .= qq|,\n\t"fontFamily": "$1"|;
                    }

                    if ($capturedContentRow =~ m/<richtext.*?\ss7\:maxfontsize=\"(.*?)\"/i) {
                        $fontSize = $1;
                    }
                    
                    if ($capturedContentRow =~ m/<richtext.*?\slineheight=\"(.*?)\"/i) {
                        $lineHeight = $1
                    }
                    
                    if ($capturedContentRow =~ m/<richtext.*?\swidth=\"(.*?)\"/i) {
                        $nonKeylineObjectData{$key} .= qq|,\n\t"widthPercent": | . sprintf("%.7f", ($1 / $designWidth) * 100);
                    }

                    if ($capturedContentRow =~ m/<richtext.*?\srotation=\"(.*?)\"/i) {
                        $nonKeylineObjectData{$key} .= qq|,\n\t"angle": $1|;
                    }

                    if ($capturedContentRow =~ m/<richtext.*?\stextalign=\"(.*?)\"/i) {
                        $nonKeylineObjectData{$key} .= qq|,\n\t"textAlign": "$1"|;
                    }
                    
                    if ($capturedContentRow =~ m/<content.*?>(.*?)<\/content>/i) {
                        my $text = $1;
                        $text =~ s/<\/span.*?<span.*?>/\\n/g;
                        $text =~ s/(?:<p.*?<span.*?>|<\/span.*?$)//g;
                        $nonKeylineObjectData{$key} .= qq|,\n\t"text": "$text"|;
                    }
                }

                
                $nonKeylineObjectData{$key} .= qq|,\n\t"fontSize": $fontSize|;
                $nonKeylineObjectData{$key} .= qq|,\n\t"lineHeight": | . ($lineHeight / $fontSize);

                $nonKeylineObjectData{$key} .= qq|\n}|;
            }
        }
    }
    

    # Do keylines
    if ($designWidth gt 0 && $designHeight gt 0) {
        foreach my $keylineCoordinates (@keylineCoordinatesList) {
            my $calculatedXPercent = sprintf("%.7f", ($keylineCoordinates->{"xCoordinate"} / $designWidth) * 100);
            my $calculatedYPercent = sprintf("%.7f", ($keylineCoordinates->{"yCoordinate"} / $designHeight) * 100);
            my $lineCoordinates = "[";

            my @splitLineCoordinates = split(" ", $keylineCoordinates->{"lineCoordinates"});
            my $loopCounter = 0;

            for (my $i = 0; $i < scalar @splitLineCoordinates; $i++) {
                $loopCounter++;

                if ($loopCounter % 2 == 1) {
                    $lineCoordinates .= ($lineCoordinates eq "[" ? "" : ", ") . sprintf("%.7f", (($keylineCoordinates->{"xCoordinate"} + $splitLineCoordinates[$i]) /# $designWidth) * 100);
                } else {
                    $lineCoordinates .= ($lineCoordinates eq "[" ? "" : ", ") . sprintf("%.7f", (($keylineCoordinates->{"yCoordinate"} + $splitLineCoordinates[$i]) /# $designHeight) * 100);
                }

                if ($loopCounter % 4 == 0) {
                    $lineCoordinates .= "]";

                    $jsonData .= ($jsonData eq "" ? "" : ",") . qq|{\n\t"type": "keyline",\n\t"coordinates": $lineCoordinates\n}|;

                    if ($location eq "front") {
                        $plainFrontJson = $jsonData;
                    } else {
                        $plainBackJson = $jsonData;
                    }
                    
                    $lineCoordinates = "[";
                    if ($loopCounter != scalar @splitLineCoordinates) {
                        $i = $i - 2;
                    }
                    $loopCounter = 0;
                }
            }
        }
    }

    my $parentLegacyDesignId = $legacyDesignId;
    $parentLegacyDesignId =~ s/(^.*?).$/$1/;
    

    if (1 == 1) {
        my $designWidth;
        my $designHeight;
        my @keylineCoordinatesList;
        my $ending = ($location eq "front" ? "B" : "A");
        my $response = $lwp->get("https://texel.envelopes.com/getFXG?id=$parentLegacyDesignId$ending");

        if (!$response->is_success || $response->content =~ m/no such file or directory/i) {
            $ending = ($location eq "front" ? "b" : "a");
            $response = $lwp->get("https://texel.envelopes.com/getFXG?id=$parentLegacyDesignId$ending");
        }
        
        $parentLegacyDesignId .= $ending;
        
        my $responseContent = $response->content;
        my @xmlRows = split(/(?:\n|\r)/, $responseContent);

        my $concatRow = 0;
        my $xmlRow;
        foreach my $row (@xmlRows) {
            chomp $row;

            if ($concatRow) {
                $xmlRow .= $row;
            } else {
                $xmlRow = $row;
            }
            
            if (!($xmlRow =~ m/(?:^\s+|^)\<.*?>(?:\s+$|$)/i)) {
                $concatRow = 1;
                next;
            } else {
                $concatRow = 0;
            }

            if ($xmlRow =~ m/\<group.*?(?:d\:pageheight.*?d\:pagewidth|d\:pagewidth.*?d\:pageheight)/i) {
                if ($xmlRow =~ m/^.*?d\:pagewidth=\"(.*?)\"/i) {
                    $designWidth = $1;
                }

                if ($xmlRow =~ m/^.*?d\:pageheight=\"(.*?)\"/i) {
                    $designHeight = $1;
                }
            } elsif ($xmlRow =~ m/(\<group.*?d\:type\=\"layer\".*?\>)/i) {
                my $groupContent = $1;
                
                if (!($groupContent =~ m/d:userlabel=\"page\"/i)) {
                    my $capturedContent = $responseContent;
                    $capturedContent =~ s/\n/magikMikeXXL/g;
                    if ($capturedContent =~ m/($groupContent.*?<\/group\>)/i) {
                        $capturedContent = $1;
                        $capturedContent =~ s/magikMikeXXL/\n/g;

                        my @xmlRows = split(/(?:\n|\r)/, $capturedContent);
                        my $concatRow = 0;
                        my $xmlRow;
                        my $ignoreNextPath = 0;
                        foreach my $row (@xmlRows) {
                            
                            if ($concatRow) {
                                $xmlRow .= $row;
                            } else {
                                $xmlRow = $row;
                            }
                            
                            if (!($xmlRow =~ m/(?:^\s+|^)\<.*?>(?:\s+$|$)/i)) {
                                $concatRow = 1;
                                next;
                            } else {
                                $concatRow = 0;
                            }

                            if ($xmlRow =~ m/(\<group.*?userlabel="stamp.*?>)/i) {
                                $ignoreNextPath = 1;
                                next;
                            }
                            
                            if ($xmlRow =~ m/(\<path.*?\>)/i && $ignoreNextPath == 0) {
                                my $xCoordinate = 0;
                                my $yCoordinate = 0;
                                my $keylineCoordinates;
                                my $fillColor = "transparent";

                                if ($xmlRow =~ m/color_bgcolor/i) {
                                    $fillColor = "dynamic";
                                }

                                if ($xmlRow =~ m/^.*?\sx=\"(.*?)\"/i) {
                                    $xCoordinate = $1;
                                }
                                
                                if ($xmlRow =~ m/^.*?\sy=\"(.*?)\"/i) {
                                    $yCoordinate = $1;
                                }

                                if ($xmlRow =~ m/^.*?data=\"(.*?)\"/i) {
                                    $keylineCoordinates = $1;
                                }

                                my $data = qq|{\n\t"type": "keyline",\n\t"fill": "$fillColor",\n\t"left": $xCoordinate,\n\t"top": $yCoordinate,\n\t"coordinates": "$keylineCoordinates"\n}|;

                                if ($location eq "front") {
                                    $plainBackJson .= ($plainBackJson eq "" ? "" : ",") . $data;
                                } else {
                                    $plainFrontJson .= ($plainFrontJson eq "" ? "" : ",") . $data;
                                }
                            }
                            
                            if ($ignoreNextPath == 1) {
                                $ignoreNextPath = 0;
                            }
                        }
                    }
                }
            }
        }

        # Do keylines

        if ($designWidth gt 0 && $designHeight gt 0) {
            foreach my $keylineCoordinates (@keylineCoordinatesList) {
                my $calculatedXPercent = sprintf("%.7f", ($keylineCoordinates->{"xCoordinate"} / $designWidth) * 100);
                my $calculatedYPercent = sprintf("%.7f", ($keylineCoordinates->{"yCoordinate"} / $designHeight) * 100);
                my $lineCoordinates = "[";

                my @splitLineCoordinates = split(" ", $keylineCoordinates->{"lineCoordinates"});
                my $loopCounter = 0;

                for (my $i = 0; $i < scalar @splitLineCoordinates; $i++) {
                    $loopCounter++;

                    if ($loopCounter % 2 == 1) {
                        $lineCoordinates .= ($lineCoordinates eq "[" ? "" : ", ") . sprintf("%.7f", (($keylineCoordinates->{"xCoordinate"} + $splitLineCoordinates [$i]) / $designWidth) * 100);
                    } else {
                        $lineCoordinates .= ($lineCoordinates eq "[" ? "" : ", ") . sprintf("%.7f", (($keylineCoordinates->{"yCoordinate"} + $splitLineCoordinates [$i]) / $designHeight) * 100);
                    }

                    if ($loopCounter % 4 == 0) {
                        $lineCoordinates .= "]";
                        if ($location eq "front") {
                            $plainBackJson .= ($plainBackJson eq "" ? "" : ",") . qq|{\n\t"type": "keyline",\n\t"coordinates": lineCoordinates\n}|;
                        } else {
                            $plainFrontJson .= ($plainFrontJson eq "" ? "" : ",") . qq|{\n\t"type": "keyline",n\t"coordinates": $lineCoordinates\n}|;
                        }

                        $lineCoordinates = "[";
                        if ($loopCounter != scalar @splitLineCoordinates) {
                            $i = $i - 2;
                        }
                        $loopCounter = 0;
                    }
                }
            }
        }
    }


    $plainFrontJson =~ s/"/&quot;/g;
    $plainBackJson =~ s/"/&quot;/g;
    
    foreach my $key (keys %nonKeylineObjectData) {

        print $fh2 qq|<DesignTemplate 
            legacyDesignId="$legacyDesignId" 
            location="$location" 
            name="$key" 
            width="| . ($designWidth / 72) . qq|" 
            height="| . ($designHeight / 72) . qq|" 
            jsonData="| . ($location eq "front" ? qq|{&quot;front&quot;: [$jsonData| . ($jsonData eq "" ? "" : ",") . qq|$nonKeylineObjectData{$key}], &quot;back&quot;: [$plainBackJson]}| : qq|{&quot;front&quot;: [$plainFrontJson], &quot;back&quot;: [$jsonData,$nonKeylineObjectData{$key}]}|) . qq|" 
            fxgData="$responseContent" 
            active="Y" />\n\n\n\n\n\n\n\n|;
    }

    if (1 == 1) {
        my %params = map { $_ => 1 } @completedPlainFrontBackDesigns;
        my $parentLegacyDesignId = $legacyDesignId;
        
        if(!exists($params{$parentLegacyDesignId})) {
            print $fh2 qq|
                <DesignTemplate 
                    legacyDesignId="$parentLegacyDesignId| . qq|A" 
                    location="front" 
                    name="Start From Scratch" 
                    width="| . ($designWidth / 72) . qq|" 
                    height="| . ($designHeight / 72) . qq|" 
                    jsonData="{&quot;front&quot;: [$plainFrontJson], &quot;back&quot;: [$plainBackJson]}" 
                    fxgData="$responseContent" 
                    active="Y" />\n\n\n\n\n\n\n\n|; 
            push(@completedPlainFrontBackDesigns, $parentLegacyDesignId);
        }
    }
    #print $plainBackJson . "\n\n\n" . $plainFrontJson;
}
close $fh2;

print "Completed.";