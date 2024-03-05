var slideSetsJSON = {slideSetsJSON: [
    {
        chooseDesign: '/envelopes/control/chooseDesignStep',
        printSettings: '/envelopes/control/printSettingsStep',
        envelopeAddressing: '/envelopes/control/envelopeAddressingStep',
        fileUpload: '/envelopes/control/fileUploadStep',
        productionTime: '/envelopes/control/productionTimeStep'
    },
    {
        chooseDesign: '/envelopes/control/chooseDesignStep',
        printSettings: '/envelopes/control/printSettingsStep',
        envelopeAddressing: '/envelopes/control/envelopeAddressingStep',
        fileUpload: '/envelopes/control/fileUploadStep',
        productionTime: '/envelopes/control/productionTimeStep'
    }
]};

QUnit.test( "Orbit Widget Test #1", function( assert ) {
    var $fixture = $('#edit-container');
    $fixture.OrbitWidget(slideSetsJSON);

    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 0, "Expected 0");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), false, "Expected 'false'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");

    $fixture.OrbitWidget('invoke', '_nextSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 1, "Expected 1");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious(-1)'), false, "Expected 'false'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");

    $fixture.OrbitWidget('invoke', '_nextSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 2, "Expected 2");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");

    $fixture.OrbitWidget('invoke', '_nextSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 3, "Expected 3");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext(1)'), false, "Expected 'false'");

    $fixture.OrbitWidget('invoke', '_nextSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 4, "Expected 4");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), false, "Expected false");

    $fixture.OrbitWidget('invoke', '_previousSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 3, "Expected 3");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");

    $fixture.OrbitWidget('invoke', '_previousSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 2, "Expected 2");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");

    $fixture.OrbitWidget('invoke', '_previousSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 1, "Expected 1");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious(-1)'), false, "Expected 'false'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");

    $fixture.OrbitWidget('invoke', '_previousSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 0, "Expected 0");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), false, "Expected 'false'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");

    $fixture.OrbitWidget('destroy');
});

QUnit.test( "Orbit Widget Test #2", function( assert ) {
    var $fixture = $('#edit-container');
    $fixture.OrbitWidget(slideSetsJSON);

    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 0, "Expected 0");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), false, "Expected 'false'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");

    $fixture.OrbitWidget('invoke', 'disableSlide("fileUpload")');

    $fixture.OrbitWidget('invoke', '_nextSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 1, "Expected 1");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious(-1)'), false, "Expected 'false'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");

    $fixture.OrbitWidget('invoke', '_nextSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 2, "Expected 2");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext(1)'), false, "Expected 'false'");

    $fixture.OrbitWidget('invoke', '_nextSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 4, "Expected 4");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), false, "Expected false");

    $fixture.OrbitWidget('invoke', '_previousSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 2, "Expected 2");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext(1)'), false, "Expected 'false'");

    $fixture.OrbitWidget('invoke', '_previousSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 1, "Expected 1");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious(-1)'), false, "Expected 'false'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");

    $fixture.OrbitWidget('invoke', '_previousSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 0, "Expected 0");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), false, "Expected 'false'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");

    $fixture.OrbitWidget('destroy');

});

QUnit.test( "Orbit Widget Test #3", function( assert ) {
    var $fixture = $('#edit-container');
    $fixture.OrbitWidget(slideSetsJSON);

    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 0, "Expected 0");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), false, "Expected 'false'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");

    $fixture.OrbitWidget('invoke', 'disableSlide("printSettings")');

    $fixture.OrbitWidget('invoke', '_nextSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 2, "Expected 2");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious(-1)'), false, "Expected 'false'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");

    $fixture.OrbitWidget('invoke', '_nextSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 3, "Expected 3");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext(1)'), false, "Expected 'false'");

    $fixture.OrbitWidget('invoke', '_nextSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 4, "Expected 4");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), false, "Expected false");

    $fixture.OrbitWidget('invoke', '_previousSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 3, "Expected 3");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext(1)'), false, "Expected 'false'");

    $fixture.OrbitWidget('invoke', '_previousSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 2, "Expected 2");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), true, "Expected 'true'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious(-1)'), false, "Expected 'false'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");

    $fixture.OrbitWidget('invoke', '_previousSlide()');
    assert.equal( $fixture.OrbitWidget('invoke', '_getSlideIdx()'), 0, "Expected 0");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasPrevious()'), false, "Expected 'false'");
    assert.equal( $fixture.OrbitWidget('invoke', '_hasNext()'), true, "Expected true");

    $fixture.OrbitWidget('destroy');

});