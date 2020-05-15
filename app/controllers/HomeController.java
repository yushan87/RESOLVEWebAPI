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

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * This is the application's main entry point.
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class HomeController extends Controller {

  // ===========================================================
  // Public Methods
  // ===========================================================

  /**
   * This method creates an object that renders the index page to this application.
   *
   * @return An {@link Result} object from rendering the index page.
   */
  public final Result index() {
    return ok(index.render("The RESOLVE Web API is currently online."));
  }
}
