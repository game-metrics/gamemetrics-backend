package com.gamemetricbackend.domain.broadcast.entitiy;

public enum BroadcastStatus {
    ONAIR(Status.ONAIR),  // 사용자 권한
    OFFAIR(Status.OFFAIR);  // 관리자 권한
    private final String status;
    BroadcastStatus(String status) {
        this.status = status;
    }

    public static class Status {
        public static final String ONAIR = "ON_AIR";
        public static final String OFFAIR = "OFF_AIR";
    }
}
