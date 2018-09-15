package com.ems.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "file_uploads", catalog = "ems")
@Data
public class FileUploadEntity {

    @Id
    @Column(name = "file_id")
    private Integer fileId;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_type")
    private String fileType;
    @Lob
    @Column(name = "file_content")
    private byte[] fileContent;
}
