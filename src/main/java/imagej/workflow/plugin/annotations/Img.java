/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imagej.workflow.plugin.annotations;

import java.lang.annotation.*;

/**
 *
 * @author aivar
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Img {
    String value() default "DEFAULT";
}
