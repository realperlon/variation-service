package org.spice3d.variation.parsers;

import org.spice3d.variation.model.VariationRecord;

/** An interface for classes that can listen to new variation getting
 * read from somewhere and that would do something with them.
 * 
 * 
 * @author andreas
 *
 */
public interface VariationConsumer {
	
	public void newVariationRecord(VariationRecord record );

}
