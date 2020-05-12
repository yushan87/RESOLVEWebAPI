/*
 * ---------------------------------
 * Copyright (c) 2020
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package controllers;

import com.typesafe.config.Config;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.api.apidoc;

/**
 * <p>This singleton class serves as the controller for displaying
 * the documentation for the various APIs.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
@Singleton
public class APIDocController extends Controller {

    // ===========================================================
    // Member Fields
    // ===========================================================

    /** <p>The configuration manager</p> */
    private final Config myConfiguration;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>This creates an object for displaying the documentation
     * for the various APIs.</p>
     *
     * @param config The configuration manager.
     */
    @Inject
    public APIDocController(Config config) {
        myConfiguration = config;
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This method creates an object that renders the index page
     * for the API documentation.</p>
     *
     * @return An {@link Result} object from rendering the index page
     * for the API documentation.
     */
    public final Result docIndex() {
        return ok(apidoc.render());
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

}