package com.hutchison.scrytop.repository;

import com.hutchison.scrytop.model.card.entity.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobStatusRepository extends JpaRepository<JobStatus, Long> {

    JobStatus findFirstByJobNameOrderByIdDesc(String jobName);
}
