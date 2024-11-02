package com.tn.summarize.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

//@Entity
//@Table("summary")
//@Cacheable(false)
//@NoArgsConstructor
//@Accessors(fluent = true)
//@Getter
//@EqualsAndHashCode
//@ToString
public class Summary
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "summaryId")
  @SequenceGenerator(name = "summaryId", sequenceName = "summary_id_seq", allocationSize = 1)
  @Column(name = "summary_id")
  private Long id;


}
