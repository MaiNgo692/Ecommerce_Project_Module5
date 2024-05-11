package com.ra.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@jakarta.persistence.Table(name = "address", schema = "ecommerce", catalog = "")
public class AddressEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "address_id", nullable = false)
    private long addressId;
    @Basic
    @Column(name = "user_id", nullable = true)
    private Long userId;
    @Basic
    @Column(name = "full_address", nullable = true, length = 255)
    private String fullAddress;
    @Basic
    @Column(name = "phone", nullable = true, length = 15)
    private String phone;
    @Basic
    @Column(name = "receive_name", nullable = true, length = 50)
    private String receiveName;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId", referencedColumnName = "user_id", nullable = false)
//    private UsersEntity usersByUserId;

//    public AddressEntity(long addressId, Long userId, String fullAddress, String phone, String receiveName) {
//        this.addressId = addressId;
//        this.userId = userId;
//        this.fullAddress = fullAddress;
//        this.phone = phone;
//        this.receiveName = receiveName;
//        this.usersByUserId = new UsersEntity(userId);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return addressId == that.addressId && Objects.equals(userId, that.userId) && Objects.equals(fullAddress, that.fullAddress) && Objects.equals(phone, that.phone) && Objects.equals(receiveName, that.receiveName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, userId, fullAddress, phone, receiveName);
    }
}
