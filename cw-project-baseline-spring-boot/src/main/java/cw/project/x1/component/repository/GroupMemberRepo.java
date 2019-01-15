package cw.project.x1.component.repository;

import cw.project.x1.model.XGroupMember;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
//@Api("XGroupMember Entity")
//@RepositoryRestResource(path = "group-member")
public interface GroupMemberRepo extends JpaRepository<XGroupMember, Long> {
    Logger log = LoggerFactory.getLogger(GroupMemberRepo.class);
}
