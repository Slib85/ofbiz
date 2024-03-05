<script type='text/javascript' src="<@ofbizContentUrl>/html/js/util/slideIt.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/util/bigNameReveal.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/util/bigNameValidation.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/folders/bottom.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
${screens.render("component://folders/widget/CommonScreens.xml#analyticsBottom")}

<!-- Start of Async Drift Code -->
<style>
    iframe#drift-widget.drift-widget-welcome-online,
    iframe#drift-widget.drift-widget-welcome-away,
    iframe#drift-widget.drift-widget-welcome-expanded-away,
    iframe#drift-widget.drift-widget-welcome-expanded-online,
    iframe#drift-widget.drift-widget-slider {
        left: 24px !important;
        right: unset !important;
    }
    iframe#drift-widget.drift-widget-sidebar {
        left: 0px !important;
        right: unset !important;
    }
    New left-align approach:
    iframe#drift-widget.drift-widget-welcome-online,
    iframe#drift-widget.drift-widget-welcome-away,
    iframe#drift-widget.drift-widget-slider {
        left: 24px !important;
        right: unset !important;
    }
    iframe#drift-widget.drift-widget-sidebar,
    iframe#drift-widget.drift-widget-welcome-expanded-away,
    iframe#drift-widget.drift-widget-welcome-expanded-online,
    iframe#drift-widget.drift-widget-consent-large-takeover,
    iframe#drift-widget.drift-widget-email-capture,
    iframe#drift-widget.drift-widget-activation-takeover {
        left: 0px !important;
        right: unset !important;
    }
    iframe#drift-widget.drift-widget-nps-preview {
        width: 350px !important;
    }
</style>
<!-- End of Async Drift Code -->