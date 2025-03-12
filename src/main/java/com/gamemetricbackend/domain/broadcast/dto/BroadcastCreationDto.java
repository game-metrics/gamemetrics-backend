package com.gamemetricbackend.domain.broadcast.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BroadcastCreationDto {
    String title;
    String thumbNail;
    Long catagoryId;
}
