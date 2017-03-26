package org.spice3d.variation.annotation;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@Compress is the name binding annotation
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface Compress {}