package com.newsnow.rescalator.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TaskDTO {
    private LocalDateTime timestamp;
    private String md5;
    private int width;
    private int height;
    private String imageUrl;

    public String getResolution() {
        return this.width+"x"+this.height;
    }

}
