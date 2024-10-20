package com.fds.siscaa.interfaceAdapters.controller.parser;

import com.fds.siscaa.domain.entity.ApplicationEntity;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import com.fds.siscaa.interfaceAdapters.controller.dto.ApplicationDto;

public class ApplicationDtoParser {
  public List<ApplicationDto> fromApplicationDtoEntities(List<ApplicationEntity> app) {
    return app.stream().map(ap -> new ApplicationDto(ap.code, ap.name, ap.monthlyPrice)).collect(Collectors.toList());
  }
}
