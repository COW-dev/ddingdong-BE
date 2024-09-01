package ddingdong.ddingdongBE.domain.activityreport.domain;

import ddingdong.ddingdongBE.common.BaseEntity;
import ddingdong.ddingdongBE.domain.activityreport.controller.dto.request.UpdateActivityReportRequest;
import ddingdong.ddingdongBE.domain.club.entity.Club;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Table;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update activity_report set deleted_at = CURRENT_TIMESTAMP where id=?")
@Where(clause = "deleted_at IS NULL")
@Table(appliesTo = "activity_report")
public class ActivityReport extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String term;

	@Column(length = 100)
	private String content;

	private String place;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	@ElementCollection
	private List<Participant> participants;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "club_id")
	private Club club;

	@Builder
	public ActivityReport(String term, String content, String place, LocalDateTime startDate, LocalDateTime endDate,
		List<Participant> participants, Club club) {
		this.term = term;
		this.content = content;
		this.place = place;
		this.startDate = startDate;
		this.endDate = endDate;
		this.participants = participants;
		this.club = club;
	}

	public void update(final UpdateActivityReportRequest updateActivityReportRequest) {
		this.content =
			updateActivityReportRequest.getContent() != null ? updateActivityReportRequest.getContent() : this.content;
		this.place =
			updateActivityReportRequest.getPlace() != null ? updateActivityReportRequest.getPlace() : this.place;
		this.startDate =
			updateActivityReportRequest.getStartDate() != null ? LocalDateTime.parse(
				updateActivityReportRequest.getStartDate(),
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : this.startDate;
		this.endDate =
			updateActivityReportRequest.getEndDate() != null ? LocalDateTime.parse(
				updateActivityReportRequest.getEndDate(),
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : this.endDate;
		this.participants =
			updateActivityReportRequest.getParticipants() != null ? updateActivityReportRequest.getParticipants() :
				this.participants;
	}
}
