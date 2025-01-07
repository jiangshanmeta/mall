package com.meta.mall.model.repository;

import com.meta.mall.model.pojo.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    @Modifying
    @Query("UPDATE mall_user SET personalized_signature = :signature WHERE id = :id")
    boolean updateSignature(@Param("id") Integer id, @Param("signature") String signature);

}
