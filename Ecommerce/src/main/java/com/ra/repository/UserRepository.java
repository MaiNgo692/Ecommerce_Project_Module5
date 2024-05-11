package com.ra.repository;


import com.ra.model.entity.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity,Long>,PagingAndSortingRepository<UsersEntity,Long>{
    UsersEntity findUsersEntityByUsername(String username);
    @Query("select u from UsersEntity u")
    Page<UsersEntity> findAllQuery (Pageable pageable);

    Page<UsersEntity> findUsersEntitiesByFullnameContainingIgnoreCase (String searchName,Pageable pageable);
}
