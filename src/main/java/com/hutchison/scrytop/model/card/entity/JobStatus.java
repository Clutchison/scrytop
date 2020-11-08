package com.hutchison.scrytop.model.card.entity;

import com.hutchison.scrytop.model.card.enums.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

@Entity()
@Table(name = "JOB_STATUS")
@Builder
public class JobStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "id")
    Long id;

    @Column(nullable = false, name = "jobName")
    String jobName;

    @Column(nullable = false, name = "status")
    Status status;

    @Column(nullable = false, name = "startTime")
    Instant startTime;

    @Column(name = "endTime")
    Instant endTime;

    @Column(name = "error")
    String error;

}
