package cw.project.activiti.tests.process;

import cw.project.activiti.tests.RuntimeTestConfig;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.process.runtime.conf.ProcessRuntimeConfiguration;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static cw.project.activiti.tests.RuntimeTestConfig.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ProcessRuntimeTests {
    static final Logger log = Logger.getLogger(ProcessRuntimeTests.class.getSimpleName());

    private static final String CATEGORIZE_PROCESS = "categorizeProcess";
    private static final String CATEGORIZE_HUMAN_PROCESS = "categorizeHumanProcess";

    @Autowired
    private ProcessRuntime procRuntime;

    @Autowired
    private UserDetailsService userDetailsService;

    @Before
    public void init() {
        //Reset test variables
        RuntimeTestConfig.discardImageConnectorExecuted = false;
        RuntimeTestConfig.tagImageConnectorExecuted = false;
        RuntimeTestConfig.discardImageConnectorExecuted = false;
    }

    @Test
    @WithUserDetails(value = "salaboy", userDetailsServiceBeanName = UDS)
    public void shouldGetConfig() {
        ProcessRuntimeConfiguration cfg = procRuntime.configuration();
        assertNotNull(cfg);
    }

    @Test
    @WithUserDetails(value = "salaboy", userDetailsServiceBeanName = UDS)
    public void shouldGetAvailableProcessDefinitionForGivenUser() {
        Page<ProcessDefinition> procDefPage = procRuntime.processDefinitions(Pageable.of(0, 50));

        assertNotNull(procDefPage.getContent());
        assertTrue(procDefPage.getContent()
            .stream().map(ProcessDefinition::getKey)
            .collect(Collectors.toSet()).containsAll(
                Set.of(CATEGORIZE_PROCESS, CATEGORIZE_HUMAN_PROCESS)
            ));
    }

    @Test
    @WithUserDetails(value = "salaboy", userDetailsServiceBeanName = UDS)
    public void createProcInstanceAndValidateHappyPath() {
        ProcessInstance categorizeProc = procRuntime.start(
            ProcessPayloadBuilder.start()
                .withProcessDefinitionKey(CATEGORIZE_PROCESS)
                .withVariable(EXPECTED_KEY, true).build());

        assertNotNull(categorizeProc);
        assertEquals(categorizeProc.getStatus(), ProcessInstance.ProcessInstanceStatus.COMPLETED);
        assertEquals(processImageConnectorExecuted, true);
        assertEquals(tagImageConnectorExecuted, true);
        assertEquals(discardImageConnectorExecuted, false);
    }

    @Test
    @WithUserDetails(value = "salaboy", userDetailsServiceBeanName = UDS)
    public void createProcInstanceAndValidateDiscardPath() {
        ProcessInstance categorizeProc = procRuntime.start(
            ProcessPayloadBuilder.start()
                .withProcessDefinitionKey(CATEGORIZE_PROCESS)
                .withVariable(EXPECTED_KEY, false).build());

        assertNotNull(categorizeProc);
        assertEquals(categorizeProc.getStatus(), ProcessInstance.ProcessInstanceStatus.COMPLETED);
        assertEquals(processImageConnectorExecuted, true);
        assertEquals(tagImageConnectorExecuted, false);
        assertEquals(discardImageConnectorExecuted, true);
    }

    @Test
    @WithUserDetails(value = "salaboy", userDetailsServiceBeanName = UDS)
    public void shouldGetProcDefinitionFromDefinitionKey() {
        ProcessDefinition categorizeHumanProc = procRuntime.processDefinition(CATEGORIZE_HUMAN_PROCESS);

        assertNotNull(categorizeHumanProc);
        assertEquals(categorizeHumanProc.getName(), CATEGORIZE_HUMAN_PROCESS);
        assertTrue(categorizeHumanProc.getId().contains(CATEGORIZE_HUMAN_PROCESS));
    }

    @Test
    @WithUserDetails(value = "salaboy", userDetailsServiceBeanName = UDS)
    public void getProcInstance() {
        Pageable page = Pageable.of(0, 50);
        Page<ProcessInstance> procInstancePage = procRuntime.processInstances(page);
        assertNotNull(procInstancePage.getContent());
        assertEquals(procInstancePage.getContent().size(), 0);

        //given
        //start a process with a business key to check filters
        procRuntime.start(ProcessPayloadBuilder.start()
            .withProcessDefinitionKey(CATEGORIZE_HUMAN_PROCESS)
            .withVariable(EXPECTED_KEY, true)
            .withBusinessKey("my business key")
            .build());

        //when
        procInstancePage = procRuntime.processInstances(
            page,
            ProcessPayloadBuilder.processInstances().build());

        assertNotNull(procInstancePage.getContent());
        assertEquals(procInstancePage.getContent().size(), 1);

        //when
        procInstancePage = procRuntime.processInstances(
            page,
            ProcessPayloadBuilder.processInstances().withBusinessKey("other key").build());

        assertNotNull(procInstancePage.getContent());
        assertEquals(procInstancePage.getContent().size(), 0);

        //when
        procInstancePage = procRuntime.processInstances(
            page,
            ProcessPayloadBuilder.processInstances().withBusinessKey("my business key").build());

        assertNotNull(procInstancePage.getContent());
        assertEquals(procInstancePage.getContent().size(), 1);

        procInstancePage = procRuntime.processInstances(page,
            ProcessPayloadBuilder
                .processInstances()
                .suspended()
                .build());

        assertNotNull(procInstancePage.getContent());
        assertEquals(procInstancePage.getContent().size(), 0);

        procInstancePage = procRuntime.processInstances(page,
            ProcessPayloadBuilder
                .processInstances()
                .active()
                .build());

        assertNotNull(procInstancePage.getContent());
        assertEquals(procInstancePage.getContent().size(), 1);

        procInstancePage = procRuntime.processInstances(page,
            ProcessPayloadBuilder
                .processInstances()
                .suspended()
                .active()
                .build());

        assertNotNull(procInstancePage.getContent());
        assertEquals(procInstancePage.getContent().size(), 1);

        ProcessInstance procInstance = procInstancePage.getContent().get(0);
        ProcessInstance suspendedProcInstance = procRuntime.suspend(ProcessPayloadBuilder.suspend(procInstance));

        assertNotNull(suspendedProcInstance);
        assertEquals(suspendedProcInstance.getStatus(),
            ProcessInstance.ProcessInstanceStatus.SUSPENDED);

        procInstancePage = procRuntime.processInstances(page,
            ProcessPayloadBuilder.processInstances().active().build());
        assertNotNull(procInstancePage.getContent());
        assertEquals(procInstancePage.getContent().size(), 0);

        procInstancePage = procRuntime.processInstances(page,
            ProcessPayloadBuilder.processInstances().suspended().build());
        assertNotNull(procInstancePage.getContent());
        assertEquals(procInstancePage.getContent().size(), 1);

        procRuntime.resume(ProcessPayloadBuilder.resume(procInstance));
        procInstancePage = procRuntime.processInstances(page,
            ProcessPayloadBuilder.processInstances().suspended().build());
        assertNotNull(procInstancePage.getContent());
        assertEquals(procInstancePage.getContent().size(), 0);

        procInstancePage = procRuntime.processInstances(page,
            ProcessPayloadBuilder.processInstances().active().build());
        assertNotNull(procInstancePage.getContent());
        assertEquals(procInstancePage.getContent().size(), 1);

        ProcessInstance getSingleProcInstance = procRuntime.processInstance(procInstance.getId());
        assertNotNull(getSingleProcInstance);
        assert
    }

}
