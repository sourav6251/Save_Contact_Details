package com.contact.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Password {

    @Size(min = 5, message = "")
    // @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,20}$",
    //     message = "Password must contain at least one uppercase & lower  letter, one digit, and one special character")
    private String oldPassword;

    @Size(min = 5,message = "")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,20}$", 
        message = "Password must contain at least one uppercase & lower  letter, one digit, and one special character")
    private String newPassword;
    
    @Size(min = 5, message = "")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,20}$",
        message = "Password must contain at least one uppercase & lower letter, one digit, and one special character")
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
