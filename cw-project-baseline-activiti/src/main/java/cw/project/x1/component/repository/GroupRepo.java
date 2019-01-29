package cw.project.x1.component.repository;

import cw.project.x1.model.XGroup;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
@Transactional
//@Api("Group Entity Repository")
//@RepositoryRestResource(path = "dr-groups")
public interface GroupRepo extends JpaRepository<XGroup, Long> {
    Logger log = LoggerFactory.getLogger(GroupRepo.class);

    @ApiOperation("find one group by its name")
    @GetMapping
    XGroup findByName(@RequestParam("group_name") String groupName);

}
