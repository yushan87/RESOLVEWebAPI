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
 * <p>This class contains all the general fields that an input message
 * may receive from the user. These fields are then used to create an
 * {@link ResolveFile}.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class CompilerMessage {

    /** <p>This contains the name of the file.</p> */
    public String name;

    /** <p>This contains the extension type of the file.</p> */
    public String type;

    /** <p>This contains the project name that the file belongs to.</p> */
    public String project;

    /** <p>This contains the contents of the file.</p> */
    public String content;

    /**
     * <p>This method returns the object in string format.</p>
     *
     * @return Object as a string.
     */
    @Override
    public String toString() {
        return "CompilerMessage{" + "name='" + name + '\'' + ", type='" + type
                + '\'' + ", project='" + project + '\'' + ", content='"
                + content + '\'' + '}';
    }
}