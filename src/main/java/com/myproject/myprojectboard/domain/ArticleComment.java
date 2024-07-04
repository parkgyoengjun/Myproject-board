package com.myproject.myprojectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter // 롬복 라이브러리에서 제공하는 getxx() 를 자동으로 생성해줌
@ToString // 자동으로 toString() 메서드를 생성해줌
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
}) // Entity 와 매핑할 테이블을 지정(설정의개념)
@EntityListeners(AuditingEntityListener.class)
@Entity// JPA를 사용해 테이블과 매핑할 클래스에 붙여서 JPA가 관리하게한다.(선언의개념)(주의사항 : 1. 기본생성자 꼭 필요 2. final, enum, interface, innter class 사용불가 3. 필드(변수)를 final로 선언 불가 )
public class ArticleComment { // 댓글

    @Id // 특정 속성을 기본키로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본값을 자동으로 DB에 생성함, GenerationType.IDENTITY : 기본키 생성을 DB에 위임
    private Long id;

    @Setter @ManyToOne(optional = false) private Article article; // 게시글 (id)
    @Setter @Column(nullable = false, length = 500) private String content; // 본문

    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일시
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자
    @LastModifiedDate @Column(nullable = false, length = 100) private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자

    protected ArticleComment() {}

    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }
    public static ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment)) return false;
        ArticleComment that = (ArticleComment) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}