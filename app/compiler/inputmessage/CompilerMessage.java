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

package compiler.inputmessage;

import edu.clemson.cs.rsrg.init.file.ResolveFile;

/**
 * This class contains all the general fields that an input message may receive from the user. These
 * fields are then used to create an {@link ResolveFile}.
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class CompilerMessage {

  /** This contains the name of the file. */
  public String name;

  /** This contains the extension type of the file. */
  public String type;

  /** This contains the project name that the file belongs to. */
  public String project;

  /** This contains the contents of the file. */
  public String content;

  /**
   * This method returns the object in string format.
   *
   * @return Object as a string.
   */
  @Override
  public String toString() {
    return "CompilerMessage{"
        + "name='"
        + name
        + '\''
        + ", type='"
        + type
        + '\''
        + ", project='"
        + project
        + '\''
        + ", content='"
        + content
        + '\''
        + '}';
  }
}
