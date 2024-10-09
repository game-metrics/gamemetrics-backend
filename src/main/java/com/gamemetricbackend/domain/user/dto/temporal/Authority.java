package com.gamemetricbackend.domain.user.dto.temporal;

import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.domain.user.entitiy.UserRoleEnum;

public class Authority {
        private UserRoleEnum authorityName;

        public Authority(UserRoleEnum authorityName) {
            this.authorityName = authorityName;
        }

        // Getters and Setters
        public UserRoleEnum getAuthorityName() {
            return authorityName;
        }

        public void setUserRoleName(UserRoleEnum userRoleName) {
            this.authorityName = authorityName;
        }
    }
