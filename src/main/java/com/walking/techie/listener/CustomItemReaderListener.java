package com.walking.techie.listener;

import com.walking.techie.model.Domain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomItemReaderListener implements ItemReadListener<Domain> {

  @Override
  public void beforeRead() {
    log.info("ItemReadListener ---- before read ");
  }

  @Override
  public void afterRead(Domain item) {
    log.info("ItemReadListener ---- after read ");
  }

  @Override
  public void onReadError(Exception ex) {
    log.info("ItemReadListener ---- exception");
  }
}
