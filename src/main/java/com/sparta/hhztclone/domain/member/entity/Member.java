package com.sparta.hhztclone.domain.member.entity;

import com.sparta.hhztclone.domain.member.entity.type.AuthorityType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member_tbl")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "authority", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityType authority;

    @Builder
    public Member(String email, String password, AuthorityType authority) {
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
