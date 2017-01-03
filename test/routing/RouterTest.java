/**
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

import org.junit.Test;
import play.mvc.Http.RequestBuilder;
import play.mvc.Result;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

/**
 * <p>The JUnit Test is designed to test all routes provided by
 * the RESOLVE WebAPI.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class RouterTest extends WithApplication {

    /**
     * <p>A simple test to ensure we get a 404.</p>
     */
    @Test
    public void testBadRoute() {
        RequestBuilder request =
                new RequestBuilder().method("GET").uri("/xx/Kiwi");

        Result result = route(request);
        assertEquals(NOT_FOUND, result.status());
    }

    /**
     * <p>Check to see if we can retrieve the main index page.</p>
     */
    @Test
    public void testIndexRoute() {
        RequestBuilder request = new RequestBuilder().method("GET").uri("/");

        Result result = route(request);
        assertEquals(OK, result.status());
    }

}