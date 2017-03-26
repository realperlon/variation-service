package org.spice3d.variation.dao;

import java.util.List;
import java.util.Set;

import org.spice3d.variation.model.VariationRecord;

public interface VariationProvider {

	/** Provides suggestions for possible gene names, based on (partial) gene names
	 * 
	 * @param partialGeneName
	 * @return a List of matching gene names
	 */
	public List<String> suggestGeneNames(String partialGeneName);
		
	/** Returns the variations that are known for a particular gene
	 * 
	 * @param geneName
	 * @return
	 */
	public List<VariationRecord> getVariantsForGeneName(String geneName);
	
	
	/** Return the names of all supported genes
	 * 
	 * @return
	 */
	public Set<String> getSupportedGeneNames();
	
}
