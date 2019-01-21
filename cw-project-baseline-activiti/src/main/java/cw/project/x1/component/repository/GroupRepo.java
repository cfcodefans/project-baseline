package cw.project.x1.component.repository;

import cw.project.x1.model.XGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
//@Api("Group Entity")
//@RepositoryRestResource(path = "groups")
public interface GroupRepo extends JpaRepository<XGroup, Long> {
    Logger log = LoggerFactory.getLogger(GroupRepo.class);

    @ApiOperation("find one group by its name")
    XGroup findByName(@Param("group_name") String groupName);

}
