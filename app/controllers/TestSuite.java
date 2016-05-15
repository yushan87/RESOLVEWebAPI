package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.testsuite;

public class TestSuite extends Controller {

    public Result index() {
        return ok(testsuite.render("Test Suite"));
    }

}