package cw.project.x1.component.repository;

import cw.project.x1.model.XFileUploadRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
//@Api("Authority Entity")
//@RepositoryRestResource(path = "authority")
public interface XFileUploadRecordRepo extends JpaRepository<XFileUploadRecord, Long> {
    Logger log = LoggerFactory.getLogger(XFileUploadRecordRepo.class);
}
