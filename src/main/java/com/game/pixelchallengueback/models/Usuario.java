package com.game.pixelchallengueback.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table (name = "userDb")
public class Usuario implements UserDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        private String name;

        @Column(unique = true,nullable = false)
        private String email;
        private String password;
        private String msisdn;
        @ManyToOne
        @JoinColumn(name = "nacionalidad_id", referencedColumnName = "id")
        private Nacionalidad nacionalidad;



        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
        }

        @Override
        public String getUsername() {
                return this.email;
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }
}
