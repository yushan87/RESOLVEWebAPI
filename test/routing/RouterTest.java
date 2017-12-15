/*
 * ---------------------------------
 * Copyright (c) 2017
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package routing;

import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http.RequestBuilder;
import play.mvc.Result;
import play.test.Helpers;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

/**
 * <p>This {@code JUnit} Test is designed to test all routes
 * provided by the {@code RESOLVE WebAPI}.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class RouterTest {

    // ===========================================================
    // Member Fields
    // ===========================================================

    /** <p>A fake instantiation of this API app.</p> */
    private Application myApplication;

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Setup for each route test.</p>
     */
    @Before
    public final void setup() {
        myApplication =
                new GuiceApplicationBuilder().configure("webapi.workingdir",
                        new File(".").getAbsolutePath()).build();
        Helpers.start(myApplication);
    }

    /**
     * <p>Tear down code for each route test.</p>
     */
    @After
    public final void tearDown() {
        Helpers.stop(myApplication);
        myApplication = null;
    }

    /**
     * <p>A simple test to ensure we get a 404.</p>
     */
    @Test
    public final void testBadRoute() {
        RequestBuilder request =
                Helpers.fakeRequest().method("GET").uri("/xx/Kiwi");

        Result result = route(myApplication, request);
        assertEquals(NOT_FOUND, result.status());
    }

    /**
     * <p>Check to see if we can retrieve the main index page.</p>
     */
    @Test
    public final void testIndexRoute() {
        RequestBuilder request = Helpers.fakeRequest().method("GET").uri("/");

        Result result = route(myApplication, request);
        assertEquals(OK, result.status());
    }

}