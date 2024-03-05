/**
 * Created by Manu on 8/14/2014.
 */

$.widget("envelopes.OrbitWidget", {
    options: {
        productId: [],
        slideSetsJSON: [],
        standaloneSlideSetsJSON: [],
        slideSetType: 'envelope',
        linkSlideSets: [false],
        headerHtml: '<div class="slider-header row edit-header no-padding">' +
                        '<div class="large-3 columns">' +
                            '<div class="button text-center no-margin button-previous">' +
                                '<i class="fa fa-chevron-left left"></i><span>Previous</span>' +
                            '</div>' +
                        '</div>' +
                        '<div class="large-6 columns text-center position-guide"></div>' +
                        '<div class="large-3 columns">' +
                            '<div class="button text-center no-margin right button-next">' +
                                '<span>Next</span><i class="fa fa-chevron-right right"></i>' +
                            '</div>' +
                        '</div>' +
                    '</div>',

        bulletHtml: '<i class="fa fa-circle bullet"></i>',
        onSlideSetChange: function() {},
        validateSlideChange: function() {return true;},
        onSlideChange: function() {},
        onSlideLoad: function() {},
        isLastSlide: function() {},
        strictLastSlide: false  /* If this is set to true, then isLastSlide() will return true if the current slide is the last slide.
                                   If this is set to false (default), then the isLastSlide() will return this.__lastSlideReached.
                                   This is for handling the option that users going back to previous slides after reaching the last slide. */

    },
    /*_slideSet: [[
        {chooseDesign: {url: '/envelopes/control/chooseDesignStep', index: 0, active: true, data: {designMethod: 'online-design|file-upload', shippingMethod: 'ground|two-day|one-day'}}},
        {printSettings: {url: '/envelopes/control/printSettingsStep', index: 1, active: true, data: {inkColorsFront: 'no-color|one-color|two-color|full-color', whiteInkFront: false, inkColorsBack: 'no-color|one-color|two-color|full-color', whiteInkBack: false, fullBleed:false}}},
        {envelopeAddressing: {url: '/envelopes/control/envelopeAddressingStep', index: 2, active: true, data: {printAddress: false}}},
        {fileUpload: {url: '/envelopes/control/fileUploadStep', index: 3, active: true, data: {files: ['Awesome_Layout.pdf', 'A_second_file.doc'], name: 'Name', email: 'Email', phone: 'Phone', comments: 'Comments'}}},
        {productionTime: {url: '/envelopes/control/productionTimeStep', index: 4, active: true, data: {production: 'standard'}}}
    ]],*/
    _slideSetsURLs: [],
    _standaloneSlideSetsURLs: [],
    _slideSetsSequenceLookup: {},
    _slideSetsURLsActiveStatus: [],
    _slideSetIdx: 0,
    _slideIdx: [0],
    _numOfSlideSets: 0,
    _numOfStandaloneSlideSets: 0,
    _numOfSlides: [0],
    _orbitSession: {},
    _lastSlideReached: false,
    _inValidationMode: false,
    _linkSlideSets: [],
    _create: function() {
        this._setUpSlideSets();
        this._markup();
        this._updateHeader();
        $(document).foundation({
            orbit: {
                navigation_arrows: true,
                slide_number: false,
                timer: false,
                next_on_click: false,
                bullets: false,
                swipe: false
            }
        });
    },
    _setUpStandaloneSlideSets: function() {
        var standaloneSlideSetsJSON = this.options.standaloneSlideSetsJSON;
        for(var i = 0; i < standaloneSlideSetsJSON.length; i ++) {
            var standaloneSlideSetJSON = standaloneSlideSetsJSON[0];
            var standaloneSlideSetURLs = [];
            var idx = 0;
            for(var prop in standaloneSlideSetJSON) {
                if(standaloneSlideSetJSON.hasOwnProperty(prop)){
                    standaloneSlideSetURLs[idx] = standaloneSlideSetJSON[prop];
                    idx ++;
                }
            }
            this._standaloneSlideSetsURLs[i] = standaloneSlideSetURLs;
        }
    },

    _setUpSlideSets: function() {
        var slideSetsJSON = this.options.slideSetsJSON;
        var linkSlideSets = this.options.linkSlideSets;
        for(var i = 0; i < slideSetsJSON.length; i ++) {
            var slideSetJSON = slideSetsJSON[i];
            var slideSetURLs = [];
            var slideSetURLsActiveStatus = {};
            var slideSetSequenceLookup = {};
            var idx = 0;
            for(var prop in slideSetJSON) {
                if(slideSetJSON.hasOwnProperty(prop)){
                    slideSetURLs[idx] = slideSetJSON[prop];
                    slideSetSequenceLookup[idx] = prop;
                    slideSetURLsActiveStatus[prop] = true;
                    idx ++;
                }
            }
            this._slideSetsURLs[i] = slideSetURLs;
            this._slideSetsURLsActiveStatus[i] = slideSetURLsActiveStatus;
            this._slideSetsSequenceLookup[i] = slideSetSequenceLookup;
            this._numOfSlides[i] = slideSetURLs.length;
            this._numOfSlideSets ++;
            this._slideIdx[i] = 0;
            if(linkSlideSets.length > 0) {
                this._linkSlideSets[i] = linkSlideSets.length - 2 <= i ? linkSlideSets[i] : false; // We won't allow cyclic linking of slide sets.
            }
        }
    },

    // Method added for testing, should be disabled on production
    invoke: function(arg) {
        return eval('this.' + arg);
    },

    changeSlideSet: function(idx) {
        this._setSlideSetIdx(idx);
    },

    incrementSlideSetIdx: function() {
        this._changeSlideSetIdx(1);
    },

    decrementSlideSetIdx: function() {
        this._changeSlideSetIdx(-1);
    },

    activeSlideSetIdx: function() {
        return this._slideSetIdx;
    },

    disableSlide: function(name, slideName, slideSetIdx) {

        if(this._slideSetsURLsActiveStatus[this._validatedSlideSetIdx(slideSetIdx)][slideName] == true && this._slideSetsURLsActiveStatus[this._validatedSlideSetIdx(slideSetIdx)].hasOwnProperty(name)) {
            this._slideSetsURLsActiveStatus[this._validatedSlideSetIdx(slideSetIdx)][name] = false;
        }
        this._updateHeader();

    },
    lastSlideReached: function(value) {
        if(value === undefined) {
            return this._lastSlideReached;
        }
        this._lastSlideReached = value;
        this._trigger('isLastSlide', null, {slideSetType: this.options.slideSetType, currentSlideSetIdx: this._slideSetIdx, isLast: this.options.strictLastSlide ? !this._hasNext() : this._lastSlideReached});
    },

    enableSlide: function(name, slideName, slideSetIdx) {
        if(this._slideSetsURLsActiveStatus[this._validatedSlideSetIdx(slideSetIdx)][slideName] == true && this._slideSetsURLsActiveStatus[this._validatedSlideSetIdx(slideSetIdx)].hasOwnProperty(name)) {
            this._slideSetsURLsActiveStatus[this._validatedSlideSetIdx(slideSetIdx)][name] = true;
        }
        this._updateHeader();
    },

    openStandaloneSlide: function(name, ignoreSlideSetIdx) {
        $('.slider-header').addClass('invisible');
        if(ignoreSlideSetIdx === undefined) {
            $('.' + name + this._slideSetIdx).trigger('click');
        } else if(ignoreSlideSetIdx == true) {
            $('.' + name).trigger('click');
        }
    },

    closeStandaloneSlide: function() {
        this._updateSlideSet();
        $('.slider-header').removeClass('invisible');

    },

    isLastSlide: function() {
        return this._hasNext();
    },

    goToSlide: function(slideIdx, slideSetIdx) {
        slideIdx = this._changeSlideIdx(0, slideIdx);
        slideSetIdx = slideSetIdx === undefined ? this._slideSetIdx : slideSetIdx;
        if(slideIdx > -1) {
            $('.slide' + slideIdx + '' + slideSetIdx).trigger('click')
        }
    },

    _getSlideName: function(index) {
        return this._slideSetsSequenceLookup[this._slideSetIdx][index];
    },

    _currentSlideIdx: function(fullIndexFlag) {
        return fullIndexFlag ? this._getFullSlideIdx() : this._getSlideIdx();
    },

    _nextSlideIdx: function(fullIndexFlag) {
        return fullIndexFlag ? this._getFullSlideIdx(1) : this._getSlideIdx(1);
    },

    _previousSlideIdx: function(fullIndexFlag) {
        return fullIndexFlag ? this._getFullSlideIdx(-1) : this._getSlideIdx(-1);
    },

    _numberOfSlides: function() {
        return this._numOfSlides[this._slideSetIdx];
    },

    _validatedSlideSetIdx: function(slideSetIdx) {
        if(slideSetIdx === undefined || typeof parseInt(slideSetIdx) != 'number') {
            return this._slideSetIdx;
        } else {
            if(slideSetIdx >= 0 && slideSetIdx < this._numOfSlideSets) {
                return slideSetIdx;
            } else {
                return this._slideSetIdx;
            }
        }
    },

    _incrementSlideIdx: function() {
        this._changeSlideIdx(1);
    },

    _decrementSlideIdx: function() {
        this._changeSlideIdx(-1);
    },

    _changeSlideSetIdx: function(offSet) {
        if(!offSet) {
            offSet = 0;
        }
        if(offSet !== undefined && this._slideSetIdx + offSet >= 0 && this._slideSetIdx + offSet < this._numOfSlideSets) {
            var previousSlideSetIdx = this._slideSetIdx;
            this._slideSetIdx += offSet;
            this._updateSlideSet();
            this._trigger('onSlideSetChange', null, {slideSetType: this.options.slideSetType, currentSlideSetIdx: this._slideSetIdx, previousSlideSetIdx: previousSlideSetIdx});
        }
    },

    _setSlideSetIdx: function(idx) {
        if(idx !== undefined && idx >= 0 && idx < this._numOfSlideSets && idx != this._slideSetIdx) {
            var previousSlideSetIdx = this._slideSetIdx;
            this._slideSetIdx = idx;
            this._updateSlideSet();
            this._trigger('onSlideSetChange', null, {slideSetType: this.options.slideSetType, currentSlideSetIdx: this._slideSetIdx, previousSlideSetIdx: previousSlideSetIdx});
        }
    },

    _changeSlideIdx: function(offSet, slideIdx) {
        if(!offSet) {
            offSet = 0;
        }
        slideIdx = slideIdx !== undefined ? slideIdx : this._validateSlideIdx(this._slideIdx[this._slideSetIdx] + offSet, offSet);
        if(slideIdx > -1) {
            var currentSlideIdx = this._currentSlideIdx();
            var isValid = true;
            if(this._inValidationMode != true) {
                this._inValidationMode = true;
                isValid = this._trigger('validateSlideChange', null, {slideSetType: this.options.slideSetType, currentSlideSetIdx: this._slideSetIdx, flowDirection: offSet > 0 ? 'next' : offSet < 0 ? 'previous' : 'none', currentSlideIdx: currentSlideIdx, currentSlideName: this._getSlideName(currentSlideIdx), nextSlideIdx: slideIdx, nextSlideName: this._getSlideName(slideIdx)});
                this._inValidationMode = false;
            }
            if(isValid) {
                this._slideIdx[this._slideSetIdx] = slideIdx;
                if (!this._lastSlideReached && !this._hasNext() && !this._linkSlideSets[this._slideSetIdx]) {
                    this._lastSlideReached = true;
                }
                this._trigger('onSlideLoad', null, {slideSetType: this.options.slideSetType, currentSlideSetIdx: this._slideSetIdx, flowDirection: offSet > 0 ? 'next' : offSet < 0 ? 'previous' : 'none', slideIdx: slideIdx, slideName: this._getSlideName(slideIdx)});
                this._trigger('onSlideChange', null, {slideSetType: this.options.slideSetType, currentSlideSetIdx: this._slideSetIdx, flowDirection: offSet > 0 ? 'next' : offSet < 0 ? 'previous' : 'none', currentSlideIdx: slideIdx, currentSlideName: this._getSlideName(slideIdx), totalNumOfSlided: this._numberOfSlides()});
                if(this._linkSlideSets[this._slideSetIdx] == true) {
                    this._trigger('isLastSlide', null, {slideSetType: this.options.slideSetType, currentSlideSetIdx: this._slideSetIdx, isLast: this.options.strictLastSlide ? false : this._lastSlideReached});
                } else {
                    this._trigger('isLastSlide', null, {slideSetType: this.options.slideSetType, currentSlideSetIdx: this._slideSetIdx, isLast: this.options.strictLastSlide ? !this._hasNext() : this._lastSlideReached});
                }

            }
        }
        return slideIdx;
    },

    _getSlideIdx: function(offSet) {
        if(!offSet) {
            offSet = 0;
            return this._slideIdx[this._slideSetIdx] + offSet;
        }

        return this._validateSlideIdx(this._slideIdx[this._slideSetIdx] + offSet, offSet);
    },

    _validateSlideIdx: function(slideIdx, offSet) {
        if(!offSet) {
            offSet = 0;
        }
        while(this._slideSetsURLsActiveStatus[this._slideSetIdx][this._getSlideName(slideIdx)] != true) {
            offSet < 0 ? slideIdx -- : slideIdx ++;
            if(slideIdx < 0 || slideIdx >= this._numberOfSlides())  {
                return -1;
            }
        }
        return slideIdx;
    },

    _getFullSlideIdx: function(offSet) {
        if(!offSet) {
            offSet = 0;
        }
        return this._slideSetIdx + '' + this._getSlideIdx(offSet);
    },

    _hasNext: function(offSet) {
        if(!offSet) {
            offSet = 0;
        }

        var slideIdx = this._getSlideIdx(offSet + 1);
        /*if(offSet > 0) {
            return slideIdx > -1 && slideIdx < this._numberOfSlides() - 1;
        }*/
        return  slideIdx > -1;
    },

    _hasPrevious: function(offSet) {
        if(!offSet) {
            offSet = 0;
        }

        var slideIdx = this._getSlideIdx(offSet - 1);
        /*if(offSet < 0) {
            return slideIdx >  0;
        }*/
        return slideIdx >  -1;
    },

    _nextSlide: function(callback) {
        if(this._hasNext()) {
            $('.slide' + this._nextSlideIdx(true)).trigger('click');
            this._incrementSlideIdx();
            this._updateHeader();

        } else if(this._linkSlideSets[this._slideSetIdx] == true) {
//            this._linkSlideSets[this._slideSetIdx] = false;
//            $('#slide-set-link' + this._slideSetIdx).remove();
            this._slideIdx[this._slideSetIdx + 1] = 0;
            this.incrementSlideSetIdx();
            if (!this._lastSlideReached && !this._hasNext()) {
                this._lastSlideReached = true;
            }
            this._trigger('onSlideLoad', null, {slideSetType: this.options.slideSetType, currentSlideSetIdx: this._slideSetIdx, flowDirection: 'next', slideIdx: this._currentSlideIdx(), slideName: this._getSlideName(this._currentSlideIdx())});
            this._trigger('onSlideChange', null, {slideSetType: this.options.slideSetType, currentSlideSetIdx: this._slideSetIdx, flowDirection: 'next', currentSlideIdx: this._currentSlideIdx(), currentSlideName: this._getSlideName(this._currentSlideIdx()), totalNumOfSlided: this._numberOfSlides()});
            this._trigger('isLastSlide', null, {slideSetType: this.options.slideSetType, currentSlideSetIdx: this._slideSetIdx, isLast: this.options.strictLastSlide ? !this._hasNext() : this._lastSlideReached});
        }
        if(callback) {
            setTimeout(function() {callback();}, 500);
        }
    },

    _previousSlide: function(callback) {
        if(this._hasPrevious()) {
//            this._updateHeader(-1);
            $('.slide' + (this._previousSlideIdx(true))).trigger('click');
            this._decrementSlideIdx();
            this._updateHeader();
        } else if(this._linkSlideSets[this._slideSetIdx - 1] == true) {
//            this._linkSlideSets[this._slideSetIdx] = false;
//            $('#slide-set-link' + this._slideSetIdx).remove();
            this.decrementSlideSetIdx();
            this._trigger('onSlideLoad', null, {slideSetType: this.options.slideSetType, currentSlideSetIdx: this._slideSetIdx, flowDirection: 'previous', slideIdx: this._currentSlideIdx(), slideName: this._getSlideName(this._currentSlideIdx())});
            this._trigger('onSlideChange', null, {slideSetType: this.options.slideSetType, currentSlideSetIdx: this._slideSetIdx, flowDirection: 'previous', currentSlideIdx: this._currentSlideIdx(), currentSlideName: this._getSlideName(this._currentSlideIdx()), totalNumOfSlided: this._numberOfSlides()});
            this._trigger('isLastSlide', null, {slideSetType: this.options.slideSetType, currentSlideSetIdx: this._slideSetIdx, isLast: this.options.strictLastSlide ? !this._hasNext() : this._lastSlideReached});
//            this._updateHeader();
        }
        if(callback) {
            setTimeout(function() {callback();}, 500);
        }
    },

    _updateHeader: function(offSet) {
        if(!offSet) {
            offSet = 0;
        }
        $('.bullet').removeClass('filled');
        $('#bullet' + this._getFullSlideIdx(offSet)).addClass('filled');

        if(!this._hasNext(offSet)) {
            $('.button-next').addClass('invisible');
        }

        if(!this._hasPrevious(offSet)) {
            $('.button-previous').addClass('invisible');
        }

        if (this._hasNext(offSet)) {
            $('.button-next').removeClass('invisible');
        }

        if(this._hasPrevious(offSet)) {
            $('.button-previous').removeClass('invisible');
        }
        if(this._linkSlideSets[this._slideSetIdx] == true) {
            $('.button-next').removeClass('invisible');
        }

        if(this._linkSlideSets[this._slideSetIdx - 1] == true) {
            $('.button-previous').removeClass('invisible');
        }
    },

    _updateSlideSet: function() {
        this._swapBullets();
        this._updateHeader();
        $('.slide' + this._currentSlideIdx(true)).trigger('click');
    },

    _swapBullets: function() {
        $('[class*=bullets]').removeClass('invisible').addClass('invisible');
        $('.bullets' + this._slideSetIdx).removeClass('invisible');
    },

    _markup: function() {
        this.element.wrap($('<div/>').addClass('slider-widget'));
        this._markupHeader();
        this._markupSlides();

    },

    _markupHeader: function() {
        this.element.before(this.options.headerHtml);

        var clickDisabled = false;

        this._on('.button-previous', {
            click: function(e) {
                if(!clickDisabled) {
                    clickDisabled = true;
                    e.preventDefault();
                    this._previousSlide(function () {
                        clickDisabled = false;
                    });
                }
            }
        });

        this._on(true, '.button-next', {
            click: function(e) {
                if(!clickDisabled) {
                    clickDisabled = true;
                    e.preventDefault();
                    this._nextSlide(function () {
                        clickDisabled = false;
                    });
                }
            }
        });
    },

    _markupSlides: function() {

        var bulletsElement = $('.position-guide');
        var slideSetsURLs = this._slideSetsURLs;
        var standaloneSlideSetsURLs = this._standaloneSlideSetsURLs;
        for(var i = 0; i < slideSetsURLs.length; i++) {
            var slideSetURLs = slideSetsURLs[i];
            var standaloneSlideSetURLs = i < standaloneSlideSetsURLs.length ? standaloneSlideSetsURLs[i] : [];
            bulletsElement.append($('<div/>').addClass('bullets-container bullets' + i).addClass(this._slideSetIdx == i ? '' : ' invisible'));
            var bulletsContainer = bulletsElement.find('.bullets' + i);
            for(var j = 0; j < slideSetURLs.length; j ++) {
                $('<div/>').addClass('slide-body').load(slideSetURLs[j] + '?idx=' + i + '&id=' + this.options.productId[i]).appendTo($('<li/>').addClass('slides' + i).addClass('slide').attr('data-orbit-slide', 'slide' + i + j).appendTo(this.element));
                bulletsContainer.append($('<a/>').addClass('slide' + i + j).attr('data-orbit-link', 'slide' + i + j)).append($(this.options.bulletHtml).attr('id', 'bullet' + i + j).addClass(j == this._slideIdx[this._slideSetIdx] ? ' filled' : ''));
            }
            if(this._linkSlideSets[i] == true) {
                bulletsContainer.append($(this.options.bulletHtml).attr('id', 'slide-set-link' + i));
            }
        }

        var standaloneSlideSetsJSON = this.options.standaloneSlideSetsJSON;
        for(var k = 0; k < standaloneSlideSetsJSON.length; k ++) {
            var standaloneSlideSetJSON = standaloneSlideSetsJSON[k];
            for(var prop in standaloneSlideSetJSON) {
                if(standaloneSlideSetJSON.hasOwnProperty(prop)) {
                    $('<div/>').addClass('slide-body').load(standaloneSlideSetJSON[prop] + '?standalone=true&idx=' + k + '&id=' + this.options.productId[k]).appendTo($('<li/>').addClass('standalone-slides' + k).addClass('slide').attr('data-orbit-slide', prop + k).appendTo(this.element));
                    bulletsElement.append($('<a/>').addClass(prop + k).attr('data-orbit-link', prop + k));
                }
            }
        }
    }/*,

    destroy: function() {
        $('.slider-widget').before('<ul id="edit-container" class="edit-container" data-orbit></ul>');
        $('.slider-widget').remove();
// Call the base destroy function.
        $.Widget.prototype.destroy.call( this );
    }*/
});
