package com.walking.techie.listener;

import com.walking.techie.model.Domain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomProcessListener implements ItemProcessListener<Domain, Domain> {

  @Override
  public void beforeProcess(Domain item) {
    log.info("ItemProcessListener ---- beforeProcess");
  }

  @Override
  public void afterProcess(Domain item, Domain result) {
    log.info("ItemProcessListener ---- afterProcess");
  }

  @Override
  public void onProcessError(Domain item, Exception e) {
    log.info("ItemProcessListener ---- onProcessError");
  }
}
