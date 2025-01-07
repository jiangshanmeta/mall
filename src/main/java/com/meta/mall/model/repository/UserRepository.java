package com.meta.mall.model.repository;

import com.meta.mall.model.pojo.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends ListCrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    boolean existsByUsername(String username);

}
