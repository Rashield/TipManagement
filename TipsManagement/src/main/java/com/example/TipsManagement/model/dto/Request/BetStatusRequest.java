package com.example.TipsManagement.model.dto.Request;

import com.example.TipsManagement.model.enums.Status;
import lombok.Data;

@Data
public class BetStatusRequest {
    private Status status;
}
