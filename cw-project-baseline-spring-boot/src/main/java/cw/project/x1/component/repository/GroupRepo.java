package cw.project.x1.component.repository;

import cw.project.x1.model.XGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface GroupRepo extends JpaRepository<XGroup, Long> {
    Logger log = LoggerFactory.getLogger(GroupRepo.class);
}
