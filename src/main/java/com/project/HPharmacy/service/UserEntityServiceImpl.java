package com.project.HPharmacy.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.HPharmacy.entity.UserEntity;
import com.project.HPharmacy.repository.RoleRepository;
import com.project.HPharmacy.repository.UserEntityRepository;

@Service
public class UserEntityServiceImpl implements UserEntityService{
	@Autowired
	private UserEntityRepository userEntityRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private RoleRepository roleRepository;
	
	@Override
	public Optional<UserEntity> findByUsername(String username) {
		// TODO Auto-generated method stub
		return userEntityRepository.findByUsername(username);
	}

	@Override
	public Optional<UserEntity> findByEmail(String email) {
		// TODO Auto-generated method stub
		return userEntityRepository.findByEmail(email);
	}
	
	@Override
	public Optional<UserEntity> findByPhone(String phone) {
		// TODO Auto-generated method stub
		return userEntityRepository.findByPhone(phone);
	}

	@Override
	public void createNewUserEntity(UserEntity userEntity) {
		// TODO Auto-generated method stub
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		userEntity.setRoles(Set.of(roleRepository.findByRole("GUEST")));
		userEntityRepository.save(userEntity);
	}

	@Override
	public UserEntity getCurrentUser() {
		// TODO Auto-generated method stub
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userEntityRepository.findByUsername(username).orElse(null);
	}

	@Override
	public UserEntity updateUserProfile(UserEntity user) {
		// TODO Auto-generated method stub
		UserEntity existingUser = userEntityRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));

        // Chỉ cập nhật thông tin cần thiết (tránh ghi đè quyền hoặc các trường quan trọng)
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());

        return userEntityRepository.save(existingUser);
	}

	@Override
	public void changeUserPassword(String newPassword) {
		// TODO Auto-generated method stub
		UserEntity currentUser = getCurrentUser();
	    currentUser.setPassword(passwordEncoder.encode(newPassword));
	    userEntityRepository.save(currentUser);
	}

	
}
