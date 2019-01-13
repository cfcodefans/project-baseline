package cw.project.x1.commons;

import java.util.Map;

public class ServiceRespDTO<T> extends ServiceDTO {

    enum Status {
        success, fail
    }

    public Status status = Status.success;
    public String msg = "ok";

    public T data;

    public ServiceRespDTO() {

    }

    public ServiceRespDTO(T data) {
        this.data = data;
    }

    public static <T> ServiceRespDTO<T> fail(Throwable e, String msg) {
        ServiceRespDTO resp = new ServiceRespDTO<>(
            Map.of(
                "exception", e.getClass().getName()
                //"source", e.getStackTrace()[0]
            )
        );
        resp.status = Status.fail;
        resp.msg = msg;
        return resp;
    }

    public static <T> ServiceRespDTO<T> fail(String msg) {
        ServiceRespDTO<T> resp = new ServiceRespDTO<>(null);
        resp.status = Status.fail;
        resp.msg = msg;
        return resp;
    }
}
