package com.atosalves.park_api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true, length = 100)
        private String username;

        @Column(nullable = false, length = 200)
        private String password;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 25)
        private Role role = Role.ROLE_CUSTOMER;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        private String createBy;
        private String updatedBy;

        public enum Role {
                ROLE_ADMIN, ROLE_CUSTOMER
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((id == null) ? 0 : id.hashCode());
                return result;
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                User other = (User) obj;
                if (id == null) {
                        if (other.id != null)
                                return false;
                } else if (!id.equals(other.id))
                        return false;
                return true;
        }

        @Override
        public String toString() {
                return "User [id=" + id + "]";
        }

}
