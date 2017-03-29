package com.walking.techie.listener;

import com.walking.techie.model.Domain;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomItemWriterListener implements ItemWriteListener<Domain> {

  @Override
  public void beforeWrite(List<? extends Domain> items) {
    log.info("ItemWriteListener ---- before write" + items);
  }

  @Override
  public void afterWrite(List<? extends Domain> items) {
    log.info("ItemWriteListener ---- after write" + items);

  }

  @Override
  public void onWriteError(Exception exception, List<? extends Domain> items) {
    log.info("ItemWriteListener ---- exception" + items);
  }
}
