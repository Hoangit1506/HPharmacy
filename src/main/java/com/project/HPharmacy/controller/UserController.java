package com.project.HPharmacy.controller;

import com.project.HPharmacy.entity.UserEntity;
import com.project.HPharmacy.service.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Hiển thị trang chỉnh sửa thông tin người dùng
    @GetMapping("/profile")
    public String viewProfile(Model model) {
        // Lấy thông tin người dùng hiện tại từ session hoặc security context
        UserEntity currentUser = userEntityService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
        }
        model.addAttribute("user", currentUser);
        return "/profile"; // Tên file template trang chỉnh sửa thông tin người dùng
    }

    // Xử lý cập nhật thông tin người dùng
    @PostMapping("/profile/update")
    public String updateProfile(
            @ModelAttribute("user") @Valid UserEntity user,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Dữ liệu nhập vào không hợp lệ.");
            return "redirect:/profile"; // Nếu có lỗi validate, quay lại trang chỉnh sửa
        }

        try {
            user.setId(userEntityService.getCurrentUser().getId());
            UserEntity updatedUser = userEntityService.updateUserProfile(user);
            model.addAttribute("success", "Thông tin đã được cập nhật thành công!");
            // Lấy lại thông tin người dùng mới sau khi cập nhật
            model.addAttribute("user", updatedUser); // Cập nhật lại đối tượng người dùng mới vào model
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi cập nhật thông tin: " + e.getMessage());
        }

        return "redirect:/profile"; // Quay lại trang profile với thông báo
    }

    @PostMapping("/profile/change-password")
    public String changePassword(
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes) {
        try {
            // Kiểm tra mật khẩu cũ có đúng không
            UserEntity currentUser = userEntityService.getCurrentUser();
            if (currentUser == null) {
                redirectAttributes.addFlashAttribute("error", "Bạn cần đăng nhập để thay đổi mật khẩu.");
                return "redirect:/profile";
            }

            if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
                redirectAttributes.addFlashAttribute("error", "Mật khẩu cũ không đúng.");
                return "redirect:/profile";
            }

            // Kiểm tra mật khẩu mới và mật khẩu xác nhận có khớp không
            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp.");
                return "redirect:/profile";
            }

            // Tiến hành thay đổi mật khẩu
            userEntityService.changeUserPassword(newPassword);
            redirectAttributes.addFlashAttribute("success", "Mật khẩu đã được thay đổi thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Đổi mật khẩu không thành công: " + e.getMessage());
        }

        return "redirect:/profile";
    }


}
