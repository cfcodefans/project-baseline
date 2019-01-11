package cw.project.x1.component.repository;

import cw.project.x1.model.XGroup;
import cw.project.x1.model.XGroupMember;
import cw.project.x1.model.XUser;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<XUser, Long> {
    Logger log = LoggerFactory.getLogger(UserRepo.class);

    //    @Query("select u, g from XUser u, XGroupMember g where u.name = g.username and g.group.name in :groups")
    @Query("select gm, u from XGroupMember gm left join fetch gm.group, XUser u where gm.username = u.username and gm.group.name in :groups")
    List<Object> findUsersAndGroups(@Param("groups") List<String> groups);

    @Query("select gm, u from XGroupMember gm left join fetch gm.group, XUser u where gm.username = u.username")
    List<Object> findUsersAndGroups();

    default Map<XGroup, List<XUser>> findUserAndGroups(String... groups) {
        Map<XGroup, List<XUser>> reMap = new HashMap<>();

        List<Object> groupsAndUsers = null;
        if (ArrayUtils.isEmpty(groups)) {
            groupsAndUsers = findUsersAndGroups();
        } else {
            groupsAndUsers = findUsersAndGroups(List.of(groups));
        }
        groupsAndUsers
            .stream()
            .map(obj -> (Object[]) obj)
            .forEach(row -> reMap.computeIfAbsent(((XGroupMember) row[0]).group, k -> new ArrayList()).add((XUser) row[1]));
        return reMap;
    }
}
