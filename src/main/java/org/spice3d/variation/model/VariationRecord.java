package org.spice3d.variation.model;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.xml.bind.annotation.*;

/** A  java bean that contains all data from a row of the variation file.
 * Also can get serialized as json, or xml.
 * 
 * @author andreas
 *
 */
@XmlRootElement(
		name = "variation"
		)
@XmlAccessorType(XmlAccessType.FIELD)

public class VariationRecord implements Serializable
{

	static Logger logger = LogManager.getLogger(VariationRecord.class);	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5867469531415295605L;

	String gene;
	String nucleotideChange;
	String proteinChange;
	String otherMappings;
	String alias;
	String transcripts;
	String region;
	String reportedClassification;
	String inferredClassification;
	String source;
	String lastUpdated;
	String url;

	String comment;

	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	public String getNucleotideChange() {
		return nucleotideChange;
	}

	public void setNucleotideChange(String nucleotideChange) {
		this.nucleotideChange = nucleotideChange;
	}

	public String getProteinChange() {
		return proteinChange;
	}

	public void setProteinChange(String proteinChange) {
		this.proteinChange = proteinChange;
	}

	public String getOtherMappings() {
		return otherMappings;
	}

	public void setOtherMappings(String otherMappings) {
		this.otherMappings = otherMappings;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getTranscripts() {
		return transcripts;
	}

	public void setTranscripts(String transcripts) {
		this.transcripts = transcripts;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getReportedClassification() {
		return reportedClassification;
	}

	public void setReportedClassification(String reportedClassification) {
		this.reportedClassification = reportedClassification;
	}

	public String getInferredClassification() {
		return inferredClassification;
	}

	public void setInferredClassification(String inferredClassification) {
		this.inferredClassification = inferredClassification;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}


	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}



	@Override
	public String toString() {
		return "VariationRecord [gene=" + gene + ", nucleotideChange=" + nucleotideChange + ", proteinChange="
				+ proteinChange + ", otherMappings=" + otherMappings + ", alias=" + alias + ", transcripts="
				+ transcripts + ", region=" + region + ", reportedClassification=" + reportedClassification
			    + ", inferredClassification=" + inferredClassification + ", source=" + source + ", lastUpdated="
			    + lastUpdated +", url=" + url + ", comment=" + comment + "]";
	}

	public String toTSV(){
		StringBuffer b = new StringBuffer();

		b.append(gene);
		b.append("\t");
		b.append(nucleotideChange);
		b.append("\t");
		b.append(proteinChange);
		b.append("\t");
		b.append(otherMappings);
		b.append("\t");
		b.append(alias);
		b.append("\t");
		b.append(transcripts);
		b.append("\t");
		b.append(region);
		b.append("\t");
		b.append(reportedClassification);
		b.append("\t");
		b.append(inferredClassification);
		b.append("\t");
		b.append(source);
		b.append("\t");
		b.append(lastUpdated);
		b.append("\t");
		b.append(url);
		b.append("\t");
		
		if ( comment != null){
			if (! comment.equals(""))
				b.append(comment);
		}

		return b.toString();

	}


	/** Returns a VariationRecord or null, if the line is wrongly formatted
	 * 
	 * @param line
	 * @return
	 */
	public static VariationRecord fromTSV(String line) {

		VariationRecord v = new VariationRecord();

		String[] spl = line.split("\\t");

		if (spl.length < 13){
			logger.error("Could not parse line, only has "+ spl.length +" columns! " + line);
			return null;
		}

		v.setGene(spl[0]);
		v.setNucleotideChange(spl[1]);
		v.setProteinChange(spl[2]);
		v.setOtherMappings(spl[3]);
		v.setAlias(spl[4]);
		v.setTranscripts(spl[5]);
		v.setRegion(spl[6]);
		v.setReportedClassification(spl[7]);
		v.setInferredClassification(spl[8]);
		v.setSource(spl[9]);
		v.setLastUpdated(spl[11]);
		v.setUrl(spl[12]);
		if ( spl.length>13)
			v.setComment(spl[13]);
		else
			v.setComment("");
		

		return v;
	}


}
