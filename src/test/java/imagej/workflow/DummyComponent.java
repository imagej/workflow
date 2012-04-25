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

package imagej.workflow;

import imagej.workflow.plugin.IPluginLauncher;
import imagej.workflow.plugin.ItemWrapper;
import imagej.workflow.plugin.annotations.Input;
import imagej.workflow.plugin.annotations.Output;
import imagej.workflow.util.xmllight.XMLException;
import imagej.workflow.util.xmllight.XMLParser;
import imagej.workflow.util.xmllight.XMLTag;
import imagej.workflow.util.xmllight.XMLWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aivar
 */
public class DummyComponent implements IModule {
    public static final String TESTCOMPONENT = "testcomponent";
    String m_name;
    List<String> m_inputNames = new ArrayList<String>();
    List<String> m_outputNames = new ArrayList<String>();

    public void setInputNames(String[] inputNames) {
        for (String name: inputNames) {
            m_inputNames.add(name);
        }
    }

    public void setOutputNames(String[] outputNames) {
        for (String name: outputNames) {
            m_outputNames.add(name);
        }
    }

    /**
     * Gets name of component.
     *
     * @return
     */
    public String getName() {
        return m_name;
    }

    /**
     * Sets name of component.
     *
     * @param name
     */
    public void setName(String name) {
        m_name = name;
    }

    public IPluginLauncher getLauncher() {
        return null;
    }

    /**
     * Saves component as XML string representation.
     *
     * @return
     */
    public String toXML() {
        StringBuilder xmlBuilder = new StringBuilder();
        XMLWriter xmlHelper = new XMLWriter(xmlBuilder);

        // add workflow tag and name
        xmlHelper.addTag(TESTCOMPONENT);
        xmlHelper.addTagWithContent(Workflow.NAME, getName());

        // add inputs
        xmlHelper.addTag(Workflow.INPUTS);
        for (String name : m_inputNames) {
            xmlHelper.addTag(Workflow.INPUT);
            xmlHelper.addTagWithContent(Workflow.NAME, name);
            xmlHelper.addEndTag(Workflow.INPUT);
        }
        xmlHelper.addEndTag(Workflow.INPUTS);

        // add outputs
        xmlHelper.addTag(Workflow.OUTPUTS);
        for (String name : m_outputNames) {
            xmlHelper.addTag(Workflow.OUTPUT);
            xmlHelper.addTagWithContent(Workflow.NAME, name);
            xmlHelper.addEndTag(Workflow.OUTPUT);
        }
        xmlHelper.addEndTag(Workflow.OUTPUTS);

        // end workflow
        xmlHelper.addEndTag(TESTCOMPONENT);

        return xmlBuilder.toString();
    }

    /**
     * Restores component from XML string representation.
     *
     * @param xml
     * @return whether successfully parsed
     */
    public boolean fromXML(String xml) {
        boolean success = false;
        XMLParser xmlHelper = new XMLParser();

        try {
            // handle test tag and name
            //
            // <testcomponent>
            //   <name>A</name>

            XMLTag tag = xmlHelper.getNextTag(xml);
            if (!TESTCOMPONENT.equals(tag.getName())) {
                throw new XMLException("Missing <testcomponent> tag");
            }
            xml = tag.getContent();
            tag = xmlHelper.getNextTag(xml);
            if (!Workflow.NAME.equals(tag.getName())) {
                throw new XMLException("Missing <name> for <workflow>");
            }
            setName(tag.getContent());
            xml = tag.getRemainder();

            // handle inputs
            //
            //  <inputs>
            //    <input>
            //      <name>RED</name>
            //   </input>
            // </inputs>

            tag = xmlHelper.getNextTag(xml);
            if (!Workflow.INPUTS.equals(tag.getName())) {
                throw new XMLException("Missing <inputs> within <testcomponent>");
            }
            String inputsXML = tag.getContent();
            xml = tag.getRemainder();
            while (!inputsXML.isEmpty()) {
                tag = xmlHelper.getNextTag(inputsXML);
                inputsXML = tag.getRemainder();

                if (tag.getName().isEmpty()) { //TODO don't think these are necessary
                    break;
                }

                if (!Workflow.INPUT.equals(tag.getName())) {
                    throw new XMLException("Missing <input> within <inputs");
                }
                String inputXML = tag.getContent();

                tag = xmlHelper.getNextTag(inputXML);
                inputXML = tag.getRemainder();

                if (!Workflow.NAME.equals(tag.getName())) {
                    throw new XMLException("Missing <name> within <input>");
                }
                String inName = tag.getContent();

                m_inputNames.add(inName);
            }


            // handle outputs
            //  <outputs>
            //    <output>
            //      <name>OUTPUT</name>
            //    </output>
            //  </outputs>
            tag = xmlHelper.getNextTag(xml);
            if (!Workflow.OUTPUTS.equals(tag.getName())) {
                throw new XMLException("Missing <outputs> within <testcomponent>");
            }
            String outputsXML = tag.getContent();
            xml = tag.getRemainder();
            while (!outputsXML.isEmpty()) {
                tag = xmlHelper.getNextTag(outputsXML);
                outputsXML = tag.getRemainder();

                if (tag.getName().isEmpty()) { //TODO don't think these are necessary
                    break;
                }

                if (!Workflow.OUTPUT.equals(tag.getName())) {
                    throw new XMLException("Missing <output> within <outputs>");
                }
                String outputXML = tag.getContent();

                tag = xmlHelper.getNextTag(outputXML);
                outputXML = tag.getRemainder();

                if (!Workflow.NAME.equals(tag.getName())) {
                    throw new XMLException("Missing <name> within <output>");
                }
                String outName = tag.getContent();
                m_outputNames.add(outName);
            }
            success = true;
        }
        catch (XMLException e) {
            System.out.println("XML Exception");
        }
        return success;
    }

    /**
     * Gets input image names.
     *
     * @return
     */
    public String[] getInputNames() {
        return m_inputNames.toArray(new String[0]);
    }

    /**
     * Gets output names.
     *
     * @return
     */
    public String[] getOutputNames() {
        return m_outputNames.toArray(new String[0]);
    }

    /**
     * Sets input settings.
     *
     * @param inputs
     */
    public void setInputs(Map<String, Object> inputs) {
        //TODO
    }

   /**
     * Furnish input image
     *
     * @param image
     */
    public void input(ItemWrapper image) {
        input(image, Input.DEFAULT);
    }

    /**
     * Furnish input image
     *
     * @param image
     * @param name
     */
    public void input(ItemWrapper image, String name) {
        //TODO
    }

    /**
     * Listen for output image.
     *
     * @param name
     * @param listener
     */
    public void setOutputListener(IOutputListener listener) {
        setOutputListener(Output.DEFAULT, listener);
    }
    /**
     * Listen for output image.
     *
     * @param name
     * @param listener
     */
    public void setOutputListener(String name, IOutputListener listener) {
        //TODO
    }
}
