package ddingdong.ddingdongBE.domain.notice.entity;

import ddingdong.ddingdongBE.common.BaseEntity;
import ddingdong.ddingdongBE.domain.notice.controller.dto.request.UpdateNoticeRequest;
import ddingdong.ddingdongBE.domain.user.entity.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update notice set is_deleted = true where id=?")
@Where(clause = "is_deleted = false")
@Table(name = "notice")
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String content;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Builder
    public Notice(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void update(UpdateNoticeRequest request) {
        this.title = request.getTitle() != null ? request.getTitle() : this.title;
        this.content = request.getContent() != null ? request.getContent() : this.content;
    }

}
