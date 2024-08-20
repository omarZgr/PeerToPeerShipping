package com.application.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "_user")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@SuperBuilder
public class UserEntity implements UserDetails, Principal {


    @Id
    @GeneratedValue
    private Integer id;


    private String nom ;
    private String prenom ;

    @Column(unique = true)
    private String email ;
    private String password ;
    private String tel ;
    private boolean isValid ;
    private boolean isEnabled ;
    private boolean isAccountLocked ;
    private String image ;
    private String cin ;

    // private String rate ;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleEntity> roleEntities;

    @OneToMany(mappedBy = "user")
    private List<TokenEntity> tokenEntities;


    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<ItemEntity> items;

    @OneToMany(mappedBy = "userSender")
    @JsonManagedReference
    private List<MeetEntity> meetsAsSender ;

    @OneToMany(mappedBy = "userTraveler")
    @JsonManagedReference
    private List<MeetEntity> meetsAsTraveler ;

    @OneToMany(mappedBy = "userSender",fetch = FetchType.LAZY)
    private List<ConfirmationEntity> confirmationsAsSender ;

    @OneToMany(mappedBy = "userTraveler",fetch = FetchType.LAZY)
    private List<ConfirmationEntity> confirmationsAsTraveler ;

    @OneToMany(mappedBy = "userTraveler",fetch = FetchType.LAZY)
    private List<CommentaireEntity> commentaireEntities;

    @OneToMany(mappedBy = "userTraveler",fetch = FetchType.LAZY)
    private List<ReclamationEntity> reclamations_ealia ;

    @OneToMany(mappedBy = "userSender",fetch = FetchType.LAZY)
    private List<ReclamationEntity> reclamations_lidrt ;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleEntities.stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getRoleName())).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String getName() {
        return email;
    }

    public String getFullName()
    {
        return nom + " " + prenom ;
    }
}
