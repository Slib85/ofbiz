 <div class="bnRevealHeader fbc-blue">
    <h3>Custom Printing Example</h3>
    <i class="fa fa-times jqs-bnRevealClose"></i>
</div>
<div class="foldersTabularRow marginTop20 productContent">
    <div class="foldersTabularRow width100 specialtyModalLeft" bns-hiddenproductinfo>
        <div bns-assetlist class="sideThumbnail textLeft">
            <div bns-selection selection-autoupdate="false" selection-value="1" selection-name="assetName" class="textCenter marginBottom10 cursorPointer specialtyThumb modalBorder">
                <img bns-productassetfront data-doogma data-doogma-key="productview" data-doogma-value="front" property="image" class="jqs-imagehero padding10" alt="${product.getId()?if_exists}" onerror="this.style.display='none';" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/p_sp-${product.getId()?if_exists}-1?fmt=png-alpha&amp;wid=48" />
            </div>
            <div bns-selection selection-autoupdate="false" selection-value="2" selection-name="assetName" class="textCenter marginBottom10 cursorPointer specialtyThumb">
                <img bns-productassetinside data-doogma data-doogma-key="productview" data-doogma-value="inside" property="image" class="jqs-imagehero padding10" alt="${product.getId()?if_exists}" onerror="this.style.display='none';" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/p_sp-${product.getId()?if_exists}-2?fmt=png-alpha&amp;wid=48" />
            </div>
            <div bns-selection selection-autoupdate="false" selection-value="3" selection-name="assetName" class="textCenter marginBottom10 cursorPointer specialtyThumb">
                <img bns-productassetback data-doogma data-doogma-key="productview" data-doogma-value="back" property="image" class="jqs-imagehero padding10" alt="${product.getId()?if_exists}" onerror="this.style.display='none';" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/p_sp-${product.getId()?if_exists}-3?fmt=png-alpha&amp;wid=48" />
            </div>
            <div bns-selection selection-autoupdate="false" selection-value="4" selection-name="assetName" class="textCenter marginBottom10 cursorPointer specialtyThumb">
                <img bns-productassetback data-doogma data-doogma-key="productview" data-doogma-value="back" property="image" class="jqs-imagehero padding10" alt="${product.getId()?if_exists}" onerror="this.style.display='none';" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()?if_exists}?fmt=png-alpha&amp;wid=48" />
            </div>
        </div>
    </div>
    <div class="textCenter" bns-heroimagecontainer>
        <h3 class="textLeft">Product Preview:</h3>
        <div class="foldersTabularRow productImages">
            <div class="sliderContainer">
                <div id="slider" class="sliderContainer2">
                    <div class="product-images jqs-prodslides noMargin slider">
                        <div class="product-images-single slide">
                            <a>
                                <img style="height: initial; max-height: initial;" data-key="1" property="image" bns-heroimage class="product-slide-image" alt="${product.getId()?if_exists}" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/p_sp-${product.getId()?if_exists}-1?fmt=png-alpha&amp;wid=400&amp;hei=350" />
                            </a>
                        </div>
                    </div>
                </div>
            </div>   
        </div> 
    </div>
</div>
<div class="foldersTabularRow textLeft marginLeft20 marginRight20 marginTop10 marginBottom20 modalProdInfo width94">
    <h4>Customization Information:</h4>
    <p>${product.getDescription()?if_exists}</p>
</div>
<div class="foldersTabularRow textLeft marginLeft20 marginRight20 marginTop10 marginBottom20 width94">
    <div class="referenceNum">
        <h4>Reference #:</h4>
        <p>${product.getId()?if_exists}</p>
    </div>
    <div bns-getQuoteButton class="foldersButton buttonGreen modalButton">
        <a href="#customQuote" class="jqs-bnRevealClose">Get a Quote</a>    
    </div>
</div>

<script>
    $('[bns-selection]').off('click.bnsSelection').on('click.bnsSelection', function() {
        var selectedImg = $(this).attr('selection-value');

        if (selectedImg == '1') {
            $('[bns-heroimage]').attr('src', 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/p_sp-${product.getId()?if_exists}-1?fmt=png-alpha&amp;wid=400&amp;hei=350&amp;ts=2');
            $('[bns-selection]').removeClass('modalBorder');
            $(this).addClass('modalBorder');
        } else if (selectedImg == '2') {
            $('[bns-heroimage]').attr('src', 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/p_sp-${product.getId()?if_exists}-2?fmt=png-alpha&amp;wid=400&amp;hei=350&amp;ts=3');
            $('[bns-selection]').removeClass('modalBorder');
            $(this).addClass('modalBorder');
        } else if (selectedImg == '3') {
            $('[bns-heroimage]').attr('src', 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/p_sp-${product.getId()?if_exists}-3?fmt=png-alpha&amp;wid=400&amp;hei=350&amp;ts=1');
            $('[bns-selection]').removeClass('modalBorder');
            $(this).addClass('modalBorder');
        } else {
            $('[bns-heroimage]').attr('src', 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()?if_exists}?fmt=png-alpha&amp;wid=400&amp;hei=350&amp;ts=1');
            $('[bns-selection]').removeClass('modalBorder');
            $(this).addClass('modalBorder');
        }
    });

     $('.jqs-bnRevealClose').off('click.bnRevealClose').on('click.bnRevealClose', function() {
            bnRevealClose();
        });

     $('[bns-getQuoteButton]').on('click', function(){
        var referenceNumber = $('.referenceNum').find('p').text();

        $('.referenceNumber').find('input').val(referenceNumber);
     });

</script>