package com.project.HPharmacy.controller;

//import java.util.Set;

import com.project.HPharmacy.dto.UserRegistrationDto;
import com.project.HPharmacy.entity.UserEntity;
import com.project.HPharmacy.service.UserEntityServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {
//	@Autowired
//	private RoleRepository roleRepository;

    @Autowired
    private UserEntityServiceImpl userEntityServiceImpl;

    @GetMapping("/registration")
    public String registration(Model model) {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        model.addAttribute("userView", userRegistrationDto);

        return "/registration";
    }

    @PostMapping("/registration")
    public ModelAndView createNewUser(@ModelAttribute("userView") @Valid UserRegistrationDto userRegistrationDto, BindingResult bindingResult) {
        //BindingResult là nơi Spring chứa kết quả của việc xác thực dữ liệu.

        if (userEntityServiceImpl.findByEmail(userRegistrationDto.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "error.email", "The email is already registered, please use another one.");
        }

        if (userEntityServiceImpl.findByPhone(userRegistrationDto.getPhone()).isPresent()) {
            bindingResult.rejectValue("phone", "error.phone", "The phone is already registered, please use another one.");
        }

        if (userEntityServiceImpl.findByUsername(userRegistrationDto.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "error.username", "There is already a user registered with the username provided.");
        }

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/registration");
            return modelAndView;
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setFullName(userRegistrationDto.getFullName());
            userEntity.setEmail(userRegistrationDto.getEmail());
            userEntity.setPhone(userRegistrationDto.getPhone());
            userEntity.setUsername(userRegistrationDto.getUsername());
            userEntity.setPassword(userRegistrationDto.getPassword());

//			// Convert enum.values() into Roles:
//			Set<Role> userRoles = roleRepository.findRolesByNameSet(userRegistrationDto.getRoles());
//			userEntity.setRoles(userRoles);

            userEntityServiceImpl.createNewUserEntity(userEntity);

            modelAndView.addObject("successMessage", "Username, " + userEntity.getUsername() + " has been registered successfully. Now you should login for shopping.");

            modelAndView.addObject("userView", userRegistrationDto);

            modelAndView.setViewName("/registration");
            return modelAndView;
        }
    }
}
