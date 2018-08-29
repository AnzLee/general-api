/**
 * DefaultRequestHandlerCombiner
 *
 * @author li.liangzhe
 * @create 2018-02-01 15:36
 **/
package com.anzlee.generalapi.pojo;

import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import springfox.documentation.RequestHandler;
import springfox.documentation.spi.service.RequestHandlerCombiner;
import springfox.documentation.spring.web.plugins.CombinedRequestHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.BuilderDefaults.nullToEmptyList;
import static springfox.documentation.spi.service.contexts.Orderings.byPatternsCondition;
import static springfox.documentation.spi.service.contexts.Orderings.patternsCondition;

public class DefaultRequestHandlerCombiner implements RequestHandlerCombiner {

    private static final PathAndParametersEquivalence EQUIVALENCE = new PathAndParametersEquivalence();

    public List<RequestHandler> combine(List<RequestHandler> source) {
        List<RequestHandler> combined = new ArrayList<RequestHandler>();
        Multimap<String, RequestHandler> byPath = LinkedListMultimap.create();
        for (RequestHandler each : nullToEmptyList(source)) {
            byPath.put(patternsCondition(each).toString(), each);
        }
        for (String key : byPath.keySet()) {
            combined.addAll(combined(byPath.get(key)));
        }
        return byPatternsCondition().sortedCopy(combined);
    }

    private Collection<? extends RequestHandler> combined(Collection<RequestHandler> requestHandlers) {
        List<RequestHandler> source = newArrayList(requestHandlers);
        if (source.size() == 0 || source.size() == 1) {
            return requestHandlers;
        }
        ListMultimap<Equivalence.Wrapper<RequestHandler>, RequestHandler> groupByEquality
                = Multimaps.index(source, equivalenceAsKey());
        List<RequestHandler> combined = newArrayList();
        for (Equivalence.Wrapper<RequestHandler> path : groupByEquality.keySet()) {
            List<RequestHandler> handlers = groupByEquality.get(path);

            RequestHandler toCombine = path.get();
            if (handlers.size() > 1) {
                for (RequestHandler each : handlers) {
                    toCombine = combine(toCombine, each);
                }
            }
            combined.add(toCombine);
        }
        return combined;
    }

    private Function<RequestHandler, Equivalence.Wrapper<RequestHandler>> equivalenceAsKey() {
        return new
                Function<RequestHandler, Equivalence.Wrapper<RequestHandler>>() {
                    @Override
                    public Equivalence.Wrapper<RequestHandler> apply(RequestHandler input) {
                        return EQUIVALENCE.wrap(input);
                    }
                };
    }

    private RequestHandler combine(RequestHandler first, RequestHandler second) {
        return new CombinedRequestHandler(first, second);
    }
}
