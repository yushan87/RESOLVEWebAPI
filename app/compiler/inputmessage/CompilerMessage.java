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
    public String NAME;

    /** <p>This contains the extension type of the file.</p> */
    public String TYPE;

    /** <p>This contains the project name that the file belongs to.</p> */
    public String PROJECT;

    /** <p>This contains the contents of the file.</p> */
    public String CONTENT;

    /**
     * <p>This method returns the object in string format.</p>
     *
     * @return Object as a string.
     */
    @Override
    public final String toString() {
        return "CompilerMessage{" + "NAME='" + NAME + '\'' + ", TYPE='" + TYPE
                + '\'' + ", PROJECT='" + PROJECT + '\'' + ", CONTENT='"
                + CONTENT + '\'' + '}';
    }
}