package com.atosalves.park_api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers_parking_spots")
@EntityListeners(AuditingEntityListener.class)
public class CustomerParkingSpot implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false, length = 24)
        private String receipt;

        @Column(nullable = false, length = 8)
        private String licensePlate;

        @Column(nullable = false, length = 45)
        private String brand;

        @Column(nullable = false, length = 45)
        private String model;

        @Column(nullable = false, length = 45)
        private String color;

        @Column(nullable = false)
        private LocalDateTime entryAt;

        private LocalDateTime exitAt;

        @Column(columnDefinition = "decimal(7,2)")
        private BigDecimal price;

        @Column(columnDefinition = "decimal(7,2)")
        private BigDecimal discount;

        @CreatedDate
        private LocalDateTime createdAt;

        @LastModifiedDate
        private LocalDateTime updatedAt;

        @CreatedBy
        private String createdBy;

        @LastModifiedBy
        private String updatedBy;

        @ManyToOne
        @JoinColumn(name = "customer_id", nullable = false)
        private Customer customer;

        @ManyToOne
        @JoinColumn(name = "parking_spot_id", nullable = false)
        private ParkingSpot parkingSpot;

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
                CustomerParkingSpot other = (CustomerParkingSpot) obj;
                if (id == null) {
                        if (other.id != null)
                                return false;
                } else if (!id.equals(other.id))
                        return false;
                return true;
        }

}
