package com.myproject.myprojectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

//@EntityListeners(AuditingEntityListener.class)
@Getter // 롬복 라이브러리에서 제공하는 getxx() 를 자동으로 생성해줌
@ToString // 자동으로 toString() 메서드를 생성해줌
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
}) // Entity 와 매핑할 테이블을 지정
@Entity
// JPA를 사용해 테이블과 매핑할 클래스에 붙여서 JPA가 관리하게한다.(주의사항 : 1. 기본생성자 꼭 필요 2. final, enum, interface, innter class 사용불가 3. 필드(변수)를 final로 선언 불가 )
public class Article extends AuditingFields { // 게시글

    @Id // 특정 속성을 기본키로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본값을 자동으로 DB에 생성함, GenerationType.IDENTITY : 기본키 생성을 DB에 위임
    private Long id;

    // @Column : 객체 필드를 테이블 컬럼과 매핑, @Column(nullable null 값 허용 여부 설정 false 는 not null 제약조건 )
    @Setter @Column(nullable = false) private String title; // 제목

    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    @Setter private String hashtag; // 해시태그

    // 양방향 바인딩, 원투매니기법
    @ToString.Exclude // ToString 의 순환을 끝어주는 역할, Article -> ArticleComment -> Article 보고있어서
    @OrderBy("id") @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) private final Set<ArticleComment> articleComments = new LinkedHashSet<>();
    // aritcle 에 연동되어 있는 comment는 중복을 허용하지 않고 다 여기에서 모아서 Collection으로 보겠다.

/*
    생성자, 생성일시, 수정자, 수정일시는 반복적으로 엔티티 클래스에 들어가는 요소이고, 도메인과 직접적인 연관이 없는 요소이므로
     추출이 가능하다. @MappedSuperClass 이용해서 상속 방식으로 추출
ArticleComment 에도 공통으로 들어가있는 내용이기에 AuditingFields 로 빼줌 상속으로 해결
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일시
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자
    @LastModifiedDate @Column(nullable = false, length = 100) private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자
*/
/*   필드에 추가하는것
    @Embedded AAA aa;
    class AAA{
        @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일시
        @CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자
        @LastModifiedDate @Column(nullable = false, length = 100) private LocalDateTime modifiedAt; // 수정일시
        @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자
    }
*/
    protected Article() {} // @Entity 때문에 무조건 생성해야함

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return id != null && id.equals(article.id); // id가 일치하는지와 부분
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
