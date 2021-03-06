/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2010 - 2014 Board of Regents of the University of
 * Wisconsin-Madison.
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
 * #L%
 */

package imagej.workflow.debug;

/**
 * Contains preview information for one workflow pipe transaction.
 *
 * @author Aivar Grislis
 */
public class PreviewInfo {
    private final String m_instanceId;
    private final String m_desc;
    private final String m_content;

    /**
     * Creates preview information instance.
     *
     * @param instanceId unique identifier for associated workflow component
     * @param desc description of this pipe transaction
     * @param content for preview
     */
    public PreviewInfo(String instanceId, String desc, String content) {
        m_instanceId = instanceId;
        m_desc = desc;
        m_content = content;
    }

    /**
     * Get unique identifier for associated workflow componenet instance.
     *
     * @return instance identifier
     */
    public String getInstanceId() {
        return m_instanceId;
    }

    /**
     * Get description of this pipe transaction.
     *
     * @return description
     */
    public String getDesc() {
        return m_desc;
    }

    /**
     * Get content for preview.
     *
     * @return content page
     */
    public String getContent() {
        return m_content;
    }
}
