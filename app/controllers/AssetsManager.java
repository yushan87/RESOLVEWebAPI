/*
 * ---------------------------------
 * Copyright (c) 2019
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package controllers;

import controllers.Assets.Asset;
import javax.inject.Inject;
import play.api.mvc.Action;
import play.api.mvc.AnyContent;

/**
 * <p>This class retrieves a public asset.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class AssetsManager {

    // ===========================================================
    // Member Fields
    // ===========================================================

    /** <p>Assets Manager</p> */
    @Inject
    private controllers.Assets myAssets;

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This method returns the asset located at {@code path}.</p>
     *
     * @param path Path where the asset files are located.
     * @param file An {@link Asset} file.
     *
     * @return Result from attempting to retrieve an {@link Asset}.
     */
    public final Action<AnyContent> versioned(String path, Asset file) {
        return myAssets.versioned(path, file);
    }

}