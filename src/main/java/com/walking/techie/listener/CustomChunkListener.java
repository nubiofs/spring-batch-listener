package com.walking.techie.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomChunkListener implements ChunkListener {

  @Override
  public void beforeChunk(ChunkContext context) {
    log.info("ChunkListener ---- before chunk called");
  }

  @Override
  public void afterChunk(ChunkContext context) {
    log.info("ChunkListener ---- after chunk called");
  }

  @Override
  public void afterChunkError(ChunkContext context) {
    log.info("ChunkListener ---- after chunk error called");
  }
}
