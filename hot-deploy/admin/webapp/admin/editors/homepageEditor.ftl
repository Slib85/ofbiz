<link rel="stylesheet" href="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.css">
<style>
	/* Homepage Editor Custom Styles */
	.scrolljack,
	.scrolljack body {
		margin: 0;
		height: 100%;
		overflow: hidden
	}
    .formSection {
        max-width: 300px;
        margin: 10px auto;
    }
    .form-control,
    .input-group {
        max-width: 300px;
    }
	.wrapper {
	  width: 100%;
	  max-width: 1200px;
	  display: inline-block;
	  position: relative;
	}
	.wrapper:after {
	  padding-top: 56.25%;
	  display: block;
	  content: '';
	}
	.mobile-wrapper {
		max-width: 400px;
	}
	.mobile-wrapper:after {
		padding-top: 177.77%;
	}
	.main {
		position: absolute;
		top: 0;
		bottom: 0;
		right: 0;
		left: 0;
		background-image: url(https://scottvariano.github.io/bars.svg);
		background-repeat: no-repeat;
		background-position: center center;
		background-size: 33.75px 35px;
	}
	.preview-frame-fg {
		cursor: wait;
	}
	.preview-frame {
		box-shadow:0 0 20px rgba(0,0,0,.5);
	}
	.preview-frame-col {
		text-align: center;
	}
	.preview-frame-col-fullscreen {
	    background: black;
	    position: fixed;
	    width: 100%;
	    height: 100%;
	    max-height: none !important;
	    z-index: 9999;
	    top: 0;
	    left: 0;
	    right: 0;
	    bottom: 0;
	}
	.preview-frame-col-fullscreen iframe {
		top: 50%;
		transform: translateY(-50%);
	}
	.preview-frame {
		width: 100%;
		border: none;
		overflow: hidden;
		-moz-transform-origin: top left;
		-webkit-transform-origin: top left;
		-o-transform-origin: top left;
		-ms-transform-origin: top left;
		transform-origin: top left;
		pointer-events: none;
	}
	.validity-alert {
	    color: red;
	    font-size: 11px;
	    font-style: italic;
	    display: inline-block;
	    position: absolute;
	}
	.wb-fullscreen {
		display: inline-block;
	    position: absolute;
	    right: 0;
	    bottom: 0;
	    font-size: 24px;
	    color: #FFF;
	    background: #000;
	    width: 40px;
	    height: 35px;
	    transform: translate(-40px, -40px);
	    opacity: 0;
	    transition: opacity .25s ease;
	}
	.preview-frame-col-fullscreen + .wb-fullscreen {
		opacity: 1;
		position: fixed;
		z-index: 9999;
	}
	.preview-frame-col-fullscreen + .wb-fullscreen:before {
		content: "\f11c";
	}
	.preview-frame-col:hover + .wb-fullscreen,
	.wb-fullscreen:hover {
		opacity: 1;
	}
	.wb-fullscreen:hover {
		cursor: pointer;
	}
	#img-validator {
		width: 0;
		height: 0;
		opacity: 0;
	}

</style>

