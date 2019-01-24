package cw.project.x1.component.endpoint;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.Instant;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/GROUP_OPERATOR")
public class OperatorCtrl {
    private static Logger log = LoggerFactory.getLogger(OperatorCtrl.class);

    @ApiOperation("GROUP_OPERATOR api endpoint info")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/info")
    public String info() {
        return format("GROUP_OPERATOR api endpoint responded at %s", Date.from(Instant.now()));
    }
}