package cw.project.x1.dto;

import cw.project.x1.component.endpoint.FileUploadCtrl;

import java.io.Serializable;
import java.util.Objects;

public class UploadFileDTO implements Serializable {
    public String fileName;
    public String fileDownloadUri;
    public String fileType;
    public long size;

    public UploadFileDTO(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public UploadFileDTO() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UploadFileDTO)) return false;
        UploadFileDTO that = (UploadFileDTO) o;
        return Objects.equals(fileDownloadUri, that.fileDownloadUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileDownloadUri);
    }
}