<form action="#" data-abide="" id="homepageImageEditor" method="post" name="homepageImageEditor">
	<div class="panel panel-primary panel-line" data-collapsed="0">
		<!-- panel head -->
		<div class="panel-heading">
			<div class="panel-title">
				Desktop
			</div>
		</div>
		<!-- panel body -->
		<div class="panel-body" style="display: block;">
			<div class="row">
				<div class="col-md-4">
					<div class="row">
						<div class="col-md-12">
							<div class="formSection">
								<div>
									Foreground Image (Required):
								</div><input disabled class="preview-form-fg-desktop form-control jqs-validateExists" name="foregroundImage" placeholder="Foreground Image (Required)" type="text" value="">
							</div>
						</div>
						<div class="col-md-12">
							<div class="formSection">
								<div>
									Background Image (Required):
								</div><input disabled class="preview-form-bg-desktop form-control jqs-validateExists" name="backgroundImage" placeholder="Background Image (Required)" type="text" value="">
							</div>
						</div>
						<div class="col-md-12">
							<div class="formSection">
								<div>
									Foreground Image Format:
								</div><select class="form-control" name="foregroundImageFormat">
									<option value="png-alpha">
										PNG
									</option>
									<option value="gif">
										GIF
									</option>
								</select>
							</div>
						</div>
						<div class="col-md-12">
							<div class="formSection">
								<div>
									Foreground Image Link:
								</div><input class="form-control" name="foregroundImageLink" placeholder="Foreground Image Link" type="text" value="">
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-8 preview-frame-col">
					<div class="wrapper">
						<div class="main"></div>
					</div><iframe class="preview-frame desktop" scrolling="no" src="/envelopes/control/main" style="display:none;"></iframe>
				</div>
				<i class="site-menu-icon wb-fullscreen" aria-hidden="true"></i>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-8">
			<div class="panel panel-primary panel-line" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">
						Mobile
					</div>
				</div>
				<div class="panel-body" style="display: block;">
					<div class="row">
						<div class="col-md-4">
							<div class="row">
								<div class="col-md-12">
									<div class="formSection">
										<div>
											Mobile Image:
										</div><input disabled class="preview-form-fg-mobile form-control preview-form-foreground" name="mobileImage" placeholder="Mobile Image" type="text" value="">
									</div>
								</div>
								<div class="col-md-12">
									<div class="formSection">
										<div>
											Mobile Image Format:
										</div><select class="form-control" name="mobileImageFormat">
											<option value="png-alpha">
												PNG
											</option>
											<option value="gif">
												GIF
											</option>
										</select>
									</div>
								</div>
								<div class="col-md-12">
									<div class="formSection">
										<div>
											Mobile Image Link:
										</div><input class="form-control" name="mobileImageLink" placeholder="Mobile Image Link" type="text" value="">
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-8 preview-frame-col">
							<div class="wrapper mobile-wrapper">
								<div class="main"></div>
							</div><iframe class="preview-frame mobile" scrolling="no" src="//localhost/envelopes/control/main" style="display:none;"></iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-4">
			<div class="panel panel-primary panel-line" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">
						Misc
					</div>
				</div>
				<div class="panel-body" style="display: block;">
					<div class="row">
						<div class="col-md-12">
							<div class="formSection">
								<div>
									Popup Name:
								</div><input class="form-control" name="modalPopup" placeholder="Popup Name" type="text" value="">
							</div>
						</div>
						<div class="col-md-12">
							<div class="formSection">
								<div>
									Image Type:
								</div><select class="form-control" name="imageType">
									<option value="timed">
										Timed
									</option>
									<option value="default">
										Default
									</option>
								</select>
							</div>
						</div>
						<div class="col-md-12 jqs-timedInfo">
							<div class="formSection">
								<div>
									Start Date:
								</div>
								<div class="input-group">
									<input class="form-control jqs-validateExists" data-format="yyyy-mm-dd" data-plugin="datepicker" name="fromDate" type="text"> <span class="input-group-addon"><i aria-hidden="true" class="icon wb-calendar"></i></span>
								</div>
							</div>
						</div>
						<div class="col-md-12 jqs-timedInfo">
							<div class="formSection">
								<div>
									End Date:
								</div>
								<div class="input-group">
									<input class="form-control jqs-validateExists" data-format="yyyy-mm-dd" data-plugin="datepicker" name="thruDate" type="text"> <span class="input-group-addon"><i aria-hidden="true" class="icon wb-calendar"></i></span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="text-center">
		<button class="btn btn-green btn-icon btn-lg icon-right jqs-submit" id="submitCreatePromo" type="submit"><span>ADD</span> <i class="entypo-check"></i></button>
	</div>
</form>

<script src="/html/js/admin/homepageEditor.js"></script>
<script src="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.js"></script>
<script src="/html/themes/global/js/Plugin/bootstrap-datepicker.js"></script>
