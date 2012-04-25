/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2012 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */

package imagej.workflow.plugin;

import imagej.workflow.plugin.annotations.Input;
import imagej.workflow.plugin.annotations.Item;
import imagej.workflow.plugin.annotations.Output;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * The PluginAnnotations class keeps sets of input and output names based
 * on plugin class annotations.
 *
 * @author Aivar Grislis
 */
public class PluginAnnotations {
    enum InputOutput { INPUT, OUTPUT };
    Set<String> m_inputNames;
    Set<String> m_outputNames;

    /**
     * Creates an instance for a given plugin class.
     *
     * @param pluginClass
     */
    public PluginAnnotations(Class pluginClass) {
        // build sets of input and output names from annotations
        m_inputNames = getInputNamesFromAnnotations(pluginClass);
        m_outputNames = getOutputNamesFromAnnotations(pluginClass);
    }

    /**
     * Gets the set of annotated input names.
     *
     * @return set of names
     */
    public Set<String> getInputNames() {
        return m_inputNames;
    }

    /**
     * Gets the set of annotated output names.
     *
     * @return set of names
     */
    public Set<String> getOutputNames() {
        return m_outputNames;
    }

    /**
     * Checks whether a given name appears in the annotations for input or
     * output images.  Puts out an error message.
     *
     * @param input whether input or output
     * @param name putative input/output name
     * @return whether or not annotated
     */
    public boolean isAnnotatedName(InputOutput inOut, String name) {
        boolean returnValue = true;
        Set<String> names = (InputOutput.INPUT == inOut) ? getInputNames() : getOutputNames();
        if (!names.contains(name)) {
            nameNotAnnotated(inOut, name);
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * Puts out an error message that an annotation is missing.
     *
     * @param inOut whether input or output
     * @param name
     */
    static void nameNotAnnotated(InputOutput inOut, String name) {
        //TODO this message was for annotation Img, the new Item annotation is different
       // System.out.println("Missing annotation: @" + ((InputOutput.INPUT == inOut) ? "In" : "Out") + "put({@Item=\"" + name + "\"})" );
        System.out.println("Missing annotation for " + ((InputOutput.INPUT == inOut) ? "input" : "output") + " " + name);
    }

    /**
     * Builds a set of input object names from the subclass annotations.
     *
     * @param pluginClass
     * @return set of names
     */
    private Set<String> getInputNamesFromAnnotations(Class pluginClass) {
        Set<String> set = new HashSet<String>();
        if (null != pluginClass) {
            Annotation annotation = pluginClass.getAnnotation(Input.class);
            if (annotation instanceof Input) {
                Input inputs = (Input) annotation;
                Item items[] = inputs.value();
                if (0 == items.length) {
                    set.add(Input.DEFAULT);
                }
                else {
                    for (Item item : items) {
                        //TODO if (Item.Type.IMAGE == item.type())
                        set.add(item.name());
                    }
                }
            }
        }
        return set;
    }

    /**
     * Builds a set of output image names from the subclass annotations.
     *
     * @param pluginClass
     * @return set of names
     */
    private Set<String> getOutputNamesFromAnnotations(Class pluginClass) {
        Set<String> set = new HashSet<String>();
        if (null != pluginClass) {
            Annotation annotation = pluginClass.getAnnotation(Output.class);
            if (annotation instanceof Output) {
                Output inputs = (Output) annotation;
                Item items[] = inputs.value();
                if (0 == items.length) {
                    set.add(Output.DEFAULT);
                }
                else {
                    for (Item item : items) {
                        set.add(item.name());
                    }
                }
            }
        }
        return set;
    }
}
