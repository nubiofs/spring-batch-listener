package com.walking.techie.jobs;

import com.walking.techie.listener.CustomChunkListener;
import com.walking.techie.listener.CustomItemReaderListener;
import com.walking.techie.listener.CustomItemWriterListener;
import com.walking.techie.listener.CustomProcessListener;
import com.walking.techie.listener.CustomStepListener;
import com.walking.techie.model.Domain;
import com.walking.techie.processor.CustomItemProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Slf4j
@Configuration
@EnableBatchProcessing
public class SpringBatchListener {

  @Autowired
  private JobBuilderFactory jobBuilderFactory;
  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Value("csv/domain*.csv")
  private Resource[] resources;

  @Bean
  public Job readFiles() {
    return jobBuilderFactory.get("readFiles").incrementer(new RunIdIncrementer()).
        start(step1(null, null, null)).build();
  }

  @Bean
  public Step step1(CustomItemProcessor itemProcessor, CustomChunkListener chunkListener,
      CustomProcessListener processListener) {
    return stepBuilderFactory.get("step1").<Domain, Domain>chunk(5)
        .reader(multiResourceItemReader()).processor(itemProcessor).writer(writer())
        .listener(processListener).listener(itemReaderListener())
        .listener(itemWriterListener()).listener(customStepListener()).listener(chunkListener)
        .build();
  }

  @Bean
  public CustomStepListener customStepListener() {
    return new CustomStepListener();
  }

  @Bean
  public CustomItemWriterListener itemWriterListener() {
    return new CustomItemWriterListener();
  }

  @Bean
  public CustomItemReaderListener itemReaderListener() {
    return new CustomItemReaderListener();
  }

  @Bean
  public MultiResourceItemReader<Domain> multiResourceItemReader() {
    log.info("Multi reader called");
    MultiResourceItemReader<Domain> resourceItemReader = new MultiResourceItemReader<Domain>();
    resourceItemReader.setResources(resources);
    resourceItemReader.setDelegate(reader());
    return resourceItemReader;
  }

  @Bean
  public FlatFileItemReader<Domain> reader() {
    log.info("reader called");
    FlatFileItemReader<Domain> reader = new FlatFileItemReader<Domain>();
    reader.setLineMapper(new DefaultLineMapper() {{
      setLineTokenizer(new DelimitedLineTokenizer() {{
        setNames(new String[]{"id", "domain"});
      }});
      setFieldSetMapper(new BeanWrapperFieldSetMapper<Domain>() {{
        setTargetType(Domain.class);
      }});
    }});
    return reader;
  }

  @Bean
  public FlatFileItemWriter<Domain> writer() {
    log.info("writer called");
    FlatFileItemWriter<Domain> writer = new FlatFileItemWriter<>();
    writer.setResource(new FileSystemResource("output/domain.all.csv"));
    writer.setAppendAllowed(true);
    writer.setLineAggregator(new DelimitedLineAggregator<Domain>() {{
      setDelimiter(",");
      setFieldExtractor(new BeanWrapperFieldExtractor<Domain>() {{
        setNames(new String[]{"id", "domain"});
      }});
    }});
    return writer;
  }
}
