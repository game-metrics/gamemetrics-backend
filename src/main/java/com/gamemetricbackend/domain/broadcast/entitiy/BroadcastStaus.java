package com.gamemetricbackend.domain.broadcast.entitiy;

import com.gamemetricbackend.domain.user.entitiy.UserRoleEnum.Authority;
import lombok.Getter;

public enum BroadcastStaus {
    ONAIR(Status.ONAIR),  // 사용자 권한
    OFFAIR(Status.OFFAIR);  // 관리자 권한
    private final String status;
    BroadcastStaus(String status) {
        this.status = status;
    }

    public static class Status {
        public static final String ONAIR = "ON_AIR";
        public static final String OFFAIR = "OFF_AIR";
    }
}
