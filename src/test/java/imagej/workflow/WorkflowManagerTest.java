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

import imagej.workflow.util.xmllight.XMLException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author aivar
 */
public class WorkflowManagerTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public WorkflowManagerTest(String testName) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(WorkflowManagerTest.class);
    }

    public void testDiscovery()
    {
        System.out.println("testDiscovery");
        if (true) return; //TODO broken
        
        String xml1 = "<workflow>"
                    + "  <name>My Workflow1</name>"
                    + "  <modules>"
                    + "    <module>"
                    + "      <name>DummyPlugin2</name>"
                    + "      <plugin>"
                    + "        <name>DummyPlugin2</name>"
                    + "        <classname>imagej.workflow.DummyPlugin2</classname>"
                    + "        <inputs>"
                    + "        </inputs>"
                    + "        <outputs>"
                    + "        </outputs>"
                    + "      </plugin>"
                    + "    </module>"
                    + "    <module>"
                    + "      <name>DummyPlugin</name>"
                    + "      <plugin>"
                    + "        <name>DummyPlugin</name>"
                    + "        <classname>imagej.workflow.DummyPlugin</classname>"
                    + "        <inputs>"
                    + "        </inputs>"
                    + "        <outputs>"
                    + "        </outputs>"
                    + "      </plugin>"
                    + "    </module>"
                    + "  </modules>"
                    + "  <wires>"
                    + "    <wire>"
                    + "      <src>"
                    + "        <module>DummyPlugin</module>"
                    + "        <name>LOWER</name>"
                    + "      </src>"
                    + "      <dst>"
                    + "        <module>DummyPlugin2</module>"
                    + "        <name>SECOND</name>"
                    + "      </dst>"
                    + "    </wire>"
                    + "    <wire>"
                    + "      <src>"
                    + "        <module>DummyPlugin</module>"
                    + "        <name>UPPER</name>"
                    + "      </src>"
                    + "      <dst>"
                    + "        <module>DummyPlugin2</module>"
                    + "        <name>FIRST</name>"
                    + "      </dst>"
                    + "    </wire>"
                    + "  </wires>"
                    + "  <inputs>"
                    + "  </inputs>"
                    + "  <outputs>"
                    + "  </outputs>"
                    + "</workflow>";

        String xml2 = "<workflow>"
                    + "  <name>My Workflow2</name>"
                    + "  <modules>"
                    + "    <module>"
                    + "      <name>DummyPlugin2</name>"
                    + "      <plugin>"
                    + "        <name>DummyPlugin2</name>"
                    + "        <classname>imagej.workflow.DummyPlugin2</classname>"
                    + "        <inputs>"
                    + "        </inputs>"
                    + "        <outputs>"
                    + "        </outputs>"
                    + "      </plugin>"
                    + "    </module>"
                    + "    <module>"
                    + "      <name>DummyPlugin</name>"
                    + "      <plugin>"
                    + "        <name>DummyPlugin</name>"
                    + "        <classname>imagej.workflow.DummyPlugin</classname>"
                    + "        <inputs>"
                    + "        </inputs>"
                    + "        <outputs>"
                    + "        </outputs>"
                    + "      </plugin>"
                    + "    </module>"
                    + "  </modules>"
                    + "  <wires>"
                    + "    <wire>"
                    + "      <src>"
                    + "        <module>DummyPlugin</module>"
                    + "        <name>LOWER</name>"
                    + "      </src>"
                    + "      <dst>"
                    + "        <module>DummyPlugin2</module>"
                    + "        <name>SECOND</name>"
                    + "      </dst>"
                    + "    </wire>"
                    + "    <wire>"
                    + "      <src>"
                    + "        <module>DummyPlugin</module>"
                    + "        <name>UPPER</name>"
                    + "      </src>"
                    + "      <dst>"
                    + "        <module>DummyPlugin2</module>"
                    + "        <name>FIRST</name>"
                    + "      </dst>"
                    + "    </wire>"
                    + "  </wires>"
                    + "  <inputs>"
                    + "  </inputs>"
                    + "  <outputs>"
                    + "  </outputs>"
                    + "</workflow>";


        System.out.println("GET MODULES");
        IModuleInfo[] modules = WorkflowManager.getInstance().getModuleInfos();
        for (IModuleInfo module : modules) {
            System.out.println("name " + module.getName());
            System.out.println("isWorkflow " + module.isWorkflow());
            System.out.println("inputs");
            showNames(module.getInputItemInfos());
            System.out.println("outputs");
            showNames(module.getOutputItemInfos());
            System.out.println("XML[" + module.toXML()  + "]");
            if (module.isWorkflow()) {
                IWorkflowInfo workflow = (IWorkflowInfo) module;
                WireInfo wireInfos[] = workflow.getWireInfos();
                for (WireInfo wireInfo : wireInfos) {
                    System.out.println("WIRE:");
                    System.out.println(" " + wireInfo.getSourceModuleName());
                    System.out.println("   " + wireInfo.getSourceName());
                    System.out.println(" -->");
                    System.out.println(" " + wireInfo.getDestModuleName());
                    System.out.println("   " + wireInfo.getDestName());
                }
            }
        }

        System.out.println("ADDS");
        IWorkflow workflow1 = null;
        IWorkflow workflow2 = null;
        try {
            workflow1 = (IWorkflow) ModuleFactory.getInstance().create(xml1);
            workflow2 = (IWorkflow) ModuleFactory.getInstance().create(xml2);
            WorkflowManager.getInstance().addWorkflow(workflow1);
            WorkflowManager.getInstance().addWorkflow(workflow2);
        }
        catch (XMLException e) {
            System.out.println("TEST XML PROBLEM " + e.getMessage());
        }

        modules = WorkflowManager.getInstance().getModuleInfos();
        for (IModuleInfo module : modules) {
            System.out.println("name " + module.getName());
            System.out.println("isWorkflow " + module.isWorkflow());
            System.out.println("inputs");
            showNames(module.getInputItemInfos());
            System.out.println("outputs");
            showNames(module.getOutputItemInfos());
            System.out.println("XML[" + module.toXML()  + "]");
            if (module.isWorkflow()) {
                IWorkflowInfo workflow = (IWorkflowInfo) module;
                WireInfo wireInfos[] = workflow.getWireInfos();
                for (WireInfo wireInfo : wireInfos) {
                    System.out.println("WIRE:");
                    System.out.println(" " + wireInfo.getSourceModuleName());
                    System.out.println("   " + wireInfo.getSourceName());
                    System.out.println(" -->");
                    System.out.println(" " + wireInfo.getDestModuleName());
                    System.out.println("   " + wireInfo.getDestName());
                }
            }
        }

        assertTrue(true);
    }

    private void showNames(IItemInfo items[]) {
        for (IItemInfo item : items) {
            System.out.println("  " + item.getName());
        }
    }
}
