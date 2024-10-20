package com.fds.siscaa.interfaceAdapters.controller.parser;

import com.fds.siscaa.domain.entity.ApplicationEntity;

import java.util.List;
import java.util.stream.Collectors;
import com.fds.siscaa.interfaceAdapters.controller.dto.ApplicationDto;

public class ApplicationDtoParser {
  public List<ApplicationDto> parseApplicationEntitiesToDto(List<ApplicationEntity> app) {
    return app.stream().map(ap -> new ApplicationDto(ap))
        .collect(Collectors.toList());
  }
}
