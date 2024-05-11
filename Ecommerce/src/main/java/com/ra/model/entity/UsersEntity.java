package com.ra.model.entity;

import com.ra.model.dto.request.UserRoleRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Objects;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "ecommerce", catalog = "")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Basic
    @Size(min = 6,max = 100,message = "name.size-error")
    @Column(name = "username", nullable = false, length = 100,unique=true)
    @NotBlank(message = "field.not-blank")
    @Pattern(regexp ="^[a-zA-Z0-9]*$",message = "name.error")
    private String username;
    @Basic
    @Email(message = "email.error")
    @Column(name = "email", nullable = true, length = 255)
    private String email;
    @Basic
    @Column(name = "fullname", nullable = false, length = 100)
    @NotBlank(message = "field.not-blank")
    private String fullname;
    @Basic
    @Column(name = "status", nullable = true)
    private Boolean status;
    @Basic
    @Column(name = "password", nullable = true, length = 255)
    private String password;
    @Basic
    @Column(name = "avatar", nullable = true, length = 255)
    private String avatar;
    @Basic
    @Pattern(regexp ="(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})" ,message = "phone.error")
    @NotBlank(message = "field.not-blank")
    @Column(name = "phone", nullable = false, length = 15,unique=true)
    private String phone;
    @Basic
    @Column(name = "address", nullable = false, length = 255)
    @NotBlank(message = "field.not-blank")
    private String address;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Date createdAt;
    @Basic
    @Column(name = "update_at", nullable = true)
    private Date updateAt;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByUserId")
    private List<UserRoleEntity> userRoleEntities;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByUserId")
//    private List<ShopingCartEntity> shopingCartEntities;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByUserId")
//    private List<WishListEntity> wishListEntities;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByUserId")
//    private List<OrdersEntity> ordersEntities;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByUserId")
//    private List<AddressEntity> addressEntities;
    public UsersEntity(Long userId) {
        this.userId = userId;
    }

    public UsersEntity(Long userId, String username, String email, String fullname, Boolean status, String password, String avatar, String phone, String address, Date createdAt, Date updateAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.status = status;
        this.password = password;
        this.avatar = avatar;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return userId == that.userId && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(fullname, that.fullname) && Objects.equals(status, that.status) && Objects.equals(password, that.password) && Objects.equals(avatar, that.avatar) && Objects.equals(phone, that.phone) && Objects.equals(address, that.address) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updateAt, that.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email, fullname, status, password, avatar, phone, address, createdAt, updateAt);
    }
}
