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
public class ResponseMessageSuggest extends ResponseMessage implements Serializable {
 private static final long serialVersionUID = 1L;
 @XmlElementWrapper
 private List<String> results = new ArrayList<>();

 public List<String> getResults() {
     return this.results;
 }

 public void setResults(List<String> results) {
     this.results = results;
 }

 public ResponseMessageSuggest() {
 }

 public String toString() {
     return "ResponseMessageSuggest [results=" + (this.results != null && this.results.size() >= 1?this.results:"") + "]";
 }
}
