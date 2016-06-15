package org.chodavarapu.datamill.http.impl;

import org.chodavarapu.datamill.http.Method;
import org.chodavarapu.datamill.http.ServerRequest;
import org.chodavarapu.datamill.http.Response;
import org.chodavarapu.datamill.http.Route;
import rx.Observable;

import java.util.Set;

/**
 * @author Ravi Chodavarapu (rchodava@gmail.com)
 */
public class TautologyMatcher extends RouteMatcher {
    public TautologyMatcher(Route route) {
        super(route);
    }

    public TautologyMatcher(Observable<Response> response) {
        super(r -> response);
    }

    public TautologyMatcher(Response response) {
        super(r -> Observable.just(response));
    }

    @Override
    protected boolean matches(ServerRequest request) {
        return true;
    }

    @Override
    public Set<Method> queryOptions(ServerRequest request) {
        return null;
    }
}