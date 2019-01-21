package cw.project.x1.component.repository;

import cw.project.x1.model.XAuthority;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
//@Api("Authority Entity")
//@RepositoryRestResource(path = "authority")
public interface AuthorityRepo extends JpaRepository<XAuthority, Long> {
    Logger log = LoggerFactory.getLogger(AuthorityRepo.class);
}
