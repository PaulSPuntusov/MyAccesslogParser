
public enum HttpMethod {

    CONNECT{String method(){return "CONNECT";}},
    DELETE{String method(){return "DELETE";}},
    GET{String method(){return "GET";}},
    HEAD{String method(){return "HEAD";}},
    OPTIONS{String method(){return "OPTIONS";}},
    PATCH{String method(){return "PATCH";}},
    POST{String method(){return "POST";}},
    PUT{String method(){return "PUT";}},
    TRACE{String method(){return "TRACE";}};
    abstract String method();


}
