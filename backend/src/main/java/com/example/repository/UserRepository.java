package com.example.repository;

import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;  // 添加这行导入

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // 修改这里：把 User findByPhone(String phone); 改为：
    Optional<User> findByPhone(String phone);  // 使用Optional
}
