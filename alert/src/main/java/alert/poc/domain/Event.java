package alert.poc.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "eventId")
	private Integer eventId;
	// we will define a master source system table
	@Column(name="source_system_name")
	private String sourceSystemName;
	
	@Column(name="event_type")
	private String eventType;
	
	@Column(name="event_name")
	private String eventName;
	
	@Column(name="event_desc")
	private String eventDescription;
	
	@Column(name="severity")
	private String severity;
	
	@Column(name="insight")
	private String insight;
	
	@Column(name="valid_from_date")
	private OffsetDateTime fromDate;
	
	@Column(name="valid_to_date")
	private OffsetDateTime toDate;
	
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "customAtrRefId")
	private Set<CustomAttribute> customAttributes= new HashSet<CustomAttribute>();

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getSourceSystemName() {
		return sourceSystemName;
	}

	public void setSourceSystemName(String sourceSystemName) {
		this.sourceSystemName = sourceSystemName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getInsight() {
		return insight;
	}

	public void setInsight(String insight) {
		this.insight = insight;
	}

	public OffsetDateTime getFromDate() {
		return fromDate;
	}

	public void setFromDate(OffsetDateTime fromDate) {
		this.fromDate = fromDate;
	}

	public OffsetDateTime getToDate() {
		return toDate;
	}

	public void setToDate(OffsetDateTime toDate) {
		this.toDate = toDate;
	}
	
	public Set<CustomAttribute> getCustomAttributes() {
		return customAttributes;
	}

	public void setCustomAttributes(Set<CustomAttribute> customAttributes) {
		this.customAttributes = customAttributes;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", sourceSystemName=" + sourceSystemName + ", eventType=" + eventType
				+ ", eventName=" + eventName + ", eventDescription=" + eventDescription + ", severity=" + severity
				+ ", insight=" + insight + ", fromDate=" + fromDate + ", toDate=" + toDate + "]";
	}
	
	
}
