<form name="productUpdate" method="POST" action="#" role="form" class="clearfix form-horizontal form-groups-bordered">
    <div class="panel panel-primary" data-collapsed="0">
        <!-- panel head -->
        <div class="panel-heading">
            <div class="panel-title">Home Page Banner</div>
        </div>

        <!-- panel body -->
        <div class="panel-body">
            <div class="row">
                <div class="col-md-2 center">
                    <img class="img-responsive center-block" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/p_hp-clearance?wid=1024&amp;fmt=png-alpha" alt="Banner" />
                </div>
                <div class="col-md-10">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Image Name</label>
                                <div class="col-sm-9">
                                    <input name="imageName" type="text" class="form-control" placeholder="Image Name" value="p_hp_clearance">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Landing URL</label>
                                <div class="col-sm-9">
                                    <input name="LandingUrl" type="text" class="form-control" placeholder="Landing URL" value="/clearance">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Background Image</label>
                                <div class="col-sm-9">
                                    <input name="backgroundImage" type="text" class="form-control" placeholder="Background Image Name" value="">
                                    <div class="col-sm-6 text-center">
                                        <label class="control-label">Repeat X</label>
                                        <br />
                                        <div class="make-switch switch-mini">
                                            <input name="repeatX" type="checkbox" value="Y">
                                        </div>
                                    </div>
                                    <div class="col-sm-6 text-center">
                                        <label class="control-label">Repeat Y</label>
                                        <br />
                                        <div class="make-switch switch-mini">
                                            <input name="repeatY" type="checkbox" value="Y">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Mobile Image</label>
                                <div class="col-sm-9">
                                    <input name="mobileImage" type="text" class="form-control" placeholder="Mobile Image Name" value="">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row text-right">
        <div class="col-md-12 center">
            <button type="submit" class="btn btn-green btn-icon btn-lg icon-right">
                SAVE
                <i class="entypo-check"></i>
            </button>
        </div>
    </div>
</form>

<script>
    $('input[name=hasBackgroundImage]').click(function() {
        if( $(this).is(':checked')) {
            $(this).val('Y');
            $('.hasBackgroundImage').show();
        } else {
            $(this).val('N');
            $('.hasBackgroundImage').hide();
        }
    });
</script>