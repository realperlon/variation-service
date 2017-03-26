package org.spice3d.variation.response;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "response"
)
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement(
        name = "status"
    )
    private int status;
    @XmlElement(
        name = "code"
    )
    private int code;
    @XmlElement(
        name = "message"
    )
    private String message;
    @XmlElement(
        name = "link"
    )
    private String link;
    @XmlElement(
        name = "count"
    )
    private int count;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ResponseMessage() {
    }

    public String toString() {
        return "ResponseMessage [status=" + this.status + ", code=" + this.code + ", message=" + this.message + ", link=" + this.link + "]";
    }
}
