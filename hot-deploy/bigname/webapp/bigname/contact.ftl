<link href="<@ofbizContentUrl>/html/themes/xplus/css/innerpages.css</@ofbizContentUrl>" rel="stylesheet">

<!-- =========== Map Area Start ============ -->
<div class="map_area">
    <div id="map_contact">
        <div id="google_map"></div>
    </div>
</div>
<!-- =========== Map Area End ============ -->


<!-- ============ Contact Area Start =========== -->
<section class="contact_area section-padding" id="contact">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="section_title contact_title">
                    <h3>Feel Free to Contact Us</h3>
                    <p></p>
                </div>
            </div>
            <div class="col-md-7 col-sm-12 col-xs-12">
                <div class="section_common_space">
                    <form id="contactform" method="post" action="https://app.bronto.com/public/webform/process/">
                        <input type="hidden" name="fid" value="327cxe0zgqaia5cambfg10fg9z5fo" />
                        <input type="hidden" name="sid" value="098ed6afc7a08d4419f7e3e9fc94f854" />
                        <input type="hidden" name="delid" value="" />
                        <input type="hidden" name="subid" value="" />
                        <input type="hidden" name="td" value="" />
                        <input type="hidden" name="formtype" value="addcontact" />

                        <div class="jsSubmit_button">
                            <div class="input-field upgraded_field col-md-6 col-sm-6 col-xs-12  col-xxs-12">
                                <input id="contact_name" type="text" class="validate" name="59953[62890]" placeholder="Name*">
                            </div>
                            <div class="input-field upgraded_field col-md-6 col-sm-6 col-xs-12  col-xxs-12">
                                <input id="contact_email" type="email" class="validate" name="59946" placeholder="Email*">
                            </div>
                            <div class="input-field upgraded_field col-md-6 col-sm-6 col-xs-12  col-xxs-12">
                                <input type="tel" class="validate" name="59954[1424735]" id="your_phone" placeholder="Phone*">
                            </div>
                            <div class="input-field upgraded_field col-md-6 col-sm-6 col-xs-12  col-xxs-12">
                                <input id="subject" type="text" class="validate" name="59959[1423822]" placeholder="Subject*">
                            </div>
                            <div class="input-field upgraded_field col-md-12 col-sm-12 col-xs-12  col-xxs-12">
                                <textarea id="textarea1" name="59957[1423818]" class="materialize-textarea massage_textarea" placeholder="Message*"></textarea>
                            </div>

                            <div class="col-md-12 col-xs-12">
                                <button type="submit" id="button-submit" type="button" class="btn xplus-btn-round send-btn-round waves-effect" name="email">Send</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-md-5 col-sm-12 col-xs-12">
                <div class="contact_text">
                    <div class="col-md-12 col-xs-12 col-xxs-12 contact_details">
                        <div class="contact_icon small_box_shadow">
                            <span class="zmdi zmdi-pin"></span>
                        </div>
                        <div class="contact_text">
                            <p>BigName Commerce LLC.<br />105 Maxess Rd.<br />Suite S215<br />Melville, NY, USA</p>
                        </div>
                    </div>
                    <div class="col-md-12 col-xs-12 col-xxs-12 contact_details">
                        <div class="contact_icon small_box_shadow">
                            <span class="zmdi zmdi-phone-forwarded"></span>
                        </div>
                        <div class="contact_text">
                            <p><#--Fax: (631) 225-4896 <br>-->Phone: <br>(877) 683-5673</p>
                        </div>
                    </div>
                    <div class="col-md-12 col-xs-12 col-xxs-12 contact_details">
                        <div class="contact_icon small_box_shadow">
                            <span class="zmdi zmdi-email"></span>
                        </div>
                        <div class="contact_text">
                            <p>info@bigname.com <br> www.bigname.com</p>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</section>
<!-- ============ Contact Area End =========== -->

<script>
    $(function() {
        $('#contactform').on('submit', function(e) {
            alert('Thank you, we will respond to your email shortly.');
            /*e.preventDefault();
            var data = $(this).serialize();
            $.post($(this).attr('action'), data, function() {
                $('#button-submit').html('Thank you!');
                $('#button-submit').attr('disabled','disabled');
            });*/
        });
    });
</script>

<#assign googleMapsKey = Static["org.apache.ofbiz.base.util.UtilProperties"].getPropertyValue("envelopes", "google.web.key") />
<script src="https://maps.googleapis.com/maps/api/js?key=${googleMapsKey}"></script>

<script>
    var myCenter=new google.maps.LatLng(40.704860, -73.397636);
    function initialize() {
        var mapProp = {
            center:myCenter,
            zoom:13,
            scrollwheel: false,
            mapTypeId:google.maps.MapTypeId.ROADMAP,
            styles: [{"featureType":"all","elementType":"labels.text.fill","stylers":[{"saturation":36},{"color":"#333333"},{"lightness":40}]},{"featureType":"all","elementType":"labels.text.stroke","stylers":[{"visibility":"on"},{"color":"#ffffff"},{"lightness":16}]},{"featureType":"all","elementType":"labels.icon","stylers":[{"visibility":"off"}]},{"featureType":"administrative","elementType":"geometry.fill","stylers":[{"color":"#fefefe"},{"lightness":20}]},{"featureType":"administrative","elementType":"geometry.stroke","stylers":[{"color":"#fefefe"},{"lightness":17},{"weight":1.2}]},{"featureType":"administrative.locality","elementType":"labels.text.fill","stylers":[{"color":"#99CA3C"}]},{"featureType":"landscape","elementType":"geometry","stylers":[{"color":"#f5f5f5"},{"lightness":20}]},{"featureType":"poi","elementType":"geometry","stylers":[{"color":"#f5f5f5"},{"lightness":21}]},{"featureType":"poi.park","elementType":"geometry","stylers":[{"color":"#dedede"},{"lightness":21}]},{"featureType":"road.highway","elementType":"geometry.fill","stylers":[{"color":"#ffffff"},{"lightness":17}]},{"featureType":"road.highway","elementType":"geometry.stroke","stylers":[{"color":"#ffffff"},{"lightness":29},{"weight":0.2}]},{"featureType":"road.arterial","elementType":"geometry","stylers":[{"color":"#ffffff"},{"lightness":18}]},{"featureType":"road.local","elementType":"geometry","stylers":[{"color":"#ffffff"},{"lightness":16}]},{"featureType":"transit","elementType":"geometry","stylers":[{"color":"#f2f2f2"},{"lightness":19}]},{"featureType":"water","elementType":"geometry","stylers":[{"color":"#e9e9e9"},{"lightness":17}]}]
        };

        var map=new google.maps.Map(document.getElementById("google_map"),mapProp);

        var marker=new google.maps.Marker({
            position:myCenter,
            animation:google.maps.Animation.BOUNCE,
            icon:'<@ofbizContentUrl>/html/themes/xplus/img/map_marker_green.png</@ofbizContentUrl>'
        });

        var infowindow = new google.maps.InfoWindow({
            content:"united-states"
        });

        marker.setMap(map);
    }

    google.maps.event.addDomListener(window, 'load', initialize);
</script>