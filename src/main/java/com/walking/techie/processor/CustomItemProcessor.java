package com.walking.techie.processor;

import com.walking.techie.model.Domain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomItemProcessor implements ItemProcessor<Domain, Domain> {

  @Override
  public Domain process(Domain item) throws Exception {
    log.info("Item is processing : " + item);
    return item;
  }
}
