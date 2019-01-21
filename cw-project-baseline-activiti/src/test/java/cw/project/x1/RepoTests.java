package cw.project.x1;

import cw.project.x1.commons.Jsons;
import cw.project.x1.component.repository.UserRepo;
import cw.project.x1.model.XGroup;
import cw.project.x1.model.XUser;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class RepoTests extends SettingTests {
    private static Logger log = LoggerFactory.getLogger(RepoTests.class);

    @Autowired
    private UserRepo ur;

    @Autowired
    private EntityManager em;

    @Test
    public void testGroupsAndUsers() {
        Map<XGroup, List<XUser>> userAndGroups = ur.findUserAndGroups();
        log.info(Jsons.toString(userAndGroups));

        if (false) {
            String hql = "select gm, u from XGroupMember gm left join fetch gm.group, XUser u where gm.username = u.username";
            List reList = em.createQuery(hql).getResultList();
            log.info(Jsons.toString(reList));
        }
    }
}
