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

import controllers.Assets.Asset;
import javax.inject.Inject;
import play.api.mvc.Action;
import play.api.mvc.AnyContent;

/**
 * This class retrieves a public asset.
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class AssetsManager {

  // ===========================================================
  // Member Fields
  // ===========================================================

  /** Assets Manager */
  @Inject private controllers.Assets myAssets;

  // ===========================================================
  // Public Methods
  // ===========================================================

  /**
   * This method returns the asset located at {@code path}.
   *
   * @param path Path where the asset files are located.
   * @param file An {@link Asset} file.
   * @return Result from attempting to retrieve an {@link Asset}.
   */
  public final Action<AnyContent> versioned(String path, Asset file) {
    return myAssets.versioned(path, file);
  }
}
