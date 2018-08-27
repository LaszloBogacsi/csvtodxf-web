package com.laszlobogacsi.csvtodxfwebservice.persistance.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConvertTask {
    @Id
    @GeneratedValue()
    private long id;

    private String jobId;

    private String downloadId;

    private String downloadPath;

}
