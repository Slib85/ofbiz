<link href="<@ofbizContentUrl>/html/css/folders/search/searchResults.css</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/folders/category/category.css</@ofbizContentUrl>" rel="stylesheet" />
<style>
    .category .resultsSet a {
        height: 325px;
    }
</style>
<input name="quotableSearch" type="text" value="" placeholder="Begin Search Here" style="background-color: #ffffff; font-size: 20px; color: #000000; padding: 20px" />
<div class="category searchResults">
    <div bns-resultsSet class="resultsSet">
        <div class="foldersFlexRow flexLeft marginTop10">
            <#list quotableItems.keySet() as key><a href="/folders/control/product/~product_id=${quotableItems.get(key).Sku}">
                <div>
                    <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${quotableItems.get(key).Sku}?hei=170&amp;wid=190" alt="Extra Capacity Folder - Expansion Pocket">
                </div>
                <h2 bns-title class="ftc-blue marginTop5">${quotableItems.get(key).Title}</h2>
                <h4 class="searchResultsSku">SKU: <span bns-sku>${quotableItems.get(key).Sku}</span></h4>
            </a></#list>
        </div>
    </div>
</div>

<script type="text/javascript">
    $('input[name="quotableSearch"]').on('input', function() {
        var inputValue = $(this).val().replace(/^\s+/, '').replace(/\s+$/, '');

        $('[bns-resultsSet] > * > *').removeClass('hidden');
        
        if (inputValue != '') {
            $('[bns-resultsSet] > * > *').each(function() { 
                var re = new RegExp(inputValue, 'gi');
                if (!$(this).find('[bns-title]').html().match(re) && !$(this).find('[bns-sku]').html().match(re)) {
                    $(this).addClass('hidden');
                }
            });
        }
    });
</script>