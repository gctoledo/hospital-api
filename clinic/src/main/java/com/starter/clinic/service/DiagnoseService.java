package com.starter.clinic.service;

import com.starter.clinic.dto.response.DiagnoseResult;

import java.util.List;

public interface DiagnoseService {
    DiagnoseResult execute(List<String> symptoms);
}
