package com.project.HPharmacy.service;

import com.project.HPharmacy.entity.UserEntity;

import java.util.Optional;

public interface UserEntityService {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPhone(String phone);

    void createNewCartForUser(UserEntity user);

    void createNewUserEntity(UserEntity userEntity);

    UserEntity getCurrentUser(); // Lấy thông tin người dùng hiện tại

    UserEntity updateUserProfile(UserEntity user); // Cập nhật thông tin người dùng

    void changeUserPassword(String newPassword);
}
