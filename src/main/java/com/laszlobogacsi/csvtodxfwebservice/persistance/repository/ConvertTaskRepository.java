package com.laszlobogacsi.csvtodxfwebservice.persistance.repository;


import com.laszlobogacsi.csvtodxfwebservice.persistance.model.ConvertTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConvertTaskRepository extends CrudRepository<ConvertTask, Long> {

    Optional<ConvertTask> findByDownloadId(String downloadId);
    Optional<ConvertTask> findByJobId(String jobId);

}
