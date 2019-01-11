package cw.project.x1.component;

import cw.project.x1.component.repository.GroupRepo;
import cw.project.x1.component.repository.UserRepo;
import cw.project.x1.model.XGroup;
import cw.project.x1.model.XGroupAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupHook implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger log = LoggerFactory.getLogger(StartupHook.class);

//    @Autowired
//    UserRepo ur;
//
//    @Autowired
//    GroupRepo gr;
//
//    void tryToInitiateGroupsAndUsers() {
//        long groupCount = gr.count();
//        if (groupCount == 0) {
//            log.info("\n\tthere is not group\n\tgoing to create default groups");
//            XGroup g1 = new XGroup();
//            g1.name = "admin";
//            XGroupAuthority ga = new XGroupAuthority();
//            ga.group = g1;
//            ga.authority = "";
//        }
//
//        long userCount = ur.count();
//        if (userCount == 0) {
//            log.info("\n\tthere is not user\n\tgoing to create default users");
//        }
//    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent ev) {
//        tryToInitiateGroupsAndUsers();
    }
}
