package org.spice3d.variation.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.spice3d.variation.model.VariationRecord;


@XmlRootElement(
 name = "response"
)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({VariationRecord.class})
public class ResponseMessageVariation extends ResponseMessage implements Serializable {
 private static final long serialVersionUID = 1L;
 @XmlElementWrapper
 private List<VariationRecord> results = new ArrayList();

 public List<VariationRecord> getResults() {
     return this.results;
 }

 public void setResults(List<VariationRecord> results) {
     this.results = results;
 }

 public ResponseMessageVariation() {
 }

 public String toString() {
     return "ResponseMessageVariation [results=" + (this.results != null && this.results.size() >= 1?this.results:"") + "]";
 }
}
