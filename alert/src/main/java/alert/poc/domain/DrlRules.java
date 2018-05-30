package alert.poc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "drlRules")
public class DrlRules {
	@Id
	@Column(name = "ruleId")
	private Integer ruleId;

	@Column(name = "packImport")
	private String packImport;

	@Column(name = "ruleSt", length = 512)
	private String ruleSt;

	@Column(name = "whenSt", length = 512)
	private String whenSt;

	@Column(name = "thenSt", length = 512)
	private String thenSt;
	
	@Column(name = "eventId")
	private Integer eventId;

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getPackImport() {
		return packImport;
	}

	public void setPackImport(String packImport) {
		this.packImport = packImport;
	}

	public String getRuleSt() {
		return ruleSt;
	}

	public void setRuleSt(String ruleSt) {
		this.ruleSt = ruleSt;
	}

	public String getWhenSt() {
		return whenSt;
	}

	public void setWhenSt(String whenSt) {
		this.whenSt = whenSt;
	}

	public String getThenSt() {
		return thenSt;
	}

	public void setThenSt(String thenSt) {
		this.thenSt = thenSt;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

}
