package com.hellspawn287.user.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;


@Data
@Builder
@Audited
@Entity(name = "USER_CREDENTIAL")
@Table(name = "USER_CREDENTIAL",
        schema = "users",
        indexes =  {@Index(name = "idx_email", columnList = "email", unique = true),
                    @Index(name = "idx_username_email", columnList = "username,email")})
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "USER_ID", columnDefinition = "UUID")
    private UUID id;

// Clustered index and non-clustered index
    @Column(name = "USERNAME")
    private String username;
    @NotNull
    @NotEmpty
    @Column(name = "EMAIL")
    private String email;
    @NotAudited
    @Column(name = "HASH_PASSWORD")
    private String hashedPassword;
    @CreatedDate
    private LocalDateTime creationDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @LastModifiedBy
    private String lastModifiedBy;
    @ManyToMany(cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(schema = "users")
    private Collection<Role> roles;

}
