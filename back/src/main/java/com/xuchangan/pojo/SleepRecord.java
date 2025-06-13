package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SleepRecord {
    private int sleepId;
    private int userId;
    private LocalDateTime sleepDate;
    private LocalDateTime bedTime;
    private LocalDateTime wakeupTime;
    private int sleepDuration;
    private String sleepQuality;
}
